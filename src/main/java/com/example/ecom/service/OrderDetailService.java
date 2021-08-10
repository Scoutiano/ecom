package com.example.ecom.service;

import com.example.ecom.controller.exception.BadRequestException;
import com.example.ecom.controller.exception.OrderDetailIdNotFoundException;
import com.example.ecom.controller.exception.ProductIdNotFoundException;
import com.example.ecom.dto.OrderDetailDto;
import com.example.ecom.model.Entity;
import com.example.ecom.model.Order;
import com.example.ecom.model.OrderDetail;
import com.example.ecom.model.Product;
import com.example.ecom.repository.OrderDetailRepository;
import com.example.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Create a set of orderdetails for a given order
     *
     * @param order Order for details to be created for
     * @param orderDetailDtos
     */
    public void create(Order order, OrderDetailDto[] orderDetailDtos) {
        for(int i = 0; i < orderDetailDtos.length; i++) {
            Optional<Product> optionalProduct = productRepository.findById(orderDetailDtos[i].getProduct());
            if(!optionalProduct.isPresent()) {
                throw new ProductIdNotFoundException();
            }
            Product product = optionalProduct.get();
            Float price = new Float(product.getPrice());

            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setCalculatedPrice(orderDetailDtos[i].getQuantity() * price);
            orderDetail.setProduct(optionalProduct.get());
            orderDetail.setQuantity(orderDetailDtos[i].getQuantity());

            quantityCheck(product,orderDetail);

            product.setQuantity(product.getQuantity() - orderDetail.getQuantity());

            orderDetailRepository.save(orderDetail);
            productRepository.save(product);
        }
    }

    /**
     * Update a given order & its orderdetails
     *
     * @param order order for its details to be updated
     * @param orderDetailDtos order details to be updated
     */
    public void update(Order order, OrderDetailDto[] orderDetailDtos) {
        for(int i = 0; i < orderDetailDtos.length; i++) {
            Optional<Product> optionalProduct = productRepository.findById(orderDetailDtos[i].getProduct());
            if(!optionalProduct.isPresent()) {
                throw new ProductIdNotFoundException();
            }
            Product product = optionalProduct.get();
            Float price = new Float(product.getPrice());

            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setCalculatedPrice(orderDetailDtos[i].getQuantity() * price);
            orderDetail.setProduct(optionalProduct.get());
            orderDetail.setQuantity(orderDetailDtos[i].getQuantity());
            orderDetail.setId(orderDetailDtos[i].getId());

            Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(orderDetail.getId());
            if(!optionalOrderDetail.isPresent()) {
                throw new OrderDetailIdNotFoundException();
            }
            OrderDetail oldOrderDetail = optionalOrderDetail.get();


            product.setQuantity(product.getQuantity() + oldOrderDetail.getQuantity());
            quantityCheck(product,orderDetail);

            product.setQuantity(product.getQuantity() - orderDetail.getQuantity());

            orderDetailRepository.save(orderDetail);
        }
    }

    /**
     * Delete a given orderdetail, details are not actually deleted, just the quantities of their products is set as if there
     * the given orderdetail doesn't exist
     *
     * @param orderDetail
     */
    public void delete(OrderDetail orderDetail){
        Product product = productRepository.findById(orderDetail.getProduct().getId()).get();
        product.setQuantity(product.getQuantity() + orderDetail.getQuantity());
        productRepository.save(product);
    }

    /**
     * Get a list of details by its order
     *
     * @param id id of order for its details to be retrieved
     * @return return list of details for the order
     */
    public List<OrderDetail> getOrderDetails(Long id){
        Order order = new Order();
        order.setId(id);
        return orderDetailRepository.findOrderDetailsByOrder(order);
    }

    /**
     * Utility method used for quantity validation
     *
     * @param product product for containing max per customer, min per customer to be compared & quantity to be updated as well
     * @param orderDetail orderDetail to validate
     */
    public void quantityCheck(Product product, OrderDetail orderDetail){
        Integer quantity = new Integer(orderDetail.getQuantity());
        if(quantity < product.getMinPerCustomer()){
            throw new BadRequestException("Order quantity is less than the minimum for product id " + product.getId(), Entity.ORDERDETAIL, "min_per_customer");
        }

        if(quantity > product.getMaxPerCustomer()){
            throw new BadRequestException("Order quantity is more than the maximum for product id " + product.getId(), Entity.ORDERDETAIL, "max_per_customer");
        }

        if(quantity > product.getQuantity()) {
            throw new BadRequestException("The quantity of the product id " + product.getId() + " is not enough for this order", Entity.ORDERDETAIL, "order_too_big");
        }
    }
}
