package com.gruppe24.backend.idclass;

import com.gruppe24.backend.entity.GameList;
import com.gruppe24.backend.entity.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * <strong>Composite key class for MadeGame entities.</strong>
 *
 * <p>
 * This class is used to define a composite primary key for HasGameList entities
 * that are uniquely
 * identified by a combination of a {@link User} and a {@link GameList}. This
 * approach allows the application
 * to associate a single gamelist with a specific user.
 * </p>
 *
 * <p>
 * Implements {@link java.io.Serializable} as required by JPA for composite key
 * classes.
 * </p>
 *
 * <p>
 * <strong>Note:</strong> This class overrides {@code equals()} and
 * {@code hashCode()} to ensure proper identification
 * of HasGameList entity instances by their composite key components.
 * </p>
 *
 * <ul>
 * <strong>Usage:</strong>
 * <li>Should be used as the {@code @IdClass} annotation value in the HasGameList
 * entity.</li>
 * <li>Does not contain JPA annotations on fields as it serves as the ID class,
 * not an entity.</li>
 * <li>Requires both user and game objects to be set for a valid composite
 * key.</li>
 * </ul>
 */
public class HasGameListID implements Serializable {

  private User user;

  private GameList gameList;

  public HasGameListID() {
  }

  public HasGameListID(User user, GameList gameList) {
    this.user = user;
    this.gameList = gameList;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public GameList getGameList() {
    return gameList;
  }

  public void setGameList(GameList gameList) {
    this.gameList = gameList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HasGameListID hasGameListID = (HasGameListID) o;
    return Objects.equals(user, hasGameListID.user) && Objects.equals(gameList, hasGameListID.gameList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, gameList);
  }

}
