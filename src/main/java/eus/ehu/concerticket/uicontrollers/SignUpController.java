package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.exceptions.PasswordIncorrectException;
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
    private Label lblError;

    String username, password, password2, email, staffCode;

    public SignUpController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void setMainGUIController(MainGUIController mainGUIController) {
        this.controller = mainGUIController;
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
        password2 = passwordField2.getText();
        staffCode = staffCodeField.getText();
        try {
            // Check if any required fields are null
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                throw new NullPointerException("Please fill in all the fields");
            }
            // Check if the account already exists
            else if (businessLogic.exists(username, email)) {
                throw new IllegalArgumentException("User already exists");
            }
            // Check if username and email formats are correct
            else if (!businessLogic.checkCredentials(username, email)) {
                throw new IllegalArgumentException("Username or email not valid");
            }
            // Check if passwords match
            else if (!businessLogic.checkPasswords(password, password2)) {
                throw new IllegalArgumentException("Passwords do not match");
            }
            // Check if staff checkbox is selected and staff code is valid
            else if (staff.isSelected() && !staffCode.equals("STAFF")) {
                throw new IllegalArgumentException("Invalid staff code");
            } else {
                if (businessLogic.createUser(username, email, password, staff.isSelected())) {
                    System.out.println("User created successfully");
                    controller.showScene("LogIn");
                    controller.signUpSuccessful();
                } else {
                    System.out.println("ERROR: User creation failed");
                    lblError.setText("Client creation failed");
                }
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
            lblError.setText(e.getMessage());
        }
    }

    @FXML
    void applyClick() {
        staffCodeField.setDisable(!staff.isSelected());
    }

    public void clear() {
        usernameField.clear();
        passwordField.clear();
        passwordField2.clear();
        emailField.clear();
        staffCodeField.clear();
        if(staff.isSelected()) staff.fire();
        lblError.setText("");
    }

    @Override
    public void signUpSuccessful() {
    }

    @Override
    public void setNull() {
    }

    @Override
    public void buyVisible(boolean b) {
    }
}