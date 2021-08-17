package com.example.ecom.service;

import com.example.ecom.controller.exception.AreaIdNotFoundException;
import com.example.ecom.controller.exception.BadRequestException;
import com.example.ecom.controller.exception.CityIdNotFoundException;
import com.example.ecom.dto.AreaDto;
import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import com.example.ecom.model.Entity;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;
    //de

    @Autowired
    private CityRepository cityRepository;

    /**
     * gets a list of all areas and their cities
     *
     * @return return List of Cities
     */
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    /**
     * Get a specific Area with its information
     *
     * @param id id of area to be retrieved
     * @return return requested area
     */
    public Area get(Long id) {
        Optional<Area> optionalArea = areaRepository.findById(id);
        if(!optionalArea.isPresent()) {
            throw new AreaIdNotFoundException();
        }
        Area area = optionalArea.get();

        return area;
    }

    /**
     * method adds a new area to the database
     *
     * @param areaDto data transfer object containing the area to be created information
     * @return return a copy of the created area for confirmation
     */
    public Area create(AreaDto areaDto){

        // Check if city with given id exists
        Optional<City> optionalCity = cityRepository.findById(areaDto.getCity());
        if(!optionalCity.isPresent()) {
            throw new CityIdNotFoundException();
        }

        City city = optionalCity.get();

        uniqueCityAreaCheck(areaDto.getAreaName(),city);

        Area area = new Area();

        area.setCity(city);
        area.setAreaName(areaDto.getAreaName());

        return areaRepository.save(area);
    }

    /**
     * method moves new data from data transfer object and adds to original then updates in database
     *
     * @param areaDto data transfer object containing modified information
     * @return returns a copy of the updated object for confirmation
     */
    public Area update(AreaDto areaDto){

        Optional<Area> optionalArea = areaRepository.findById(areaDto.getId());

        if(!optionalArea.isPresent()) {
            throw new AreaIdNotFoundException();
        }

        Area area = optionalArea.get();

        area.setAreaName(areaDto.getAreaName());
        City city = new City();
        city.setId(areaDto.getCity());
        area.setCity(city);
        return areaRepository.save(area);
    }

    /**
     * delete a given area by its id
     *
     * @param id area to be deleted id
     */
    public void delete(Long id) {
        areaRepository.deleteById(id);
    }

    /**
     * Utility method to check for a violation in unq_city_area constraint
     *
     * @param areaName name of area, along side city must be unique
     * @param city along side name of area must be unique
     * @throws BadRequestException when there exists a previous record with the same city and area name
     */
    public void uniqueCityAreaCheck(String areaName, City city){
        int count = areaRepository.findConflictingAreas(areaName,city);
        if(count != 0) {
            throw new BadRequestException("There already exists an area with the given name within this city", Entity.AREA,"area_duplicate");
        }
    }
}
