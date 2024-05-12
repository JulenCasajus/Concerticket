package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.domain.Concert;
import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.domain.Concert;
import eus.ehu.concerticket.ui.MainGUI;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class QueryConcertController implements Controller {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button btnClose;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableColumn<Concert, String> qc1;
    @FXML
    private TableColumn<Concert, Integer> qc2;
    @FXML
    private TableColumn<Concert, Float> qc3;
    @FXML
    private TableColumn<Concert, String> qc4;
    @FXML
    private ComboBox<String> comboArrivalCity;
    @FXML
    private ComboBox<String> comboDepartCity;
    @FXML
    private ComboBox<Integer> comboSeats;
    @FXML
    private Button btnBook;
    @FXML
    private Label bookTxt;
    @FXML
    private TableView<Concert> tblRides;
    private MainGUI mainGUI;
    //private List<LocalDate> datesWithBooking = new ArrayList<>();
    private BlFacade businessLogic;
    private MainGUIController controller;

    public QueryConcertController(BlFacade bl) {
        businessLogic = bl;
    }

    public void setMainGUIController(MainGUIController controller) {
        this.controller = controller;
    }

    @FXML
    public void initialize() {
        /*
        ObservableList<String> departureCities = FXCollections.observableArrayList(new ArrayList<>());
        departureCities.setAll(businessLogic.getDepartCities());
        ObservableList<String> arrivalCities = FXCollections.observableArrayList(new ArrayList<>());
        comboDepartCity.setItems(departureCities);
        comboArrivalCity.setItems(arrivalCities);

        // Update DatePicker cells when Arrival city changes
        comboArrivalCity.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(comboSeats.getValue() != null)// && datePicker.getValue() != null)
                businessLogic.updateDatePickerCellFactory(datePicker, comboDepartCity.getValue(), newVal, comboSeats.getValue());
            updateTable();
        });

        // Update DatePicker cells when Seats value changes
        comboSeats.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) //comboArrivalCity.getValue() != null && datePicker.getValue() != null) {
                businessLogic.updateDatePickerCellFactory(datePicker, comboDepartCity.getValue(), comboArrivalCity.getValue(), newVal);
            updateTable();
        });

        // when the user selects a departure city, update the arrival cities
        comboDepartCity.setOnAction(actionEvent -> {
            arrivalCities.clear();
            arrivalCities.setAll(businessLogic.getArrivalCities(comboDepartCity.getValue()));
        });

        // a date has been chosen, update the combobox of Rides
        datePicker.setOnAction(actionEvent -> {
            //if(comboArrivalCity.getValue() != null && comboSeats.getValue() != null) {
                updateTable();
        });

        comboSeats.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9));

        qc1.setCellValueFactory(data -> {
            Ride ride = data.getValue();
            return new SimpleStringProperty(ride != null ? ride.toString2() : "<no name>");
        });

        qc2.setCellValueFactory(data -> {
            Ride ride = data.getValue();
            return new SimpleIntegerProperty(ride != null ? ride.getNumPlaces() : 0).asObject();
        });

        qc3.setCellValueFactory(new PropertyValueFactory<>("price"));

        qc4.setCellValueFactory(data -> {
            Ride ride = data.getValue();
            return new SimpleStringProperty(ride != null ? ride.getDriver().toString2() : "<no name>");
        });

        tblRides.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tblRides.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && businessLogic.getCurrentTraveler()!=null) {
                btnBook.setDisable(false);
            } else {
                btnBook.setDisable(true);
            }
        });*/
    }

    @FXML
    private void reserveButton() {
        /*Ride ride2 = tblRides.getSelectionModel().getSelectedItem();
        int seats = comboSeats.getSelectionModel().getSelectedItem();
        businessLogic.bookRide(businessLogic.getCurrentTraveler(),ride2, seats, Booking.Status.Pending);
        tblRides.getItems().clear();
        List<Ride> rides = businessLogic.getRidesWithSeats(comboDepartCity.getValue(), comboArrivalCity.getValue(), Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), comboSeats.getValue());
        for (Ride ride : rides) {
            tblRides.getItems().add(ride);
        }
        btnBook.setDisable(true);*/
    }

    @FXML
    public void updateTable() {
        /*
        tblRides.getItems().clear();
        if(comboArrivalCity.getValue() != null && comboSeats.getValue() != null && datePicker.getValue() != null) {
            List<Ride> rides = businessLogic.getRidesWithSeats(comboDepartCity.getValue(), comboArrivalCity.getValue(), Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), comboSeats.getValue());
            tblRides.getItems().addAll(rides);
        }*/
    }

    public void setNull() {
        datePicker.setValue(null);
        comboSeats.setValue(null);
        comboDepartCity.setValue(null);
        comboArrivalCity.setValue(null);
        tblRides.getItems().clear();
    }

    public void bookVisible(boolean b) {
        btnBook.setVisible(b);
        bookTxt.setVisible(!b);
    }
}