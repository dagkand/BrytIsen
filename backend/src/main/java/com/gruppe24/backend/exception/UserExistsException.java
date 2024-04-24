package com.gruppe24.backend.exception;

public class UserExistsException extends RuntimeException {

  public UserExistsException(String error) {
    super(error);
  }

  public UserExistsException() {
    throw new UserExistsException("User already exists");
  }

}
