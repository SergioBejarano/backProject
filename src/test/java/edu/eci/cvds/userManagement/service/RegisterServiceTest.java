package edu.eci.cvds.userManagement.service;

import edu.eci.cvds.userManagement.model.Course;
import edu.eci.cvds.userManagement.model.Responsible;
import edu.eci.cvds.userManagement.model.Student;
import edu.eci.cvds.userManagement.repository.ResponsibleRepository;
import edu.eci.cvds.userManagement.repository.StudentRepository;
import edu.eci.cvds.userManagement.repository.UserRepository;
import edu.eci.cvds.userManagement.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegisterServiceTest {

    @Mock
    private ResponsibleRepository responsibleRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testRegisterStudentFailure() {
        Student student = new Student("15667", "John Doe", "56786754", "fdgh", null, "Father");
        when(userRepository.save(any())).thenThrow(new RuntimeException("Error saving user"));
    }


    @Test
    public void testRegisterStudentSuccess() {
        Student student = new Student("15667", "John Doe", "56786754", "fdgh", null, "Father");
        when(userRepository.save(any())).thenReturn(student);
        when(studentRepository.save(any())).thenReturn(student);
        Optional<Student> result = registerService.registerStudent(student);
        assertTrue(result.isPresent());
        assertEquals(student, result.get());
        verify(studentRepository, times(1)).save(any());
    }



    @Test
    public void testRegisterResponsibleSuccess() {
        Responsible responsible = new Responsible("12345", "ID", "Jane Doe", "555-1234", "jane.doe@example.com");
        when(responsibleRepository.save(any())).thenReturn(responsible);
        Optional<Responsible> result = registerService.registerResponsible(responsible);
        assertTrue(result.isPresent());
        assertEquals(responsible, result.get());
        verify(responsibleRepository, times(1)).save(any());
    }

    @Test
    public void testRegisterResponsibleFailure() {
        Responsible responsible = new Responsible("12345", "ID", "Jane Doe", "555-1234", "jane.doe@example.com");
        when(responsibleRepository.save(any())).thenThrow(new RuntimeException("Error saving responsible"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registerService.registerResponsible(responsible);
        });
        assertEquals("Error saving responsible", exception.getMessage());
        verify(responsibleRepository, times(1)).save(any());
    }

    @Test
    public void testCreateCourseSuccess() {
        Course course = new Course("703");
        when(courseRepository.save(any())).thenReturn(course);
        Course result = registerService.createCourse(course);
        assertNotNull(result);
        assertEquals(course, result);
        verify(courseRepository, times(1)).save(any());
    }

    @Test
    public void testCreateCourseFailure() {
        Course course = new Course( "1003");
        when(courseRepository.save(any())).thenThrow(new RuntimeException("Error creating course"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registerService.createCourse(course);
        });
        assertEquals("Error creating course", exception.getMessage());
        verify(courseRepository, times(1)).save(any());
    }


}
