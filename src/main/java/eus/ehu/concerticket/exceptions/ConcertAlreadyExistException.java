package eus.ehu.concerticket.exceptions;

import java.io.Serial;

public class ConcertAlreadyExistException extends Exception {

 @Serial
 private static final long serialVersionUID = 1L;
 
 public ConcertAlreadyExistException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public ConcertAlreadyExistException(String s)
  {
    super(s);
  }
}