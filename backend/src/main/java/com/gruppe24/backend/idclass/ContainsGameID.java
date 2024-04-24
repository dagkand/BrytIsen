package com.gruppe24.backend.idclass;


import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.GameList;

import java.io.Serializable;
import java.util.Objects;

/**
 * <strong>Composite key class for ContainsGame entities.</strong>
 *
 * <p>This class is used to define a composite primary key for ContainsGame entities that are uniquely
 * identified by a combination of a {@link GameList} and a {@link Game}. This approach allows the application
 * to associate a single ContainsGame with a specific game list and game pair, ensuring that each game list
 * can submit only one ContainsGame per game.</p>
 *
 * <p>Implements {@link java.io.Serializable} as required by JPA for composite key classes.</p>
 *
 * <p><strong>Note:</strong> This class overrides {@code equals()} and {@code hashCode()} to ensure proper identification
 * of ContainsGame entity instances by their composite key components.</p>
 *
 * <ul>
 *   <strong>Usage:</strong>
 *   <li>Should be used as the {@code @IdClass} annotation value in the ContainsGame entity.</li>
 *   <li>Does not contain JPA annotations on fields as it serves as the ID class, not an entity.</li>
 *   <li>Requires both game list and game objects to be set for a valid composite key.</li>
 * </ul>
 */
public class ContainsGameID implements Serializable {

  private Game game;

  private GameList gameList;

  public ContainsGameID() {
  }

  public ContainsGameID(Game game, GameList gameList) {
    this.game = game;
    this.gameList = gameList;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public GameList getGameList() {
    return gameList;
  }

  public void setGameList(GameList gameList) {
    this.gameList = gameList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainsGameID that = (ContainsGameID) o;
    return Objects.equals(game, that.game) && Objects.equals(gameList, that.gameList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(game, gameList);
  }
}