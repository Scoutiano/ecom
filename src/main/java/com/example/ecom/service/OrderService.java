package com.example.ecom.service;

import com.example.ecom.controller.exception.OrderIdNotFoundException;
import com.example.ecom.dto.CustomerOrderReport;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.dto.OrderReportQuery;
import com.example.ecom.model.Customer;
import com.example.ecom.model.Order;
import com.example.ecom.model.OrderDetail;
import com.example.ecom.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerService customerService;

    /**
     * Get a specific order's information with a calculated price
     *
     * @param id order id to have its price calculated
     * @return return requested order with its calculatedPrice
     */
    public Order get(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(!optionalOrder.isPresent()){
            throw new OrderIdNotFoundException();
        }
        Order order = optionalOrder.get();

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
     * @param orderDto data transfer object for information to be changed & details
     * @return return copy of updated order
     */
    public Order update(OrderDto orderDto){

        Optional<Order> optionalOrder = orderRepository.findById(orderDto.getId());
        if(!optionalOrder.isPresent()){
            throw new OrderIdNotFoundException();
        }
        Order order = optionalOrder.get();

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

    //For my own memory
    //4 conditions
    //A new customer is started
    //New customer has no orders
    //New customer has an initial order
    //Adding order to current customer

    /**
     * Method used for mapping a list of orders to their customer
     * receives raw data from orderRepository in the form of a projection interface
     * named OrderReportQuery
     *
     *
     *
     * @return A mapped list of orders by their customers
     */
    public Map<Long,CustomerOrderReport> getCustomersOrders() {

        Map<Long,CustomerOrderReport> customersOrders = new HashMap<>();

        List<OrderReportQuery> orderReportQuerys = orderRepository.getCustomersOrders();
        CustomerOrderReport customer = new CustomerOrderReport();
        List<Order> orders = new ArrayList<>();
        customer.setId(-1L);
        for(OrderReportQuery orderReportQuery:orderReportQuerys) {

            //Same customer as the previous iteration
            if(customer.getId() == orderReportQuery.getCustomerId()) {
                Order order = orderReportQueryToOrder(orderReportQuery, customer);
                customer.addOrder(order);
            }
            //New customer
            else {

                //Map previous customer if not first iteration
                if(customer.getId() != -1L) {
                    customersOrders.put(customer.getId(), customer);
                }

                customer = customerService.orderReportQueryToCustomer(orderReportQuery);

                orders = new ArrayList<>();
                if(orderReportQuery.getOrderId() == null) { //Customer has no orders
                    customersOrders.put(customer.getId(),customer);
                } else {    //Add first order
                    Order order = orderReportQueryToOrder(orderReportQuery, customer);
                    customer.addOrder(order);
                }
            }
        }

        return customersOrders;
    }

    public Order orderReportQueryToOrder(OrderReportQuery orderReportQuery, CustomerOrderReport customer) {
        Order order = new Order();
        Customer customer1 = new Customer();
        customer1.setId(customer.getId());
        order.setCustomer(customer1);
        order.setId(orderReportQuery.getOrderId());
        order.setCalculatedPrice(orderReportQuery.getCalculatedPrice());
        order.setActive(orderReportQuery.getActive());

        return order;
    }
}
