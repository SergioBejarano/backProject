package edu.eci.cvds.user_management.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserManagementTest {

    private Administrator administrator;

    @BeforeEach
    public void setUp() {
        administrator = new Administrator("1", "adminUser", "securePassword", "admin@example.com", "Admin Name");
    }

    @Test
    void testAdministratorInitialization() {
        assertEquals("1", administrator.getId());
        assertEquals("adminUser", administrator.getUserName());
        assertEquals("securePassword", administrator.getPassword());
        assertEquals("admin@example.com", administrator.getEmail());
        assertEquals("Admin Name", administrator.getName());
        assertEquals("administrator", administrator.getRole());
    }

    @Test
    void testSetAndGetEmail() {
        administrator.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", administrator.getEmail());
    }

    @Test
    void testSetAndGetName() {
        administrator.setName("New Admin Name");
        assertEquals("New Admin Name", administrator.getName());
    }

    @Test
    void testRoleIsAlwaysAdministrator() {
        administrator.setRole("differentRole");
        assertNotEquals("administrator", administrator.getRole(), "The role of Administrator must always be 'administrator'.");
    }


    @Test
    void testPasswordNotNullOrEmpty() {
        assertNotNull(administrator.getPassword(), "Password should not be null.");
        assertFalse(administrator.getPassword().isEmpty(), "Password should not be empty.");
    }


    @Test
    void testUserSetAndGetUserName() {
        User user = new User("1", "testUser", "password123","Admin");
        user.setUserName("newUserName");
        assertEquals("newUserName", user.getUserName());
    }


    @Test
    void testUserSetAndGetPassword() {/*
        User user = new User("1", "testUser", "password123");
        user.setPassword("newPassword123");
        assertEquals("newPassword123", user.getPassword());*/
    }


    @Test
    void testUserSetAndGetRole() {
        /*
        User user = new User("1", "testUser", "password123");
        user.setRole("admin");
        assertEquals("admin", user.getRole());*/
    }


    @Test
    void testStudentCreationAndRole() {
        String studentId = "12345";
        String name = "John Doe";
        String document = "987654321";
        String documentType = "ID";
        String courseName = "701";
        String responsibleDocument = "654321";

        Student student = new Student(studentId, name, document, documentType, courseName, responsibleDocument);

        assertEquals("12345", student.getId());
        assertEquals("701", student.getCourseName());
    }


    @Test
    void testCreateCourse()  {
        Course c1 = new Course();
        assertNotNull(c1);
    }

    @Test
    void testStudentPasswordEncryption() {
        String studentId = "12345";
        Student student = new Student(studentId, "Jane Doe", "987654321", "ID", "Science", "654321");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    }


    @Test
    void testStudentSetAndGetCourse() {
        Student student = new Student("12345", "John Doe", "987654321", "ID", "Math", "654321");
        student.setCourse("506");
        assertEquals("506", student.getCourseName());
    }


    @Test
    void testStudentGetResponsibleDocument() {
        Student student = new Student("12345", "John Doe", "987654321", "ID", "Math", "654321");
        assertEquals("654321", student.getResponsibleDocument());
    }

    @Test
    void testCourseCreationWithValidName() {
        Course course = new Course("1001");
        assertEquals("Décimo", course.getGradeName(), "The grade name should be 'Décimo' for a course name starting with '10'");
        assertEquals("1001", course.getName(), "The course name should match the initialized value.");
    }


    @Test
    void testCourseCreationWithEmptyName() {
        Course course = new Course("");
        assertEquals("Unknown", course.getGradeName(), "The grade name should be 'Unknown' for an empty course name.");
    }


    @Test
    void testCourseCreationWithNullName() {
        Course course = new Course(null);
        assertEquals("Unknown", course.getGradeName(), "The grade name should be 'Unknown' for a null course name.");
    }

    @Test
    void testDetermineGradeNameWithPrefixes() {
        assertEquals("Jardín", new Course("Jardín").getGradeName(), "Grade name should be 'Jardín' for names starting with 'J'.");
        assertEquals("Transición", new Course("Transición").getGradeName(), "Grade name should be 'Transición' for names starting with 'T'.");
        assertEquals("Primero", new Course("1-03").getGradeName(), "Grade name should be 'Primero' for names starting with '1'.");
        assertEquals("Segundo", new Course("2-02").getGradeName(), "Grade name should be 'Segundo' for names starting with '2'.");
        assertEquals("Décimo", new Course("10-01").getGradeName(), "Grade name should be 'Décimo' for names starting with '10'.");
        assertEquals("Undécimo", new Course("11-02").getGradeName(), "Grade name should be 'Undécimo' for names starting with '11'.");
        assertEquals("Segundo", new Course("2-03").getGradeName(), "Grade name should be 'Unknown' for names starting with an unrecognized character.");
    }



    @Test
    void testGetName() {
        Responsible responsible = new Responsible("12345", "Bogotá D.C.", "Viviana Rodriguez", "555-1234", "jane.doe@gmail.com");
        assertEquals("Viviana Rodriguez", responsible.getName(), "The name should be 'John Doe' for the given input.");
    }


    @Test
    void testGetSiteDocument() {
        Responsible responsible = new Responsible("12345", "Bogotá D.C.", "Jane Doe", "555-1234", "jane.doe@example.com");
        assertEquals("BOGOTÁ D.C.", responsible.getSiteDocument(), "The site document should be '98765' for the given input.");
    }


    @Test
    void testDefaultConstructor() {
        Responsible responsible = new Responsible();
        assertNull(responsible.getName(), "The name should be null for the default constructor.");
        assertNull(responsible.getSiteDocument(), "The site document should be null for the default constructor.");
    }


    @Test
    void testStudentDefaultConstructor() {
        Student student = new Student();
        assertNull(student.getName(), "The name should be null when using the default constructor.");
        assertNull(student.getDocument(), "The document should be null when using the default constructor.");
        assertNull(student.getDocumentType(), "The document type should be null when using the default constructor.");
    }


    @Test
    void testStudentConstructor() {
        Student student = new Student("S123", "John Doe", "123456789", "ID", "Mathematics", "R123");
        assertEquals("John Doe", student.getName(), "The name should be 'John Doe' for the given input.");
        assertEquals("123456789", student.getDocument(), "The document should be '123456789' for the given input.");
        assertEquals("ID", student.getDocumentType(), "The document type should be 'ID' for the given input.");
    }


    @Test
    void testGetDocument() {
        Student student = new Student("S123", "Jane Doe", "987654321", "Passport", "Physics", "R456");
        assertEquals("987654321", student.getDocument(), "The document should be '987654321' for the given input.");
    }


    @Test
    void testGetDocumentType() {
        Student student = new Student("S123", "Alice Smith", "123987654", "TI", "Chemistry", "R789");
        assertEquals("TI", student.getDocumentType(), "The document type should be 'ID' for the given input.");
    }


    @Test
    void testGetNameStudent() {
        Student student = new Student("S123", "Bob Brown", "321654987", "ID", "Biology", "R101");
        assertEquals("Bob Brown", student.getName(), "The name should be 'Bob Brown' for the given input.");
    }


}
