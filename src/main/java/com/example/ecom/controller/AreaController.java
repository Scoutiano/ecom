package com.example.ecom.controller;

import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // Get all areas by cities
    @GetMapping("")
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    // Get an area
    @GetMapping("/{id}")
    public Area get(@PathVariable Long id){
        Optional<Area> optionalArea = areaRepository.findById(id);
        if(!optionalArea.isPresent()) {
            throw new NullPointerException("Invalid area id.");
        }
        return optionalArea.get();
    }

    // Add an area to a specific city
    @PostMapping("/{id}")
    public Area create(@PathVariable Long id, @RequestBody String areaName){
        Optional<City> optionalCity = cityRepository.findById(id);
        if(!optionalCity.isPresent()) {
            throw new NullPointerException("Invalid city id.");
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

    // Update an area
    @PutMapping("/{id}")
    public Area update(@PathVariable Long id, @RequestBody String areaName){
        Optional<Area> optionalArea = areaRepository.findById(id);
        if(!optionalArea.isPresent()) {
            throw new NullPointerException("Invalid area id.");
        }
        Area area = optionalArea.get();
        area.setAreaName(areaName);
        return areaRepository.save(area);
    }
}
