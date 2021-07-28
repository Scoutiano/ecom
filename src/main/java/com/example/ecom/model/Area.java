package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Table(uniqueConstraints={
        @UniqueConstraint(name = "unq_city_area", columnNames = {"city_id", "areaName"})
})
public class Area extends Auditable<Area>{
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String areaName;
    @Column(columnDefinition = "boolean default true")
    private Boolean active = true;

    // City
    @NonNull
    @JsonBackReference
    @ManyToOne
    private City city;
}
