package com.assignment.spring.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

  private static final String SOMETHING_WENT_WRONG = "Something went wrong with the server.";

  @ExceptionHandler(RestClientResponseException.class)
  public ResponseEntity<RestError> handleConflict(RestClientResponseException e) {
    if (401 == e.getRawStatusCode()) {
      log.error("Invalid API key for OpenWeather. Update the server configuration.", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new RestError(SOMETHING_WENT_WRONG));
    }

    if (404 == e.getRawStatusCode()) {
      log.debug("Requested city not found", e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestError("City not found"));
    }

    log.error("Unexpected error", e);
    return ResponseEntity.status(e.getRawStatusCode()).body(new RestError(SOMETHING_WENT_WRONG));
  }

  @Getter
  @AllArgsConstructor
  static class RestError {

    private final String message;
  }
}