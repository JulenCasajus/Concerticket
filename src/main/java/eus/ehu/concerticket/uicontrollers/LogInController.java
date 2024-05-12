package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class LogInController implements Controller {
    private MainGUI mainGUI;
    private MainGUIController controller;
    private BlFacade businessLogic;
    @FXML
    private SignUpController signUpController;
    @FXML
    private Label done;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpBtn;
    @FXML
    private TextField userField;

    public LogInController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void setMainGUIController(MainGUIController controller){
        this.controller = controller;
    }

    public void initialize() {
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                logInButton.requestFocus();
                event.consume(); // Evitar que el tabulador mueva el foco a otro componente
            }
        });
        logInButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logInBtnClick(new ActionEvent());
                event.consume(); // Evitar que la tecla Enter realice otra acción
            }
        });
        signUpBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signUpBtnClick(new ActionEvent());
                event.consume(); // Evitar que la tecla Enter realice otra acción
            }
        });
    }

    @FXML
    void logInBtnClick(ActionEvent event) { //LOG IN
        String user = userField.getText();
        String password = passwordField.getText();
        if (businessLogic.isClient(user,password)) {
            businessLogic.setCurrentClient(user, password);
            controller.setUsername(businessLogic.getCurrentClient().getUsername());
            controller.setAbleCreateConcertBtn(false);
            controller.setAbleQueryPurchaseBtn(true);
            controller.showScene("CreateRide");
            controller.hideLogInButton();
            bookVisible(false);
        } else if (businessLogic.isStaff(user,password)) {
            businessLogic.setCurrentStaff(user, password);
            controller.setUsername(businessLogic.getCurrentStaff().getUsername());
            controller.showScene("QueryRides");
            controller.hideLogInButton();
            controller.setAbleCreateConcertBtn(true);
            controller.setAbleQueryPurchaseBtn(true);
            bookVisible(true);
        } else {
            done.setText("Error");
        }
        done.setText("");
        passwordField.setText("");
        userField.setText("");
        controller.setVisible(true);
    }
    @FXML
    void signUpBtnClick(ActionEvent event) {
        System.out.println("SignUp");
        controller.showScene("SignUp");
    }

    @Override
    public void setNull() {
        controller.setNull();
    }

    @Override
    public void bookVisible(boolean b) {
        controller.bookVisible(b);
    }
}