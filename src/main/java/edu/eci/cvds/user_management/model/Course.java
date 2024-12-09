package edu.eci.cvds.user_management.model;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * Represents a course entity in the system. Each course is identified by its name and has an associated grade name,
 * which is automatically determined based on the course's name.
 */
@Getter
@Entity
@Table(name = "courses", schema = "public")
public class Course {

    /**
     * The unique name of the course.
     */
    @Id
    private String name;
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
        return switch (firstChar) {
            case 'J' -> "Jardín";
            case 'T' -> "Transición";
            case '1' -> "Primero";
            case '2' -> "Segundo";
            case '3' -> "Tercero";
            case '4' -> "Cuarto";
            case '5' -> "Quinto";
            case '6' -> "Sexto";
            case '7' -> "Séptimo";
            case '8' -> "Octavo";
            case '9' -> "Noveno";
            default -> "Unknown";
        };
    }
}
