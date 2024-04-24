package com.gruppe24.backend.controller;

import com.gruppe24.backend.dto.ApiErrorResponse;
import com.gruppe24.backend.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler class that centralizes the handling of exceptions thrown by controllers
 * throughout the application. Utilizes the @ControllerAdvice annotation to apply to all controllers,
 * capturing specified exceptions and converting them into user-friendly API error responses.
 * Each handler method logs the exception and returns a standardized {@link ApiErrorResponse}
 * with appropriate HTTP status codes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    log.error("User not found: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "USER_NOT_FOUND");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RelationNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleErrorCreatingRelationException(RelationNotFoundException e) {
    log.error("Relation not found: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "RELATION_NOT_FOUND");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(GameNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleGameNotFoundException(GameNotFoundException e) {
    log.error("Game not found: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "GAME_NOT_FOUND");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ListNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleListNotFoundException(ListNotFoundException e) {
    log.error("List not found: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "LIST_NOT_FOUND");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ReviewNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleReviewNotFoundException(ReviewNotFoundException e) {
    log.error("Review not found: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "REVIEW_NOT_FOUND");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
    log.error("Category(ies) not found: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "CATEGORY_NOT_FOUND");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ErrorCreatingGameException.class)
  public ResponseEntity<ApiErrorResponse> handleErrorCreatingGameException(ErrorCreatingGameException e) {
    log.error("Error creating game: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "ERROR_CREATING_GAME");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ErrorCreatingRelationException.class)
  public ResponseEntity<ApiErrorResponse> handleErrorCreatingRelationException(ErrorCreatingRelationException e) {
    log.error("Error creating relation: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "ERROR_CREATING_RELATION");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserExistsException.class)
  public ResponseEntity<ApiErrorResponse> handleUserExistsException(UserExistsException e) {
    log.error("User already exists: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "USER_ALREADY_EXISTS");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidDtoException.class)
  public ResponseEntity<ApiErrorResponse> handleInvalidDtoException(InvalidDtoException e) {
    log.error("Invalid DTO: " + e.getMessage() + ":" + e.getCause());
    ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), "INVALID_DTO");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleAllUncaughtException(Exception e) {
    log.error("Unexpected error: " + e.getMessage(), e);
    ApiErrorResponse response = new ApiErrorResponse("An unexpected error occurred", "UNEXPECTED_ERROR");
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
