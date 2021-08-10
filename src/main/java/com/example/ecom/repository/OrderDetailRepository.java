package com.example.ecom.repository;

import com.example.ecom.model.Order;
import com.example.ecom.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    void deleteOrderDetailsByOrder(Order order);
    List<OrderDetail> findOrderDetailsByOrder(Order order);
}
