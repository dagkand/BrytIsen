package com.gruppe24.backend.service;

import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.GameList;
import com.gruppe24.backend.exception.UserNotFoundException;
import com.gruppe24.backend.relation.HasGameList;
import com.gruppe24.backend.relation.MadeGame;
import com.gruppe24.backend.relation.Review;
import com.gruppe24.backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <strong>User Relation Service</strong>
 *
 * <p>
 * This service offers a comprehensive suite of functionalities for managing the
 * relationships associated with
 * the {@link Game} entity, focusing on interactions tied to user activities
 * within the application. It ensures the
 * integrity and consistency of user-related data through transactional
 * management, crucial for maintaining the
 * application's stability and data accuracy.
 * </p>
 *
 * <strong>Core Functionalities:</strong>
 * <ul>
 * <li><strong>Retrieving User-specific Data:</strong> Fetches collections of
 * {@link GameList}, {@link Game},
 * and {@link Review} entities associated with a specific user, providing
 * insights into their activities and
 * contributions.</li>
 * <li><strong>Managing User Relations:</strong> Handles the creation and
 * deletion of various user relations,
 * including game lists created by users, games made by users, and reviews
 * authored by users, ensuring a cohesive
 * user experience.</li>
 * <li><strong>Deleting User Relations:</strong> Offers a robust mechanism for
 * systematically removing all relational
 * data linked to a user, including their game lists, made games, and written
 * reviews, thereby facilitating
 * comprehensive account management and data cleanup.</li>
 * </ul>
 * <p>
 * <p>
 * Usage of this service is pivotal for ensuring that user engagement with game
 * lists, game creation, and review
 * processes is efficiently managed, safeguarded, and reflective of the dynamic
 * interactions users have within
 * the application's ecosystem.
 * </p>
 */
@Service
public class UserRelationService {

  private final UserRepository userRepository;
  private final HasGameListRepository hasGameListRepository;
  private final MadeGameRepository madeGameRepository;
  private final ReviewRepository reviewRepository;

  private final GameRepository gameRepository;
  private final GameListRepository gameListRepository;
  private final HasCategoryRepository hasCategoryRepository;
  private final ContainsGameRepository containsGameRepository;

  public UserRelationService(UserRepository userRepository, HasGameListRepository hasGameListRepository,
                             MadeGameRepository madeGameRepository, ReviewRepository reviewRepository,
                             GameRepository gameRepository, GameListRepository gameListRepository,
                             HasCategoryRepository hasCategoryRepository,
                             ContainsGameRepository containsGameRepository) {
    this.userRepository = userRepository;
    this.hasGameListRepository = hasGameListRepository;
    this.madeGameRepository = madeGameRepository;
    this.reviewRepository = reviewRepository;
    this.gameRepository = gameRepository;
    this.gameListRepository = gameListRepository;
    this.hasCategoryRepository = hasCategoryRepository;
    this.containsGameRepository = containsGameRepository;
  }

  @Transactional
  public List<GameList> getUsersLists(String username) {
    return hasGameListRepository.findByUser_UserName(username).orElse(List.of())
            .stream().map(HasGameList::getGameList).toList();
  }

  @Transactional
  public List<Game> getUsersMadeGame(String username) {
    return madeGameRepository.findByUser_UserName(username).orElse(List.of()).stream()
            .map(MadeGame::getGame).toList();
  }


  @Transactional
  public void deleteUserRelations(String username) {
    if (userRepository.findById(username).isEmpty()) {
      throw new UserNotFoundException();
    }

    hasGameListRepository.findByUser_UserName(username).ifPresent(hasGameLists -> {
      List<GameList> lists = hasGameLists.stream().map(HasGameList::getGameList).toList();
      hasGameListRepository.deleteAllInBatch(hasGameLists);
      lists.forEach(list -> {
        containsGameRepository.findByGameListIDOrderByNumberInList(list.getID()).ifPresent(containsGameRepository::deleteAllInBatch);
        gameListRepository.delete(list);
      });
    });

    madeGameRepository.findByUser_UserName(username).ifPresent(madeGames -> {
      List<Game> games = madeGames.stream().map(MadeGame::getGame).toList();
      madeGameRepository.deleteAllInBatch(madeGames);
      games.forEach(game -> {
        hasCategoryRepository.findByGame_ID(game.getID()).ifPresent(hasCategoryRepository::deleteAllInBatch);
        gameRepository.delete(game);
      });
    });

    reviewRepository.findByUser_UserName(username).ifPresent(reviewRepository::deleteAllInBatch);

  }

  @Transactional
  public void createFavoriteList(String username) {
    GameList gameList = new GameList();
    gameList.setName("Favoritter");
    HasGameList hasGameList = new HasGameList();
    hasGameList.setUser(userRepository.findById(username).orElseThrow(UserNotFoundException::new));
    hasGameList.setGameList(gameList);
    gameListRepository.save(gameList);
    hasGameListRepository.save(hasGameList);
  }
}
