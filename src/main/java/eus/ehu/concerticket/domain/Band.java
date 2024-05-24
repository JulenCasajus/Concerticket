package eus.ehu.concerticket.domain;

import jakarta.persistence.*;

@Entity
public class Band {
    @Id
    private String name;
    private Integer creationYear;
    private String leader;

    public Band() {
    }

    public Band(String name, Integer creationYear, String leader) {
        this.name = name;
        this.creationYear = creationYear;
        this.leader = leader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
