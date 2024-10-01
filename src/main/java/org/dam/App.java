package org.dam;

import com.formdev.flatlaf.FlatDarkLaf;
import org.dam.controllers.AltaDialogController;
import org.dam.controllers.ConsultasDialogController;
import org.dam.controllers.MainFrameController;
import org.dam.controllers.TomarDialogController;
import org.dam.models.ClientsDAO;
import org.dam.models.ClientsModel;
import org.dam.models.PedidoDAO;
import org.dam.models.ProductsDAO;
import org.dam.models.ProductsModel;
import org.dam.services.WindowsServices;
import org.dam.views.AltaDialog;
import org.dam.views.ConsultasDialog;
import org.dam.views.MainFrame;
import org.dam.views.TomarDialog;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        FlatDarkLaf.setup();

        WindowsServices windowsServices = new WindowsServices();

        //MODELS
        ClientsModel clientsModel = new ClientsModel();
        ProductsModel productsModel = new ProductsModel();

        //DAOs
        ProductsDAO productsDAO = new ProductsDAO();
        ClientsDAO clientsDAO = new ClientsDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        //VIEWS
        MainFrame mainFrame = new MainFrame();
        windowsServices.registerWindow("MainFrame", mainFrame);

        TomarDialog tomarDialog = new TomarDialog(mainFrame, true);
        windowsServices.registerWindow("TomarDialog", tomarDialog);

        AltaDialog altaDialog = new AltaDialog(mainFrame, true);
        windowsServices.registerWindow("AltaDialog", altaDialog);

        ConsultasDialog consultasDialog = new ConsultasDialog(mainFrame, true);
        windowsServices.registerWindow("ConsultasDialog", consultasDialog);

        //CONTROLLERS
        MainFrameController mainFrameController = new MainFrameController(windowsServices);
        TomarDialogController tomarDialogController = new TomarDialogController(windowsServices, productsDAO, clientsDAO, pedidoDAO);
        AltaDialogController altaDialogController = new AltaDialogController(windowsServices,clientsDAO);
        ConsultasDialogController consultasDialogController = new ConsultasDialogController(windowsServices, clientsDAO, productsDAO, pedidoDAO);

        mainFrame.addListener(mainFrameController);
        tomarDialog.addListener(tomarDialogController);
        altaDialog.addListener(altaDialogController);
        consultasDialog.addListener(consultasDialogController);

        mainFrame.showWindow();

    }
}
