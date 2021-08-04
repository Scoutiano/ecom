package com.example.ecom.service;

import com.example.ecom.controller.exception.ProductIdNotFoundException;
import com.example.ecom.dto.OrderDetailDto;
import com.example.ecom.model.Order;
import com.example.ecom.model.OrderDetail;
import com.example.ecom.model.Product;
import com.example.ecom.repository.OrderDetailRepository;
import com.example.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    public void create(Order order, OrderDetailDto[] orderDetailDtos) {
        for(int i = 0; i < orderDetailDtos.length; i++) {
            Optional<Product> optionalProduct = productRepository.findById(orderDetailDtos[i].getProduct());
            if(!optionalProduct.isPresent()) {
                throw new ProductIdNotFoundException();
            }
            Float price = new Float(optionalProduct.get().getPrice());

            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setCalculatedPrice(orderDetailDtos[i].getQuantity() * price);
            orderDetail.setProduct(optionalProduct.get());
            orderDetail.setQuantity(orderDetailDtos[i].getQuantity());

            orderDetailRepository.save(orderDetail);
        }
    }
}
