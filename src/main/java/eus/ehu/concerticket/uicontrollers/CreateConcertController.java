package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.domain.Band;
import eus.ehu.concerticket.domain.Place;
import eus.ehu.concerticket.utils.Dates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.ArrayList;

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
    }

    @FXML
    void createConcertClick() {
        try {
            String bandName = comboBand.getValue();
            String placeName = comboPlace.getValue();
            Band band = businessLogic.getBand(bandName);
            Place place = businessLogic.getPlace(placeName);
            LocalDate date = datePicker.getValue();

            if (bandName == null || placeName == null || date == null || txtNumberOfTickets.getText() == null || txtPrice.getText() == null) {
                throw new NullPointerException("ERROR: Please fill in all the fields");
            }
            int maxTickets = Integer.parseInt(txtNumberOfTickets.getText());
            float price = Float.parseFloat(txtPrice.getText());
            float discount = Float.parseFloat(txtDiscount.getText());
            businessLogic.createConcert(band, place, Dates.convertToDate(date), maxTickets, price, discount);

            lblError.setText("");
            lblSuccess.setText("Concert created successfully");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            lblError.setText("ERROR: Please fill in all the fields");
            lblSuccess.setText("");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid numbers for tickets and price");
            lblError.setText("ERROR: Invalid numbers for tickets and price");
            lblSuccess.setText("");
        }
    }

    @Override
    public void setNull() {
    }

    @Override
    public void buyVisible(boolean b) {
    }
}
