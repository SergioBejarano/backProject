package edu.eci.cvds.userManagement.model;

/**
 * The UserManagementException class represents custom exceptions specifically
 * related to user management operations in the system. This class provides
 * predefined error messages for common user management issues.
 */
public class UserManagementException extends Exception {

    public static final String USER_NOT_FOUND = "User not found with the given ID.";
    public static final String INVALID_USER_DATA = "The provided user data is invalid.";
    public static final String DUPLICATE_USER = "A user with the given information already exists.";
    public static final String DATABASE_CONNECTION_ERROR = "Unable to connect to the database.";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access attempt detected.";
    public static final String USER_CREATION_FAILED = "Failed to create a new user.";
    public static final String USER_UPDATE_FAILED = "Failed to update the user information.";
    public static final String USER_DELETION_FAILED = "Failed to delete the user.";
    public static final String EMAIL_ALREADY_EXISTS = "A user with the given email already exists.";
    public static final String EXPIRED_TOKEN = "The provided token has expired.";
    public static final String INVALID_TOKEN = "The provided token is invalid.";
    public static final String ACCESS_DENIED = "Access denied: insufficient permissions.";
    public static final String RESPONSIBLE_NOT_FOUND = "Responsible entity not found with the given document number.";
    public static final String COURSES_NOT_FOUND = "Courses not found for the given grade.";
    public static final String GRADE_NOT_FOUND = "Grade not found with the given name.";
    public static final String GRADE_NAME_REQUIRED = "The grade name is required.";
    public static final String COURSE_NOT_FOUND = "Course not found with the given name";

    /**
     * Constructs a new UserManagementException with a specified error message.
     *
     * @param message the detail message for the exception.
     */
    public UserManagementException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserManagementException with a specified error message and cause.
     *
     * @param message the detail message for the exception.
     * @param cause the underlying cause of the exception.
     */
    public UserManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}

