package com.example.smartpizza.repository;


import com.example.smartpizza.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findOrderByUserId(int userId);

    Optional<Order> findByUserId(int userId);
}