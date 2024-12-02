package edu.eci.cvds.user_management.service;

import edu.eci.cvds.user_management.model.Course;
import edu.eci.cvds.user_management.model.Responsible;
import edu.eci.cvds.user_management.model.Student;
import edu.eci.cvds.user_management.repository.AdministratorRepository;
import edu.eci.cvds.user_management.repository.CourseRepository;
import edu.eci.cvds.user_management.repository.ResponsibleRepository;
import edu.eci.cvds.user_management.repository.StudentRepository;
import edu.eci.cvds.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {
    private final ResponsibleRepository responsibleRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    /**
     * Constructor for RegisterService.
     *
     * @param responsibleRepository Repository for managing responsible entities.
     * @param studentRepository     Repository for managing student entities.
     * @param courseRepository      Repository for managing course entities.
     * @param userRepository        Repository for managing user entities (not used here, but injected).
     * @param administratorRepository Repository for managing administrator entities (not used here, but injected).
     */
    @Autowired
    public RegisterService(ResponsibleRepository responsibleRepository, StudentRepository studentRepository, CourseRepository courseRepository, UserRepository userRepository, AdministratorRepository administratorRepository) {
        this.responsibleRepository = responsibleRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Registers a new student by saving their details in the student repository.
     *
     * @param student The student object containing the student's data to be saved.
     * @return An Optional containing the saved student entity.
     */
    public Optional<Student> registerStudent(Student student) {
        return Optional.of(studentRepository.save(student));
    }

    /**
     * Registers a new responsible person by saving their details in the responsible repository.
     *
     * @param responsible The responsible object containing the data to be saved.
     * @return An Optional containing the saved responsible entity.
     */
    public Optional<Responsible> registerResponsible(Responsible responsible) {
        return Optional.of(responsibleRepository.save(responsible));
    }

    /**
     * Creates a new course by saving the course details in the course repository.
     *
     * @param course The course object containing the data to be saved.
     * @return The saved course entity.
     */
    public Course createCourse(Course course){
        return courseRepository.save(course);
    }

    /**
     * Updates the student's external ID and activates the student by calling a custom update method on the repository.
     *
     * @param id        The ID of the student to be updated.
     * @param newExtId  The new external ID to be assigned to the student.
     */
    public void updateStudentExtIdAndActivate(String id, String newExtId) {
        studentRepository.updateExtIdAndActivate(id, newExtId);
    }
}
