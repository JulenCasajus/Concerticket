package eus.ehu.concerticket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Concert implements Serializable {

    @Id
    @GeneratedValue
    private Integer concertID;
    private Date date;
    private float price;
    private Integer maxTickets;
    private float discount;
    private String place;
    private String group;

    public Concert() {
        super();
    }

    public Concert(Integer concertID, Date date, float price, Integer maxTickets, float discount, String place, String group) {
        super();
        this.concertID = concertID;
        this.date = date;
        this.price = price;
        this.maxTickets = maxTickets;
        this.discount = discount;
        this.place = place;
        this.group = group;
    }

    public Concert(Date date, float price, Integer maxTickets, float discount, String place, String group) {
        super();
        this.date = date;
        this.price = price;
        this.maxTickets = maxTickets;
        this.discount = discount;
        this.place = place;
        this.group = group;
    }

    public Integer getConcertID() {
        return concertID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getMaxTickets() {
        return maxTickets;
    }

    public void setMaxTickets(Integer maxTickets) {
        this.maxTickets = maxTickets;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getDateWithoutHours(Date date) {
        String[] dateParts = date.toString().split(" ");
        return dateParts[0];
    }

    public String toString() {
        return  group + " | " + place + " | " + getDateWithoutHours(date);
    }

    public String toString2() {
        return  group + " | " + place;
    }
}
