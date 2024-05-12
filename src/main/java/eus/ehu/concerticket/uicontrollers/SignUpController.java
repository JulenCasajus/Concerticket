package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

public class SignUpController implements Controller {

    @FXML
    private PasswordField passwordFieldSingUp;
    @FXML
    private PasswordField passwordFieldSingUp2;
    @FXML
    private Label successful;
    @FXML
    private TextField userFieldSingUp;
    private MainGUI mainGUI;
    private MainGUIController controller;
    private BlFacade businessLogic;
    @FXML
    public RadioButton staff;
    @FXML
    private TextField emailField;
    @FXML
    private Button registerButton;

    //constructor
    public SignUpController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void setMainGUIController(MainGUIController controller) {
        this.controller = controller;
    }

    public void initialize() {
        registerButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signUpBtnClick();
            }
        });
    }

    @FXML
    public void logInBtnClick(ActionEvent event) {
        System.out.println("LogIn");
        controller.showScene("LogIn");
    }

    @FXML
    void signUpBtnClick() {

        String username = userFieldSingUp.getText();
        String password = passwordFieldSingUp.getText();
        String password2 = passwordFieldSingUp2.getText();
        String email = emailField.getText();

        //RadioButton selectedToggle = (RadioButton) getSelectedToggle();
        //String mode = selectedToggle.getText();

        if(businessLogic.checkCredentials(username, email)) { //Username's and email's format correct
            if(businessLogic.checkPasswords(password, password2)) { //Password and password2 match
                if(!businessLogic.exists(username, email)) { //Account does not exist already
                    /*if (mode.equals("Staff")) {
                        if (!businessLogic.createStaff(email, username, password)) {
                            successful.setText("Staff creation failed");
                        }
                        System.out.println("Staff created successfully");
                    } else {*/
                        if (!businessLogic.createClient(email, username, password)) {
                            successful.setText("Client creation failed");
                        }
                        System.out.println("Client created successfully");
                    //}
                    this.SetAllEmpty();
                    successful.setText("");
                    controller.showScene("Login");
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
    void applyClick(ActionEvent event) {
    }

    @FXML
    public void SetAllEmpty() {
        userFieldSingUp.setText("");
        passwordFieldSingUp.setText("");
        passwordFieldSingUp2.setText("");
        emailField.setText("");
    }

    @Override
    public void setNull() {
    }

    @Override
    public void bookVisible(boolean b) {
    }
}