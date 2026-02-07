package ca.bakuryn.bugtracker.controller;

import java.util.Map;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final String MESSAGE_KEY = "message";
  private static final String SERVER_ERROR_MESSAGE = "Internal Server Error";

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFoundException(EntityNotFoundException exception) {
    return new ResponseEntity<>(Map.of(MESSAGE_KEY, exception.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException(Exception exception) {
    return new ResponseEntity<>(Map.of(MESSAGE_KEY, exception.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleCommonException(Exception exception) {
    exception.printStackTrace();
    return new ResponseEntity<>(Map.of(MESSAGE_KEY, SERVER_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
