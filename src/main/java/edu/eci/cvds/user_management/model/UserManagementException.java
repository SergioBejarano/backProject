package edu.eci.cvds.user_management.model;

/**
 * The UserManagementException class represents custom exceptions specifically
 * related to user management operations in the system. This class provides
 * predefined error messages for common user management issues.
 */
public class UserManagementException extends Exception {

    public static final String INVALID_TOKEN = "The provided token is invalid.";
    public static final String ROLE_NOT_FOUND = "THE ROLE WAS NOT FOUND IN THE PAYLOAD OF THE TOKEN JWT";


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
