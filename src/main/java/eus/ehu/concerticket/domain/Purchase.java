package eus.ehu.concerticket.domain;

import jakarta.persistence.*;

@Entity
public class Purchase {

    @Id
    @GeneratedValue
    private Integer purchaseID;
    private Integer tickets;
    private float price;
    @OneToOne
    Client client;
    @OneToOne
    Concert concert;

    public Purchase() {
    }

    public Purchase(Client client, Concert concert, Integer tickets, float price) {
        this.client = client;
        this.concert = concert;
        this.tickets = tickets;
        this.price = price;
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

    public Concert getConcert() {
        return concert;
    }

    @Override
    public String toString() {
        return "Purchase{" + "purchaseID=" + purchaseID + ", tickets=" + tickets + ", price=" + price + ", client=" + client + ", concert=" + concert + '}';
    }

    public String toString2() {
        return concert + " | " + tickets + " tickets | " + price + "€";
    }
}
