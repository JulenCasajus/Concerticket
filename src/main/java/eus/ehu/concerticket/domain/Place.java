package eus.ehu.concerticket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Place {

    @Id
    private String name;
    private Integer maxCapacity;
    private Integer maxTickets;

    public Place() {
    }

    public Place(String name, Integer maxCapacity, Integer maxTickets) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.maxTickets = maxTickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
