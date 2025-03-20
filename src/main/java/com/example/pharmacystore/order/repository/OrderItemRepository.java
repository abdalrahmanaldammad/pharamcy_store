package com.example.pharmacystore.order.repository;

import com.example.pharmacystore.order.model.OrderItemModel;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemModel, Long> {

  @Query("SELECT oi FROM OrderItemModel oi  WHERE  oi.order.id=:orderId AND oi.drug.id=:drugId")
  Optional<OrderItemModel> findOrderItem(
      @Param("orderId") Long orderId, @Param("drugId") Long drugId);
}
