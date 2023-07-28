package com.example.smartpizza.service;


import com.example.smartpizza.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void save(Order order);

    List<Order> getOrders();

    Optional<Order> getOrderByUserId(int id);
}
