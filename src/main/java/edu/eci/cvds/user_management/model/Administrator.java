package edu.eci.cvds.user_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * Represents an administrator in the system.
 * This class is an entity mapped to the "administrators" table in the "public" schema
 * and extends the {@link User} class to inherit common user properties.
 *
@Entity
@Table(name = "administrators", schema = "public")
public class Administrator extends User {


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Constructs a new Administrator with the specified details.
     *
     * @param id       The unique identifier of the administrator.
     * @param userName The username for the administrator's account.
     * @param password The password for the administrator's account.
     * @param email    The email address of the administrator.
     * @param name     The name of the administrator.
     *
    public Administrator(String id, String userName, String password, String email, String name) {
        super(id, userName, password);
        this.email = email;
        this.name = name;
        setRole("administrator");
    }

    /**
     * Default constructor for JPA.
     * This is required for entity instantiation by JPA.
     *
    public Administrator() {
        super();
    }

    /**
     * Retrieves the email address of the administrator.
     *
     * @return The email address of the administrator.
     *
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the administrator.
     *
     * @param email The email address to be assigned to the administrator.
     *
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the name of the administrator.
     *
     * @return The name of the administrator.
     *
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the administrator.
     *
     * @param name The name to be assigned to the administrator.
     *
    public void setName(String name) {
        this.name = name;
    }
}
*/