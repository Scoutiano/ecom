package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Where(clause="active=1")
@SQLDelete(sql = "UPDATE customer SET active = false WHERE id=?")
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Customer extends Auditable{
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    @Basic(optional = false)
    private String lastName;

    @NonNull
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date dob;

    @NonNull
    @Basic(optional = false)
    private Boolean active = true;

    // Area
    @NonNull
    @JoinColumn(nullable = false)
    @JsonBackReference
    @ManyToOne
    private Area area;

    // Order
    @NonNull
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    public void addOrder(Order order){
        orders.add(order);
    }
}
