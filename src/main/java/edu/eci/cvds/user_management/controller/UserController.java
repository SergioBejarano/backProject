package edu.eci.cvds.user_management.controller;

import edu.eci.cvds.user_management.model.Responsible;
import edu.eci.cvds.user_management.model.Student;
import edu.eci.cvds.user_management.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    /**
     * Endpoint to retrieve a paginated list of students.
     *
     * @param pageNumber The page number to retrieve (1-based).
     * @param pageSize The number of students to return per page.
     * @return A list of students for the specified page.
     */
    @GetMapping("/students")
    public ResponseEntity<Map<String, Object>> getStudents(
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        try {
            List<Student> students = userService.getStudents(pageNumber, pageSize);
            return buildResponse(students, HttpStatus.OK.value(), "Students retrieved successfully.", "Page retrieved: " + pageNumber);
        } catch (Exception e) {
            return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving students.", e.getMessage());
        }
    }

    /**
     * Endpoint to get the total count of students.
     *
     * @return The total number of students wrapped in a ResponseEntity.
     */
    @GetMapping("/students/count")
    public ResponseEntity<Map<String, Object>> getTotalStudentCount() {
        try {
            long totalStudents = userService.getTotalStudentCount();
            return buildResponse(totalStudents, HttpStatus.OK.value(), "Total student count retrieved successfully.", "");
        } catch (Exception e) {
            return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving total student count.", e.getMessage());
        }
    }

    /**
     * Endpoint to get the total count of responsibles.
     *
     * @return The total number of responsibles wrapped in a ResponseEntity.
     */
    @GetMapping("/responsibles/count")
    public ResponseEntity<Map<String, Object>> getTotalResponsibleCount() {
        try {
            long totalResponsibles = userService.getTotalResponsibleCount();
            return buildResponse(totalResponsibles, HttpStatus.OK.value(), "Total responsible count retrieved successfully.", "");
        } catch (Exception e) {
            return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving total responsible count.", e.getMessage());
        }
    }


    /**
     * Endpoint to update the course of a student.
     *
     * @param studentId The ID of the student to update (String).
     * @param course    The new course.
     * @return A response indicating success or failure.
     */
    @PatchMapping("/students/{id}/course")
    public ResponseEntity<Map<String, Object>> updateStudentCourse(@PathVariable("id") String studentId, @RequestParam String course) {
        try {
            userService.updateStudentCourse(studentId, course);
            return buildResponse(null, HttpStatus.NO_CONTENT.value(), "Student course updated successfully.", "");
        } catch (EntityNotFoundException e) {
            return buildResponse(null, HttpStatus.NOT_FOUND.value(), "Student not found.", e.getMessage());
        } catch (Exception e) {
            return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error updating student course.", e.getMessage());
        }
    }

    /**
     * Endpoint to update the contact information of a responsible.
     *
     * @param docNumber   The document number of the responsible.
     * @param email       The new email address, if updating.
     * @param phoneNumber The new phone number, if updating.
     * @return A response indicating success or failure.
     */
    @PatchMapping("/responsibles/{docNumber}/contact")
    public ResponseEntity<Map<String, Object>> updateResponsibleContactInfo(
            @PathVariable("docNumber") String docNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {
        try {
            userService.updateResponsibleContactInfo(docNumber, email, phoneNumber);
            return buildResponse(null, HttpStatus.NO_CONTENT.value(), "Responsible contact info updated successfully.", "");
        } catch (EntityNotFoundException e) {
            return buildResponse(null, HttpStatus.NOT_FOUND.value(), "Responsible not found.", e.getMessage());
        } catch (Exception e) {
            return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error updating responsible contact info.", e.getMessage());
        }
    }


    /**
     * Retrieves a paginated list of all responsibles.
     *
     * @param pageNumber The page number to retrieve (1-based).
     * @param pageSize   The number of responsibles to return per page.
     * @return A ResponseEntity containing a list of responsibles.
     */
    @GetMapping("/responsibles")
    public ResponseEntity<Map<String, Object>> getAllResponsibles(
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        try {
            ArrayList<Responsible> responsibles = userService.getAllResponsibles(pageNumber, pageSize);
            return buildResponse(responsibles, HttpStatus.OK.value(), "Responsibles retrieved successfully.", "Page retrieved: " + pageNumber);
        } catch (Exception e) {
            return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving responsibles.", e.getMessage());
        }
    }

    /**
     * Eliminate an economic responsible for its ID.
     *
     * @param document The number of document of the economic responsible to delete.
     * @return ResponseEntity with the status of the operation.
     */
    @DeleteMapping("delete/{document}")
    public ResponseEntity<Map<String, Object>> deleteResponsible(@PathVariable String document) {
        try {
            userService.deleteByDocument(document);
            return buildResponse(null, HttpStatus.NO_CONTENT.value(), "Responsible successfully deleted.", "");
        } catch (EntityNotFoundException e) {
            return buildResponse(null, HttpStatus.NOT_FOUND.value(), "Responsible not found.", e.getMessage());
        } catch (Exception e) {
            return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error deleting responsible.", e.getMessage());
        }
    }

    /**
     * Endpoint to update the status of a student to false.
     *
     * @param studentId The ID of the student to update.
     * @return A response indicating success or failure.
     */
    @PatchMapping("/students/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateStudent(@PathVariable("id") String studentId) {
        try {
            userService.updateStudentStatus(studentId, false);
            return buildResponse(null, HttpStatus.OK.value(), "Student successfully deactivated.", "");
        } catch (EntityNotFoundException e) {
            return buildResponse(null, HttpStatus.NOT_FOUND.value(), "Student not found.", e.getMessage());
        } catch (Exception e) {
            return buildResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error deactivating student.", e.getMessage());
        }
    }

    /**
     * Builds a response entity with the given data, status, message, and details.
     * @param data The response data. Can be any object and is optional.
     * @param status The HTTP status code for the response.
     * @param message A message describing the response.
     * @param details Additional details about the response.
     * @return A ResponseEntity containing a map with the provided information.
     */
    private ResponseEntity<Map<String, Object>> buildResponse(Object data, int status, String message, String details) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("details", details);
        if (data != null) {
            response.put("data", data);
        }
        return ResponseEntity.status(status).body(response);
    }
}