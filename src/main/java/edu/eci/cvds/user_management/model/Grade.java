package edu.eci.cvds.user_management.model;

import jakarta.persistence.*;

/**
 * Represents a grade entity in the system.
 * This class is mapped to the "grades" table in the "public" schema of the database.
 */
@Entity
@Table(name = "grades", schema = "public")
public class Grade {

    @Id
    public String name;
    @Column
    private String description;

    /**
     * Constructs a new Grade instance with the specified name and description.
     *
     * @param name        The unique name of the grade.
     * @param description A brief description of the grade.
     */
    public Grade(String name, String description){
        this.name=name;
        this.description=description;

    }

    /**
     * Default constructor for JPA.
     * This is required by the JPA specification.
     */
    public Grade() {
    }
}
