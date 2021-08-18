package com.example.ecom.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class OrderProductDetail {

    @NonNull
    private Long id;

    @NonNull
    private String productName;

    @NonNull
    private Float productPrice;

    @NonNull
    private Float calculatedPrice;

    @NonNull
    private Integer quantity;

    @NonNull
    private Long orderId;

    @NonNull
    private Long productId;

}
