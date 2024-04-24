package com.gruppe24.backend.controller;

import com.gruppe24.backend.dto.GameDTO;
import com.gruppe24.backend.dto.ReviewDTO;
import com.gruppe24.backend.entity.Category;
import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.relation.Review;
import com.gruppe24.backend.service.GameRelationService;
import com.gruppe24.backend.service.GameService;
import com.gruppe24.backend.service.ReviewService;
import com.gruppe24.backend.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <strong>Game Controller</strong>
 *
 * <p> This controller provides API endpoints for managing {@link Game} entities. It serves as the interface
 * between the front-end and the service layer, handling web requests to perform CRUD operations
 * on game data. </p>
 *
 * <ul>
 *  <strong>Responsibilities include</strong>:
 *  <li>Retrieving a list of all games from the database.</li>
 *  <li>(Future methods might include creating, updating, and deleting games, as well as authentication and authorization.)</li>
 * </ul>
 *
 * <p> All responses are formatted as JSON, making it easy for clients to parse and use the data. This
 * controller works closely with the {@link GameService} to delegate business logic operations, ensuring
 * that the controller remains focused on web-related tasks. </p>
 *
 * <ul>
 * <strong>Usage</strong>:
 *   <li>GET /games: Retrieves a list of all games</li>
 *   <li>GET /games/{ID}: Retrieves the game with the given ID</li>
 *   <li>Post /games/create: Tries to create a game with given information</li>
 *   <li>Patch /games/{ID}: Tries to update the game with given ID</li>
 *   <li>Delete /games/{ID}: Tries to delete the game with given ID</li>
 * </ul>
 *
 * @version 1.1
 */
@RestController
@RequestMapping("/games")
public class GameController {

  private final GameService gameService;
  private final GameRelationService gameRelationService;
  private final SecurityService securityService;
  private final ReviewService reviewService;

  private static final Logger log = LoggerFactory.getLogger(GameController.class);

  private GameController(GameService gameService, GameRelationService gameRelationService, SecurityService securityService, ReviewService reviewService) {
    this.gameService = gameService;
    this.gameRelationService = gameRelationService;
    this.securityService = securityService;
    this.reviewService = reviewService;
  }

  /**
   * Retrieves a list of all games
   *
   * @return A list of {@link Game} entites
   */
  @GetMapping
  public ResponseEntity<List<GameDTO>> readGames() {
    return new ResponseEntity<>(gameRelationService.convertToDto(gameService.readGames()), HttpStatus.OK);
  }

  @GetMapping("/{ID}")
  public ResponseEntity<Game> getGame(@PathVariable Long ID) {
    return new ResponseEntity<>(gameService.getGame(ID), HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<Game> createGame(@RequestBody GameDTO gameDTO) {
    log.info(gameDTO.toString());
    Game game = gameService.createGame(gameDTO);
    gameRelationService.createMadeGameRelation(game, securityService.getAuthenticatedUser());
    gameRelationService.addCategories(game.getID(), gameDTO.getCategories().orElse(List.of()));
    return new ResponseEntity<>(game, HttpStatus.CREATED);
  }

  @PatchMapping("/{ID}/update")
  public ResponseEntity<String> updateGame(@PathVariable Long ID, @RequestBody GameDTO gameDTO) {
    gameService.updateGame(gameDTO, ID);
    return new ResponseEntity<>("Game successfully updated", HttpStatus.OK);
  }

  @DeleteMapping("/{ID}/delete")
  public ResponseEntity<String> deleteGame(@PathVariable Long ID) {
    gameService.deleteGame(ID);
    gameRelationService.deleteMadeGameRelation(ID);
    return new ResponseEntity<>("Game successfully deleted", HttpStatus.OK);
  }

  @GetMapping("/{ID}/reviews")
  public ResponseEntity<List<Review>> getGameReviews(@PathVariable Long ID) {
    return new ResponseEntity<>(reviewService.getGamesReviews(ID), HttpStatus.OK);
  }

  @GetMapping("/{ID}/myreview")
  public ResponseEntity<Review> getMyReviewFromGame(@PathVariable Long ID) {
    return new ResponseEntity<>(reviewService.getReview(securityService.getAuthenticatedUser(), ID), HttpStatus.OK);
  }

  @PostMapping("/{ID}/reviews")
  public ResponseEntity<String> createReview(@PathVariable Long ID, @RequestBody ReviewDTO reviewDTO) {
    reviewService.createReview(securityService.getAuthenticatedUser(), ID, reviewDTO);
    return new ResponseEntity<>("Successfully created review", HttpStatus.CREATED);
  }

  @PatchMapping("/{ID}/reviews")
  public ResponseEntity<String> updateReview(@PathVariable Long ID, @RequestBody ReviewDTO reviewDTO) {
    reviewService.updateReview(securityService.getAuthenticatedUser(), ID, reviewDTO);
    return new ResponseEntity<>("Review successfully updated", HttpStatus.OK);
  }

  @DeleteMapping("/{ID}/reviews")
  public ResponseEntity<String> deleteReview(@PathVariable Long ID) {
    reviewService.deleteReview(securityService.getAuthenticatedUser(), ID);
    return new ResponseEntity<>("Successfully deleted review", HttpStatus.OK);
  }

  @GetMapping("/{ID}/categories")
  public ResponseEntity<List<Category>> getGamesCategories(@PathVariable Long ID) {
    return new ResponseEntity<>(gameRelationService.getGamesCategories(ID), HttpStatus.OK);
  }

  @PostMapping("/{ID}/categories")
  public ResponseEntity<String> addCategoryToGame(@PathVariable Long ID, @RequestBody List<Category> categories) {
    gameRelationService.addCategories(ID, categories);
    return new ResponseEntity<>("Successfully added categories to game", HttpStatus.CREATED);
  }

  @GetMapping("/games")
  public ResponseEntity<List<Game>> getGamesByCategory(@RequestParam String category) {
    return new ResponseEntity<>(gameService.getGamesByCategory(category), HttpStatus.OK);
  }

  @GetMapping("/{ID}/report")
  public ResponseEntity<Boolean> hasReportedGame(@PathVariable Long ID) {
    try {
      return new ResponseEntity<>(
              gameRelationService.hasReportedGame(securityService.getAuthenticatedUser(), ID),
              HttpStatus.OK);

    } catch (RuntimeException e) {
      return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/{ID}/report")
  public ResponseEntity<Boolean> reportGame(@PathVariable Long ID) {
    try {
      gameRelationService.reportGame(securityService.getAuthenticatedUser(), ID);
      return new ResponseEntity<>(true, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{ID}/report")
  public ResponseEntity<Boolean> unReportGame(@PathVariable Long ID) {
    try {
      gameRelationService.unReportGame(securityService.getAuthenticatedUser(), ID);
      return new ResponseEntity<>(true, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/categories")
  public ResponseEntity<List<Category>> getAllCategories() {
    return new ResponseEntity<>(gameRelationService.getAllCategories(), HttpStatus.OK);
  }
}
