package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.domain.Concert;
import eus.ehu.concerticket.businessLogic.BlFacade;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryConcertController implements Controller {
    @FXML
    private ComboBox<String> comboBand;
    @FXML
    private ComboBox<String> comboPlace;
    @FXML
    private ComboBox<Integer> comboTickets;
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
    private Button btnBuy;
    @FXML
    private Label buyTxt;
    @FXML
    private TableView<Concert> tblConcerts;
    private final BlFacade businessLogic;

    ObservableList<String> bands = FXCollections.observableArrayList(new ArrayList<>());
    ObservableList<String> places = FXCollections.observableArrayList(new ArrayList<>());

    public QueryConcertController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    public void initialize() {
        bands.setAll(businessLogic.getBands());
        places.setAll(businessLogic.getPlaces());
        comboBand.setItems(bands);
        comboPlace.setItems(places);

        // Update DatePicker cells when Band changes
        comboBand.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(comboTickets.getValue() != null)// && datePicker.getValue() != null)
                businessLogic.updateDatePickerCellFactory(datePicker, newVal, comboPlace.getValue(), comboTickets.getValue());
            updateTable();
        });

        // Update DatePicker cells when Place changes
        comboPlace.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(comboTickets.getValue() != null)// && datePicker.getValue() != null)
                businessLogic.updateDatePickerCellFactory(datePicker, comboBand.getValue(), newVal, comboTickets.getValue());
            updateTable();
        });

        // Update DatePicker cells when Ticket value changes
        comboTickets.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null)
                businessLogic.updateDatePickerCellFactory(datePicker, comboBand.getValue(), comboPlace.getValue(), newVal);
            updateTable();
        });

        // a date has been chosen, update the combobox of Rides
        datePicker.setOnAction(actionEvent -> {
            //if(comboArrivalCity.getValue() != null && comboSeats.getValue() != null) {
                updateTable();
        });

        comboTickets.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9));

        qc1.setCellValueFactory(data -> {
            Concert concert = data.getValue();
            return new SimpleStringProperty(concert != null ? concert.toString2() : "<no name>");
        });

        qc2.setCellValueFactory(data -> {
            Concert concert = data.getValue();
            return new SimpleIntegerProperty(concert != null ? concert.getTickets() : 0).asObject();
        });

        qc3.setCellValueFactory(new PropertyValueFactory<>("price"));

        qc4.setCellValueFactory(data -> {
            Concert concert = data.getValue();
            return new SimpleFloatProperty(concert != null ? concert.getPrice() : 0).asString();
        });

//        tblConcerts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tblConcerts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                btnBuy.setDisable(newSelection == null || businessLogic.getCurrentClient() == null));
    }

    @FXML
    private void buyButton() {
        Concert concert = tblConcerts.getSelectionModel().getSelectedItem();
        int tickets = comboTickets.getSelectionModel().getSelectedItem();
        businessLogic.purchaseConcert(businessLogic.getCurrentClient(), concert, tickets);
        tblConcerts.getItems().clear();
        List<Concert> concerts = businessLogic.getConcerts(comboBand.getValue(), comboPlace.getValue(), Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), comboTickets.getValue());
        for (Concert concert1 : concerts) {
            tblConcerts.getItems().add(concert1);
        }
        btnBuy.setDisable(true);
    }

    @FXML
    public void updateTable() {
        tblConcerts.getItems().clear();
        if(comboPlace.getValue() != null && comboTickets.getValue() != null && datePicker.getValue() != null) {
            List<Concert> concerts = businessLogic.getConcerts(comboBand.getValue(), comboPlace.getValue(), Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), comboTickets.getValue());
            tblConcerts.getItems().addAll(concerts);
        }
    }

    public void setNull() {
        datePicker.setValue(null);
        comboBand.setValue(null);
        comboPlace.setValue(null);
        comboTickets.setValue(null);
        tblConcerts.getItems().clear();
    }

    public void buyVisible(boolean b) {
        btnBuy.setVisible(b);
        buyTxt.setVisible(!b);
    }
}