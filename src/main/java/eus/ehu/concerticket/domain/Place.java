package eus.ehu.concerticket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
public class Place {

    @Id
    private String name;
    private Integer maxCapacity;
    private Integer maxTickets;

    public Place() {
    }

    public Place(Integer maxCapacity, Integer maxTickets) {
        this.maxCapacity = maxCapacity;
        this.maxTickets = maxTickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getMaxTickets() {
        return maxTickets;
    }

    public void setMaxTickets(Integer maxTickets) {
        this.maxTickets = maxTickets;
    }
}
