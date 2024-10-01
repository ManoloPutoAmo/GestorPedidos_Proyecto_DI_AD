package org.dam.views;

import com.github.lgooddatepicker.components.DatePicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.Color;

import java.sql.Date;

import org.dam.models.ClientsModel;
import org.dam.models.PedidoModel;
import org.dam.models.ProductsModel;

import static org.dam.controllers.ConsultasDialogController.*;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ConsultasDialog extends JDialog implements InterfaceView {
    private JTable tbl_queries;
    private JPanel panel1;
    private JTextField txt_pedido;
    private JTextField txt_cliente;
    private JLabel lb_alert;
    private JLabel lb_pedido;
    private JLabel lb_cliente;
    private JLabel lb_dependiente;
    private JButton btn_date;
    private JButton btn_pedido;
    private JButton btn_cliente;
    private DatePicker dp_from;
    private DatePicker dp_to;
    private JLabel lb_producto;
    private JCheckBox chb_alcohol;
    private JButton btn_producto;
    private JTextField txt_producto;
    private JButton btn_next;
    private JButton btn_back;
    private JComboBox cb_items;
    private JLabel lbl_pages;

    private int totalElements;
    private int actual_page;
    private int total_pages;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public ConsultasDialog(JFrame frame, Boolean modal) {
        super(frame, modal);
        initWindow();
        initComponents();
    }

    @Override
    public void initWindow() {
        setContentPane(panel1);
        pack();
        setLocationRelativeTo(null);
        setCommands();
    }

    //! HACER ESTO
    public void loadComboProducts(){
        ArrayList<ProductsModel> productos;
    }

    public void showClients(ArrayList<ClientsModel> clientes) {
        try {
            String[] indexClients = new String[] { "ID", "Nombre", "Edad", "Telefono" };
            DefaultTableModel model = new DefaultTableModel(indexClients, 0);
            for (ClientsModel cliente : clientes) {
                model.addRow(cliente.toArray());
            }
            tbl_queries.setModel(model);
        } catch (Exception e) {
            setAlert("Error al consultar los datos de los clientes", true);
            e.printStackTrace();
        }

    }

    public void showPedidos(ArrayList<PedidoModel> pedidos) {
        try {
            String[] indexProductos = new String[] { "ID", "ClienteID", "ProductoID", "Cantidad", "Dependiente", "Precio", "Fecha" };
            DefaultTableModel model = new DefaultTableModel(indexProductos, 0);
            for (PedidoModel pedido : pedidos) {
                model.addRow(pedido.toArray());
            }
            tbl_queries.setModel(model);
        } catch (Exception e) {
            setAlert("Error al consultar los datos de los clientes",true);
            e.printStackTrace();
        }

    }

    public void showProductos(ArrayList<ProductsModel> productos) {
        try {
            String[] indexProductos = new String[] { "ID", "Nombre", "Precio" };
            DefaultTableModel model = new DefaultTableModel(indexProductos, 0);
            for (ProductsModel producto : productos) {
                model.addRow(producto.toArray());
            }
            tbl_queries.setModel(model);
        } catch (Exception e) {
            setAlert("Error al consultar los datos de los clientes",true);
            e.printStackTrace();
        }

    }

    public int getActual_page() {
        return actual_page;
    }

    public void setActual_page(int actual_page) {
        this.actual_page = actual_page;
    }

    public String getTxtPedido() {
        return txt_pedido.getText();
    }

    public Date getFromDate() {
        return Date.valueOf(dp_from.getDate());
    }

    public Date getToDate() {
        return Date.valueOf(dp_to.getDate());
    }

    public String getProductoName() {
        return txt_producto.getText();
    }

    public boolean isAlcohol() {
        return chb_alcohol.isSelected();
    }

    public void setAlert(String message, boolean error) {
        if (error) {
            lb_alert.setForeground(Color.RED);
        } else {
            lb_alert.setForeground(Color.GREEN);
        }
        lb_alert.setText(message);
        lb_alert.setVisible(true);
    }

    public void removeFirstCb(){
        
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setLabelPages(){
        setTotal_pages((int) Math.ceil((double)getTotalElements()/(int)cb_items.getSelectedItem()));
        lbl_pages.setText(getActual_page()+"/"+getTotal_pages());

        //lbl_pages.setText("Página: "+acutual_page+" de "+Math.ceil(getTotalElements()/(int)cb_items.getSelectedItem()));
    }
    
    public void loadCbItemsPages(){
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (int i = 1; i <= 6; i++) {
            model.addElement(i*10);
        }
        cb_items.setModel(model);
    }

    public int getPedidoID(){
        TableModel model = tbl_queries.getModel();
        return Integer.parseInt(model.getValueAt(tbl_queries.getSelectedRow(), 0).toString());
    }

    public int getCbItems(){
        return Integer.parseInt(cb_items.getSelectedItem().toString());
    }

    @Override
    public void showWindow() {
        setVisible(true);
    }

    @Override
    public void closeWindow() {
        dispose();
    }

    @Override
    public void setCommands() {
        btn_pedido.setActionCommand(SEARCH_ID_PEDIDO);
        btn_producto.setActionCommand(SEARCH_PRODUCTO_NAME_AL);
        btn_date.setActionCommand(SEARCH_DATE);
        btn_next.setActionCommand(NEXT_PAGE);
        btn_back.setActionCommand(BACK_PAGE);
    }

    @Override
    public void addListener(ActionListener listener) {
        addWindowListener((WindowListener) listener);
        btn_pedido.addActionListener(listener);
        btn_producto.addActionListener(listener);
        btn_date.addActionListener(listener);
        tbl_queries.addMouseListener((MouseListener) listener);
        txt_pedido.addKeyListener((KeyListener) listener);
        cb_items.addItemListener((ItemListener) listener);
        btn_next.addActionListener(listener);
        btn_back.addActionListener(listener);
    }

    @Override
    public void initComponents() {
        Icon icon;
        try {
            System.out.println(System.getProperty("user.dir"));
            icon = new ImageIcon(ImageIO.read(getClass().getResource("org/dam/res/lupa.png")).getScaledInstance(20, 20, DO_NOTHING_ON_CLOSE));
            btn_cliente.setIcon(icon);
            btn_date.setIcon(icon);
            btn_pedido.setIcon(icon);
            btn_producto.setIcon(icon);
            btn_cliente.setText("");
            btn_date.setText("");
            btn_pedido.setText("");
            btn_producto.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            btn_cliente.setText("BUSCAR");
            btn_date.setText("BUSCAR");
            btn_pedido.setText("BUSCAR");
            btn_producto.setText("BUSCAR");
            icon = null;
        }



    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
