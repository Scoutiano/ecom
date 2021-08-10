package com.example.ecom.service;

import com.example.ecom.dto.AreaDto;
import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * method adds a new area to the database
     *
     * @param areaDto data transfer object containing the area to be created information
     * @return return a copy of the created area for confirmation
     */
    public Area create(AreaDto areaDto){

        Area area = new Area();
        City city = new City();
        city.setId(areaDto.getCity());

        area.setCity(city);
        area.setAreaName(areaDto.getAreaName());
        city.addArea(area);

        return areaRepository.save(area);
    }

    /**
     * method moves new data from data transfer object and adds to original then updates in database
     *
     * @param area original area object to be modified
     * @param areaDto data transfer object containing modified information
     * @return returns a copy of the updated object for confirmation
     */
    public Area update(Area area, AreaDto areaDto){

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
}
