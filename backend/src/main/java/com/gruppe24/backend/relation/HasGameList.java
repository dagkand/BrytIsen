package com.gruppe24.backend.relation;

import com.gruppe24.backend.entity.GameList;
import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.idclass.HasGameListID;
import jakarta.persistence.*;

/**
 * Represents a HasGameList relationship in the database.
 * <p>
 * This class contains the username of the user and the ID of the GameList the user has.
 * </p>
 */
@Entity
@IdClass(HasGameListID.class)
public class HasGameList {

  @Id
  @ManyToOne
  @JoinColumn(name = "UserName")
  private User user;

  @Id
  @OneToOne
  @JoinColumn(name = "ListId")
  private GameList gameList;

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

  // TODO: ADD TOSTRING

}
