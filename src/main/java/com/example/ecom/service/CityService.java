package com.example.ecom.service;

import com.example.ecom.model.City;
import com.example.ecom.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * method adds a new city to the database
     *
     * @param city data transfer object containing the city to be created information
     * @return return a copy of the created area for confirmation
     */
    public City create(City city) {
        return cityRepository.save(city);
    }

    /**
     * Update a given city with new information then save to database
     *
     * @param city original city object to be modified
     * @param cityDto data transfer object containing the information to be modified
     * @return return copy of updated city for confirmation
     */
    public City update(City city, City cityDto) {
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
