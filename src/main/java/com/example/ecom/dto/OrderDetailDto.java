package com.example.ecom.dto;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@RequiredArgsConstructor
public class OrderDetailDto {

    private Long id;

    @NonNull
    private Integer quantity;

    @NonNull
    private Long product;

}
