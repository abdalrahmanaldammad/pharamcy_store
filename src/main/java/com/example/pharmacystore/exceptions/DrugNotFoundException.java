package com.example.pharmacystore.exceptions;

public class DrugNotFoundException extends RuntimeException {
  public DrugNotFoundException(String message) {
    super(message);
  }

  public DrugNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
