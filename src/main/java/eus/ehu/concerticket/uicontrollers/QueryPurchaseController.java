package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.businessLogic.BlFacade;
import eus.ehu.concerticket.domain.Purchase;
import eus.ehu.concerticket.domain.Client;
import eus.ehu.concerticket.ui.MainGUI;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;


public class QueryPurchaseController implements Controller{

    @FXML
    private TableColumn<Purchase, String> q1;
    @FXML
    private TableColumn<Purchase, String> q2;
    @FXML
    private TableColumn<Purchase, Integer> q3;
    @FXML
    private TableView<Purchase> tablePurchases;
    private MainGUI mainGUI;
    private BlFacade businessLogic;
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
        Client client = businessLogic.getCurrentClient();
        List<Purchase> purchases = businessLogic.getPurchases(client);
        tablePurchases.getItems().addAll(purchases);

        q1.setCellValueFactory(data -> {
            Purchase purchase = data.getValue();
            return new SimpleStringProperty(purchase != null ? purchase.toString2() : "<no name>");
        });
        q2.setCellValueFactory(data -> {
            Purchase purchase = data.getValue();
            return new SimpleStringProperty(purchase != null ? purchase.getConcert().getDateWithoutHours(purchase.getConcert().getDate()) : "<no date>");
        });
        q3.setCellValueFactory(data -> {
            Purchase purchase = data.getValue();
            return new SimpleIntegerProperty(purchase != null ? purchase.getTickets() : 0).asObject();
        });
    }

    @Override
    public void setNull() {
    }

    @Override
    public void bookVisible(boolean b) {
    }
}

