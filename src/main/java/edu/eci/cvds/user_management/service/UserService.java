package edu.eci.cvds.user_management.service;

import edu.eci.cvds.user_management.model.Responsible;
import edu.eci.cvds.user_management.model.Student;
import edu.eci.cvds.user_management.repository.ResponsibleRepository;
import edu.eci.cvds.user_management.repository.StudentRepository;
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


    /**
     * Updates the contact information of a responsible.
     *
     * @param docNumber   The document number of the responsible.
     * @param email       The new email address, if updating.
     * @param phoneNumber The new phone number, if updating.
     */
    @Transactional
    public void updateResponsibleContactInfo(String docNumber, String email, String phoneNumber) {
        Responsible responsible = responsibleRepository.findByDocument(docNumber)
                .orElseThrow(() -> new IllegalArgumentException("Responsible with document number " + docNumber + " not found."));

        if (email != null) responsible.setEmail(email);
        if (phoneNumber != null) responsible.setPhoneNumber(phoneNumber);

        responsibleRepository.save(responsible);
    }

    /**
     * Retrieves a paginated list of responsibles.
     *
     * @param pageNumber The page number to retrieve (0-based for Pageable).
     * @param pageSize   The number of responsibles per page.
     * @return A paginated list of responsibles.
     */
    public ArrayList<Responsible> getAllResponsibles(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Responsible> page = responsibleRepository.findAll(pageable);
        return new ArrayList<>(page.getContent());
    }

    /**
     * Delete a responsible using number of document
     *
     * @param document Of the Economic Responsible
     */
    public void deleteByDocument(String document) {
        responsibleRepository.deleteByDocument(document);
    }


    public void updateStudentStatus(String studentId, boolean status) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        student.setActive(status);
        studentRepository.save(student);
    }

}