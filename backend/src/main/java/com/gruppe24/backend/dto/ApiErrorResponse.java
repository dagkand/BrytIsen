package com.gruppe24.backend.dto;

public class ApiErrorResponse {

  private String message;

  private String errorCode;

  public ApiErrorResponse(String message, String errorCode) {
    this.message = message;
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
}
