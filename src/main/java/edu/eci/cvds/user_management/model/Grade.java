package edu.eci.cvds.user_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grades", schema = "public")
public class Grade {

    @Id
    public String name;
    @Column
    private String description;


    /**
     *
     * @param name
     * @param description
     */
    public Grade(String name, String description){
        this.name=name;
        this.description=description;

    }

    public Grade() {
    }

}
