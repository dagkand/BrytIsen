package com.gruppe24.backend.relation;

import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.idclass.MadeGameID;
import jakarta.persistence.*;

import java.time.LocalDateTime;


/**
 * Represents a MadeGame relationship in the database.
 * <p>
 * This class contains the ID of the game and the ID of the user who made it, as well as a timestamp of when the game was made.
 * </p>
 */
@Entity
@IdClass(MadeGameID.class)
public class MadeGame {

  @Id
  @ManyToOne
  @JoinColumn(name = "UserName")
  private User user;

  @Id
  @ManyToOne
  @JoinColumn(name = "GameID")
  private Game game;

  private LocalDateTime createdAt;

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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "MadeGame{" +
            "user=" + user +
            ", game=" + game +
            ", timestamp=" + createdAt +
            '}';
  }
}
