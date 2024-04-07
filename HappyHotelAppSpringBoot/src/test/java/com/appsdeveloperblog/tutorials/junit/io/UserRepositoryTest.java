package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UsersRepository usersRepository;

    private final String userId1 = UUID.randomUUID().toString();
    private final String userId2 = UUID.randomUUID().toString();
    private final String email1 = "test@test.com";
    private final String email2 = "test2@test.com";

    @BeforeEach
    void setup() {
        UserEntity user1 = new UserEntity();
        user1.setUserId(userId1);
        user1.setEmail(email1);
        user1.setEncryptedPassword("12345678");
        user1.setFirstName("Sergey");
        user1.setLastName("Kargopolov");
        testEntityManager.persistAndFlush(user1);

        UserEntity user2 = new UserEntity();
        user2.setUserId(userId2);
        user2.setEmail(email2);
        user2.setEncryptedPassword("12345678");
        user2.setFirstName("John");
        user2.setLastName("Sears");
        testEntityManager.persistAndFlush(user2);
    }

    @Test
    void testFindByEmail_WhenGivenCorrectEmail_ReturnsUserEntity() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setFirstName("Sergey");
        user.setLastName("Kargopolov");
        user.setEmail("test1@test.com");
        user.setUserId(UUID.randomUUID().toString());
        user.setEncryptedPassword("123456789");
        testEntityManager.persistAndFlush(user);

        // Act
        UserEntity storedUser = usersRepository.findByEmail(user.getEmail());

        // Assert
        Assertions.assertEquals(user.getEmail(), storedUser.getEmail());
    }

    @Test
    void testFindByUserId_WhenGivenCorrectUserId_ReturnsUserEntity() {
        // Arrange

        // Act
        UserEntity stored = usersRepository.findByUserId(userId2);

        // Assert
        Assertions.assertNotNull(stored);
        Assertions.assertEquals(userId2, stored.getUserId());

    }

    @Test
    void testFindUsersWithEmailEndsWith_WhenGivenEmailDomain_ReturnsUsersEntity() {
        // Arrange

        // Act
        List<UserEntity> users = usersRepository.findUsersWithEmailEndingWith("@test.com");

        // Assert
        Assertions.assertEquals(2, users.size());
        Assertions.assertTrue(users.get(0).getEmail().endsWith("@test.com"));
    }

}
