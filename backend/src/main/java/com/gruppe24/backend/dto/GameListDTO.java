package com.gruppe24.backend.dto;

import java.util.Optional;

/**
 * Represents a GameList-Data Transfer Object.
 * <p>
 * This class carries information from the json-object retrieved from the
 * frontend.
 * </p>
 */
public class GameListDTO {

  private String name;

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  public void setName(String name) {
    this.name = name;
  }

}