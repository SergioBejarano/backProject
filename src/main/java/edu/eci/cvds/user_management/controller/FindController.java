package edu.eci.cvds.user_management.controller;

import edu.eci.cvds.user_management.model.*;
import edu.eci.cvds.user_management.service.FindService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/find")
public class FindController {

    private final FindService findService;

    public FindController(FindService findService) {
        this.findService = findService;
    }

    /**
     * Endpoint to find a course by its name.
     * Validates the JWT token and then queries the service to find the course.
     * If the course is not found, it throws a UserManagementException with a specific error code.
     *
     * @param name The name of the course to search for.
     * @return The Course object corresponding to the provided name.
     */
    @GetMapping("/courses/name")
    public ResponseEntity<Map<String, Object>> findCourseByName(
            @RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            Course course = findService.findCourseByName(name);
            if (course == null) {
                return buildResponse(response, 404, "Course not found.", "The course with the specified name was not found.");
            }
            response.put("course", course);
            return buildResponse(response, 200, "Course found.", "Course details successfully retrieved.");
        } catch (Exception e) {
            return buildResponse(response, 500, "Unexpected error occurred in find : " + e.getMessage(), "An unexpected error occurred during the course search. Please try again later.");
        }
    }

    /**
     * Endpoint to find a grade by its name.
     * Validates the JWT token and then queries the service to find the grade.
     * If the grade is not found, it throws a UserManagementException with a specific error code.
     *
     * @param name The name of the grade to search for.
     * @return The Grade object corresponding to the provided name.
     */
    @GetMapping("/grades/name")
    public ResponseEntity<Map<String, Object>> findGradeByName(
            @RequestParam("name") String name){
        Map<String, Object> response = new HashMap<>();
        try {
            Grade grade = findService.findGradeByName(name);
            if (grade == null) {
                return buildResponse(response, 404, "Grade not found.", "The grade with the specified name was not found.");
            }
            response.put("grade", grade);
            return buildResponse(response, 200, "Grade found.", "Grade details successfully retrieved.");
        } catch (Exception e) {
            return buildResponse(response, 500, "Unexpected error occurred in find: " + e.getMessage(), "An unexpected error occurred during the degree search. Please try again later.");
        }
    }

    /**
     * Endpoint to find a responsible person by their document number.
     * Validates the JWT token and then queries the service to find the responsible person.
     * If the responsible person is not found, it throws a UserManagementException with a specific error code.
     *
     * @param responsibleDocNumber The document number of the responsible person.
     * @return The Responsible object corresponding to the provided document number.
     */
    @GetMapping("/responsible/document")
    public ResponseEntity<Map<String, Object>> findResponsibleByDocument(
            @RequestParam String responsibleDocNumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            Responsible responsible = findService.findResponsibleByDocument(responsibleDocNumber);
            if (responsible == null) {
                return buildResponse(response, 404, "Responsible not found.", "No responsible person found with the provided document number.");
            }
            response.put("responsible", responsible);
            return buildResponse(response, 200, "Responsible found.", "Responsible details successfully retrieved.");
        } catch (Exception e) {
            return buildResponse(response, 500, "Unexpected error occurred in find: " + e.getMessage(), "An unexpected error occurred during the search for the person responsible. Please try again later.");
        }
    }

    /**
     * Endpoint to find courses related to a grade.
     * Validates the JWT token and then queries the service to find the courses.
     * If no courses are found, it throws a UserManagementException with a specific error code.
     *
     * @param gradeName The name of the grade to search for related courses.
     * @return A list of Course objects related to the provided grade name.
     */
    @GetMapping("/courses/grade")
    public ResponseEntity<Map<String, Object>> findCoursesByGrade(
            @RequestParam String gradeName) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Course> courses = findService.findCoursesByGradeName(gradeName);
            if (courses == null || courses.isEmpty()) {
                return buildResponse(response, 404, "Courses not found.", "No courses found for the specified grade.");
            }
            response.put("courses", courses);
            return buildResponse(response, 200, "Courses found.", "Courses related to the grade successfully retrieved.");
        } catch (Exception e) {
            return buildResponse(response, 500, "Unexpected error occurred in find of student: " + e.getMessage(), "An unexpected error occurred. Please try again later.");
        }
    }

    /**
     * Endpoint to find a list of students by a course ID.
     * Validates the JWT token and then queries the service to find the students.
     * If no students are found for the given course, it throws a UserManagementException with a specific error code.
     *
     * @param courseName The ID of the course.
     * @return A ResponseEntity containing a JSON object with the list of students for the given course.
     */
    @GetMapping("/students/course")
    public ResponseEntity<Map<String, Object>> findStudentsByCourse(
            @RequestParam String courseName) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Student> students = findService.findStudentsByCourse(courseName);
            if (students == null || students.isEmpty()) {
                return buildResponse(response, 404, "Students not found.", "No students found for the provided course ID.");
            }
            response.put("students", students);
            return buildResponse(response, 200, "Students found.", "Student list successfully retrieved.");
        } catch (Exception e) {
            return buildResponse(response, 500, "Unexpected error occurred: " + e.getMessage(), "An unexpected error occurred during the search for students. Please try again later.");
        }
    }

    /**
     * Endpoint to find a student by their ID.
     * Validates the JWT token and then queries the service to find the student.
     * Retrieves the student's name and their responsible name and email.
     *
     * @param studentId The ID of the student to search for.
     * @return A map containing the student's name, the responsible name, and their email.
     */
    @GetMapping("/student/{id}")
    public ResponseEntity<Map<String, Object>> findStudentById(
            @PathVariable("id") String studentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Student student = findService.findStudentById(studentId);
            if (student == null) {
                return buildResponse(response, 404, "Student not found.", "No student found with the specified ID.");
            }
            Responsible responsible = findService.findResponsibleByDocument(student.getResponsibleDocument());
            if (responsible == null) {
                return buildResponse(response, 404, "Responsible not found.", "No responsible found for the student.");
            }
            response.put("studentName", student.getName());
            response.put("responsibleName", responsible.getName());
            response.put("responsibleEmail", responsible.getEmail());
            return buildResponse(response, 200, "Student found.", "Student and responsible information successfully retrieved.");
        } catch (Exception e) {
            return buildResponse(response, 500, "Unexpected error occurred: " + e.getMessage(), "An unexpected error occurred. Please try again later.");
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
            case 404 -> httpStatus = HttpStatus.NOT_FOUND;
            default -> httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(response);
    }
}