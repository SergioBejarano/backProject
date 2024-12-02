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
 *
 * Each method processes a registration request, interacts with the RegisterService to persist the data,
 * and returns an appropriate response with success or error messages.
 */
@RestController
@RequestMapping
public class RegisterController {
    private final RegisterService registerService;

    /**
     * Constructor to initialize the RegisterController with the required RegisterService dependency.
     *
     * @param registerService the service responsible for handling the registration of students and responsible persons.
     */
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/registerStudent")
    public ResponseEntity<Map<String, Object>> registerStudent(@RequestBody Student studentData) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isRegistered = registerService.registerStudent(studentData).isPresent();
            if (isRegistered) {
                return assignValues(response, 200,
                        "Student registration successful.",
                        "The student has been registered successfully.");
            } else {
                return assignValues(response, 400,
                        "Student registration failed due to invalid input or constraints.",
                        "Failed to register the student. Please check your data.");
            }
        } catch (Exception e) {
            return assignValues(response, 500,
                    "An unexpected error occurred: " + e.getMessage(),
                    "An error occurred while processing your request. Please try again later.");
        }
    }

    private ResponseEntity<Map<String, Object>> assignValues(Map<String, Object> response, int status, String developerMessage, String userMessage) {
        response.put("status", status);
        response.put("developerMessage", developerMessage);
        response.put("userMessage", userMessage);

        HttpStatus httpStatus;
        switch (status) {
            case 200 -> httpStatus = HttpStatus.OK;
            case 400 -> httpStatus = HttpStatus.BAD_REQUEST;
            default -> httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(response);
    }



    @PostMapping("/assignExtIdStudent")
    public ResponseEntity<Map<String, Object>> assignExtIdStudent(@RequestBody Responsible newResponsible){
        Map<String, Object> response = new HashMap<>();
        return null;
    }


    @PostMapping("/registerResponsible")
    public ResponseEntity<Map<String, Object>> registerResponsible(@RequestBody Responsible newResponsible) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isRegistered = registerService.registerResponsible(newResponsible).isPresent();
            if (isRegistered) {
                return assignValues(response, 200,
                        "Responsible registration successful.",
                        "The responsible has been registered successfully.");
            } else {
                return assignValues(response, 400,
                        "Responsible registration failed due to invalid input or constraints.",
                        "Failed to register the responsible. Please check your data.");
            }
        } catch (Exception e) {
            return assignValues(response, 500,
                    "An unexpected error occurred: " + e.getMessage(),
                    "An error occurred while processing your request. Please try again later.");
        }
    }

    @PostMapping("/createCourse")
    public ResponseEntity<Map<String, Object>> createCourse(@RequestBody Course newCourse) {
        Map<String, Object> response = new HashMap<>();
        try {
            Course course = registerService.createCourse(newCourse);
            return assignValues(response, 200,
                    "Course creation successful.",
                    "The course has been created successfully.");
        } catch (Exception e) {
            return assignValues(response, 400,
                    "Course creation failed: " + e.getMessage(),
                    "Failed to create the course. Please check the provided data.");
        }
    }

}
