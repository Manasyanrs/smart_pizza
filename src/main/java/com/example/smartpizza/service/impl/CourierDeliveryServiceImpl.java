package com.example.smartpizza.service.impl;

import com.example.smartpizza.entity.CourierDelivery;
import com.example.smartpizza.entity.Order;
import com.example.smartpizza.entity.OrderStatus;
import com.example.smartpizza.repository.CourierDeliveryRepository;
import com.example.smartpizza.repository.OrderRepository;
import com.example.smartpizza.service.CourierDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierDeliveryServiceImpl implements CourierDeliveryService {
    private final CourierDeliveryRepository courierDeliveryRepository;
    private final OrderRepository orderRepository;

    @Override
    public void takeDeliveryOrderById(int deliveryId, int courierId) {
        Optional<Order> orderById = orderRepository.findById(deliveryId);
        if (orderById.isPresent()) {
            CourierDelivery build = CourierDelivery.builder()
                    .order(orderById.get())
                    .courierId(courierId)
                    .build();
            courierDeliveryRepository.save(build);
            orderById.get().setOrderStatus(OrderStatus.IN_PROCESS);
            orderRepository.save(orderById.get());
        }
    }

    @Override
    public List<CourierDelivery> getDeliveriesByCourier(int id, OrderStatus orderStatus) {
        return courierDeliveryRepository.findAllByCourierIdAndOrderStatus(id, orderStatus);
    }

    @Override
    public void changeDeliveryOrderStatusByOrderId(int productId) {
        List<CourierDelivery> courierDeliveryByOrderId = courierDeliveryRepository.findCourierDeliveryByOrder_Id(productId);
        for (CourierDelivery courierDelivery : courierDeliveryByOrderId) {
            courierDelivery.setOrderStatus(OrderStatus.DELIVERED);
            courierDeliveryRepository.save(courierDelivery);
        }
    }


}
