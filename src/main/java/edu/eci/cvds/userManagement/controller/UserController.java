package edu.eci.cvds.userManagement.controller;

//import edu.eci.cvds.userManagement.model.Responsible;
import edu.eci.cvds.userManagement.model.Student;
import edu.eci.cvds.userManagement.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<List<Student>> getStudents(
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        List<Student> students = userService.getStudents(pageNumber, pageSize);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * Endpoint to get the total count of students.
     *
     * @return The total number of students wrapped in a ResponseEntity.
     */
    @GetMapping("/students/count")
    public ResponseEntity<Long> getTotalStudentCount() {
        long totalStudents = userService.getTotalStudentCount();
        return ResponseEntity.ok(totalStudents);
    }

}