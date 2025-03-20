package com.example.pharmacystore.exceptions;

public class DrugAvailbleQuantityExcetion extends RuntimeException {
  public DrugAvailbleQuantityExcetion(String message) {
    super(message);
  }

  public DrugAvailbleQuantityExcetion(String message, Throwable cause) {
    super(message, cause);
  }
}
