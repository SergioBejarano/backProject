package edu.eci.cvds.user_management.model;

import jakarta.persistence.*;


/**
 * The Student class represents a student entity, including information about
 * their academic course, year, responsible person, and the relationship with the responsible person.
 */
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

    /**
     * Retrieves the document of the responsible person associated with the student.
     *
     * @return The document of the responsible person.
     */
    public String getResponsibleDocument() {
        return responsibleDocument;
    }

    /**
     * Retrieves the student's document.
     *
     * @return The document of the student.
     */
    public String getDocument() {
        return document;
    }

    /**
     * Retrieves the type of document associated with the student.
     *
     * @return The type of document (e.g., passport, ID card) of the student.
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Sets the course of the student.
     *
     * @param courseName the new course name.
     */
    public void setCourse(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the name of the student.
     *
     * @return the student's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new external ID for the student.
     *
     * @param newExtId The new external ID to be assigned to the student.
     */
    public void setExtId(String newExtId){
        this.extId=newExtId;
    }

    /**
     * Retrieves the unique identifier of the student.
     *
     * @return The ID of the student as a String.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the name of course.
     *
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Updates state of student.
     *
     * @param status The new status.
     */
    public void setActive(boolean status) {
        this.active=status;
    }

    /**
     * Retrieves the external ID of the student.
     *
     * @return The external ID of the student.
     */
    public String getExtId() {
        return extId;
    }
}
