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
import javafx.scene.input.KeyCode;

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
    private Label lblDiscounts;
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
            return new SimpleStringProperty(concert != null ? concert.getBand().getName() : "<no band>");
        });
        qc2.setCellValueFactory(cellData -> {
            Concert concert = cellData.getValue();
            return new SimpleStringProperty(concert != null ? concert.getPlace().getName() : "<no place>");
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
            float discount = concert.getDiscount();
            LocalDate thirtyDaysAfterToday = LocalDate.now().plusDays(30);
            LocalDate concertDate = concert.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (!concertDate.isAfter(thirtyDaysAfterToday)) {
                discount = 0;
            }
            return new SimpleStringProperty(discount + "%");
        });

        tblConcerts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                btnBuy.setDisable(newSelection == null || businessLogic.getCurrentUser() == null || comboTickets.getValue() == null)); //CAMBIAR A getCurrentClient???

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
        comboTickets.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                comboTickets.show();
                event.consume();
            }
        });
        clearBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clearQuery();
                event.consume();
            }
        });
        datePicker.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                datePicker.show();
                event.consume();
            }
        });
    }

    @FXML
    private void buyButton() {
        Concert concert = tblConcerts.getSelectionModel().getSelectedItem();
        int tickets = comboTickets.getSelectionModel().getSelectedItem();
        float price = concert.getPrice();
        LocalDate thirtyDaysAfterToday = LocalDate.now().plusDays(30);
        LocalDate concertDate = concert.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (concertDate.isAfter(thirtyDaysAfterToday)) {
            price = price * (100 - concert.getDiscount()) / 100;
        }
        btnBuy.setDisable(true);
        businessLogic.purchaseConcert(businessLogic.getCurrentUser(), concert, tickets, price);
        if(tickets == 1) lblMessage.setText(tickets + " ticket bought for " + price + "€!");
        else lblMessage.setText(tickets + " tickets bought for " + price + "€ each!");
    }

    public void updateTable() {
        tblConcerts.getItems().clear();
        String band = comboBand.getValue() != null ? comboBand.getValue() : null;
        String place = comboPlace.getValue() != null ? comboPlace.getValue() : null;
        LocalDate dateValue = datePicker.getValue() != null ? datePicker.getValue() : null;
        Date date = dateValue != null ? Date.from(dateValue.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
        int tickets = comboTickets.getValue() != null ? comboTickets.getValue() : 0;
        List<Concert> concerts;
        if(band != null || place != null || dateValue != null || tickets != 0) {
            concerts = businessLogic.getConcerts(band, place, date, tickets);
            System.out.println(concerts.toString());
            tblConcerts.getItems().addAll(concerts);
        }
    }

    @FXML
    private void clearQuery() {
        setNull();
    }

    public void clear() {
        lblMessage.setText("");
    }

    @Override
    public void signUpSuccessful() {
    }

    public void setNull() {
        comboBand.setValue(null);
        comboPlace.setValue(null);
        comboTickets.setValue(null);
        datePicker.setValue(null);
        businessLogic.updateDatePickerCellFactory(datePicker, null, null, 0);
        tblConcerts.getItems().clear();
    }

    public void buyVisible(boolean b) {
        btnBuy.setVisible(b);
        buyTxt.setVisible(!b);
    }
}