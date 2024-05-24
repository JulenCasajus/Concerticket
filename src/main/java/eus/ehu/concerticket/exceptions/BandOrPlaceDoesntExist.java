package eus.ehu.concerticket.exceptions;

import java.io.Serial;

public class BandOrPlaceDoesntExist extends  Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    /**This exception is triggered if the question already exists
     *@param s String of the exception
     */
    public BandOrPlaceDoesntExist(String s) {
        super(s);
    }
    public BandOrPlaceDoesntExist() {
        super();
    }
}