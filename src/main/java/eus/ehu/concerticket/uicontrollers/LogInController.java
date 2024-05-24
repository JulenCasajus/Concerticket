package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.exceptions.PasswordIncorrectException;
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
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Label lblError;
    @FXML
    private Label lblSuccess;

    public LogInController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void setMainGUIController(MainGUIController mainGUIController) {
        this.controller = mainGUIController;
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
        try {
            // Check if any required fields are null
            if (user.isEmpty() || password.isEmpty()) {
                throw new NullPointerException("Fill in all the fields");
            }
            if(businessLogic.exists(user)) {
                if(businessLogic.isUser(user, password)) {
                    businessLogic.setCurrentUser(user, password);
                    controller.setUsername(businessLogic.getCurrentUser().getUsername());
                    controller.setAbleCreateConcertBtn(businessLogic.getCurrentUser().isStaff());
                    QueryConcertController queryConcertController = (QueryConcertController) controller.queryConcertWin.controller;
                    queryConcertController.updateTable();
                    buyVisible(true);
                    controller.setAbleQueryPurchaseBtn(true);
                    controller.hideLogInButton(true);
                    passwordField.setText("");
                    userField.setText("");
                    lblSuccess.setText("");
                    controller.showScene("QueryConcert");
                } else {
                    throw new PasswordIncorrectException("Password is incorrect");
                }
            } else {
                lblSuccess.setText("");
                lblError.setText("User with that name or email does not exist");
            }
        } catch (NullPointerException | PasswordIncorrectException e) {
            lblSuccess.setText("");
            System.out.println("ERROR: " + e.getMessage());
            lblError.setText(e.getMessage());
        }
    }

    @FXML
    void signUpClick() {
        System.out.println("SignUp");
        controller.showScene("SignUp");
    }

    public void clear() {
        userField.clear();
        passwordField.clear();
        lblError.setText("");
        lblSuccess.setText("");
    }

    public void signUpSuccessful() {
        lblSuccess.setText("Sign up successful");
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