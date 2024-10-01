package org.dam.views;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.util.*;
import java.time.LocalDate;

import org.dam.models.ClientsModel;
import org.dam.models.PedidoModel;
import org.dam.models.ProductsModel;
import java.awt.Color;

import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import static org.dam.controllers.TomarDialogController.EDIT_MODE;
import static org.dam.controllers.TomarDialogController.FINALIZAR_FORM_DIALOG;
import static org.dam.controllers.TomarDialogController.REHACER_FORM_DIALOG;
import static org.dam.controllers.TomarDialogController.UPDATE_PEDIDO;;


public class TomarDialog extends JDialog implements InterfaceView {
    private JPanel tomarPanel;
    private JLabel lb_tomar;
    private JTextField tx_atendido;
    private JComboBox cb_cliente;
    private JComboBox cb_producto;
    private JButton btn_rehacer;
    private JButton btn_finalizar;
    private JSlider sd_cantidad;
    private JLabel lb_atendido;
    private JLabel lb_fecha;
    private JLabel lb_cliente;
    private JLabel lb_producto;
    private JLabel lb_cantidad;
    private JLabel lb_total;
    private JLabel lb_total_pagar;
    private DatePicker dp_fecha;
    private JLabel lb_alerta;

    private int id_pedido;

    public TomarDialog(JFrame frame, Boolean modal) {
        super(frame, modal);
        initWindow();
        initComponents();
    }

    @Override
    public void initWindow() {
        setContentPane(tomarPanel);
        pack();
        setLocationRelativeTo(null);
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

    public void clearData() {
        tx_atendido.setText("");
        cb_cliente.setSelectedIndex(0);
        cb_producto.setSelectedIndex(0);
        sd_cantidad.setValue(0);
        dp_fecha.setDate(null);
        lb_total_pagar.setText("0.00€");
    }

    @Override
    public void setCommands() {
        btn_finalizar.setActionCommand(FINALIZAR_FORM_DIALOG);
        btn_rehacer.setActionCommand(REHACER_FORM_DIALOG);
    }
    @Override
    public void addListener(ActionListener listener) {
        btn_finalizar.addActionListener(listener);
        btn_rehacer.addActionListener(listener);
        
    }

    public void updatePrice() {
        sd_cantidad.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lb_total_pagar.setText((String.format("%.2f",((ProductsModel)cb_producto.getSelectedItem()).getPrecio() * sd_cantidad.getValue()))+"€");
            }
        });

    }
    

    public void loadClients(ArrayList<ClientsModel> clientes) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(clientes.toArray());
        cb_cliente.setModel(model);
    }

    public void loadProducts(ArrayList<ProductsModel> productos) {
        DefaultComboBoxModel model = new DefaultComboBoxModel<>(productos.toArray());
        cb_producto.setModel(model);
    }

    public void setAlert(String message, boolean error) {
        if (error) {
            lb_alerta.setForeground(Color.RED);
        } else {
            lb_alerta.setForeground(Color.GREEN);
        }
        lb_alerta.setText(message);
        lb_alerta.setVisible(true);
    }

    @Override
    public void initComponents() {
        lb_alerta.setVisible(false);
        sd_cantidad.setValue(0);
        updatePrice();
    }

    public PedidoModel getPedidoModel() {
        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setCantidad(sd_cantidad.getValue());
        pedidoModel.setCliente_id(((ClientsModel) cb_cliente.getSelectedItem()).getId());
        pedidoModel.setDependiente(tx_atendido.getText());
        pedidoModel.setProducto_id(((ProductsModel) cb_producto.getSelectedItem()).getId());
        String dinero = lb_total_pagar.getText();
        pedidoModel.setPrecio_total(Double.parseDouble(dinero.substring(0, dinero.length()-1).replace(",",".")));
        pedidoModel.setFecha(dp_fecha.getDate());
        if (pedidoModel.getFecha() == null) {
            pedidoModel.setFecha(LocalDate.now());
        }
        pedidoModel.setId(id_pedido);

        return pedidoModel;
    }

    public void setPedidoModel(PedidoModel pedidoModel) {
        this.id_pedido = pedidoModel.getId();
        tx_atendido.setText(pedidoModel.getDependiente());
        cb_cliente.setSelectedItem(pedidoModel.getCliente_id());
        cb_producto.setSelectedItem(pedidoModel.getProducto_id());
        sd_cantidad.setValue(pedidoModel.getCantidad());        
        dp_fecha.setDate(pedidoModel.getFecha());
        lb_total_pagar.setText(String.format("%.2f",pedidoModel.getPrecio_total())+"€");
    }

    public void setMode(String mode) {        
        if (mode.equals(EDIT_MODE)) {
            btn_finalizar.setText("EDITAR");
            btn_finalizar.setActionCommand(UPDATE_PEDIDO);

        }else if (mode.equals("CREATE_MODE")) {
            btn_finalizar.setText("ENVIAR");
            btn_finalizar.setActionCommand(FINALIZAR_FORM_DIALOG);
            
        }
    }
    
}
