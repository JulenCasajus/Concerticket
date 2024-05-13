package eus.ehu.concerticket.businessLogic;

// import eus.ehu.concerticket.domain.Hello;

import eus.ehu.concerticket.domain.*;
import eus.ehu.concerticket.exceptions.ConcertAlreadyExistException;
import eus.ehu.concerticket.exceptions.ConcertMustBeLaterThanTodayException;
import javafx.scene.control.DatePicker;

import java.util.Date;
import java.util.List;

/**
 * Interface that specifies the business logic.
 */
public interface BlFacade  {

    void setUserNull();

    List<String> getBands();

    List<String> getPlaces();

    List<Date> getDatesConcert(String concert, String Place, Integer tickets);

    List<Purchase> getPurchases(Client client);

    boolean exists(String username, String email);

    boolean createClient(String username, String email, String password);

    boolean isClient(String user, String password);

    Client getCurrentClient();

    void setCurrentClient(String username,String password);

    boolean createStaff(String username, String email, String password);

    boolean isStaff(String user, String password);

    Staff getCurrentStaff();

    void setCurrentStaff(String username, String password);

    boolean checkCredentials(String username, String email);

    boolean checkPasswords(String password, String password2);

    void createConcert(String band, String place, Date date, float price, Integer maxTickets, float discount) throws ConcertMustBeLaterThanTodayException, ConcertAlreadyExistException;

    List<Concert> getConcerts(String band, String place, Date date, Integer tickets);

    void setConcert(Concert concert);

    void purchaseConcert(Client client, Concert concert, Integer tickets);

    void updateDatePickerCellFactory(DatePicker datePicker, String concert, String place, int tickets);
}