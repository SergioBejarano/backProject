package edu.eci.cvds.user_management.service;

import edu.eci.cvds.user_management.model.Responsible;
import edu.eci.cvds.user_management.model.Student;
import edu.eci.cvds.user_management.repository.ResponsibleRepository;
import edu.eci.cvds.user_management.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ResponsibleRepository responsibleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudents() {
        int pageNumber = 1;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Student> students = new ArrayList<>();
        students.add(new Student("123", "Student1", "123456789", "ID", "Course1", "11111"));
        students.add(new Student("124", "Student2", "987654321", "ID", "Course2", "22222"));
        Page<Student> page = new PageImpl<>(students, pageable, students.size());
        when(studentRepository.findAll(pageable)).thenReturn(page);
        List<Student> result = userService.getStudents(pageNumber, pageSize);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepository).findAll(pageable);
    }

    @Test
    void testUpdateStudentCourse() {
        String studentId = "123";
        String newCourse = "New Course";
        Student student = new Student(studentId, "Student1", "123456789", "ID", "Old Course", "11111");
        when(studentRepository.existsById(studentId)).thenReturn(true);
        userService.updateStudentCourse(studentId, newCourse);
        verify(studentRepository).updateStudentCourse(studentId, newCourse);
    }


    @Test
    void testUpdateStudentCourseStudentNotFound() {
        String studentId = "123";
        String newCourse = "New Course";
        when(studentRepository.existsById(studentId)).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateStudentCourse(studentId, newCourse));
        assertEquals("Student with ID 123 not found.", exception.getMessage());
    }

    @Test
    void testUpdateResponsibleContactInfo() {
        String docNumber = "12345";
        String newEmail = "newemail@example.com";
        String newPhone = "123456789";
        Responsible responsible = new Responsible(docNumber, "ID", "John Doe", "987654321", "oldemail@example.com");
        when(responsibleRepository.findByDocument(docNumber)).thenReturn(Optional.of(responsible));
        userService.updateResponsibleContactInfo(docNumber, newEmail, newPhone);
        assertEquals(newEmail, responsible.getEmail());
        assertEquals(newPhone, responsible.getPhoneNumber());
        verify(responsibleRepository).save(responsible);
    }


    @Test
    void testGetAllResponsibles() {
        String doc1 = "12345";
        String doc2 = "67890";
        Responsible responsible1 = new Responsible(doc1, "ID", "John Doe", "987654321", "email1@example.com");
        Responsible responsible2 = new Responsible(doc2, "Passport", "Jane Smith", "123456789", "email2@example.com");
        List<Responsible> responsiblesList = new ArrayList<>();
        responsiblesList.add(responsible1);
        responsiblesList.add(responsible2);

        Page<Responsible> pagedResponsibles = new PageImpl<>(responsiblesList);
        when(responsibleRepository.findAll(any(Pageable.class))).thenReturn(pagedResponsibles);
        List<Responsible> result = userService.getAllResponsibles(1, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(doc1, result.get(0).getDocument());
        assertEquals(doc2, result.get(1).getDocument());
    }

    @Test
    void testDeleteByDocument() {
        String document = "12345";

        userService.deleteByDocument(document);

        verify(responsibleRepository).deleteByDocument(document);
    }




}
