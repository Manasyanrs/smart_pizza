package com.example.smartpizza.service.impl;


import com.example.smartpizza.entity.Order;
import com.example.smartpizza.entity.OrderStatus;
import com.example.smartpizza.repository.OrderRepository;
import com.example.smartpizza.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Optional<Order> getOrderByUserId(int id) {
        return orderRepository.findOrderByUserId(id);
    }

    @Override
    public Page<Order> getUnDeliveredOrders(Optional<Integer> size, Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return orderRepository.findOrdersByOrderStatus(OrderStatus.UNDELIVERED, pageable);
    }

}
