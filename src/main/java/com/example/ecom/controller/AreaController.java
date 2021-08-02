package com.example.ecom.controller;

import com.example.ecom.controller.exception.AreaIdNotFoundException;
import com.example.ecom.controller.exception.BadRequestException;
import com.example.ecom.controller.exception.CityIdNotFoundException;
import com.example.ecom.controller.exception.NullIdException;
import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import com.example.ecom.model.Entity;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    AreaRepository areaRepository;
    @Autowired
    CityRepository cityRepository;

    /**
     * {@code GET /area/} Get all areas information with their cities
     *
     * @return list of cities containing all areas
     */
    @GetMapping("")
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    /**
     * {@code GET /area/:id} Get a specific area's information through its id
     *
     * @param id id used to retrieve the requested area
     * @return return requested Area object
     * @throws AreaIdNotFoundException when the requested area id is not found
     */
    @GetMapping("/{id}")
    public Area get(@PathVariable Long id) {

        nullIdCheck(id,Entity.AREA);

        Optional<Area> optionalArea = areaRepository.findById(id);
        if(!optionalArea.isPresent()) {
            throw new AreaIdNotFoundException();
        }
        return optionalArea.get();
    }

    /**
     * {@code POST /area/:id} Create a new area within a selected city
     *
     * @param id id of city for area to be created in
     * @param area area to be added
     * @return return the created area to confirm its creation
     * @throws CityIdNotFoundException when the requested city id is not found
     */
    @PostMapping("/{id}")
    public Area create(@PathVariable Long id, @RequestBody Area area){

        nullIdCheck(id,Entity.CITY);
        areaNullCheck(area);

        // Check if city with given id exists
        Optional<City> optionalCity = cityRepository.findById(id);
        if(!optionalCity.isPresent()) {
            throw new CityIdNotFoundException();
        }

        City city = optionalCity.get();

        uniqueCityAreaCheck(area.getAreaName(),city);

        area.setCity(city);
        areaRepository.save(area);
        city.addArea(area);

        return area;
    }

    /**
     * {@code DELETE /area/:id} Delete an area a given id
     *
     * @param id the area with this given id is to be deleted (Set active to false)
     * @throws AreaIdNotFoundException when the requested area id is not found
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        nullIdCheck(id,Entity.AREA);
        areaRepository.deleteById(id);
    }

    /**
     *{@code PUT /area/:id} Update an area through its id
     *
     * @param id id used to retrieve an area
     * @param newArea updated area with new values
     * @return return updated Area object to confirm update
     * @throws AreaIdNotFoundException when the requested area id is not found
     */
    @PutMapping("/{id}")
    public Area update(@PathVariable Long id, @RequestBody Area newArea){

        nullIdCheck(id, Entity.AREA);
        areaNullCheck(newArea);

        Optional<Area> optionalArea = areaRepository.findById(id);

        if(!optionalArea.isPresent()) {
            throw new AreaIdNotFoundException();
        }

        Area area = optionalArea.get();

        area.setAreaName(newArea.getAreaName());
        area.setCity(newArea.getCity());
        return areaRepository.save(area);
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
     * @param area area to be checked for null variables
     * @throws BadRequestException if area or area.getAreaName() are null
     */
    public void areaNullCheck(Area area) {
        if(area == null) {
            throw new BadRequestException("Area is null",Entity.AREA,"area_null");
        }

        if(area.getAreaName() == null) {
            throw new BadRequestException("Area name is null",Entity.AREA,"areaName_null");
        }

        if(area.getCity() == null) {
            throw new BadRequestException("Area city is null", Entity.CITY, "areaCity_null");
        }
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
            throw new BadRequestException("There already exists an area with the given name within this city",Entity.AREA,"area_duplicate");
        }
    }
}
