package com.gruppe24.backend;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Date;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DefaultControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private static final String SECRET = "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";
  private static final long EXPIRATION_TIME = 900_000;


  private HttpEntity<String> entity;
  @BeforeEach
  public void generateHeader() {
    String token = JWT.create()
            .withSubject("testToken")
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(SECRET.getBytes()));

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + token);
    entity = new HttpEntity<>(headers);
  }

  @Test
  public void greetingsShouldReturnDefaultMessage() throws Exception {
    ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:" + port + "/",
            HttpMethod.GET,
            entity,
            String.class
    );

    assertThat(response.getBody()).as("Response body should not be null").isNotNull();
    assertThat(response.getBody()).contains("Welcome!");
  }

  @Test
  public void statusShouldReturnServiceStatus() throws Exception {
    ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:" + port + "/status",
            HttpMethod.GET,
            entity,
            String.class
    );
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).contains("Service is up and running");
  }
}