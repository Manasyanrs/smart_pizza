package com.example.smartpizza.repository;


import com.example.smartpizza.entity.Order;
import com.example.smartpizza.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findOrderByUserId(int userId);

    Page<Order> findOrdersByOrderStatus(OrderStatus orderStatus, Pageable pageable);

}
