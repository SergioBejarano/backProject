package edu.eci.cvds.user_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrators", schema = "public")
public class Administrator extends User {

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;



    public Administrator(String id, String userName, String password, String email, String name) {
        super(id, userName, password);
        this.email = email;
        this.name = name;
        setRole("administrator");
    }

    public Administrator() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
