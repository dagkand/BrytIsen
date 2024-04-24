package com.gruppe24.backend.relation;

import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.idclass.HasReportedID;
import jakarta.persistence.*;

@Entity
@IdClass(HasReportedID.class)
public class HasReported {

  @Id
  @ManyToOne
  @JoinColumn(name = "UserName")
  private User user;

  @Id
  @ManyToOne
  @JoinColumn(name = "GameID")
  private Game game;

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
  public String toString() {
    return "HasReported{" +
            "user=" + user +
            ", game=" + game +
            '}';
  }
}
