package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String authorizationToken;

    @Test
    @Order(1)
    void testCreateUser_WhenValidUserDetailsProvided_ReturnsCreateUserDetails() throws JSONException {
        // Arrange
        JSONObject userDetailsRequestObject = new JSONObject();
        userDetailsRequestObject.put("firstName", "Sergey");
        userDetailsRequestObject.put("lastName", "Kargopoloy");
        userDetailsRequestObject.put("email", "test@test.com");
        userDetailsRequestObject.put("password", "12345678");
        userDetailsRequestObject.put("repeatPassword", "12345678");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestObject.toString(), httpHeaders);

        // Act
        ResponseEntity<UserRest> createdUserDetailsEntity = testRestTemplate.postForEntity("/users", request, UserRest.class);
        UserRest createdUserDetails = createdUserDetailsEntity.getBody();

        // Assert
        Assertions.assertEquals(HttpStatus.OK, createdUserDetailsEntity.getStatusCode());
        Assertions.assertEquals(userDetailsRequestObject.get("firstName"), createdUserDetails.getFirstName());

    }

    @Test
    @Order(2)
    void testGetUsers_WhenMissingJWT_Returns403() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application.json");

        HttpEntity request = new HttpEntity(headers);

        // Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users", HttpMethod.GET, request, new ParameterizedTypeReference<List<UserRest>>() {
        });

        // Assert
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(3)
    void testUserLogin_WhenValidCredentialProvider_ReturnsJWTinAuthorizationHeader() throws JSONException {
        // Arrange
        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@test.com");
        loginCredentials.put("password", "12345678");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<String> request = new HttpEntity<>(loginCredentials.toString(), httpHeaders);

        // Act
        ResponseEntity response = testRestTemplate.postForEntity("/users/login", request, null);

        authorizationToken = response.getHeaders().getValuesAsList("Authorization").get(0);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(authorizationToken);
        Assertions.assertNotNull(response.getHeaders().getValuesAsList("UserId").get(0));
    }

    @Test
    @Order(4)
    void testGetUsers_WhenValidJWTProvided_ReturnsUsers() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authorizationToken);

        HttpEntity request = new HttpEntity(headers);

        // Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users", HttpMethod.GET, request, new ParameterizedTypeReference<List<UserRest>>() {
        });

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().size() == 1);
    }

}
