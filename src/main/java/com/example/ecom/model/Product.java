package com.example.ecom.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Product extends Auditable<Product>{

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String productName;
    @NonNull
    private Float price;
    @NonNull
    private Boolean active = true;
}
