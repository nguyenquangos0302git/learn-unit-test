package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.UUID;

@DataJpaTest
public class UserEntityIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private UserEntity userEntity;

    @BeforeEach
    void setup() {
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopolov");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("12345678");
    }

    @Test
    void testUserEntity_WhenValidUserDetailsProvided_ShouldReturnStoredUserDetails() {
        // Arrange

        // Act
        UserEntity storedUserEntity = testEntityManager.persistAndFlush(userEntity);

        // Assert
        Assertions.assertTrue(storedUserEntity.getId() > 0);
        Assertions.assertEquals(userEntity.getUserId(), storedUserEntity.getUserId());
    }

    @Test
    void testUserEntity_WhenFirstNameIsTooLong_ShouldThrowException() {
        // Arrange
        userEntity.setFirstName("123211231232312dwadaw312d1d12dasdawdawdawd1d123123121");

        // Act
        Executable executable = () -> testEntityManager.persistAndFlush(userEntity);

        // Assert
        Assertions.assertThrows(PersistenceException.class, executable);
    }

    @Test
    void testUserEntity_WhenExistingUserIdProvided_ShouldThrowException() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setUserId("1");
        user.setFirstName("Sergey");
        user.setLastName("Kargopolov");
        user.setEmail("test@test.com");
        user.setEncryptedPassword("12345678");

        userEntity.setUserId("1");

        // Act
        testEntityManager.persistAndFlush(user);
        Executable executable = () -> testEntityManager.persistAndFlush(userEntity);

        // Assert
        Assertions.assertThrows(PersistenceException.class, executable);

    }

}
