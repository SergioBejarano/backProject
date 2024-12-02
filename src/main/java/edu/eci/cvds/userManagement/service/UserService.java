package edu.eci.cvds.userManagement.service;

import edu.eci.cvds.userManagement.model.Responsible;
import edu.eci.cvds.userManagement.model.Student;
import edu.eci.cvds.userManagement.repository.ResponsibleRepository;
import edu.eci.cvds.userManagement.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ResponsibleRepository responsibleRepository;

    /**
     * Retrieves a paginated list of students.
     *
     * @param pageNumber The page number to retrieve (1-based).
     * @param pageSize The number of students to return per page.
     * @return A list of students for the specified page.
     * @throws SQLException If an SQL error occurs while retrieving the students.
     */
    public List<Student> getStudents(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return studentRepository.findAll(pageable).getContent();
    }

    /**
     * Retrieves the total count of students.
     *
     * @return The total number of students.
     */
    public long getTotalStudentCount() {
        return studentRepository.count();
    }

    /**
     * Updates the course of a student by their ID.
     *
     * @param studentId The ID of the student to update.
     * @param course    The new course.
     */
    @Transactional
    public void updateStudentCourse(String studentId, String course) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.updateStudentCourse(studentId, course);
        } else {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
        }
    }


    /**
     * Retrieves the total count of responsibles.
     *
     * @return The total number of responsibles.
     */
    public long getTotalResponsibleCount() {
        return responsibleRepository.count();
    }

}