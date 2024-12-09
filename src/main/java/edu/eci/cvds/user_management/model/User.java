package edu.eci.cvds.user_management.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    private String role;

    /**
     * Default constructor for User.
     * Initializes an empty User object.
     */
    protected User() {
    }

    /**
     * Parameterized constructor for User.
     *
     * @param id       The unique identifier for the user.
     * @param userName The username associated with the user.
     * @param password The password associated with the user.
     */
    public User(String id, String userName, String password,String role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

}

