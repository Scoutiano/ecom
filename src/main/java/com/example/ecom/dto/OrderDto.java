package com.example.ecom.dto;

import com.example.ecom.model.OrderDetail;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;

@Setter
@Getter
@RequiredArgsConstructor
public class OrderDto {

    @NonNull
    private Long customer;

    @NonNull
    private OrderDetailDto[] orderDetails;
}
