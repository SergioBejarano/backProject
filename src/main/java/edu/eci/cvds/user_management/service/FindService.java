package edu.eci.cvds.user_management.service;

import edu.eci.cvds.user_management.model.Course;
import edu.eci.cvds.user_management.model.Grade;
import edu.eci.cvds.user_management.model.Responsible;
import edu.eci.cvds.user_management.repository.CourseRepository;
import edu.eci.cvds.user_management.repository.GradeRepository;
import edu.eci.cvds.user_management.repository.ResponsibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FindService {
    private final ResponsibleRepository responsibleRepository;
    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;

    /**
     * Constructor for FindService.
     *
     * @param responsibleRepository Repository for managing Responsible entities.
     * @param gradeRepository       Repository for managing Grade entities.
     * @param courseRepository      Repository for managing Course entities.
     */
    @Autowired
    public FindService(ResponsibleRepository responsibleRepository, GradeRepository gradeRepository, CourseRepository courseRepository) {
        this.responsibleRepository = responsibleRepository;

        this.gradeRepository = gradeRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Finds a Responsible entity by its document.
     *
     * @param document The document to search for.
     * @return The Responsible entity if found, or null if not found.
     */
    public Responsible findResponsibleByDocument(String document) {
        Optional<Responsible> optionalResponsible = responsibleRepository.findByDocument(document);
        return optionalResponsible.orElse(null);
    }

    /**
     * Finds a Grade entity by its name.
     *
     * @param name The name of the grade to search for.
     * @return The Grade entity if found, or null if not found.
     */
    public Grade findGradeByName(String name){
        return gradeRepository.findByName(name);
    }

    /**
     * Finds a Course entity by its name.
     *
     * @param name The name of the course to search for.
     * @return The Course entity if found, or null if not found.
     */
    public Course findCourseByName(String name){
        return courseRepository.findByName(name);
    }

    /**
     * Finds all Course entities associated with a specific grade name.
     *
     * @param gradeName The name of the grade to filter courses by.
     * @return A list of Course entities associated with the given grade name.
     */
    public List<Course> findCoursesByGradeName(String gradeName){
        return courseRepository.findByGradeName(gradeName);
    }
}
