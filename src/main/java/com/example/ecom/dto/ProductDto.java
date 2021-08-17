package com.example.ecom.dto;

import com.example.ecom.model.Attribute;
import com.example.ecom.model.Product;
import lombok.*;

import javax.persistence.Basic;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    @NonNull
    private String productName;

    @NonNull
    private Float price;

    @NonNull
    private Integer quantity;

    @NonNull
    private Integer maxPerCustomer;

    @NonNull
    private Integer minPerCustomer;

    @NonNull
    private Integer alertQuantity;

    @NonNull
    private List<AttributeDto> attributes;
}
