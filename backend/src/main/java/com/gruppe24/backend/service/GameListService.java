package com.gruppe24.backend.service;

import com.gruppe24.backend.dto.GameListDTO;
import com.gruppe24.backend.entity.GameList;
import com.gruppe24.backend.exception.GameNotFoundException;
import com.gruppe24.backend.exception.ListNotFoundException;
import com.gruppe24.backend.repository.GameListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <strong>Service layer for managing {@link GameList} entities.</strong>
 *
 * <p>
 * This service encapsulates the business logic for game list management, acting
 * as an intermediary
 * between the controller layer and the data access layer. It leverages the
 * {@link GameListRepository}
 * for CRUD operations on {@link GameList} entities, ensuring that business
 * rules and logic are
 * consistently applied. This abstraction allows for cleaner controllers and
 * promotes the separation
 * of concerns within the application.
 * </p>
 *
 * <p>
 * Methods in this service are transactional, ensuring data integrity and
 * consistency during
 * operations that involve multiple steps or queries. This transactional
 * management is crucial
 * for operations that modify data, safeguarding against partial updates and
 * data anomalies.
 * </p>
 *
 * <ul>
 * <strong>Key Functionalities Include:</strong>
 * <li>Retrieving all game lists from the database.</li>
 * <li>(Future functionalities such as creating, updating, and deleting game
 * lists.)</li>
 * </ul>
 *
 * <p>
 * Usage of this service should be limited to interaction through higher-level
 * components
 * such as REST controllers or other services requiring game list manipulation
 * and retrieval.
 * </p>
 */
@Service
public class GameListService {

  private final GameListRepository gameListRepository;

  public GameListService(GameListRepository gameListRepository) {
    this.gameListRepository = gameListRepository;
  }

  /**
   * Retrieves all game lists from the repository.
   *
   * @return A list of {@link GameList} entities.
   */
  @Transactional
  public List<GameList> readLists() {
    return gameListRepository.findAll();
  }

  @Transactional
  public GameList getList(Long listID) {
    return gameListRepository.findByID(listID).orElseThrow(ListNotFoundException::new);
  }

  @Transactional
  public GameList createGameList(GameListDTO gameListDTO) {
    GameList gameList = new GameList();
    gameListDTO.getName().ifPresentOrElse(gameList::setName, GameNotFoundException::new);
    return gameListRepository.save(gameList);
  }

  @Transactional
  public GameList updateGameList(GameListDTO gameListDTO, Long ID) {
    GameList gameList = gameListRepository.findByID(ID).orElseThrow(ListNotFoundException::new);
    gameListDTO.getName().ifPresentOrElse(gameList::setName, GameNotFoundException::new);
    return gameListRepository.save(gameList);
  }

  @Transactional
  public void deleteGameList(Long ID) {
    GameList list = gameListRepository.findByID(ID).orElseThrow(ListNotFoundException::new);
    if (list.getName().equals("Favoritter")) {
      throw new IllegalArgumentException("Cannot delete permanent list");
    }
    gameListRepository.delete(list);
  }

  // Example of a method that creates a permanent game list
  @Transactional
  public GameList createPermanentGameList() {
    GameList gameList = new GameList();
    gameList.setName("Favoritter");
    return gameListRepository.save(gameList);
  }

  public boolean isDeletable(Long ID) {
    GameList gameList = gameListRepository.findByID(ID).orElseThrow(ListNotFoundException::new);
    return !gameList.getName().equals("Favoritter");
  }
}
