package com.gruppe24.backend.exception;

public class GameNotFoundException extends RuntimeException {

  public GameNotFoundException(String txt) {
    super(txt);
  }

  public GameNotFoundException() {
    throw new GameNotFoundException("The game was not found");
  }
}
