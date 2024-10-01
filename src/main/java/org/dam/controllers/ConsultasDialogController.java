package org.dam.controllers;

import static org.dam.controllers.TomarDialogController.EDIT_MODE;

import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import javax.swing.table.DefaultTableModel;

import org.dam.models.ClientsDAO;
import org.dam.models.ClientsModel;
import org.dam.models.PedidoDAO;
import org.dam.models.PedidoModel;
import org.dam.models.ProductsDAO;
import org.dam.models.ProductsModel;
import org.dam.services.WindowsServices;
import org.dam.views.ConsultasDialog;
import org.dam.views.TomarDialog;
import java.awt.event.KeyListener;


public class ConsultasDialogController implements ActionListener, WindowListener, MouseListener, ItemListener, KeyListener {

    public static final String SEARCH_ID_PEDIDO = "SEARCH_ID_PEDIDO";
    public static final String SEARCH_CLIENTE = "SEARCH_CLIENTE";
    public static final String SEARCH_DEPENDIENTE = "SEARCH_DEPENDIENTE";
    public static final String SEARCH_DATE = "SEARCH_DATE";
    public static final String SEARCH_PRODUCTO_NAME_AL = "SEARCH_PRODUCTO_NAME_AL";
    public static final String NEXT_PAGE= "NEXT_PAGE";
    public static final String BACK_PAGE= "BACK_PAGE";

    private ConsultasDialog consultasDialog;
    private WindowsServices windowsServices;
    private ClientsDAO clientsDAO;
    private ProductsDAO productosDAO;
    private PedidoDAO pedidoDAO;

    private int offset = 0;

    public ConsultasDialogController(WindowsServices windowsServices, ClientsDAO clientsDAO, ProductsDAO productosDAO, PedidoDAO pedidoDAO) {
        this.windowsServices = windowsServices;
        this.clientsDAO = clientsDAO;
        this.pedidoDAO = pedidoDAO;
        this.productosDAO = productosDAO;
        this.consultasDialog = (ConsultasDialog) this.windowsServices.getWindow("ConsultasDialog");
    }

    public void handleSearchCliente() {
        ArrayList<ClientsModel> clientes;
        try {
            clientes = clientsDAO.readClientes();
            consultasDialog.showClients(clientes);
        } catch (SQLException e) {
            consultasDialog.setAlert("Error al consultar los datos de los clientes",true);
            e.printStackTrace();
        }

    }

    public void hadleLoadProducts() {
        ArrayList<ProductsModel> productos;
        try {
            productos = productosDAO.readProductos();
            consultasDialog.showProductos(productos);
        } catch (SQLException e) {
            consultasDialog.setAlert("Error al consultar los datos de los pedidos",true);
            e.printStackTrace();
        }
    }

    public void handleSearchPedidos(int offset, int limit) {
        ArrayList<PedidoModel> pedidos;
        try {
            pedidos = pedidoDAO.readPedidos(offset, limit);
            consultasDialog.showPedidos(pedidos);
        } catch (SQLException e) {
            consultasDialog.setAlert("Error al consultar los datos de los pedidos",true);
            e.printStackTrace();
        }

    }

    public void handleSearchPedidoById() {
        int id = 0;
        try {
            id = Integer.parseInt(consultasDialog.getTxtPedido());
        } catch (Exception e) {
            handleSearchPedidos(0, consultasDialog.getCbItems());
            return;
        }
        System.out.println(id);
        ArrayList<PedidoModel> pedidos;
        try {
            pedidos = pedidoDAO.readPedidoById(id);
            consultasDialog.showPedidos(pedidos);
        } catch (SQLException e) {
            consultasDialog.setAlert("Error al consultar los datos de los pedidos",true);
            e.printStackTrace();
        }

    }

    public void handleSearchProductoByNameAndAlcohol() {
        String name = consultasDialog.getProductoName();
        boolean alcohol = consultasDialog.isAlcohol();
        ArrayList<ProductsModel> productos;
        try {
            productos = productosDAO.readProductoByNameAndAlcohol(name, alcohol);
            consultasDialog.showProductos(productos);
        } catch (SQLException e) {
            consultasDialog.setAlert("Error al consultar los datos de los pedidos",true);
            e.printStackTrace();
        }        
    }

    public void handleSearchPedidosByDates(){
        Date from = consultasDialog.getFromDate();
        Date to = consultasDialog.getToDate();
        ArrayList<PedidoModel> pedidos;

        try {
            pedidos = pedidoDAO.readPedidoByDates(from, to);
            consultasDialog.showPedidos(pedidos);
        } catch (SQLException e) {
            consultasDialog.setAlert("Error al consultar los datos de los pedidos",true);
            e.printStackTrace();
        }
    }

    private void setTotalElements() {
        try {
            consultasDialog.setTotalElements(pedidoDAO.readCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case SEARCH_ID_PEDIDO:
                handleSearchPedidoById();
                break;
            case SEARCH_PRODUCTO_NAME_AL:
                handleSearchProductoByNameAndAlcohol();
                break;
            case SEARCH_DATE:
                handleSearchPedidosByDates();
                break;
            case NEXT_PAGE:
                handleNextPage();
                break;
            case BACK_PAGE:
                handleBackPage();
                break;

            default:
                break;
        }
    }

    private void handleNextPage() {
        if (consultasDialog.getActual_page()+1 > consultasDialog.getTotal_pages()) {
            return;
        }
        offset += consultasDialog.getCbItems();
        consultasDialog.setActual_page(consultasDialog.getActual_page()+1);
        consultasDialog.setLabelPages();
        handleSearchPedidos(offset, consultasDialog.getCbItems());
    }

    private void handleBackPage() {
        if (consultasDialog.getActual_page() <= 1) {
            return;
        }
        offset -= consultasDialog.getCbItems();
        consultasDialog.setActual_page(consultasDialog.getActual_page()-1);
        consultasDialog.setLabelPages();

        handleSearchPedidos(offset, consultasDialog.getCbItems());
    }
   

    private void handleLoadCbItemsPages() {
        consultasDialog.loadCbItemsPages();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowActivated(WindowEvent e) {
        handleLoadCbItemsPages();
        handleSearchPedidos(0, 10);
        setTotalElements();
        consultasDialog.setActual_page(1);
        consultasDialog.setLabelPages();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
            try {
                PedidoModel pedido = pedidoDAO.readPedidoById(consultasDialog.getPedidoID()).get(0);
                TomarDialog tomarDialog = (TomarDialog) this.windowsServices.getWindow("TomarDialog");
                //consultasDialog.closeWindow();
                tomarDialog.setPedidoModel(pedido);
                tomarDialog.setMode(EDIT_MODE);
                tomarDialog.showWindow();
                
            } catch (Exception d) {
                d.printStackTrace();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println("Key pressed: " + e.getKeyCode());
        System.out.println(consultasDialog.getTxtPedido());
        if (e.getKeyCode() !=-1) {
            handleSearchPedidoById();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            consultasDialog.setActual_page(1);
            consultasDialog.setLabelPages();
            offset = 0;
            handleSearchPedidos(0, consultasDialog.getCbItems());
        }
    }
}
