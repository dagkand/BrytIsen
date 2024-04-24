package com.gruppe24.backend.idclass;

import com.gruppe24.backend.entity.Category;
import com.gruppe24.backend.entity.Game;

import java.io.Serializable;
import java.util.Objects;

/**
 * <strong>Composite key class for ContainsGame entities.</strong>
 *
 * <p>This class is used to define a composite primary key for ContainsGame entities that are uniquely
 * identified by a combination of a {@link Category} and a {@link Game}. This approach allows the application
 * to associate a single ContainsGame with a specific category and game pair, ensuring that each category
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
 *   <li>Requires both category and game objects to be set for a valid composite key.</li>
 * </ul>
 */
public class HasCategoryID implements Serializable {

  private Game game;

  private Category category;

  public HasCategoryID() {
  }

  public HasCategoryID(Game game, Category category) {
    this.game = game;
    this.category = category;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HasCategoryID that = (HasCategoryID) o;
    return Objects.equals(game, that.game) && Objects.equals(category, that.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(game, category);
  }
}