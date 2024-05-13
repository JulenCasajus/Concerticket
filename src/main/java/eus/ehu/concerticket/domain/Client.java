package eus.ehu.concerticket.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Vector;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {

    @OneToMany(mappedBy = "Client", fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private final List<Purchase> purchases = new Vector<>();

    public Client() {
        super();
    }

    public Client(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setPassword(String password){
        super.setPassword(password);
    }

    @Override
    public String getPassword(){
        return super.getPassword();
    }

    @Override
    public void setUsername(String name) {
        super.setUsername(name);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String toString() {
        return "Client [Username=" + super.getUsername() + ", Email=" + super.getEmail() + "]";
    }

    @Override
    public String toString2() {
        return super.toString2();
    }
}
