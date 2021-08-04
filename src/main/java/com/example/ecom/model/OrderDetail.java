package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.Constraint;
import java.util.Optional;

@Entity
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderDetail {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Basic(optional = false)
    private Integer quantity;

    @NonNull
    @Basic(optional = false)
    private Float calculatedPrice;

    // Order
    @NonNull
    @JsonBackReference
    @JoinColumn(nullable = false)
    @ManyToOne
    private Order order;

    // Product
    @NonNull
    @JsonBackReference
    @JoinColumn(nullable = false)
    @ManyToOne
    private Product product;
}
