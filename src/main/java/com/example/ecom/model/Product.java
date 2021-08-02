package com.example.ecom.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Where(clause="active=1")
@SQLDelete(sql = "UPDATE product SET active = false WHERE id=?")
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
