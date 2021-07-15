package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@Table(uniqueConstraints={
        @UniqueConstraint(name = "unq_city_area", columnNames = {"city_id", "areaName"})
})
public class Area {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String areaName;

    // City
    @NonNull
    @JsonBackReference
    @ManyToOne
    private City city;
}
