package edu.eci.cvds.userManagement.model;

import jakarta.persistence.*;

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
    public User(String id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Sets the username for the user.
     *
     * @param userName The username to be set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the password for the user.
     *
     * @param password The password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The role to be assigned to the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the role of the user.
     *
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The unique ID of the user.
     */
    public String getId() {
        return id;
    }
}

