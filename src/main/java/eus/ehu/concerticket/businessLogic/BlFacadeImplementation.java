package eus.ehu.concerticket.businessLogic;

import eus.ehu.concerticket.configuration.Config;
import eus.ehu.concerticket.dataAccess.DataAccess;
import eus.ehu.concerticket.domain.*;
import eus.ehu.concerticket.utils.Dates;
import eus.ehu.concerticket.exceptions.ConcertAlreadyExistException;
import eus.ehu.concerticket.exceptions.ConcertMustBeLaterThanTodayException;
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
    private Client currentClient;
    private Staff currentStaff;
    private final List<LocalDate> datesWithConcert = new ArrayList<>();

    public BlFacadeImplementation() {
        System.out.println("Creating BlFacadeImplementation instance");
        boolean initialize = config.getDataBaseOpenMode().equals("initialize");
        dbManager = new DataAccess();
        if (initialize) dbManager.initializeDB();
//        dbManager.close();
    }

    @Override
    public void setUserNull() {
        this.currentClient = null;
        this.currentStaff = null;
    }

    public List<String> getBands() {
        return dbManager.getBands();
    }


    public List<String> getPlaces() {
        return dbManager.getPlaces();
    }

    public List<Date> getDatesConcert(String concert, String place, Integer tickets) {
        return dbManager.getDatesConcert(concert, place, tickets);
    }

    public List<Purchase> getPurchases(Client client) {
        return dbManager.getPurchases(client);
    }

    @Override
    public boolean exists(String username, String email) {
        return dbManager.exists(username, email);
    }

    public boolean createStaff(String username, String email, String password) {
        return dbManager.createStaff( username, email, password);
    }

    public boolean isStaff(String user, String password) {
        return dbManager.isStaff(user, password);
    }

    public Staff getCurrentStaff() {
        return this.currentStaff;
    }

    public void setCurrentStaff(String username, String password) {
        this.currentStaff = dbManager.setCurrentStaff(username, password);
    }

    public boolean createClient(String username, String email, String password) {
        return dbManager.createClient(username, email, password);
    }

    public boolean isClient(String user, String password) {
        return dbManager.isClient(user, password);
    }

    public Client getCurrentClient() {
        return this.currentClient;
    }

    public void setCurrentClient(String username,String password) {
        this.currentClient = dbManager.setCurrentClient(username, password);
    }

    @Override
    public boolean checkCredentials(String username, String email) {
        return dbManager.checkCredentials(username, email);
    }

    @Override
    public boolean checkPasswords(String password, String password2) {
        return dbManager.checkPasswords(password, password2);
    }
    @Override
    public void createConcert(String band, String place, Date date, float price, Integer maxTickets, float discount) throws ConcertMustBeLaterThanTodayException, ConcertAlreadyExistException {
        dbManager.createConcert(band, place, date, price, discount);
    }

    public List<Concert> getConcerts(String band, String place, Date date, Integer tickets) {
        return dbManager.getConcerts(band, place, date, tickets);
    }

    public void setConcert(Concert concert) {
        dbManager.setConcert(concert);
    }

    public void purchaseConcert(Client client, Concert concert, Integer tickets) {
    }

    public void updateDatePickerCellFactory(DatePicker datePicker, String concert, String place, int tickets) {
        List<Date> dates = getDatesConcert(concert, place, tickets);

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
