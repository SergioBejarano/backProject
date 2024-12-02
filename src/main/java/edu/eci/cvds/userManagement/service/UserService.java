package edu.eci.cvds.userManagement.service;

import edu.eci.cvds.userManagement.model.Student;
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


    /**
     * Retrieves the total count of students.
     *
     * @return The total number of students.
     */
    public long getTotalStudentCount() {
        return studentRepository.count();
    }

}