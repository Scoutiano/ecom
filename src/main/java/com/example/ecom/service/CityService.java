package com.example.ecom.service;

import com.example.ecom.controller.exception.CityIdNotFoundException;
import com.example.ecom.dto.CityDto;
import com.example.ecom.model.City;
import com.example.ecom.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    /**
     * gets a list of all cities
     *
     * @return return List of Cities
     */
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    /**
     * Get a specific city through its id
     *
     * @param id id of city to be retrieved
     * @return return requested city
     */
    public City get(Long id) {
        // Check if city with given id exists
        Optional<City> optionalCity = cityRepository.findById(id);
        if(!optionalCity.isPresent()) {
            throw new CityIdNotFoundException();
        }
        City city = optionalCity.get();

        return city;
    }

    /**
     * method adds a new city to the database
     *
     * @param cityDto data transfer object containing the city to be created information
     * @return return a copy of the created area for confirmation
     */
    public City create(CityDto cityDto) {
        City city = new City();
        city.setCityName(cityDto.getCityName());
        return cityRepository.save(city);
    }

    /**
     * Update a given city with new information then save to database
     *
     * @param cityDto data transfer object containing the information to be modified
     * @return return copy of updated city for confirmation
     */
    public City update(CityDto cityDto) {
        Optional<City> optionalCity = cityRepository.findById(cityDto.getId());

        if(!optionalCity.isPresent()) {
            throw new CityIdNotFoundException();
        }
        City city = optionalCity.get();

        city.setCityName(cityDto.getCityName());

        return cityRepository.save(city);
    }

    /**
     * delete a given city by its id
     *
     * @param id id of city to be deleted
     */
    public void delete(Long id) {
        cityRepository.deleteById(id);
    }

}
