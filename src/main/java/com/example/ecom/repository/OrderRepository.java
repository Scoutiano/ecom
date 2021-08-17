package com.example.ecom.repository;

import com.example.ecom.dto.OrderReportQuery;
import com.example.ecom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery=true, value =
            "SELECT c.id as customerId, c.first_name as firstName, c.last_name as lastName,\n" +
            "\t o.id as orderId, SUM(od.calculated_price) as calculatedPrice, o.active as active\n" +
            "FROM customer c \n" +
            "LEFT JOIN `order` o ON c.id=o.customer_id \n" +
            "LEFT JOIN order_detail od ON o.id = od.order_id\n" +
            "GROUP BY c.id, o.id\n" +
            "ORDER BY c.id,calculated_price;"
    )
    List<OrderReportQuery> getCustomersOrders();
}