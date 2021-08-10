package com.example.ecom.controller;

import com.example.ecom.controller.exception.NullDTOException;
import com.example.ecom.controller.exception.NullIdException;
import com.example.ecom.controller.exception.OrderIdNotFoundException;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.model.Entity;
import com.example.ecom.model.Order;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    /**
     * {@code GET /order/} Get all orders
     *
     * @return returns List of Orders
     */
    @GetMapping
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    /**
     * {@code GET /order/:id} Get a specific order's information
     *
     * @param id id used to retrieve the order to be updated
     * @return return the requested order object
     * @throws NullIdException when given id is null
     * @throws OrderIdNotFoundException when the given order id does not exist
     */
    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        // Null check for id
        if(id == null) {
            throw new NullIdException(Entity.ORDER);
        }

        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(!optionalOrder.isPresent()){
            throw new OrderIdNotFoundException();
        }

        Order order = optionalOrder.get();
        return orderService.get(order);
    }

    /**
     * {@code POST /order/} create a new order and save it to database
     *
     * @param orderDto data transfer object for order information & customer id
     * @return return saved order to confirm its creation
     */
    @PostMapping
    public Order create(@RequestBody OrderDto orderDto) {
        if(orderDto == null ) {
            throw new NullDTOException(Entity.ORDER);
        }

        return orderService.create(orderDto);
    }

    /**
     * {@code PUT /product/:id} Update an existing order by its id
     *
     * @param id id used to retrieve the order to be updated
     * @param orderDto new order with updated values
     * @return return new order to confirm update
     * @throws NullIdException when the given order id is null
     * @throws OrderIdNotFoundException when the given order id does not exist
     */
    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        // Null check for id
        if(id == null) {
            throw new NullIdException(Entity.ORDER);
        }

        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(!optionalOrder.isPresent()){
            throw new OrderIdNotFoundException();
        }
        Order order = optionalOrder.get();

        return orderService.update(order,orderDto);
    }

    /**
     * {@code DELETE /order/:id} delete a order by its given id
     *
     * @param id id of order to be deleted (Set active to false)
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        // null check for id
        if(id == null) {
            throw new NullIdException(Entity.ORDER);
        }
        orderService.delete(id);
    }
}