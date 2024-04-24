package com.gruppe24.backend.service;

import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.GameList;
import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.exception.ErrorCreatingRelationException;
import com.gruppe24.backend.exception.GameNotFoundException;
import com.gruppe24.backend.exception.ListNotFoundException;
import com.gruppe24.backend.exception.RelationNotFoundException;
import com.gruppe24.backend.idclass.ContainsGameID;
import com.gruppe24.backend.relation.ContainsGame;
import com.gruppe24.backend.relation.HasGameList;
import com.gruppe24.backend.repository.ContainsGameRepository;
import com.gruppe24.backend.repository.GameListRepository;
import com.gruppe24.backend.repository.GameRepository;
import com.gruppe24.backend.repository.HasGameListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameListRelationService {
  private final GameRepository gameRepository;
  private final GameListRepository gameListRepository;
  private final ContainsGameRepository containsGameRepository;
  private final HasGameListRepository hasGameListRepository;

  public GameListRelationService(GameRepository gameRepository, GameListRepository gameListRepository, ContainsGameRepository containsGameRepository, HasGameListRepository hasGameListRepository) {
    this.gameRepository = gameRepository;
    this.gameListRepository = gameListRepository;
    this.containsGameRepository = containsGameRepository;
    this.hasGameListRepository = hasGameListRepository;
  }

  @Transactional
  public User getCreator(Long listID) {
    return hasGameListRepository.findByGameList_ID(listID)
            .orElseThrow(ListNotFoundException::new)
            .getUser();
  }

  @Transactional
  public List<Game> getGames(Long listID) {
    if (containsGameRepository.findByGameListIDOrderByNumberInList(listID).isEmpty()) {
      throw new GameNotFoundException("Games not found from list");
    }
    return containsGameRepository.findByGameListIDOrderByNumberInList(listID).get().stream()
            .map(ContainsGame::getGame).toList();
  }

  @Transactional
  public void addGameToList(Long gameID, Long listID) {
    ContainsGame containsGame = new ContainsGame();
    List<ContainsGame> list = containsGameRepository.findByGameListIDOrderByNumberInList(listID)
            .orElseThrow(ListNotFoundException::new);
    containsGame.setGame(gameRepository.findByID(gameID).orElseThrow(GameNotFoundException::new));
    containsGame.setGameList(gameListRepository.findByID(listID).orElseThrow(ListNotFoundException::new));
    containsGame.setNumberInList(list.size());
    containsGameRepository.save(containsGame);
  }

  @Transactional
  public void updateListOrder(List<Long> gameIDs, Long listID) {
    for (int i = 0; i < gameIDs.size(); i++) {
      ContainsGame containsGame = containsGameRepository.findByGameList_IDAndGame_ID(listID, gameIDs.get(i))
              .orElseThrow(ListNotFoundException::new);
      containsGame.setNumberInList(i);
      containsGameRepository.save(containsGame);
    }
  }
  
  @Transactional
  public void removeGameFromList(Long gameID, Long listID) {
    Game game = gameRepository.getReferenceById(gameID);
    GameList gameList = gameListRepository.getReferenceById(listID);
    ContainsGameID containsGameID = new ContainsGameID(game, gameList);
    containsGameRepository.deleteById(containsGameID);
  }

  @Transactional
  public void removeAllGamesFromList(Long ID) {
    List<ContainsGame> containsGames = containsGameRepository.findByGameListIDOrderByNumberInList(ID).orElseThrow(RelationNotFoundException::new);
    containsGameRepository.deleteAllInBatch(containsGames);
  }

  @Transactional
  public void createHasGameListRelation(User user, GameList list) {
    if (user == null || list == null) {
      throw new ErrorCreatingRelationException();
    }
    HasGameList hasGameList = new HasGameList();
    hasGameList.setUser(user);
    hasGameList.setGameList(list);
    hasGameListRepository.save(hasGameList);
  }

  @Transactional
  public void deleteHasGameListRelation(User user, Long listID) {
    GameList list = gameListRepository.findByID(listID).orElseThrow(ListNotFoundException::new);
    hasGameListRepository.delete(hasGameListRepository.findByUserAndGameList(user, list).orElseThrow(RelationNotFoundException::new));
  }


}