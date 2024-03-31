package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Sample02ControllerSampleTest {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@MockBean
	private BookingService bookingService;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/greeting");
	}

	@Test
	public void getHello() throws Exception {
		// given
		String expected = "Greetings from The Happy Hotel. We've got enough beds for 10 guests!";
		when(bookingService.getAvailablePlaceCount()).thenReturn(10);

		// when
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String actual = response.getBody();

		// then
		assertEquals(expected, actual);
	}

}
