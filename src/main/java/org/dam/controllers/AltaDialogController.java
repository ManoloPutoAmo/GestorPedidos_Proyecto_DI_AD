package org.dam.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.dam.models.ClientsDAO;
import org.dam.services.WindowsServices;
import org.dam.views.AltaDialog;

public class AltaDialogController implements ActionListener{

    public static final String REHACER_ALTA_DIALOG= "REHACER_ALTA_DIALOG";
    public static final String REGISTER_ALTA_DIALOG= "REGISTER_ALTA_DIALOG";

    private AltaDialog altaDialog;
    private WindowsServices windowsServices;
    private ClientsDAO clientsDAO;


    public AltaDialogController( WindowsServices windowsServices, ClientsDAO clientsDAO) {
        this.windowsServices = windowsServices;
        this.clientsDAO = clientsDAO;
        this.altaDialog = (AltaDialog) this.windowsServices.getWindow("AltaDialog");
    }

    public void handleRehacerAltaDialog(){
        altaDialog.clearData();
    }

    public void handleCreateCliente(){
        try {
            boolean result = clientsDAO.createClient(altaDialog.getClienteModel());
            if (result) {
                altaDialog.setAlert("Cliente creado con éxito");
                altaDialog.clearData();
            }
        } catch (Exception e) {
            altaDialog.setAlert("Error al crear cliente");
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case REHACER_ALTA_DIALOG:
                handleRehacerAltaDialog();
                break;
            case REGISTER_ALTA_DIALOG:
                handleCreateCliente();
                break;
            

            default:
                System.out.println("Unknown action command: " + command);
                break;
        }
    }
}
