package org.dam.controllers;

import org.dam.models.ClientsDAO;
import org.dam.models.PedidoDAO;
import org.dam.models.PedidoModel;
import org.dam.models.ProductsDAO;
import org.dam.services.WindowsServices;
import org.dam.views.TomarDialog;

import java.sql.SQLException;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TomarDialogController implements ActionListener {
    public static final String REHACER_FORM_DIALOG = "REHACER_FORM_DIALOG";
    public static final String FINALIZAR_FORM_DIALOG = "FINALIZAR_FORM_DIALOG";
    public static final String UPDATE_PEDIDO = "UPDATE_PEDIDO";


    public static final String EDIT_MODE = "EDIT_MODE";
    public static final String CREATE_MODE = "CREATE_MODE";


    private TomarDialog tomarDialog;
    private WindowsServices windowsServices;
    private ClientsDAO clientsDAO;
    private ProductsDAO productsDAO;
    private PedidoDAO pedidoDAO;

    public TomarDialogController(WindowsServices windowsServices, ProductsDAO productsDAO, ClientsDAO clientsDAO, PedidoDAO pedidoDAO) {
        this.productsDAO = productsDAO;
        this.clientsDAO = clientsDAO;
        this.pedidoDAO = pedidoDAO;
        this.windowsServices = windowsServices;
        this.tomarDialog = (TomarDialog) this.windowsServices.getWindow("TomarDialog");
        try {
            handleLoadClients();
            handleLoadProducts();
        } catch (Exception e) {
            tomarDialog.setAlert("Error al cargar clientes o productos",true);
            e.printStackTrace();
        }

    }

    private void handleCreatePedido() {
        try {
            boolean result = pedidoDAO.createPedido(tomarDialog.getPedidoModel());
            if (result) {
                tomarDialog.setAlert("Pedido creado con éxito",false);
                tomarDialog.clearData();
            }
        } catch (Exception e) {
            tomarDialog.setAlert("Error al crear pedido",true);
            e.printStackTrace();
        }
    }

    private void handleRehacerFormDialog() {
        tomarDialog.clearData();
    }


    private void handleCloseTomarDialog() {
        tomarDialog.closeWindow();
    }

    private void handleLoadClients() throws SQLException {
        try {
            tomarDialog.loadClients(clientsDAO.readClientes());
        } catch (Exception e) {
            throw new SQLException("Error al cargar clientes");
        }
    }

    private void handleLoadProducts() throws SQLException {
        try {
            tomarDialog.loadProducts(productsDAO.readProductos());
        } catch (Exception e) {
            throw new SQLException("Error al cargar productos");
        }
    }

    private void handleUpdatePedido(){
        try {
            if (pedidoDAO.updatePedido(tomarDialog.getPedidoModel())) {
                tomarDialog.setAlert("Pedido actualizado con éxito",false);
            }
        } catch (Exception e) {
            tomarDialog.setAlert("Error al actualizar pedido",true);
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case FINALIZAR_FORM_DIALOG:
                handleCreatePedido();
                break;
            case REHACER_FORM_DIALOG:
                handleRehacerFormDialog();
                break;
            case UPDATE_PEDIDO:
                handleUpdatePedido();
                break;
            default:
                System.out.println("Unknown action command: " + command);
                break;
        }
    }


}
