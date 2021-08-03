package com.example.ecom.controller;

import com.example.ecom.controller.exception.NullIdException;
import com.example.ecom.controller.exception.OrderIdNotFoundException;
import com.example.ecom.model.Entity;
import com.example.ecom.model.Order;
import com.example.ecom.model.OrderDetail;
import com.example.ecom.repository.OrderDetailRepository;
import com.example.ecom.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;

    /**
     * {@code GET /order/:id} Get a specific order's details
     *
     * @param id id used to retrieve the order to be retrieved
     * @return return a list of details for the requested order
     * @throws NullIdException when given id is null
     * @throws OrderIdNotFoundException when the given order id does not exist
     */
    @GetMapping("/{id}/details")
    public List<OrderDetail> get(@PathVariable Long id) {

        // null check for id
        if(id == null) {
            throw new NullIdException(Entity.ORDER);
        }

        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(!optionalOrder.isPresent()) {
            throw new OrderIdNotFoundException();
        }

        Order order = optionalOrder.get();

        return order.getOrderDetails();
    }
}
