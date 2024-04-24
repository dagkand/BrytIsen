package com.gruppe24.backend.exception;

public class CategoryNotFoundException extends RuntimeException {
  public CategoryNotFoundException(String txt) {
    super(txt);
  }

  public CategoryNotFoundException() {
    throw new CategoryNotFoundException("Category was not found");
  }
}
