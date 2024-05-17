package eus.ehu.concerticket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Concert implements Serializable {

    @Id
    @GeneratedValue
    private Integer concertID;
    private Date date;
    private Integer tickets;
    private float price;
    private Integer maxTickets;
    private float discount;
    @OneToOne
    private Place place;
    @OneToOne
    private Band band;

    public Concert() {
        super();
    }

    public Concert(Band band, Place place, Date date, Integer maxTickets, float price, float discount) {
        super();
        this.date = date;
        this.tickets = place.getMaxCapacity();
        this.price = price;
        this.maxTickets = maxTickets;
        this.discount = discount;
        this.place = place;
        this.band = band;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public String toString() {
        return  band.getName() + " | " + place.getName() + " | " + getDateString(date);
    }

    public String toString2() {
        return  band.getName() + " | " + place.getName();
    }
}
