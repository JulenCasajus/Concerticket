package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUIController implements Controller {

    private BlFacade businessLogic;
    @FXML
    private BorderPane mainWrapper;
    @FXML
    private Button queryConcertBtn;
    @FXML
    private Button queryPurchaseBtn;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button createConcertBtn;
    @FXML
    private Label lblUser;
    @FXML
    private ImageView image;
    Window createConcertWin, queryConcertWin, logInWin, signUpWin, queryPurchaseWin;

    static class Window {
        Controller controller;
        Parent ui;
    }

    public MainGUIController(BlFacade blFacade) {
        businessLogic = blFacade;
    }

    @FXML
    void logInClick(ActionEvent event) {
        System.out.println("LogIn");
        showScene("LogIn");
    }

    @FXML
    public void hideLogInButton() {
        logInButton.setVisible(false);
        signUpButton.setVisible(false);
        logOutButton.setVisible(true);
    }

    @FXML
    void signUpClick(ActionEvent event) {
        System.out.println("SignUp");
        showScene("SignUp");
    }

    @FXML
    void queryConcerts(ActionEvent event) {
        System.out.println("QueryConcerts");
        showScene("QueryConcerts");
    }

    @FXML
    void createConcert(ActionEvent event) {
        System.out.println("CreateConcert");
        showScene("CreateConcert");
    }

    @FXML
    void queryPurchaseClick(ActionEvent event) {
        System.out.println("QueryPurchase");
        showScene("QueryPurchase");
    }

    @FXML
    void initialize() {
        setAbleCreateConcertBtn(false);
        setAbleQueryPurchaseBtn(false);
        queryConcertWin = load("QueryConcert.fxml");
        createConcertWin = load("CreateConcert.fxml");
        logInWin = load("LogIn.fxml");
        signUpWin = load("SignUp.fxml");
        queryPurchaseWin = load("QueryPurchase.fxml");
        mainWrapper.setCenter(queryConcertWin.ui);

        queryConcertBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                queryConcerts(new ActionEvent());
            }
        });
        createConcertBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                createConcert(new ActionEvent());
            }
        });
        logInButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logInClick(new ActionEvent());
            }
        });
        signUpButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signUpClick(new ActionEvent());
            }
        });
        logOutButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logOutClick(new ActionEvent());
            }
        });
        queryPurchaseBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                queryPurchaseClick(new ActionEvent());
            }
        });
    }

    public void showScene(String scene) {
        switch (scene) {
            case "QueryConcert":
                mainWrapper.setCenter(queryConcertWin.ui);
                QueryConcertController queryConcertController = (QueryConcertController) queryConcertWin.controller;
                queryConcertController.setMainGUIController(this);
                break;
            case "CreateConcert":
                mainWrapper.setCenter(createConcertWin.ui);
                CreateConcertController createConcertController = (CreateConcertController) createConcertWin.controller;
                createConcertController.setMainGUIController(this);
                break;
            case "SignUp":
                mainWrapper.setCenter(signUpWin.ui);
                SignUpController SignUpController = (SignUpController) signUpWin.controller;
                SignUpController.setMainGUIController(this);
                break;
            case "LogIn":
                mainWrapper.setCenter(logInWin.ui);
                LogInController logInController = (LogInController) logInWin.controller;
                logInController.setMainGUIController(this);
                break;
            case "queryPurchase":
                mainWrapper.setCenter(queryPurchaseWin.ui);
                QueryPurchaseController queryPurchaseController = (QueryPurchaseController) queryPurchaseWin.controller;
                queryPurchaseController.setMainGUIController(this);
                break;
            default:
                break;
        }
        mainWrapper.getScene().getWindow().sizeToScene();
    }


    Window load(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource(fxml), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
            loader.setControllerFactory(controllerClass -> {
                try {
                    return controllerClass.getConstructor(BlFacade.class).newInstance(businessLogic);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            });
            Parent ui = loader.load();
            Controller controller = loader.getController();
            Window window = new Window();
            window.controller = controller;
            window.ui = ui;
            return window;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading FXML file: " + e.getMessage());
        }

    }

    @FXML
    void logOutClick(ActionEvent event) {
        logInButton.setVisible(true);
        signUpButton.setVisible(true);
        logOutButton.setVisible(false);
        lblUser.setText("");
        image.setVisible(false);
        businessLogic.setUserNull();
        setAbleCreateConcertBtn(false);
        setAbleQueryPurchaseBtn(false);
        showScene("LogIn");
        bookVisible(false);
        setNull();
    }

    void setAbleCreateConcertBtn(boolean able) {
        createConcertBtn.setDisable(!able);
    }

    void setAbleQueryPurchaseBtn(boolean able) {
        queryPurchaseBtn.setDisable(!able);
    }

    public void setVisible(boolean visible) {
        image.setVisible(visible);
    }

    public void setUsername(String username) {
        lblUser.setText(username);
    }

    @Override
    public void setNull() {
    }

    @Override
    public void bookVisible(boolean b) {
    }
}