package com.example.ecom.dto;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class OrderDetailDto {

    @NonNull
    private Integer quantity;

    @NonNull
    private Long product;
}
