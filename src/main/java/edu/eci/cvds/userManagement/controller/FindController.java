package edu.eci.cvds.userManagement.controller;

import edu.eci.cvds.userManagement.model.Course;
import edu.eci.cvds.userManagement.model.Grade;
import edu.eci.cvds.userManagement.model.Responsible;
import edu.eci.cvds.userManagement.model.UserManagementException;
import edu.eci.cvds.userManagement.service.FindService;
import edu.eci.cvds.userManagement.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class FindController {
    private final FindService findService;
    private final JwtService jwtService;

    /**
     * Constructor of the FindController class.
     * Initializes the FindService and JwtService.
     *
     * @param findService Service used to find courses, grades, and responsible individuals.
     * @param jwtService Service used to validate and parse JWT tokens.
     */
    public FindController(FindService findService, JwtService jwtService){
        this.findService = findService;
        this.jwtService = jwtService;
    }

    /**
     * Endpoint to find a course by its name.
     * Validates the JWT token and then queries the service to find the course.
     * If the course is not found, it throws a UserManagementException with a specific error code.
     *
     * @param token The JWT token sent in the request header.
     * @param name The name of the course to search for.
     * @return The Course object corresponding to the provided name.
     * @throws UserManagementException If the course is not found or the token is invalid.
     */
    @GetMapping("/course/name")
    public Course findCourseByName(
            @RequestHeader("Authorization") String token,
            @RequestParam String name) throws UserManagementException {
        verifyTokenDates(token);
        Course course = findService.findCourseByName(name);
        if (course == null) {
            throw new UserManagementException(UserManagementException.COURSE_NOT_FOUND);
        }
        return course;
    }

    /**
     * Endpoint to find a grade by its name.
     * Validates the JWT token and then queries the service to find the grade.
     * If the grade is not found, it throws a UserManagementException with a specific error code.
     *
     * @param name The name of the grade to search for.
     * @param token The JWT token sent in the request header.
     * @return The Grade object corresponding to the provided name.
     * @throws UserManagementException If the grade is not found or the token is invalid.
     */
    @GetMapping("/grade/name")
    public Grade findGradeByName(
            @RequestParam("name") String name,
            @RequestHeader("Authorization") String token) throws UserManagementException {
        verifyTokenDates(token);
        Grade grade = findService.findGradeByName(name);
        if (grade == null) {
            throw new UserManagementException(UserManagementException.GRADE_NOT_FOUND);
        }
        return grade;
    }

    /**
     * Endpoint to find a responsible person by their document number.
     * Validates the JWT token and then queries the service to find the responsible person.
     * If the responsible person is not found, it throws a UserManagementException with a specific error code.
     *
     * @param responsibleDocNumber The document number of the responsible person.
     * @param token The JWT token sent in the request header.
     * @return The Responsible object corresponding to the provided document number.
     * @throws UserManagementException If the responsible person is not found or the token is invalid.
     */
    @GetMapping("/responsible/document")
    public Responsible findResponsibleByDocument(
            @RequestParam String responsibleDocNumber,
            @RequestHeader("Authorization") String token) throws UserManagementException {
        verifyTokenDates(token);
        Responsible responsible = findService.findResponsibleByDocument(responsibleDocNumber);
        if (responsible == null) {
            throw new UserManagementException(UserManagementException.RESPONSIBLE_NOT_FOUND);
        }
        return responsible;
    }

    /**
     * Endpoint to find courses related to a grade.
     * Validates the JWT token and then queries the service to find the courses.
     * If no courses are found, it throws a UserManagementException with a specific error code.
     *
     * @param gradeName The name of the grade to search for related courses.
     * @param token The JWT token sent in the request header.
     * @return A list of Course objects related to the provided grade name.
     * @throws UserManagementException If no courses are found or the token is invalid.
     */
    @GetMapping("/courses/grade")
    public List<Course> findCoursesByGrade(
            @RequestParam String gradeName,
            @RequestHeader("Authorization") String token) throws UserManagementException {
        verifyTokenDates(token);
        List<Course> courses=findService.findCoursesByGradeName(gradeName);
        if(courses==null){
            throw new UserManagementException(UserManagementException.COURSES_NOT_FOUND);
        }
        return courses;
    }

    /**
     * Verifies the validity of the JWT token and ensures that the token is not expired.
     * It also checks that the user has the role of "administrator".
     *
     * @param token The JWT token sent in the request header.
     * @throws UserManagementException If the token is expired or the user's role is not "administrator".
     */
    private void verifyTokenDates(@RequestHeader("Authorization") String token) throws UserManagementException {
        Claims claims = jwtService.parseToken(token);

        if (!jwtService.isTokenExpired(claims)) {
            throw new UserManagementException(UserManagementException.EXPIRED_TOKEN);
        }
        String role = claims.get("role", String.class);
        if (!"administrator".equals(role)) {
            throw new UserManagementException(UserManagementException.ACCESS_DENIED);
        }
    }
}
