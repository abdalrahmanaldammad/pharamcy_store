package com.example.pharmacystore.exceptions;

import com.cloudinary.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.time.ZonedDateTime;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

  private final View error;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<String> errors =
        ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            ZonedDateTime.now(),
            errors);
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ZonedDateTime.now(),
            List.of());
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ApiError> handleDuplicateResourceException(
      DuplicateResourceException ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.CONFLICT.value(),
            ZonedDateTime.now(),
            List.of());
    return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ImageUploadingException.class)
  public ResponseEntity<ApiError> handleImageUploadingException(
      ImageUploadingException ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            ZonedDateTime.now(),
            List.of());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(VerificationException.class)
  public ResponseEntity<ApiError> handleVerificationException(
      VerificationException ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            ZonedDateTime.now(),
            List.of());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<ApiError> handleCategoryNotFoundException(
      CategoryNotFoundException ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            ZonedDateTime.now(),
            List.of());
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DrugNotFoundException.class)
  public ResponseEntity<ApiError> handleDrugNotFoundException(
      DrugNotFoundException ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            ZonedDateTime.now(),
            List.of());
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DrugAvailbleQuantityExcetion.class)
  public ResponseEntity<ApiError> handleDrugAvailableException(
      DrugAvailbleQuantityExcetion ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            ZonedDateTime.now(),
            List.of());
    return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(OrderCancellationNotAllowedException.class)
  public ResponseEntity<ApiError> handleOrderCancellationNotAllowedException(
      OrderCancellationNotAllowedException ex, HttpServletRequest request) {
    ApiError apiError =
        new ApiError(
            request.getRequestURI(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            ZonedDateTime.now(),
            List.of());
    return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
  }
}
