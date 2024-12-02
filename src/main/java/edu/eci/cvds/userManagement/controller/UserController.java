package edu.eci.cvds.userManagement.controller;

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