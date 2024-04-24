package com.gruppe24.backend.exception;

public class ReviewNotFoundException extends RuntimeException {

  public ReviewNotFoundException(String txt) {
    super(txt);
  }

  public ReviewNotFoundException() {
    throw new ReviewNotFoundException("Review not found.");
  }

}
