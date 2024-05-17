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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    private TableColumn<Concert, String> qc2;
    @FXML
    private TableColumn<Concert, String> qc3;
    @FXML
    private TableColumn<Concert, Integer> qc4;
    @FXML
    private TableColumn<Concert, String> qc5;
    @FXML
    private TableColumn<Concert, String> qc6;
    @FXML
    private Button btnBuy;
    @FXML
    private Button clearBtn;
    @FXML
    private Label buyTxt;
    @FXML
    private Label lblMessage;
    @FXML
    private TableView<Concert> tblConcerts;
    BlFacade businessLogic;
    private MainGUIController controller;

    public QueryConcertController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void setMainGUIController(MainGUIController mainGUIController) {
        this.controller = mainGUIController;
    }

    @FXML
    public void initialize() {

        ObservableList<String> bands = FXCollections.observableArrayList(new ArrayList<>());
        ObservableList<String> places = FXCollections.observableArrayList(new ArrayList<>());

        bands.setAll(businessLogic.getBands());
        places.setAll(businessLogic.getPlaces());

        comboBand.setItems(bands);
        comboPlace.setItems(places);

        businessLogic.updateDatePickerCellFactory(datePicker, null, null, 0);

        comboBand.valueProperty().addListener((obs, oldVal, newVal) -> {
            businessLogic.updateDatePickerCellFactory(datePicker, newVal,
                    comboPlace.getValue() != null ? comboPlace.getValue() : null,
                    comboTickets.getValue() != null ? comboTickets.getValue() : 0);
            updateTable();
        });

        comboPlace.valueProperty().addListener((obs, oldVal, newVal) -> {
            businessLogic.updateDatePickerCellFactory(datePicker,
                    comboBand.getValue() != null ? comboBand.getValue() : null,
                    newVal, comboTickets.getValue() != null ? comboTickets.getValue() : 0);
            updateTable();
        });

        // Update DatePicker cells when Ticket value changes
        comboTickets.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                businessLogic.updateDatePickerCellFactory(datePicker,
                        comboBand.getValue() != null ? comboBand.getValue() : null,
                        comboPlace.getValue() != null ? comboPlace.getValue() : null,
                        newVal);
                updateTable();
            }
        });

        // a date has been chosen, update the combobox of Rides
        datePicker.setOnAction(actionEvent -> updateTable());

        comboTickets.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9));

        qc1.setCellValueFactory(cellData -> {
            Concert concert = cellData.getValue();
            return new SimpleStringProperty(concert != null ? concert.getBand().getName() : "<no name>");
        });
        qc2.setCellValueFactory(cellData -> {
            Concert concert = cellData.getValue();
            return new SimpleStringProperty(concert != null ? concert.getPlace().getName() : "<no name>");
        });
        qc3.setCellValueFactory(cellData -> {
            Concert concert = cellData.getValue();
            return new SimpleStringProperty(concert != null ? concert.getDateString(concert.getDate()) : "<no date>");
        });
        qc4.setCellValueFactory(cellData -> {
            Concert concert = cellData.getValue();
            Integer concertMaxTickets = concert.getMaxTickets();
            Integer placeMaxTickets = concert.getPlace().getMaxTickets();
            return new SimpleIntegerProperty(Math.min(concertMaxTickets, placeMaxTickets)).asObject();
        });
        qc5.setCellValueFactory(cellData -> {
            Concert concert = cellData.getValue();
            return new SimpleStringProperty(concert != null ? concert.getPrice() + "€" : "<no price>");
        });
        qc6.setCellValueFactory(cellData -> {
            Concert concert = cellData.getValue();
            return new SimpleStringProperty(concert != null ? concert.getDiscount() + "%" : "<no price>");
        });

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
        String band = comboBand.getValue() != null ? comboBand.getValue() : null;
        String place = comboPlace.getValue() != null ? comboPlace.getValue() : null;
        LocalDate dateValue = datePicker.getValue() != null ? datePicker.getValue() : null;
        Date date = dateValue != null ? Date.from(dateValue.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
        Integer tickets = comboTickets.getValue() != null ? comboTickets.getValue() : 0;

        List<Concert> concerts = businessLogic.getConcerts(band, place, date, tickets);
        System.out.println(concerts.toString());
        tblConcerts.getItems().addAll(concerts);
    }

    @FXML
    private void clearQuery() {
        setNull();
    }

    public void setNull() {
        ResourceBundle bundle = ResourceBundle.getBundle("Etiquetas_en", Locale.getDefault());
        comboBand.setValue(null);
        comboPlace.setValue(null);
        comboTickets.setValue(null);
        datePicker.setValue(null);
        comboBand.setPromptText(bundle.getString("Band"));
        comboPlace.setPromptText(bundle.getString("Place"));
        comboTickets.setPromptText(bundle.getString("Tickets"));
        datePicker.setPromptText(bundle.getString("Date"));
        businessLogic.updateDatePickerCellFactory(datePicker, null, null, 0);
        tblConcerts.getItems().clear();
    }

    public void buyVisible(boolean b) {
        btnBuy.setVisible(b);
        buyTxt.setVisible(!b);
    }
}