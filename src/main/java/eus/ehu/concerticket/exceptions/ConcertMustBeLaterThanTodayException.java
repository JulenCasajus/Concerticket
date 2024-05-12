package eus.ehu.concerticket.exceptions;

import java.io.Serial;

public class ConcertMustBeLaterThanTodayException extends Exception {

 @Serial
 private static final long serialVersionUID = 1L;
 
 public ConcertMustBeLaterThanTodayException()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public ConcertMustBeLaterThanTodayException(String s)
  {
    super(s);
  }
}