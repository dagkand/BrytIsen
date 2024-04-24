package com.gruppe24.backend.controller;

import com.gruppe24.backend.dto.UserDTO;
import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.exception.InvalidDtoException;
import com.gruppe24.backend.repository.UserRepository;
import com.gruppe24.backend.service.SecurityService;
import com.gruppe24.backend.service.UserRelationService;
import com.gruppe24.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <strong>Register Controller</strong>
 *
 * <p>
 * This controller facilitates the user registration process, interfacing
 * between the client-side application
 * and the backend service layer. It handles web requests for registering new
 * {@link User} entities
 * within the application.
 * </p>
 *
 * <ul>
 * <strong>Responsibilities include:</strong>
 * <li>Processing user registration requests and creating new user
 * accounts.</li>
 * <li>Redirecting users to appropriate pages upon successful registration or in
 * case of errors.</li>
 * </ul>
 *
 * <p>
 * All interactions are managed through HTTP requests and responses, with
 * considerations for RESTful principles and
 * user authentication states. This controller collaborates closely with the
 * {@link UserService} to execute business
 * logic operations related to user creation and validation, ensuring the
 * integrity and security of user data.
 * </p>
 *
 * <ul>
 * <strong>Usage:</strong>
 * <li>GET /register: Shows the registration form to the user, pre-filled with
 * OAuth2 authentication
 * details if available.</li>
 * <li>POST /register: Accepts and processes the registration form, creating a
 * new user account.
 * Redirects to a specified URL upon success or returns error details if the
 * registration fails.</li>
 * </ul>
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

  private final UserRepository userRepository;
  private final UserService userService;
  private final UserRelationService userRelation;

  private final SecurityService securityService;
  private static final long EXPIRATION_TIME = 900_000;
  private static final String SECRET = "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";

  private static final Logger log = LoggerFactory.getLogger(GameController.class);

  public RegisterController(UserRepository userRepository, UserService userService, SecurityService securityService,
      UserRelationService userRelation) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.userRelation = userRelation;
    this.securityService = securityService;
  }

  @PostMapping
  public ResponseEntity<String> registerUserAccount(@RequestBody UserDTO userDto, HttpServletResponse response) {
    String username = userDto.getUserName().orElseThrow(InvalidDtoException::new);
    if (userRepository.findById(username).isPresent()) {
      return new ResponseEntity<>("Username already taken", HttpStatus.CONFLICT);
    }
    userDto.setEmail(securityService.getAuthenticatedEmail());
    userService.createUser(userDto);

    userRelation.createFavoriteList(username);

    return new ResponseEntity<>("Successfully registered new user", HttpStatus.CREATED);
  }
}
