package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.ui.MainGUI;
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

    private final BlFacade businessLogic;
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
    void logInClick() {
        System.out.println("LogIn");
        showScene("LogIn");
    }

    @FXML
    public void hideLogInButton(boolean b) {
        logInButton.setVisible(!b);
        signUpButton.setVisible(!b);
        logOutButton.setVisible(b);
        image.setVisible(b);
        lblUser.setVisible(b);
    }

    @FXML
    void signUpClick() {
        System.out.println("SignUp");
        showScene("SignUp");
    }

    @FXML
    void logOutClick() {
        logInButton.setVisible(true);
        signUpButton.setVisible(true);
        logOutButton.setVisible(false);
        lblUser.setText("");
        image.setVisible(false);
        businessLogic.setUserNull();
        setAbleCreateConcertBtn(false);
        setAbleQueryPurchaseBtn(false);
        showScene("LogIn");
        buyVisible(false);
        setNull();
        queryConcertWin.controller.setNull();
    }

    @FXML
    void queryConcertClick() {
        System.out.println("QueryConcert");
        showScene("QueryConcert");
    }

    @FXML
    void createConcertClick() {
        System.out.println("CreateConcert");
        showScene("CreateConcert");
    }

    @FXML
    void queryPurchaseClick() {
        System.out.println("QueryPurchase");
        showScene("QueryPurchase");
    }

    @FXML
    void initialize() {
        setAbleCreateConcertBtn(false);
        setAbleQueryPurchaseBtn(false);
        logInWin = load("LogIn.fxml");
        signUpWin = load("SignUp.fxml");
        queryConcertWin = load("QueryConcert.fxml");
        queryPurchaseWin = load("QueryPurchase.fxml");
        createConcertWin = load("CreateConcert.fxml");
        mainWrapper.setCenter(queryConcertWin.ui);

        logInButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logInClick();
            }
        });
        signUpButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signUpClick();
            }
        });
        logOutButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logOutClick();
            }
        });
        queryConcertBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                queryConcertClick();
            }
        });
        createConcertBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                createConcertClick();
            }
        });
        queryPurchaseBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                queryPurchaseClick();
            }
        });
    }

    public void showScene(String scene) {
        switch (scene) {
            case "QueryConcert":
                mainWrapper.setCenter(queryConcertWin.ui);
                QueryConcertController queryConcertController = (QueryConcertController) queryConcertWin.controller;
                queryConcertController.clear();
                queryConcertController.setMainGUIController(this);
                break;
            case "CreateConcert":
                mainWrapper.setCenter(createConcertWin.ui);
                CreateConcertController createConcertController = (CreateConcertController) createConcertWin.controller;
                createConcertController.clear();
                createConcertController.setMainGUIController(this);
                break;
            case "SignUp":
                mainWrapper.setCenter(signUpWin.ui);
                SignUpController signUpController = (SignUpController) signUpWin.controller;
                signUpController.clear();
                signUpController.setMainGUIController(this);
                break;
            case "LogIn":
                mainWrapper.setCenter(logInWin.ui);
                LogInController logInController = (LogInController) logInWin.controller;
                logInController.clear();
                logInController.setMainGUIController(this);
                break;
            case "QueryPurchase":
                mainWrapper.setCenter(queryPurchaseWin.ui);
                QueryPurchaseController queryPurchaseController = (QueryPurchaseController) queryPurchaseWin.controller;
                queryPurchaseController.updateTable();
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

    void setAbleCreateConcertBtn(boolean able) {
        createConcertBtn.setVisible(able);
    }

    void setAbleQueryPurchaseBtn(boolean able) {
        queryPurchaseBtn.setDisable(!able);
    }

    public void setUsername(String username) {
        lblUser.setText(username);
    }

    public void signUpSuccessful() {
        logInWin.controller.signUpSuccessful();
    }

    @Override
    public void setNull() {
    }

    @Override
    public void buyVisible(boolean b) {
        queryConcertWin.controller.buyVisible(b);
    }
}