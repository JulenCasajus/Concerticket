package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.domain.Staff;
import eus.ehu.concerticket.domain.Concert;
import eus.ehu.concerticket.exceptions.ConcertAlreadyExistException;
import eus.ehu.concerticket.exceptions.ConcertMustBeLaterThanTodayException;
import eus.ehu.concerticket.ui.MainGUI;
import eus.ehu.concerticket.utils.Dates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateConcertController implements Controller {

    private BlFacade businessLogic;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private DatePicker datePicker;
    private MainGUI mainGUI;
    @FXML
    private Label lblErrorMessage;
    @FXML
    private Label lblErrorMinBet;
    @FXML
    private Button btnCreateRide;
    @FXML
    private TextField txtArrivalCity;
    @FXML
    private TextField txtDepartCity;
    @FXML
    private TextField txtNumberOfSeats;
    @FXML
    private TextField txtPrice;
    @FXML
    public ComboBox<String> comboCreateDepartCity;
    @FXML
    public ComboBox<String> comboCreateArrivalCity;

    private final ObservableList<String> departureCities = FXCollections.observableArrayList(new ArrayList<>());
    private final ObservableList<String> arrivalCities = FXCollections.observableArrayList(new ArrayList<>());

    private MainGUIController controller;
    private List<LocalDate> holidays = new ArrayList<>();

    public CreateConcertController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void setMainGUIController(MainGUIController mainGUIController) {
        this.controller = mainGUIController;
    }

    @Override
    public void setNull() {

    }

    @Override
    public void bookVisible(boolean b) {

    }

    @FXML
    void initialize() {
/*
        btnCreateRide.setOnAction(event -> {
            createRideClick();
        });
        departureCities.setAll(businessLogic.getDepartCities());
        comboCreateDepartCity.setItems(departureCities);
        comboCreateArrivalCity.setItems(arrivalCities);

        // when the user selects a departure city, update the arrival cities
        comboCreateDepartCity.setOnAction(actionEvent -> {
            arrivalCities.clear();
            arrivalCities.setAll(businessLogic.getArrivalCities(comboCreateDepartCity.getValue()));
        });

*/
/*
    Callback<ListView<Event>, ListCell<Event>> factory = lv -> new ListCell<>() {
      @Override
      protected void updateItem(Event item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? "" : item.getDescription());
      }
    };


    comboEvents.setCellFactory(factory);
    comboEvents.setButtonCell(factory.call(null));

 */


        // setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());


        // get a reference to datepicker inner content
        // attach a listener to the  << and >> buttons
        // mark events for the (prev, current, next) month and year shown
/*       datePicker.setOnMouseClicked(e -> {
            DatePickerSkin skin = (DatePickerSkin) datePicker.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {
                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();
                    String month = ((Label) (labels.get(0))).getText();
                    String year = ((Label) (labels.get(1))).getText();
                    YearMonth ym = Dates.getYearMonth(month + " " + year);
                    // setEventsPrePost(ym.getYear(), ym.getMonthValue());
                });
            });
        });*/

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (holidays.contains(item)) {
                                this.setStyle("-fx-background-color: pink");
                            }
                        }
                    }
                };
            }
        });

        // when a date is selected...
        datePicker.setOnAction(actionEvent -> {
     /* comboEvents.getItems().clear();

      oListEvents = FXCollections.observableArrayList(new ArrayList<>());
      oListEvents.setAll(businessLogic.getEvents(Dates.convertToDate(datePicker.getValue())));

      comboEvents.setItems(oListEvents);

      if (comboEvents.getItems().size() == 0)
        btnCreateRide.setDisable(true);
      else {
         btnCreateRide.setDisable(false);
        // select first option
        comboEvents.getSelectionModel().select(0);
      }
*/
        });

    }

    @FXML
    void createRideClick() {
/*
        clearErrorLabels();

        //  Event event = comboEvents.getSelectionModel().getSelectedItem();
        String errors = field_Errors();

        if (errors != null) {
            // businessLogic.createQuestion(event, inputQuestion, inputPrice);
            displayMessage(errors, "danger");

        } else {
            try {

                int inputSeats = Integer.parseInt(txtNumberOfSeats.getText());
                float price = Float.parseFloat(txtPrice.getText());
                Driver driver = businessLogic.getCurrentDriver();
                Ride r = businessLogic.createRide(comboCreateDepartCity.getEditor().getText(), comboCreateArrivalCity.getEditor().getText(), Dates.convertToDate(datePicker.getValue()), inputSeats, price, driver);
                displayMessage(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"), "success");
           } catch (ConcertMustBeLaterThanTodayException e1) {
                displayMessage(e1.getMessage(), "danger");
            } catch (ConcertAlreadyExistException e1) {
                displayMessage(e1.getMessage(), "danger");
            }
        }
*/
/*
    if (lblErrorMinBet.getText().length() > 0 && showErrors) {
      lblErrorMinBet.getStyleClass().setAll("lbl", "lbl-danger");
    }
    if (lblErrorQuestion.getText().length() > 0 && showErrors) {
      lblErrorQuestion.getStyleClass().setAll("lbl", "lbl-danger");
    }
 */
    }

  /*private void setEventsPrePost(int year, int month) {
    LocalDate date = LocalDate.of(year, month, 1);
    setEvents(date.getYear(), date.getMonth().getValue());
    setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
    setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
  }*/

 /* private void setEvents(int year, int month) {

    Date date = Dates.toDate(year, month);

    for (Date day : businessLogic.getEventsMonth(date)) {
      holidays.add(Dates.convertToLocalDateViaInstant(day));
    }
  }*/

    private void clearErrorLabels() {
        lblErrorMessage.setText("");
        lblErrorMinBet.setText("");
        lblErrorMinBet.getStyleClass().clear();
        lblErrorMessage.getStyleClass().clear();
    }

    private String field_Errors() {

        try {
            if ((comboCreateDepartCity.getEditor().getText().isEmpty()) || (comboCreateDepartCity.getEditor().getText().isEmpty()) || (txtNumberOfSeats.getText().isEmpty()) || (txtPrice.getText().length() == 0))
                return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
            else {

                // trigger an exception if the introduced string is not a number
                int inputSeats = Integer.parseInt(txtNumberOfSeats.getText());

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
        lblErrorMessage.getStyleClass().clear();
        lblErrorMessage.getStyleClass().setAll("lbl", "lbl-"+label);
        lblErrorMessage.setText(message);
    }
}
