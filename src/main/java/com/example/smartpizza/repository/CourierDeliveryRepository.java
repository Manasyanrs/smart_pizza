package com.example.smartpizza.repository;

import com.example.smartpizza.entity.CourierDelivery;
import com.example.smartpizza.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierDeliveryRepository extends JpaRepository<CourierDelivery, Integer> {
    List<CourierDelivery> findAllByCourierIdAndOrderStatus(int courierId, OrderStatus orderStatus);

    List<CourierDelivery> findCourierDeliveryByOrder_Id(int orderId);
}
