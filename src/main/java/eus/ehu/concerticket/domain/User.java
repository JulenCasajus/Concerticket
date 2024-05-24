package eus.ehu.concerticket.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Vector;


@Entity
@Table(name = "USERS")
public class User {

    @Id
    private String email;
    private String username;
    private String password;
    private boolean staff;

    public User() {
    }

    public User(String username, String email, String password, boolean staff) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.staff = staff;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isStaff() {
        return staff;
    }

    public String toString2() {
        return username;
    }
}