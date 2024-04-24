package com.gruppe24.backend.controller;

import com.gruppe24.backend.dto.GameListDTO;
import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.GameList;
import com.gruppe24.backend.relation.ContainsGame;
import com.gruppe24.backend.service.GameListRelationService;
import com.gruppe24.backend.service.GameListService;
import com.gruppe24.backend.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <strong>Game List Controller</strong>
 *
 * <p>
 * This controller provides API endpoints for managing {@link GameList}
 * entities. It serves as the interface
 * between the front-end and the service layer, handling web requests to perform
 * CRUD (Create, Read, Update, Delete) operations
 * on game list data.
 * </p>
 *
 * <strong>Responsibilities include:</strong>
 * <ul>
 * <li>Retrieving a list of all game lists from the database.</li>
 * <li>Creating new game lists.</li>
 * <li>Updating existing game lists.</li>
 * <li>Deleting game lists.</li>
 * <li>Adding games to and removing games from lists.</li>
 * </ul>
 *
 * <p>
 * All responses are formatted as JSON, making it easy for clients to parse and
 * use the data. This
 * controller works closely with the {@link GameListService} and
 * {@link GameListRelationService} to delegate business logic operations,
 * ensuring
 * that the controller remains focused on web-related tasks.
 * </p>
 *
 * <strong>Usage:</strong>
 * <ul>
 * <li><code>GET /lists</code>: Retrieves a list of all game lists.</li>
 * <li><code>POST /lists/create</code>: Creates a new game list. (Consider
 * changing to <code>POST /lists</code> for RESTful conventions)</li>
 * <li><code>PATCH /lists/{ID}</code>: Updates an existing game list identified
 * by <code>ID</code>.</li>
 * <li><code>DELETE /lists/{ID}</code>: Deletes an existing game list identified
 * by <code>ID</code>.</li>
 * <li><code>POST /lists/{ID}/{gameID}</code>: Adds a game identified by
 * <code>gameID</code> to a list identified by <code>ID</code>.</li>
 * <li><code>DELETE /lists/{ID}/{gameID}</code>: Removes a game identified by
 * <code>gameID</code> from a list identified by <code>ID</code>.</li>
 * </ul>
 *
 * @version 1.2
 */
@RestController
@RequestMapping("/lists")
public class ListController {

  private final GameListService gameListService;
  private final GameListRelationService gameListRelationService;
  private final SecurityService securityService;

  public ListController(GameListService gameListService, GameListRelationService gameListRelationService,
      SecurityService securityService) {
    this.gameListService = gameListService;
    this.gameListRelationService = gameListRelationService;
    this.securityService = securityService;
  }

  /**
   * Retrieves a list of all game-lists
   *
   * @return HTTP response <b>200 OK</b> list of {@link GameList} entites
   */
  @GetMapping
  public ResponseEntity<List<GameList>> readLists() {
    return ResponseEntity.ok(gameListService.readLists());
  }

  // GameList CRUD operations

  @GetMapping("/{ID}")
  public ResponseEntity<GameList> getGameList(@PathVariable Long ID) {
    return new ResponseEntity<>(gameListService.getList(ID), HttpStatus.OK);
  }

  @GetMapping("/{ID}/view")
  public ResponseEntity<GameList> getViewedGameList(@PathVariable Long ID) {
    return new ResponseEntity<>(gameListService.getList(ID), HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<GameList> createGameList(@RequestBody GameListDTO gameListDTO) {
    GameList list = gameListService.createGameList(gameListDTO);
    gameListRelationService.createHasGameListRelation(securityService.getAuthenticatedUser(), list);
    return new ResponseEntity<>(list, HttpStatus.CREATED);
  }

  @PatchMapping("/{ID}")
  public ResponseEntity<GameList> updateGameList(@PathVariable Long ID, @RequestBody GameListDTO gameListDTO) {
    return new ResponseEntity<>(gameListService.updateGameList(gameListDTO, ID), HttpStatus.OK);
  }

  @DeleteMapping("/{ID}")
  public ResponseEntity<String> deleteGameList(@PathVariable Long ID) {
    if (gameListService.isDeletable(ID)) {
      gameListRelationService.deleteHasGameListRelation(securityService.getAuthenticatedUser(), ID);
      gameListRelationService.removeAllGamesFromList(ID);
      gameListService.deleteGameList(ID);
      return new ResponseEntity<>("List successfully deleted", HttpStatus.OK);
    }
    return new ResponseEntity<>("Cant delete this list", HttpStatus.UNAUTHORIZED);
  }

  // GameList relation handling

  @GetMapping("/{ID}/games")
  public ResponseEntity<List<Game>> getListsGames(@PathVariable Long ID) {
    return new ResponseEntity<>(gameListRelationService.getGames(ID), HttpStatus.OK);
  }

  @PostMapping("/{ID}/{gameID}")
  public ResponseEntity<String> addGameToList(@PathVariable Long ID, @PathVariable Long gameID) {
    gameListRelationService.addGameToList(gameID, ID);
    return new ResponseEntity<>("Game successfully added to list", HttpStatus.OK);
  }

  @PutMapping("/{ID}/order")
  public ResponseEntity<String> updateListOrder(@PathVariable Long ID, @RequestBody List<Long> gameIDwithOrder) {
    gameListRelationService.updateListOrder(gameIDwithOrder, ID);
    return new ResponseEntity<>("List successfully updated", HttpStatus.OK);
  }


  @DeleteMapping("/{ID}/{gameID}")
  public ResponseEntity<String> removeGameFromList(@PathVariable Long ID, @PathVariable Long gameID) {
    gameListRelationService.removeGameFromList(gameID, ID);
    return new ResponseEntity<>("Game successfully removed from list", HttpStatus.OK);
  }

  @PostMapping("/create/permanent")
  public ResponseEntity<GameList> createPermanentGameList() {
    GameList list = gameListService.createPermanentGameList();
    return new ResponseEntity<>(list, HttpStatus.CREATED);
  }
}
