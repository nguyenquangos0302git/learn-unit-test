package com.appsdeveloperblog.estore.service;

import com.appsdeveloperblog.estore.data.UsersRepository;
import com.appsdeveloperblog.estore.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UsersRepository usersRepository;

    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        firstName = "Sergey";
        lastName  = "Kargopolov";
        email = "test@test.com";
        password = "12345678";
        repeatPassword = "12345678";
    }

    @DisplayName("User object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {
        // Arrange
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(true);

        // Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "The createUser() should not have returned null");
        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect.");
        assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        assertEquals(email, user.getEmail(), "User's email is incorrect");
        assertNotNull(user.getId(), "User id is missing");
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @DisplayName("Empty first name causes correct exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        String firstName = "";
        String expectedExceptionMessage = "User's first name is empty";

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        },"Empty first name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(expectedExceptionMessage,thrown.getMessage(),
                "Exception error message is not correct");
    }

    @DisplayName("Empty last name causes correct exception")
    @Test
    void testCreateUser_whenLastNameIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        String lastName = "";
        String expectedExceptionMessage = "User's last name is empty";

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        },"Empty last name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(expectedExceptionMessage,thrown.getMessage(),
                "Exception error message is not correct");
    }

    @Test
    void testCreateUser_WhenSaveMethodThrowsException_thenThrowsUserServiceException() {
        // Arrange
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);

        // Act
        Executable executable = () -> userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertThrows(UserServiceException.class, executable);
    }

    @Test
    void testCreateUser_WhenEmailNotificationExceptionThrown_ThenThrowsUserServiceException() {
        // Arrange
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(true);
        Mockito.doThrow(RuntimeException.class).when(emailVerificationService).scheduleEmailConfirmation(Mockito.any(User.class));

        Mockito.doNothing().when(emailVerificationService).scheduleEmailConfirmation(Mockito.any(User.class));

        // Act
        Executable executable = () -> userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertThrows(UserServiceException.class, executable);
        Mockito.verify(emailVerificationService, Mockito.times(1)).scheduleEmailConfirmation(Mockito.any(User.class));
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));

    }

    @Test
    void testCreateUser_WhenUserCreated_ThenSchedulesEmailConfirmation() {
        // Arrange
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(true);
        Mockito.doCallRealMethod().when(emailVerificationService)
                .scheduleEmailConfirmation(Mockito.any(User.class));

        // Act
        userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        Mockito.verify(emailVerificationService, Mockito.times(1)).scheduleEmailConfirmation(Mockito.any(User.class));
    }

}
