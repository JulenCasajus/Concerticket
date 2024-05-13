package eus.ehu.concerticket.ui;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.businessLogic.BlFacadeImplementation;
import eus.ehu.concerticket.configuration.Config;

import java.util.Locale;

public class ApplicationLauncher {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        Config config = Config.getInstance();

        Locale locale = new Locale.Builder().setLanguage(config.getLocale()).build();
        Locale.setDefault(locale);
        System.out.println("Locale: " + Locale.getDefault());

        BlFacade businessLogic;

        try {
            if (config.isBusinessLogicLocal()) {
                businessLogic = new BlFacadeImplementation();
                new MainGUI(businessLogic);
            }
        } catch (Exception e) {
            System.err.println("Error in ApplicationLauncher: " + e);
        }
    }
}