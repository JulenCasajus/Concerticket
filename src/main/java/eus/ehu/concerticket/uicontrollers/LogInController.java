package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class LogInController implements Controller {
    public MainGUIController controller;
    public BlFacade businessLogic;
    @FXML
    private Label done;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField userField;

    public LogInController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void initialize() {
        logInButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logInClick();
                event.consume();
            }
        });
        signUpButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signUpClick();
                event.consume();
            }
        });
    }

    @FXML
    void logInClick() {
        String user = userField.getText();
        String password = passwordField.getText();
        if (businessLogic.isClient(user,password)) {
            businessLogic.setCurrentClient(user, password);
            controller.setUsername(businessLogic.getCurrentClient().getUsername());
            controller.setAbleCreateConcertBtn(false);
            controller.setAbleQueryPurchaseBtn(true);
            controller.showScene("CreateRide");
            controller.hideLogInButton();
            buyVisible(false);
        } else if (businessLogic.isStaff(user,password)) {
            businessLogic.setCurrentStaff(user, password);
            controller.setUsername(businessLogic.getCurrentStaff().getUsername());
            controller.showScene("QueryRides");
            controller.hideLogInButton();
            controller.setAbleCreateConcertBtn(true);
            controller.setAbleQueryPurchaseBtn(true);
            buyVisible(true);
        } else {
            done.setText("Error");
        }
        done.setText("");
        passwordField.setText("");
        userField.setText("");
        controller.setVisible(true);
    }
    @FXML
    void signUpClick() {
        controller.signUpClick();
    }

    @Override
    public void setNull() {
        controller.setNull();
    }

    @Override
    public void buyVisible(boolean b) {
        controller.buyVisible(b);
    }
}