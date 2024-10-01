package org.dam.controllers;

import org.dam.services.WindowsServices;
import org.dam.views.AltaDialog;
import org.dam.views.ConsultasDialog;
import org.dam.views.MainFrame;
import org.dam.views.TomarDialog;
import static org.dam.controllers.TomarDialogController.CREATE_MODE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrameController implements ActionListener {
    public static final String SHOW_FORM_TOMAR = "SHOW_FORM_TOMAR";
    public static final String SHOW_FORM_BUSCAR = "SHOW_FORM_BUSCAR";
    public static final String SHOW_FORM_ALTA = "SHOW_FORM_ALTA";

    public static final String CLOSE_MAIN_FRAME = "CLOSE_MAIN_FRAME";

    private MainFrame mainFrame;
    private WindowsServices windowsServices;


    public MainFrameController( WindowsServices windowsServices) {
        this.windowsServices = windowsServices;
        this.mainFrame = (MainFrame) this.windowsServices.getWindow("MainFrame");
    }

    private void handleShowFormTomar(){
        TomarDialog tomarDialog = (TomarDialog) this.windowsServices.getWindow("TomarDialog");
        tomarDialog.setMode(CREATE_MODE);
        tomarDialog.showWindow();
    }
    private void handleShowFormBuscar(){
        ConsultasDialog consultasDialog = (ConsultasDialog) this.windowsServices.getWindow("ConsultasDialog");
        consultasDialog.showWindow();

    }
    private void handleShowFormAlta(){
        AltaDialog altaDialog = (AltaDialog) this.windowsServices.getWindow("AltaDialog");
        altaDialog.showWindow();
    }

    private void handleCloseMainFrame() {
        int response = JOptionPane.showConfirmDialog(null,
                "¿Quieres salir?");
        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null,
                    "Adios");
            mainFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Gracias por quedarte");
        }

    }


    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case SHOW_FORM_TOMAR:
                handleShowFormTomar();
                break;
            case CLOSE_MAIN_FRAME:
                handleCloseMainFrame();
                break;
            case SHOW_FORM_ALTA:
                handleShowFormAlta();
                break;
            case SHOW_FORM_BUSCAR:
                handleShowFormBuscar();
                break;

            default:
                System.out.println("Unknown action command: " + command);
                break;
        }
    }
}
