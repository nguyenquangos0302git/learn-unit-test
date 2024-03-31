package com.appsdeveloperblog.estore.service;

import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        // Arrange
        UserService userService = new UserServiceImpl();
        String firstName = "Quang";
        String lastName = "Nguyen";
        String email = "test@gmail.com";
        String password = "12345";
        String repeatPassword = "12345";

        // Act
        userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
    }

}
