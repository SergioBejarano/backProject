package edu.eci.cvds.userManagement.model;

import jakarta.persistence.*;

/**
 * Represents a course entity in the system. Each course is identified by its name and has an associated grade name,
 * which is automatically determined based on the course's name.
 */
@Entity
@Table(name = "courses", schema = "public")
public class Course {

    /**
     * The unique name of the course.
     */
    @Id
    private String name;

    /**
     * The grade name associated with the course, derived from the course's name.
     */
    @Column(name = "grade_name")
    private String gradeName;

    /**
     * Constructor that initializes a course with a given name. The grade name is automatically determined
     * based on the course name using the {@link #determineGradeName(String)} method.
     *
     * @param name the name of the course.
     */
    public Course(String name) {
        this.name = name;
        this.gradeName = determineGradeName(name);
    }

    /**
     * Default constructor for creating an empty course instance. Typically used by JPA.
     */
    public Course() {
    }

    /**
     * Gets the grade name associated with the course.
     *
     * @return the grade name.
     */
    public String getGradeName() {
        return gradeName;
    }


    public String getName() {
        return name;
    }

    /**
     * Determines the grade name based on the course's name.
     * The mapping is as follows:
     * <ul>
     *     <li>Names starting with "J" -> Jardín</li>
     *     <li>Names starting with "T" -> Transición</li>
     *     <li>Names starting with "10" -> Décimo</li>
     *     <li>Names starting with "11" -> Undécimo</li>
     *     <li>Names starting with numeric prefixes like "1", "2", etc., determine their respective grades.</li>
     * </ul>
     * If the name does not match any of the criteria, "Unknown" is returned.
     *
     * @param name the name of the course.
     * @return the corresponding grade name, or "Unknown" if no match is found.
     */
    private String determineGradeName(String name) {
        if (name == null || name.isEmpty()) {
            return "Unknown";
        }
        if (name.startsWith("10")) {
            return "Décimo";
        } else if (name.startsWith("11")) {
            return "Undécimo";
        }
        char firstChar = name.charAt(0);
        switch (firstChar) {
            case 'J': return "Jardín";
            case 'T': return "Transición";
            case '1': return "Primero";
            case '2': return "Segundo";
            case '3': return "Tercero";
            case '4': return "Cuarto";
            case '5': return "Quinto";
            case '6': return "Sexto";
            case '7': return "Séptimo";
            case '8': return "Octavo";
            case '9': return "Noveno";
            default: return "Unknown";
        }
    }
}
