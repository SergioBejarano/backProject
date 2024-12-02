package edu.eci.cvds.userManagement.service;

import edu.eci.cvds.userManagement.model.Course;
import edu.eci.cvds.userManagement.model.User;
import edu.eci.cvds.userManagement.model.Responsible;
import edu.eci.cvds.userManagement.model.Student;
import edu.eci.cvds.userManagement.repository.AdministratorRepository;
import edu.eci.cvds.userManagement.repository.CourseRepository;
import edu.eci.cvds.userManagement.repository.ResponsibleRepository;
import edu.eci.cvds.userManagement.repository.StudentRepository;
import edu.eci.cvds.userManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {
    private final ResponsibleRepository responsibleRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AdministratorRepository administratorRepository;

    @Autowired
    public RegisterService(ResponsibleRepository responsibleRepository, StudentRepository studentRepository, CourseRepository courseRepository, UserRepository userRepository, AdministratorRepository administratorRepository) {
        this.responsibleRepository = responsibleRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.administratorRepository = administratorRepository;
    }

    public Optional<Student> registerStudent(Student student) {
        return Optional.of(studentRepository.save(student));
    }

    public Optional<Responsible> registerResponsible(Responsible responsible) {
        return Optional.ofNullable(responsibleRepository.save(responsible));
    }

    public Course createCourse(Course course){
        return (Course) courseRepository.save(course);
    }

}
