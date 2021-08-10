package com.example.ecom.service;

import com.example.ecom.controller.exception.OrderIdNotFoundException;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.model.Customer;
import com.example.ecom.model.Order;
import com.example.ecom.model.OrderDetail;
import com.example.ecom.model.Product;
import com.example.ecom.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
     * Get a specific order's information with a calculated price
     *
     * @param order order to have its price calculated
     * @return return requested order with its calculatedPrice
     */
    public Order get(Order order){
        order.setCalculatedPrice(calculatePrice(order.getId()));
        return order;
    }

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

        orderRepository.save(order);
        orderDetailService.create(order,orderDto.getOrderDetails());


        return orderRepository.findById(order.getId()).get();
    }

    /**
     * Update a given order
     *
     * @param order Order to be updated
     * @param orderDto data transfer object for information to be changed & details
     * @return return copy of updated order
     */
    public Order update(Order order, OrderDto orderDto){

        Customer customer = order.getCustomer();
        customer.setId(orderDto.getCustomer());
        order.setCustomer(customer);

        orderDetailService.update(order,orderDto.getOrderDetails());

        orderRepository.save(order);

        return orderRepository.findById(order.getId()).get();
    }

    /**
     * Delete an order by its id, this is a soft delete and does not delete the orderDetails
     *
     * @param id id of the order to be deleted
     */
    public void delete(Long id) {

        List<OrderDetail> orderDetails = orderDetailService.getOrderDetails(id);
        for(OrderDetail orderDetail:orderDetails) {
            orderDetailService.delete(orderDetail);
        }
        orderRepository.deleteById(id);
    }

    /**
     * Utility method to calculate price of a given order through its list of details
     *
     * @param id id of order for its price to be calculated
     * @return return the calculatedPrice
     */
    public Float calculatePrice(Long id) {
        Float calculatedPrice = new Float(0);

        List<OrderDetail> orderDetails = orderDetailService.getOrderDetails(id);
        for(OrderDetail orderDetail:orderDetails) {
            calculatedPrice += orderDetail.getCalculatedPrice();
        }
        return calculatedPrice;
    }
}
