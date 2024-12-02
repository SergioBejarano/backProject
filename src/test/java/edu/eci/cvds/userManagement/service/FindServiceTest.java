package edu.eci.cvds.userManagement.service;

import edu.eci.cvds.userManagement.model.Course;
import edu.eci.cvds.userManagement.model.Grade;
import edu.eci.cvds.userManagement.model.Responsible;
import edu.eci.cvds.userManagement.repository.CourseRepository;
import edu.eci.cvds.userManagement.repository.GradeRepository;
import edu.eci.cvds.userManagement.repository.ResponsibleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindServiceTest {

    @Mock
    private ResponsibleRepository responsibleRepository;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private FindService findService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindResponsibleByDocumentSuccess() {
        String document = "123456";
        Responsible expectedResponsible = new Responsible(document, "ID", "Jane Doe", "555-1234", "jane.doe@example.com");
        when(responsibleRepository.findByDocument(document)).thenReturn(Optional.of(expectedResponsible));
        Responsible result = findService.findResponsibleByDocument(document);
        assertNotNull(result);
        assertEquals(expectedResponsible, result);
        verify(responsibleRepository, times(1)).findByDocument(document);
    }

    @Test
    public void testFindResponsibleByDocumentNotFound() {
        String document = "999999";
        when(responsibleRepository.findByDocument(document)).thenReturn(Optional.empty());
        Responsible result = findService.findResponsibleByDocument(document);
        assertNull(result);
        verify(responsibleRepository, times(1)).findByDocument(document);
    }

    @Test
    public void testFindGradeByNameSuccess() {
        String gradeName = "Décimo";
        Grade expectedGrade = new Grade(gradeName, "Curso para profundizar matemáticas");
        when(gradeRepository.findByName(gradeName)).thenReturn(expectedGrade);
        Grade result = findService.findGradeByName(gradeName);
        assertNotNull(result);
        assertEquals(expectedGrade, result);
        verify(gradeRepository, times(1)).findByName(gradeName);
    }

    @Test
    public void testFindGradeByNameNotFound() {
        String gradeName = "Nonexistent Grade";
        when(gradeRepository.findByName(gradeName)).thenReturn(null);
        Grade result = findService.findGradeByName(gradeName);
        assertNull(result);
        verify(gradeRepository, times(1)).findByName(gradeName);
    }

    @Test
    public void testFindCourseByNameSuccess() {
        String courseName = "601";
        Course expectedCourse = new Course(courseName);
        when(courseRepository.findByName(courseName)).thenReturn(expectedCourse);
        Course result = findService.findCourseByName(courseName);
        assertNotNull(result);
        assertEquals(expectedCourse, result);
        verify(courseRepository, times(1)).findByName(courseName);
    }

    @Test
    public void testFindCourseByNameNotFound() {
        String courseName = "Nonexistent Course";
        when(courseRepository.findByName(courseName)).thenReturn(null);
        Course result = findService.findCourseByName(courseName);
        assertNull(result);
        verify(courseRepository, times(1)).findByName(courseName);
    }

    @Test
    public void testFindCoursesByGradeNameSuccess() {
        String gradeName = "Noveno";
        List<Course> expectedCourses = Arrays.asList(
                new Course("901"),
                new Course("902")
        );
        when(courseRepository.findByGradeName(gradeName)).thenReturn(expectedCourses);
        List<Course> result = findService.findCoursesByGradeName(gradeName);
        assertNotNull(result);
        assertEquals(expectedCourses.size(), result.size());
        assertIterableEquals(expectedCourses, result);
        verify(courseRepository, times(1)).findByGradeName(gradeName);
    }

    @Test
    public void testFindCoursesByGradeNameNotFound() {
        String gradeName = "Nonexistent Grade";
        when(courseRepository.findByGradeName(gradeName)).thenReturn(List.of());
        List<Course> result = findService.findCoursesByGradeName(gradeName);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(courseRepository, times(1)).findByGradeName(gradeName);
    }
}
