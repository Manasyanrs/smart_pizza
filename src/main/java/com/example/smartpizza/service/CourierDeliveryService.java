package com.example.smartpizza.service;

import com.example.smartpizza.entity.CourierDelivery;
import com.example.smartpizza.entity.OrderStatus;

import java.util.List;

public interface CourierDeliveryService {
    void takeDeliveryOrderById(int deliveryId, int courierId);

    List<CourierDelivery> getDeliveriesByCourier(int id, OrderStatus orderStatus);

    void changeDeliveryOrderStatusByOrderId(int orderId);
}
