package eus.ehu.concerticket.businessLogic;

import eus.ehu.concerticket.configuration.Config;
import eus.ehu.concerticket.dataAccess.DataAccess;
import eus.ehu.concerticket.domain.*;
import eus.ehu.concerticket.utils.Dates;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implements the business logic as a web service.
 */
public class BlFacadeImplementation implements BlFacade {

    DataAccess dbManager;
    Config config = Config.getInstance();
    private User currentUser;
    private final List<LocalDate> datesWithConcert = new ArrayList<>();

    public BlFacadeImplementation() {
        System.out.println("Creating BlFacadeImplementation instance");
        boolean initialize = config.getDataBaseOpenMode().equals("initialize");
        dbManager = new DataAccess();
        if (initialize) dbManager.initializeDB();
    }

    @Override
    public void setUserNull() {
        this.currentUser = null;
    }

    public List<String> getBands() {
        return dbManager.getBands();
    }

    public Band getBand(String band) {
        return dbManager.getBand(band);
    }

    public List<String> getPlaces() {
        return dbManager.getPlaces();
    }

    public Place getPlace(String name) {
        return dbManager.getPlace(name);
    }

    public List<Date> getDatesConcert(String concert, String place, int tickets) {
        return dbManager.getDatesConcert(concert, place, tickets);
    }

    public List<Purchase> getPurchases(User user) {
        return dbManager.getPurchases(user);
    }

    @Override
    public boolean exists(String username, String email) {
        return dbManager.exists(username, email);
    }

    @Override
    public boolean exists(String user) {
        return dbManager.exists(user);
    }

    public boolean isUser(String user, String password) {
        return dbManager.isUser(user, password);
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(String username,String password) {
        this.currentUser = dbManager.getCurrentUser(username, password);
    }

    public boolean createUser(String username, String email, String password, boolean staff) {
        return dbManager.createUser(username, email, password, staff);
    }

    @Override
    public boolean checkCredentials(String username, String email) {
        return !username.contains("@") && email.contains("@");
    }

    @Override
    public boolean checkPasswords(String password, String password2) {
            return password.equals(password2);
    }

    @Override
    public void createConcert(Band band, Place place, Date date, int maxTickets, float price, float discount) {
        dbManager.createConcert(band, place, date, maxTickets, price, discount);
    }

    public List<Concert> getConcerts(String band, String place, Date date, int tickets) {
        return dbManager.getConcerts(band, place, date, tickets);
    }

    public void setConcert(Concert concert) {
        dbManager.setConcert(concert);
    }

    public void purchaseConcert(User user, Concert concert, int tickets, float price) {
        dbManager.purchaseConcert(user, concert, tickets, price);
    }

    public void updateDatePickerCellFactory(DatePicker datePicker, String band, String place, int tickets) {
        List<Date> dates = getDatesConcert(band, place, tickets);

        datesWithConcert.clear();
        for (Date day : dates) {
            datesWithConcert.add(Dates.convertToLocalDateViaInstant(day));
        }

        datePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            if (datesWithConcert.contains(item)) {
                                this.setStyle("-fx-background-color: pink");
                            } else {
                                this.setStyle("-fx-background-color: rgb(235, 235, 235)");
                            }
                        }
                    }
                };
            }
        });
    }
}
