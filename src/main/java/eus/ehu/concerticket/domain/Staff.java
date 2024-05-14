package eus.ehu.concerticket.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("STAFF")
public class Staff extends User {

    public Staff() {
        super();
    }

    public Staff(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj == null) return false;
        else if (getClass() != obj.getClass()) return false;
        Staff other = (Staff) obj;
        return Objects.equals(super.getEmail(), other.getEmail());
    }

    @Override
    public String toString() {
        return "Staff [Username=" + super.getUsername() + ", Email=" + super.getEmail() + "]";
    }

    public String toString2() {
        return super.toString2();
    }
}