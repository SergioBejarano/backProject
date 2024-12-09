package edu.eci.cvds.user_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


/**
 * Represents an administrator in the system.
 * This class is an entity mapped to the "administrators" table in the "public" schema
 * and extends the {@link User} class to inherit common user properties.
 */
@Setter
@Getter
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
     */
    public Administrator(String id, String userName, String password, String email, String name) {
        super(id,userName,password, "Administrator");
        this.email = email;
        this.name = name;
        setRole("administrator");
    }

    /**
     * Default constructor for JPA.
     * This is required for entity instantiation by JPA.
     */
    public Administrator() {
        super();
    }

}