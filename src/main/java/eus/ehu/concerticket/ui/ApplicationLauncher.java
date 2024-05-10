package eus.ehu.concerticket.ui;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.businessLogic.BlFacadeImplementation;
import eus.ehu.concerticket.configuration.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public class ApplicationLauncher {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        Config config = Config.getInstance();

        Locale.setDefault(new Locale(config.getLocale()));
        System.out.println("Locale: " + Locale.getDefault());

        BlFacade businessLogic;

        try {
            if (config.isBusinessLogicLocal()) {
                businessLogic = new BlFacadeImplementation();
                new MainGUI(businessLogic);
            }
        }
        catch (Exception e) {
            System.err.println("Error in ApplicationLauncher: " + e);
        }
    }
}