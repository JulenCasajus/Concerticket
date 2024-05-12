package eus.ehu.concerticket.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Group {

    @Id
    private String name;
    private Integer creationYear;
    private String leader;

    public Group() {
    }

    public Group(String name, Integer creationYear, String leader) {
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

    public Integer getCreationYear() {
        return creationYear;
    }

    public void setCreationYear(Integer creationYear) {
        this.creationYear = creationYear;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}
