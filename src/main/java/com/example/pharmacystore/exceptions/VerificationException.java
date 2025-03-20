package com.example.pharmacystore.exceptions;

public class VerificationException extends RuntimeException {
  public VerificationException(String message) {
    super(message);
  }

  public VerificationException(String message, Throwable cause) {
    super(message, cause);
  }
}
