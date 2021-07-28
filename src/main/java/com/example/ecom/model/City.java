package com.example.ecom.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class City extends Auditable<City>{
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String cityName;

    @Column(columnDefinition = "boolean default true")
    private Boolean active = true;

    // Area
    @JsonManagedReference
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<Area> areas = new ArrayList<>();
    public void addArea(Area area){
        areas.add(area);
    }
}
