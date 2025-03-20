package com.example.pharmacystore.exceptions;

public class ImageUploadingException extends RuntimeException {
  public ImageUploadingException(String message) {
    super(message);
  }

  public ImageUploadingException(String message, Throwable cause) {
    super(message, cause);
  }
}
