package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

public class SignUpController implements Controller {

    @FXML
    private PasswordField PsswdFieldSingUp;
    @FXML
    private PasswordField PsswdFieldSingUp2;
    @FXML
    private Label Successful;
    @FXML
    private TextField UsrFieldSingUp;
    private MainGUI mainGUI;
    private MainGUIController controller;
    private BlFacade businessLogic;
    @FXML
    private TextField emailField;
    @FXML
    private ToggleGroup TravelerOrDriver;
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

    @Override
    public void setNull() {

    }

    @Override
    public void bookVisible(boolean b) {

    }

    public void initialize() {
        registerButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                SignUpBtnClick();
            }
        });
    }

    @FXML
    void LoginBtnClick(ActionEvent event) {
        System.out.println("Login");
        controller.showScene("Login");
    }

    @FXML
    void SignUpBtnClick() {

        String username = UsrFieldSingUp.getText();
        String password = PsswdFieldSingUp.getText();
        String password2 = PsswdFieldSingUp2.getText();
        String email = emailField.getText();

        RadioButton selectedToggle = (RadioButton) TravelerOrDriver.getSelectedToggle();
        String mode = selectedToggle.getText();

        /*

        if(businessLogic.checkCredentials(username, email)) { //Username's and email's format correct
            if(businessLogic.checkPasswords(password, password2)) { //Password and password2 match
                if(!businessLogic.exists(username, email)) { //Account does not exist already
                    if (mode.equals("Driver")) {
                        if (!businessLogic.createDriver(email, username, password)) {
                            Successful.setText("Driver creation failed");
                        }
                        System.out.println("Driver created successfully");
                    } else {
                        if (!businessLogic.createTraveler(email, username, password)) {
                            Successful.setText("Traveler creation failed");
                        }
                        System.out.println("Traveler created successfully");
                    }
                    this.SetAllEmpty();
                    Successful.setText("");
                    controller.showScene("Login");
                } else { //Account exists already
                    Successful.setText("Error: User already exists");
                }
            } else { //Password and password2 do not match
                Successful.setText("Error: Passwords do not match or are empty");
            }
        } else { //Username's and email's format incorrect
            Successful.setText("Error: Username or email not valid");
        }*/
    }

    @FXML
    void applyClick(ActionEvent event) {
    }

    @FXML
    public void SetAllEmpty() {
        UsrFieldSingUp.setText("");
        //UsrFieldSingUp.setPromptText("");
        PsswdFieldSingUp.setText("");
        PsswdFieldSingUp2.setText("");
        emailField.setText("");
    }
}