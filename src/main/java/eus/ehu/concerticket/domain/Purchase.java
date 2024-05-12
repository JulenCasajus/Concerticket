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
}
