package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause="active=1")
@SQLDelete(sql = "UPDATE product SET active = false WHERE id=?")
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Product extends Auditable{

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Basic(optional = false)
    private String productName;

    @NonNull
    @Basic(optional = false)
    private Float price;

    @NonNull
    @Basic(optional = false)
    private Integer quantity;

    @NonNull
    @Basic(optional = false)
    private Integer maxPerCustomer;

    @NonNull
    @Basic(optional = false)
    private Integer minPerCustomer;

    @NonNull
    @Basic(optional = false)
    private Integer alertQuantity;

    @NonNull
    @Basic(optional = false)
    private Boolean active = true;

    // OrderDetail
    @NonNull
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
    }
}
