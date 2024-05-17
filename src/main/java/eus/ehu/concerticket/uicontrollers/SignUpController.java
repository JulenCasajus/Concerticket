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
        password2 = passwordField.getText();
        staffCode = staffCodeField.getText();
        try {
            // Check if any required fields are null
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                throw new NullPointerException("Please fill in all the fields");
            }
            // Check if username and email formats are correct
            else if (!businessLogic.checkCredentials(username, email)) {
                throw new IllegalArgumentException("Username or email not valid");
            }
            // Check if passwords match
            else if (!businessLogic.checkPasswords(password, password2)) {
                throw new IllegalArgumentException("Passwords do not match or are empty");
            }
            // Check if the account already exists
            else if (businessLogic.exists(username, email)) {
                throw new IllegalArgumentException("User already exists");
            }
            // Check if staff checkbox is selected and staff code is valid
            else if (staff.isSelected() && !staffCode.equals("STAFFCODE")) {
                throw new IllegalArgumentException("Invalid staff code");
            }
            // Create staff or client account based on selection
            else if (staff.isSelected()) {
                if (!businessLogic.createStaff(email, username, password)) {
                    lblError.setText("Staff creation failed");
                } else {
                    System.out.println("Staff created successfully");
                }
            } else {
                if (!businessLogic.createClient(email, username, password)) {
                    lblError.setText("Client creation failed");
                } else {
                    System.out.println("Client created successfully");
                }
            }
            this.setAllEmpty();
            lblError.setText("");
            controller.showScene("LogIn");
        } catch (NullPointerException | IllegalArgumentException e) {
            lblError.setText("ERROR: " + e.getMessage());
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