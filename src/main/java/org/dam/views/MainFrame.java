package org.dam.views;

import javax.swing.*;
import java.awt.event.ActionListener;

import static org.dam.controllers.MainFrameController.*;

public class MainFrame extends JFrame implements InterfaceView {
    private JPanel mainPanel;
    private JLabel txt_label;
    private JButton btn_tomar;
    private JButton btn_buscar;
    private JButton btn_alta;
    private JButton btn_salir;

    public MainFrame() {
        initWindow();
    }

    public void addListener(ActionListener listener) {
        btn_tomar.addActionListener(listener);
        btn_buscar.addActionListener(listener);
        btn_alta.addActionListener(listener);
        btn_salir.addActionListener(listener);
    }

    @Override
    public void initWindow() {
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCommands();
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
        btn_tomar.setActionCommand(SHOW_FORM_TOMAR);
        btn_buscar.setActionCommand(SHOW_FORM_BUSCAR);
        btn_alta.setActionCommand(SHOW_FORM_ALTA);
        btn_salir.setActionCommand(CLOSE_MAIN_FRAME);

    }

    @Override
    public void initComponents() {

    }
}
