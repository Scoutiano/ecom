package com.example.ecom.controller;

import com.example.ecom.controller.exception.AreaIdNotFoundException;
import com.example.ecom.controller.exception.BadRequestException;
import com.example.ecom.controller.exception.CityIdNotFoundException;
import com.example.ecom.controller.exception.NullIdException;
import com.example.ecom.model.City;
import com.example.ecom.model.Entity;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    AreaRepository areaRepository;

    /**
     * {@code GET /city/} Get a list of all cities
     *
     * @return list of all cities
     */
    @GetMapping("")
    public List<City> getAll(){
        return cityRepository.findAll();
    }

    /**
     * {@code GET /city/:id} Get a specific area with the given id
     *
     * @param id id used to retrieve the requested city
     * @return return requested City object
     * @throws CityIdNotFoundException when the requested city id is not found
     */
    @GetMapping("/{id}")
    public City get(@PathVariable Long id){

        nullIdCheck(id,Entity.CITY);

        // Check if city with given id exists
        Optional<City> optionalCity = cityRepository.findById(id);
        if(!optionalCity.isPresent()) {
            throw new CityIdNotFoundException();
        }
        return optionalCity.get();
    }

    /**
     * {@code POST /city/} Create a new city
     *
     * @param city City object to be added
     * @return return created City object to confirm its creation
     */
    @PostMapping()
    public City create(@RequestBody City city) {
        cityNullCheck(city);
        return cityRepository.save(city);
    }

    /**
     * {@code PUT /city/:id} update a given city through the provided id.
     *
     * @param id id used to find the city that's going to be updated.
     * @param newCity modified City object with null id
     * @return City object after it has been modified to confirm update.
     * @throws BadRequestException if the given id is null
     * @throws CityIdNotFoundException if a city with the given id has not been found.
     */
    @PutMapping("/{id}")
    public City update(@PathVariable Long id, @RequestBody City newCity) throws BadRequestException {

        nullIdCheck(id, Entity.CITY);
        cityNullCheck(newCity);

        Optional<City> optionalCity = cityRepository.findById(id);

        if(!optionalCity.isPresent()) {
            throw new AreaIdNotFoundException();
        }
        City city = optionalCity.get();
        city.setCityName(newCity.getCityName());

        return cityRepository.save(city);
    }

    /**
     * {@code DELETE /city/:id} Delete a city by its given id
     *
     * @param id id of the city to be deleted (Set active to false)
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        nullIdCheck(id,Entity.CITY);
        cityRepository.deleteById(id);
    }

    /**
     * Utility method to check if given id is null
     *
     * @param id given entity id to check
     * @param entity entity name to use in exception
     * @throws NullIdException when given id is null
     */
    public void nullIdCheck(Long id, Entity entity){
        if(id == null) {
            throw new NullIdException(entity);
        }
    }

    /**
     * Utility method used to check if area or any of its passed variables are null
     *
     * @param city city to be checked for null variables
     * @throws BadRequestException if area or area.getAreaName() are null
     */
    public void cityNullCheck(City city) {
        if(city == null) {
            throw new BadRequestException("City is null",Entity.CITY,"city_null");
        }

        if(city.getCityName() == null) {
            throw new BadRequestException("City name is null",Entity.CITY,"cityName_null");
        }
    }
}
