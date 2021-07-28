package com.example.ecom.controller;

import com.example.ecom.controller.exception.AreaIdNotFoundException;
import com.example.ecom.controller.exception.BadRequestException;
import com.example.ecom.controller.exception.CityIdNotFoundException;
import com.example.ecom.controller.exception.NullIdException;
import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/area")
public class AreaController {


    AreaRepository areaRepository;
    CityRepository cityRepository;

    /**
     * Constructor for AreaController class
     *
     * @param cityRepository used to perform queries related to City entity
     * @param areaRepository used to perform queries related to Area entity
     */
    public AreaController(AreaRepository areaRepository, CityRepository cityRepository){
        this.areaRepository = areaRepository;
        this.cityRepository = cityRepository;
    }

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

        nullIdCheck(id,"area");

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

        nullIdCheck(id,"city");
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
     * @param id the area with this given id is to be deleted
     * @throws AreaIdNotFoundException when the requested area id is not found
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        nullIdCheck(id,"area");

        // Check if area with given id exists *REVIEW
        if(!areaRepository.existsById(id)) {
            throw new AreaIdNotFoundException();
        }

        areaRepository.deleteById(id);
    }

    /**
     *{@code PUT /area/:id} Update an area through its id
     *
     * @param id id used to retrieve an area
     * @param area updated area with new values
     * @return return updated Area object to confirm update
     * @throws AreaIdNotFoundException when the requested area id is not found
     */
    @PutMapping("/{id}")
    public Area update(@PathVariable Long id, @RequestBody Area area){

        nullIdCheck(id,"area");
        areaNullCheck(area);

        // Check if area with given id exists
        Optional<Area> optionalArea = areaRepository.findById(id);
        if(!optionalArea.isPresent()) {
            throw new AreaIdNotFoundException();
        }
        City city = optionalArea.get().getCity();

        uniqueCityAreaCheck(area.getAreaName(),city);

        area.setCreationDate(optionalArea.get().getCreationDate());
        area.setCity(city);
        area.setId(id);

        return areaRepository.save(area);
    }

    /**
     * Utility method to check if given id is null
     *
     * @param id given entity id to check
     * @param entity entity name to use in exception
     * @throws NullIdException when given id is null
     */
    public void nullIdCheck(Long id, String entity){
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
            throw new BadRequestException("Area is null","Area","area_null");
        }

        if(area.getAreaName() == null) {
            throw new BadRequestException("Area name is null","Area","areaName_null");
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
            throw new BadRequestException("There already exists an area with the given name within this city","area","area_duplicate");
        }
    }
}
