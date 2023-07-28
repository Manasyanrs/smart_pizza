package com.example.smartpizza.service.impl;


import com.example.smartpizza.entity.Order;
import com.example.smartpizza.repository.OrderRepository;
import com.example.smartpizza.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderByUserId(int id) {
        return orderRepository.findOrderByUserId(id);
    }

}
