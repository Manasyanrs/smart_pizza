package com.example.smartpizza.service;


import com.example.smartpizza.entity.Order;

import java.util.List;

public interface OrderService {
    void save(Order order);

    List<Order> getOrders();

}
