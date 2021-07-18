package com.example.ecom.controller;

import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/city")
public class CityController {

    CityRepository cityRepository;
    AreaRepository areaRepository;

    public CityController(CityRepository cityRepository, AreaRepository areaRepository){
        this.areaRepository = areaRepository;
        this.cityRepository = cityRepository;
    }

    // Get all cities
    @GetMapping("")
    public List<City> getAll(){
        return cityRepository.findAll();
    }

    // Get a specific city
    @GetMapping("/{id}")
    public City get(@PathVariable Long id){
        Optional<City> optionalCity = cityRepository.findById(id);
        if(!optionalCity.isPresent()) {
            throw new NullPointerException("Invalid area id.");
        }
        return optionalCity.get();
    }

    // Create a city
    @PostMapping()
    public City create(@RequestBody String cityName) {
        City city = new City(cityName);
        return cityRepository.save(city);
    }

    // Update a specific city
    @PutMapping("/{id}")
    public City update(@PathVariable Long id, @RequestBody String cityName){
        Optional<City> optionalCity = cityRepository.findById(id);
        if(!optionalCity.isPresent()){
            throw new NullPointerException("Invalid city id.");
        }
        City city = optionalCity.get();
        city.setCityName(cityName);
        return cityRepository.save(city);
    }

    // Delete a specific city
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        cityRepository.deleteById(id);
    }


}
