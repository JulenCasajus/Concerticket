package eus.ehu.concerticket.businessLogic;

// import eus.ehu.concerticket.domain.Hello;

import eus.ehu.concerticket.domain.*;
import javafx.scene.control.DatePicker;

import java.util.Date;
import java.util.List;

/**
 * Interface that specifies the business logic.
 */
public interface BlFacade  {

    void setUserNull();

    List<String> getBands();

    Band getBand(String band);

    List<String> getPlaces();

    Place getPlace(String place);

    List<Date> getDatesConcert(String concert, String Place, int tickets);

    List<Purchase> getPurchases(User user);

    boolean exists(String username, String email);

    boolean exists(String user);

    boolean isUser(String user, String password);

    boolean createUser(String username, String email, String password, boolean staff);

    User getCurrentUser();

    void setCurrentUser(String user, String password);

    boolean checkCredentials(String username, String email);

    boolean checkPasswords(String password, String password2);

    void createConcert(Band band, Place place, Date date, int maxTickets, float price, float discount);

    List<Concert> getConcerts(String band, String place, Date date, int tickets);

    void setConcert(Concert concert);

    void purchaseConcert(User user, Concert concert, int tickets, float price);

    void updateDatePickerCellFactory(DatePicker datePicker, String concert, String place, int tickets);
}