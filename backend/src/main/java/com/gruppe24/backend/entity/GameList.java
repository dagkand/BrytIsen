package com.gruppe24.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a gameList entity in the database.
 * <p>
 * This class is mapped to the GameList table in the database and includes details about each gameList such as its ID and name.
 * </p>
 */
@Entity
public class GameList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long ID;

  private String name;

  public long getID() {
    return ID;
  }

  public void setID(long iD) {
    ID = iD;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "GameList [ID=" + ID + ", name=" + name + "]";
  }

}
