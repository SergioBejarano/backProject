package edu.eci.cvds.userManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import edu.eci.cvds.userManagement.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    /**
     * Finds a Course entity by its name.
     *
     * @param name The name of the course to find.
     * @return The Course entity matching the given name, or null if none is found.
     */
    Course findByName(String name);

    /**
     * Finds all Course entities associated with a specific grade name.
     *
     * @param gradeName The name of the grade to find courses for.
     * @return A list of Course entities associated with the specified grade name.
     */
    List<Course> findByGradeName(String gradeName);

}
