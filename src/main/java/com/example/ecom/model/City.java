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
@SQLDelete(sql = "UPDATE city SET active = false WHERE id=?")
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class City extends Auditable{
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Basic(optional = false)
    private String cityName;

    @NonNull
    @Basic(optional = false)
    private Boolean active = true;

    // Area
    @JsonManagedReference
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<Area> areas = new ArrayList<>();
    public void addArea(Area area){
        areas.add(area);
    }
}
