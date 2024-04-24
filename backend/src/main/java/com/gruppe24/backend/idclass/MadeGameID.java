package com.gruppe24.backend.idclass;

import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * <strong>Composite key class for MadeGame entities.</strong>
 *
 * <p>
 * This class is used to define a composite primary key for MadeGame entities
 * that are uniquely
 * identified by a combination of a {@link User} and a {@link Game}. This
 * approach allows the application
 * to associate a single game with a specific user.
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
 * of MadeGame entity instances by their composite key components.
 * </p>
 *
 * <ul>
 * <strong>Usage:</strong>
 * <li>Should be used as the {@code @IdClass} annotation value in the MadeGame
 * entity.</li>
 * <li>Does not contain JPA annotations on fields as it serves as the ID class,
 * not an entity.</li>
 * <li>Requires both user and game objects to be set for a valid composite
 * key.</li>
 * </ul>
 */
public class MadeGameID implements Serializable {

  private User user;

  private Game game;

  public MadeGameID() {
  }

  public MadeGameID(User user, Game game) {
    this.user = user;
    this.game = game;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    MadeGameID madeGameID = (MadeGameID) o;
    return Objects.equals(user, madeGameID.user) && Objects.equals(game, madeGameID.game);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, game);
  }

}
