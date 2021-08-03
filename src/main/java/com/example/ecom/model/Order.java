package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "`order`")
@Where(clause="active=1")
@SQLDelete(sql = "UPDATE order SET active = false WHERE id=?")
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Order extends Auditable{

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Basic(optional = false)
    private Float calculatedPrice;

    // Customer
    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(nullable = false)
    private Customer customer;

    // OrderDetail
    @NonNull
    @JsonManagedReference
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails = new ArrayList<>();
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
    }
}
