package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.domain.Concert;
import eus.ehu.concerticket.domain.Purchase;
import eus.ehu.concerticket.domain.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class QueryPurchaseController implements Controller {

    @FXML
    private TableColumn<Purchase, String> q1;
    @FXML
    private TableColumn<Purchase, String> q2;
    @FXML
    private TableColumn<Purchase, String> q3;
    @FXML
    private TableColumn<Purchase, Integer> q4;
    @FXML
    private TableColumn<Purchase, String> q5;
    @FXML
    private TableView<Purchase> tblPurchases;
    BlFacade businessLogic;
    private MainGUIController controller;

    public QueryPurchaseController(BlFacade bl) {
        businessLogic = bl;
        this.controller = new MainGUIController(businessLogic);
    }

    public void setMainGUIController(MainGUIController mainGUIController) {
        this.controller = mainGUIController;
    }

    @FXML
    void initialize() {
        q1.setCellValueFactory(data -> {
            Purchase purchase = data.getValue();
            return new SimpleStringProperty(purchase.getConcert().getBand().getName());
        });
        q2.setCellValueFactory(data -> {
            Purchase purchase = data.getValue();
            return new SimpleStringProperty(purchase.getConcert().getPlace().getName());
        });
        q3.setCellValueFactory(data -> {
            Purchase purchase = data.getValue();
            return new SimpleStringProperty(purchase.getConcert().getDateString(purchase.getConcert().getDate()));
        });
        q4.setCellValueFactory(data -> {
            Purchase purchase = data.getValue();
            return new SimpleIntegerProperty(purchase.getTickets()).asObject();
        });
        q5.setCellValueFactory(data -> {
            Purchase purchase = data.getValue();
            return new SimpleStringProperty(purchase.getPrice() + "€");
        });
    }

    public void updateTable() {
        tblPurchases.getItems().clear();
        User user = businessLogic.getCurrentUser();
        List<Purchase> purchases = businessLogic.getPurchases(user);
        tblPurchases.getItems().addAll(purchases);
    }

    @Override
    public void signUpSuccessful() {
    }

    @Override
    public void setNull() {
    }

    @Override
    public void buyVisible(boolean b) {
    }
}