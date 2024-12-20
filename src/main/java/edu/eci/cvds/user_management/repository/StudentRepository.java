package edu.eci.cvds.user_management.repository;
import edu.eci.cvds.user_management.model.Student;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * StudentRepository is a data access class that manages database interactions for
 * the Student entity, allowing saving of Student records to the database.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    /**
     * Retrieves a paginated list of students.
     *
     * @param pageable The Pageable object containing pagination information.
     * @return A Page of students.
     */
    @NonNull
    Page<Student> findAll(@NonNull Pageable pageable);

    /**
     * Updates the course of a student by their ID.
     *
     * @param id         The ID of the student.
     * @param courseName The new course name.
     */
    @Modifying
    @Query("UPDATE Student s SET s.courseName = :courseName WHERE s.id = :id")
    void updateStudentCourse(@Param("id") String id, @Param("courseName") String courseName);

    /**
     * Updates the extId and sets active to true for a student by their ID.
     *
     * @param id    The ID of the student.
     * @param extId The new external ID to set.
     */
    @Modifying
    @Query("UPDATE Student s SET s.extId = :extId, s.active = true WHERE s.id = :id")
    void updateExtIdAndActivate(@Param("id") String id, @Param("extId") String extId);

    /**
     * Finds a list of students associated with a specific course name.
     *
     * @param courseName The name of the course to search students for.
     * @return A list of Student entities associated with the given course name.
     */
    @Query("SELECT s FROM Student s WHERE s.courseName = :courseName")
    List<Student> findByCourse(@Param("courseName") String courseName);

    /**
     * Finds a Student entity by their ID.
     *
     * @param id The ID of the student to search for.
     * @return An Optional containing the Student if found, otherwise empty.
     */
    @NonNull
    Optional<Student> findById(@NonNull String id);

}
