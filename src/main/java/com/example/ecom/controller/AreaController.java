package com.example.ecom.controller;

import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/area")
public class AreaController {

    AreaRepository areaRepository;
    CityRepository cityRepository;

    public AreaController(AreaRepository areaRepository, CityRepository cityRepository){
        this.areaRepository = areaRepository;
        this.cityRepository = cityRepository;
    }

    // Add an area to a specific city
    @PostMapping("/{id}")
    public Area create(@PathVariable Long id, @RequestBody String areaName){
        Optional<City> optionalCity = cityRepository.findById(id);
        if(!optionalCity.isPresent()) {
            throw new NullPointerException();
        }
        City city = optionalCity.get();
        Area area = new Area(areaName,city);
        areaRepository.save(area);
        city.addArea(area);
        return area;
    }

    // Delete an area
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        areaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Area area){
        Optional<Area> optionalArea = areaRepository.findById(id);
        if(!optionalArea.isPresent()){
            throw new NullPointerException();
        }

    }
}
