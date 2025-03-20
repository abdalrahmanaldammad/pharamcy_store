package com.example.pharmacystore.exceptions;

public class OrderCancellationNotAllowedException extends RuntimeException {
  public OrderCancellationNotAllowedException(String message) {
    super(message);
  }

  public OrderCancellationNotAllowedException(String message, Throwable cause) {
    super(message, cause);
  }
}
