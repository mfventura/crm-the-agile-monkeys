package com.theagilemonkeys.mfventura.crm.infrastructure.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ErrorHandler {
  private final Log logger = LogFactory.getLog(getClass());
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public Map<String, String> handleInternalErrors(Exception e) {
    Map<String, String> errors = new HashMap<>();
    final var errorUUID = UUID.randomUUID().toString();
    errors.put("error:", "Unexpected API error, contact with an administrator. error-code:"+errorUUID);
    logger.error("Error: "+errorUUID, e);
    return errors;
  }
}
