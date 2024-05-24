package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.domain.Band;
import eus.ehu.concerticket.domain.Concert;
import eus.ehu.concerticket.domain.Place;
import eus.ehu.concerticket.exceptions.BandOrPlaceDoesntExist;
import eus.ehu.concerticket.exceptions.ConcertAlreadyExistException;
import eus.ehu.concerticket.exceptions.ConcertMustBeLaterThanTodayException;
import eus.ehu.concerticket.utils.Dates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.time.LocalDate;
import java.util.*;
import java.time.ZoneId;

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
    private TextField txtDiscount;
    @FXML
    private Button btnCreateConcert;
    @FXML
    private Label lblError;
    @FXML
    private Label lblSuccess;
    private MainGUIController controller;


    public CreateConcertController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void setMainGUIController(MainGUIController mainGUIController) {
        this.controller = mainGUIController;
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

        comboBand.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                comboBand.show();
                event.consume();
            }
        });
        comboPlace.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                comboPlace.show();
                event.consume();
            }
        });
        btnCreateConcert.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                createConcertClick();
                event.consume();
            }
        });
    }

    @FXML
    void createConcertClick() {
        try {
            String bandName = comboBand.getValue();
            String placeName = comboPlace.getValue();
            Band band = businessLogic.getBand(bandName);
            Place place = businessLogic.getPlace(placeName);
            LocalDate localDate = datePicker.getValue();

            if (bandName == null || placeName == null || localDate == null || txtNumberOfTickets.getText() == null || txtPrice.getText() == null) {
                throw new NullPointerException("Please fill in all the fields");
            }

            if(band == null) {
                throw new BandOrPlaceDoesntExist("Band does not exist");
            }

            if(place == null) {
                throw new BandOrPlaceDoesntExist("Place does not exist");
            }

            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            int maxTickets = Integer.parseInt(txtNumberOfTickets.getText());
            float price = Float.parseFloat(txtPrice.getText());
            float discount = Float.parseFloat(txtDiscount.getText());

            if(date.compareTo(new Date()) <= 0) {
                throw new ConcertMustBeLaterThanTodayException("Concert date must be later than today");
            }

            if (!businessLogic.getConcerts(band.getName(), place.getName(), date, 0).isEmpty()) {
                throw new ConcertAlreadyExistException("This concert already exists");
            }
            businessLogic.createConcert(band, place, date, maxTickets, price, discount);

            lblError.setText("");
            lblSuccess.setText("Concert created successfully");
        } catch (NullPointerException | ConcertAlreadyExistException | ConcertMustBeLaterThanTodayException | BandOrPlaceDoesntExist e) {
            System.out.println("ERROR: " + e.getMessage());
            lblError.setText(e.getMessage());
            lblSuccess.setText("");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid numbers for tickets and price");
            lblError.setText("Invalid numbers for tickets and price");
            lblSuccess.setText("");
        }
    }

    public void clear() {
        comboBand.setValue(null);
        comboPlace.setValue(null);
        datePicker.setValue(null);
        txtNumberOfTickets.clear();
        txtPrice.clear();
        txtDiscount.clear();
        lblError.setText("");
        lblSuccess.setText("");
    }

    @Override
    public void signUpSuccessful(){
    }

    @Override
    public void setNull() {
    }

    @Override
    public void buyVisible(boolean b) {
    }
}
