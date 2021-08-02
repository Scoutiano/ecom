package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;

@Entity
@Where(clause="active=1")
@SQLDelete(sql = "UPDATE area SET active = false WHERE id=?")
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Table(uniqueConstraints={
        @UniqueConstraint(name = "unq_city_area", columnNames = {"city_id", "areaName"})
})
public class Area extends Auditable{
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Basic(optional = false)
    private String areaName;

    @NonNull
    @Basic(optional = false)
    private Boolean active = true;

    // City
    @NonNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private City city;

    // Customer
    @JsonManagedReference
    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<Customer> customers;
}
