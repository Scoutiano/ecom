package com.example.ecom.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class SingleOrderReport {

    @NonNull
    private Long id;

    @NonNull
    private LocalDateTime creationDate;

    @NonNull
    private LocalDateTime lastModifiedDate;

    @NonNull
    private Float calculatedPrice;

    @NonNull
    private Boolean active;

    @NonNull
    private List<OrderProductDetail> orderDetails = new ArrayList<>();
    public void addOrderProductDetail(OrderProductDetail orderProductDetail) {
        orderDetails.add(orderProductDetail);
    }
}
