package com.example.pharmacystore.order.repository;

import com.example.pharmacystore.order.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
  Page<OrderModel> findByUserId(Long id, Pageable pageable);
}
