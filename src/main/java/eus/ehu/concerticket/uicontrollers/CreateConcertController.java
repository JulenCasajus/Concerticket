package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.domain.Band;
import eus.ehu.concerticket.domain.Place;
import eus.ehu.concerticket.exceptions.ConcertAlreadyExistException;
import eus.ehu.concerticket.exceptions.ConcertMustBeLaterThanTodayException;
import eus.ehu.concerticket.utils.Dates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateConcertController implements Controller {
    BlFacade businessLogic;
    @FXML
    public ComboBox<String> comboBand;
    @FXML
    public ComboBox<String> comboPlace;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtNumberOfTickets;
    @FXML
    private TextField txtPrice;
    @FXML
    private Button btnCreateConcert;
    @FXML
    private Label lblError;



    public CreateConcertController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {
        btnCreateConcert.setOnAction(event -> createConcertClick());

        ObservableList<String> bands = FXCollections.observableArrayList(new ArrayList<>());
        ObservableList<String> places = FXCollections.observableArrayList(new ArrayList<>());

        bands.setAll(businessLogic.getBands());
        places.setAll(businessLogic.getPlaces());

        comboBand.setItems(bands);
        comboPlace.setItems(places);

        datePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            this.setStyle("-fx-background-color: pink");
                        }
                    }
                };
            }
        });
    }

    @FXML
    void createConcertClick() {

        clearErrorLabels();

        String bandName = comboBand.getEditor().getText();
        String placeName = comboPlace.getEditor().getText();

        Band band = businessLogic.getBand(bandName);
        Place place = businessLogic.getPlace(placeName);
        String errors = field_Errors();

        if (errors != null) {
            displayMessage(errors, "danger");
        } else {
            int tickets = Integer.parseInt(txtNumberOfTickets.getText());
            float price = Float.parseFloat(txtPrice.getText());
            businessLogic.createConcert( band, place, Dates.convertToDate(datePicker.getValue()), price, 0, tickets, 0);
            displayMessage(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"), "success");
        }
    }

    private void clearErrorLabels() {
        lblError.setText("");
        lblError.getStyleClass().clear();
    }

    private String field_Errors() {
        try {
            if ((comboBand.getEditor().getText().isEmpty()) || (comboPlace.getEditor().getText().isEmpty()) || (txtNumberOfTickets.getText().isEmpty()) || (txtPrice.getText().isEmpty()))
                return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
            else {

                // trigger an exception if the introduced string is not a number
                int inputSeats = Integer.parseInt(txtNumberOfTickets.getText());

                if (inputSeats <= 0) {
                    return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.SeatsMustBeGreaterThan0");
                } else {
                    float price = Float.parseFloat(txtPrice.getText());
                    if (price <= 0)
                        return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.PriceMustBeGreaterThan0");
                    else
                        return null;
                }
            }
        } catch (NumberFormatException e1) {
            return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorNumber");
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    void displayMessage(String message, String label){
        lblError.getStyleClass().clear();
        lblError.getStyleClass().setAll("lbl", "lbl-"+label);
        lblError.setText(message);
    }

    @Override
    public void setNull() {
    }

    @Override
    public void buyVisible(boolean b) {
    }
}
