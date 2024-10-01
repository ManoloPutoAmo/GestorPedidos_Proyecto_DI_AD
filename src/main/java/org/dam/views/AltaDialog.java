package org.dam.views;

import static org.dam.controllers.TomarDialogController.REHACER_FORM_DIALOG;

import java.awt.event.ActionListener;
import static org.dam.controllers.AltaDialogController.REGISTER_ALTA_DIALOG;
import static org.dam.controllers.AltaDialogController.REHACER_ALTA_DIALOG;

import javax.swing.*;

import org.dam.models.ClientsModel;

public class AltaDialog extends JDialog implements InterfaceView {
    private JPanel altaPanel;
    private JTextField txt_telefono;
    private JTextField txt_nombre;
    private JTextField txt_edad;
    private JButton btn_rehacer;
    private JButton btn_registrar;
    private JLabel lb_alerta;

    public AltaDialog(JFrame frame, Boolean modal) {
        super(frame, modal);
        initWindow();
        initComponents();

    }

    @Override
    public void initWindow() {
        setContentPane(altaPanel);
        pack();
        setLocationRelativeTo(null);
        setCommands();
    }

    public void setAlert(String message) {
        lb_alerta.setText(message);
        lb_alerta.setVisible(true);
    }

    public void clearData() {
        txt_nombre.setText("");
        txt_edad.setText("");
        txt_telefono.setText("");
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
        btn_rehacer.setActionCommand(REHACER_ALTA_DIALOG);
        btn_registrar.setActionCommand(REGISTER_ALTA_DIALOG);

    }

    @Override
    public void addListener(ActionListener listener) {

        btn_rehacer.addActionListener(listener);
        btn_registrar.addActionListener(listener);
    }

    public ClientsModel getClienteModel() {
        ClientsModel clienteModel = new ClientsModel();
        clienteModel.setNombre(txt_nombre.getText());
        clienteModel.setEdad(Integer.parseInt(txt_edad.getText()));
        clienteModel.setTelefono(Integer.parseInt(txt_telefono.getText()));
        return clienteModel;
    }

    @Override
    public void initComponents() {

    }
    
}
