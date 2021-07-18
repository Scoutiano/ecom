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
public class City {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String cityName;

    // Area
    @JsonManagedReference
    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private List<Area> areas = new ArrayList<>();
    public void addArea(Area area){
        areas.add(area);
    }
}
