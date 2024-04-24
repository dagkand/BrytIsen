package com.gruppe24.backend.controller;

import com.gruppe24.backend.dto.ReviewDTO;
import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.GameList;
import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.relation.Review;
import com.gruppe24.backend.service.ReviewService;
import com.gruppe24.backend.service.SecurityService;
import com.gruppe24.backend.service.UserRelationService;
import com.gruppe24.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <strong>User Controller</strong>
 *
 * <p>
 * This controller provides API endpoints for managing {@link User} entities. It
 * serves as the interface
 * between the front-end and the service layer, handling web requests to perform
 * CRUD operations
 * on user data.
 * </p>
 *
 * <ul>
 * <strong>Responsibilities include:</strong>
 * <li>Retrieving a list of all users from the database.</li>
 * <li>(Future methods might include creating, updating, and deleting users, as
 * well as
 * authentication and authorization.)</li>
 * </ul>
 *
 * <p>
 * All responses are formatted as JSON, making it easy for clients to parse and
 * use the data. This
 * controller works closely with the {@link UserService} to delegate business
 * logic operations, ensuring
 * that the controller remains focused on web-related tasks.
 * </p>
 *
 * <ul>
 * <strong>Usage:</strong>
 * <li>GET /users: Retrieves a list of all users</li>
 * <li>GET /myProfile: Retrieves the logged in user</li>
 * <li>GET /myProfile/games: Retrieves the logged in users created games</li>
 * <li>GET /myProfile/lists: Retrieves the logged in users created
 * game-lists</li>
 * <li>GET /myProfile/review: Retrieves the logged in users created reviews</li>
 *
 * </ul>
 *
 * @version 1.2
 */
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final UserRelationService userRelationService;
  private final SecurityService securityService;
  private final ReviewService reviewService;

  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  public UserController(UserService userService,
                        UserRelationService userRelationService,
                        SecurityService securityService, ReviewService reviewService) {
    this.userService = userService;
    this.userRelationService = userRelationService;
    this.securityService = securityService;
    this.reviewService = reviewService;
  }

  @GetMapping
  public ResponseEntity<List<User>> readUsers() {
    return new ResponseEntity<>(userService.readUsers(), HttpStatus.OK);
  }

  @GetMapping("/{username}")
  public ResponseEntity<User> readUser(@PathVariable String username) {
    return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
  }

  @GetMapping("/myProfile")
  public ResponseEntity<User> getUser() {
    return new ResponseEntity<>(securityService.getAuthenticatedUser(), HttpStatus.OK);
  }

  @GetMapping("/isLoggedIn")
  public ResponseEntity<Boolean> isLoggedIn() {
    try {
      securityService.getAuthenticatedUser();
      return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }
  }

  @GetMapping("/myProfile/games")
  public ResponseEntity<List<Game>> getUsersGames() {
    return new ResponseEntity<>(userRelationService.getUsersMadeGame(securityService.getAuthenticatedUser()
        .getUserName()), HttpStatus.OK);
  }

  @GetMapping("/myProfile/lists")
  public ResponseEntity<List<GameList>> getUsersLists() {
    return new ResponseEntity<>(userRelationService.getUsersLists(securityService.getAuthenticatedUser()
        .getUserName()), HttpStatus.OK);
  }

  @GetMapping("/myProfile/reviews")
  public ResponseEntity<List<Review>> getUsersReviews() {
    return new ResponseEntity<>(
            reviewService.getUsersReviews(securityService.getAuthenticatedUser().getUserName()), HttpStatus.OK);
  }

  @DeleteMapping("/myProfile/reviews/{gameID}")
  public ResponseEntity<String> deleteReview(@PathVariable Long gameID) {
    reviewService.deleteReview(securityService.getAuthenticatedUser(), gameID);
    return new ResponseEntity<>("Successfully deleted review", HttpStatus.OK);
  }

  @DeleteMapping("/{username}")
  public ResponseEntity<String> deleteUser(@PathVariable String username) {
    if (securityService.isAdmin()) {
      userRelationService.deleteUserRelations(username);
      userService.deleteUser(username);
      return new ResponseEntity<>("Successfully deleted user", HttpStatus.OK);
    }
    return new ResponseEntity<>("You dont have permission to delete user", HttpStatus.UNAUTHORIZED);
  }
}
