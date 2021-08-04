package com.example.ecom.service;

import com.example.ecom.controller.exception.ProductIdNotFoundException;
import com.example.ecom.dto.OrderDetailDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.model.Customer;
import com.example.ecom.model.Order;
import com.example.ecom.model.OrderDetail;
import com.example.ecom.model.Product;
import com.example.ecom.repository.OrderDetailRepository;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * Method to add an order with its orderdetails
     *
     * @param orderDto data transfer object containing all order and orderdetail information
     * @return return copy of created order for confirmation
     */
    public Order create(OrderDto orderDto) {

        Order order = new Order();
        Customer customer = new Customer();

        customer.setId(orderDto.getCustomer());
        order.setCustomer(customer);

        orderDetailService.create(order,orderDto.getOrderDetails());

        orderRepository.save(order);

        return orderRepository.findById(order.getId()).get();
    }

    /**
     * Update a given order
     *
     * @param order
     * @param orderDto
     * @return
     */
    public Order update(Order order, OrderDto orderDto){

        Customer customer = order.getCustomer();
        customer.setId(orderDto.getCustomer());
        order.setCustomer(customer);

        orderDetailService.create(order,orderDto.getOrderDetails());

        orderRepository.save(order);

        return orderRepository.findById(order.getId()).get();
    }

    public void delete(Long id) {

    }
}
