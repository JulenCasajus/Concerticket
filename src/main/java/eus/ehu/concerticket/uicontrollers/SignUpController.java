package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

public class SignUpController implements Controller {
    public MainGUIController controller;
    public BlFacade businessLogic;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordField2;
    @FXML
    public RadioButton staff;
    @FXML
    private PasswordField staffCodeField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;
    @FXML
    private Label successful;

    String username, password, password2, email, staffCode;

    public SignUpController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void initialize() {
        signUpButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signUpClick();
            }
        });
        staff.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                staff.fire();
            }
        });
        logInButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logInClick();
            }
        });
    }

    @FXML
    public void logInClick() {
        controller.logInClick();
    }

    @FXML
    void signUpClick() {
        username = usernameField.getText();
        email = emailField.getText();
        password = passwordField.getText();
        password2 = passwordField.getText();
        staffCode = staffCodeField.getText();

        if(businessLogic.checkCredentials(username, email)) { //Username's and email's format correct
            if(businessLogic.checkPasswords(password, password2)) { //Password and password2 match
                if(!businessLogic.exists(username, email)) { //Account does not exist already
                    if (staff.isSelected() && staffCode.equals("STAFFCODE")) {
                        if (!businessLogic.createStaff(email, username, password)) {
                            successful.setText("Staff creation failed");
                        }
                        System.out.println("Staff created successfully");
                    } else {
                        if (!businessLogic.createClient(email, username, password)) {
                            successful.setText("Client creation failed");
                        }
                        System.out.println("Client created successfully");
                    }
                    this.setAllEmpty();
                    successful.setText("");
                    controller.showScene("LogIn");
                } else { //Account exists already
                    successful.setText("Error: User already exists");
                }
            } else { //Password and password2 do not match
                successful.setText("Error: Passwords do not match or are empty");
            }
        } else { //Username's and email's format incorrect
            successful.setText("Error: Username or email not valid");
        }
    }

    @FXML
    void applyClick() {
        staffCodeField.setDisable(!staff.isSelected());
    }

    @FXML
    public void setAllEmpty() {
        usernameField.clear();
        passwordField.clear();
        passwordField2.clear();
        emailField.clear();
        staffCodeField.clear();
    }

    @Override
    public void setNull() {
    }

    @Override
    public void buyVisible(boolean b) {
    }
}