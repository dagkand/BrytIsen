package com.gruppe24.backend.exception;

public class ErrorCreatingRelationException extends RuntimeException {

  public ErrorCreatingRelationException(String error) {
    super(error);
  }

  public ErrorCreatingRelationException() {
    throw new ErrorCreatingRelationException("Failed to creating a relation");
  }
}
