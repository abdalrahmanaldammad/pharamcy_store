package com.example.pharmacystore.order;

public enum OrderStatus {
  PENDING, // Order is placed but not processed yet
  PROCESSING, // Order is being prepared
  SHIPPED, // Order has been shipped
  DELIVERED, // Order has been delivered to the customer
  CANCELED // Order was canceled
}
