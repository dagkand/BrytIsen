package com.gruppe24.backend.exception;

public class RelationNotFoundException extends RuntimeException {

  public RelationNotFoundException(String txt) {
    super(txt);
  }

  public RelationNotFoundException() {
    throw new RelationNotFoundException("Relation not found.");
  }

}
