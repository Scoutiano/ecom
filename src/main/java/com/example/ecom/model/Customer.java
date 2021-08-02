package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Where(clause="active=1")
@SQLDelete(sql = "UPDATE customer SET active = false WHERE id=?")
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Customer extends Auditable<Customer>{
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    @Temporal(TemporalType.DATE)
    private Date dob;
    @NonNull
    private Boolean active = true;

    // Area
    @NonNull
    @JsonBackReference
    @ManyToOne
    private Area area;
}
