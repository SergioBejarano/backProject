package edu.eci.cvds.user_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 * The Student class represents a student entity, including information about
 * their academic course, year, responsible person, and the relationship with the responsible person.
 */
@Getter
@Setter
@Entity
@Table(name = "students", schema = "public")
public class Student{
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private String name;
    private String document;
    private String extId;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "responsible_document")
    private String responsibleDocument;

    @Column(name = "active")
    private boolean active;

    /**
     * Constructs a new Student with the specified attributes. Automatically sets the role to "student"
     * and encodes the student's ID as their password using BCrypt.
     *
     * @param id                  The unique identifier for the student.
     * @param name                The name of the student.
     * @param document            The document number of the student.
     * @param documentType        The type of document (e.g., ID, passport).
     * @param courseName          The name of the course the student is enrolled in.
     * @param responsibleDocument The document number of the responsible person for the student.
     */
    public Student (String id, String name,String document, String documentType, String courseName, String responsibleDocument){
        this.id=id;
        this.extId= null;
        this.name=name;
        this.document = document;
        this.documentType = documentType;
        this.courseName = courseName;
        this.responsibleDocument = responsibleDocument;
        this.active = true;
    }

    /**
     * Default constructor required by JPA. Calls the superclass constructor.
     */
    public Student() {
    }

}
