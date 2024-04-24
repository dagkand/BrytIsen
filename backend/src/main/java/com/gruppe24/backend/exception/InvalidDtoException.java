package com.gruppe24.backend.exception;

public class InvalidDtoException extends RuntimeException {

  public InvalidDtoException(String error) {
    super(error);
  }

  public InvalidDtoException() {
    throw new InvalidDtoException("The data transfer object didnt have the necessary information");
  }

}