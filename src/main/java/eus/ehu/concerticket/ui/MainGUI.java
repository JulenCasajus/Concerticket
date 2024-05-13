package eus.ehu.concerticket.ui;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.uicontrollers.Controller;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUI {
    private BlFacade businessLogic;
    private Stage stage;
    private Scene scene;
    private Controller controller;

    public MainGUI(BlFacade bl) {
        Platform.startup(() -> {
            try {
                setBusinessLogic(bl);
                init(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public BlFacade getBusinessLogic() {
        return businessLogic;
    }

    public void setBusinessLogic(BlFacade afi) {
        businessLogic = afi;
    }

    public void init(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("MainGUI.fxml"), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
        loader.setControllerFactory(controllerClass -> {
            try{
                return controllerClass.getConstructor(BlFacade.class).newInstance(businessLogic);
            }catch(Exception e) {
                throw new RuntimeException(e);
            }
        });
        Scene scene = new Scene(loader.load());
        stage.setTitle("Concerticket BorderLayout");
        stage.setScene(scene);
        stage.show();
    }
}