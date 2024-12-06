package edu.eci.cvds.user_management.service;

import edu.eci.cvds.user_management.UserManagementApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserManagementApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        assertNotNull(userService, "UserService should be loaded in the context");
    }

    @Test
    void testMainMethod() {
        UserManagementApplication userManagementApplication = new UserManagementApplication();
        userManagementApplication.main(new String[] {});
    }
}
