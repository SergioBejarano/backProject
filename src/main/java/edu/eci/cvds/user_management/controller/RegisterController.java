package edu.eci.cvds.user_management.controller;

import edu.eci.cvds.user_management.model.Course;
import edu.eci.cvds.user_management.service.RegisterService;
import edu.eci.cvds.user_management.model.Responsible;
import edu.eci.cvds.user_management.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides endpoints to register students and responsible persons in the system.
 * It exposes two POST endpoints:
 * - `/registerStudent` for registering a new student.
 * - `/registerResponsible` for registering a new responsible person.
 * Each method processes a registration request, interacts with the RegisterService to persist the data,
 * and returns an appropriate response with success or error messages.
 */
@RestController
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;

    /**
     * Constructor for RegisterController.
     *
     * @param registerService Service for handling registration operations.
     */
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * Endpoint to register a new student.
     *
     * @param studentData The student details provided in the request body.
     * @return A ResponseEntity containing the operation's status, developer message, and user message.
     */
    @PostMapping("/students")
    public ResponseEntity<Map<String, Object>> registerStudent(
            @RequestBody Student studentData) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isRegistered = registerService.registerStudent(studentData).isPresent();
            if (isRegistered) {
                return buildResponse(response, 200, "Student registration successful.", "The student has been registered successfully.");
            } else {
                return buildResponse(response, 400, "Invalid student data.", "Failed to register the student. Please check your data.");
            }
        } catch (Exception e) {
            return buildResponse(response, 500, "Unexpected error occurred in register: " + e.getMessage(), "An error occurred while processing your request in register. Please try again later.");
        }
    }

    /**
     * Endpoint to register a new responsible person.
     *
     * @param newResponsible The responsible details provided in the request body.
     * @return A ResponseEntity containing the operation's status, developer message, and user message.
     */
    @PostMapping("/responsible")
    public ResponseEntity<Map<String, Object>> registerResponsible(
            @RequestBody Responsible newResponsible) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isRegistered = registerService.registerResponsible(newResponsible).isPresent();
            if (isRegistered) {
                return buildResponse(response, 200, "Responsible registration successful.", "The responsible has been registered successfully.");
            } else {
                return buildResponse(response, 400, "Invalid responsible data.", "Failed to register the responsible. Please check your data.");
            }
        } catch (Exception e) {
            return buildResponse(response, 500, "Unexpected error occurred: " + e.getMessage(), "An error occurred while processing your request. Please try again later.");
        }
    }

    /**
     * Endpoint to create a new course.
     *
     * @param newCourse The course details provided in the request body.
     * @return A ResponseEntity containing the operation's status, developer message, and user message.
     */
    @PostMapping("/courses")
    public ResponseEntity<Map<String, Object>> createCourse(
            @RequestBody Course newCourse) {
        Map<String, Object> response = new HashMap<>();
        try {
            registerService.createCourse(newCourse);
            return buildResponse(response, 200, "Course creation successful.", "The course has been created successfully.");
        } catch (Exception e) {
            return buildResponse(response, 400, "Invalid course data.", "Failed to create the course. Please check the provided data.");
        }
    }

    /**
     * Endpoint to assign a new external ID to a student and activate them.
     *
     * @param requestData A map containing the student's ID ("studentId") and the new external ID ("newExtId").
     * @return A ResponseEntity with the operation's status, message, and details.
     */
    @PostMapping("/assignExtIdStudent")
    public ResponseEntity<Map<String, Object>> assignExtIdStudent(
            @RequestBody Map<String, String> requestData) {
        Map<String, Object> response = new HashMap<>();
        try {
            String studentId = requestData.get("studentId");
            String newExtId = requestData.get("newExtId");
            if (studentId == null || newExtId == null) {
                return buildResponse(response, 400, "Missing required fields.", "Both 'studentId' and 'newExtId' must be provided.");
            }
            registerService.updateStudentExtIdAndActivate(studentId, newExtId);
            return buildResponse(response, 200, "External ID assignment successful.", "The external ID has been assigned successfully.");
        } catch (Exception e) {
            return buildResponse(response, 500, "Unexpected error occurred: " + e.getMessage(), "An error occurred while processing your request. Please try again later.");
        }
    }

    /**
     * Utility method to build a standardized response.
     *
     * @param response          The map to populate with response data.
     * @param status            The HTTP status code.
     * @param developerMessage  A message aimed at developers, describing the operation's result.
     * @param userMessage       A user-friendly message describing the operation's result.
     * @return A ResponseEntity containing the response map and HTTP status.
     */
    private ResponseEntity<Map<String, Object>> buildResponse(Map<String, Object> response, int status, String developerMessage, String userMessage) {
        response.put("status", status);
        response.put("developer_message", developerMessage);
        response.put("user_message", userMessage);

        HttpStatus httpStatus;
        switch (status) {
            case 200 -> httpStatus = HttpStatus.OK;
            case 400 -> httpStatus = HttpStatus.BAD_REQUEST;
            default -> httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(response);
    }
}
