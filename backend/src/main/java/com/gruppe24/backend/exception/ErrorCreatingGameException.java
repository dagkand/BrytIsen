package com.gruppe24.backend.exception;

public class ErrorCreatingGameException extends RuntimeException {

  public ErrorCreatingGameException(String error) {
    super(error);
  }

  public ErrorCreatingGameException() {
    throw new ErrorCreatingGameException("Failed to create a new game");
  }

}
