package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Attribute {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String value;

    @NonNull
    private Integer position;

    @NonNull
    @ManyToOne
    @JsonBackReference
    private Product product;


}
