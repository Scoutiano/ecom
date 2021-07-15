package com.example.ecom;

import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcomApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcomApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(AreaRepository areaRepository, CityRepository cityRepository) {
        return args -> {

            City city1 = new City("Ramallah");
            City city2 = new City("Nablus");
            City city3 = new City("Qalqilia");

            cityRepository.save(city1);
            cityRepository.save(city2);
            cityRepository.save(city3);

            for(Long i = 1L; i < 4; i++){
                City city = cityRepository.findById(i).get();
                Area area1 = new Area("Old City",city);
                Area area2 = new Area("Second Area",city);
                Area area3 = new Area("Third Area",city);

                areaRepository.save(area1);
                areaRepository.save(area2);
                areaRepository.save(area3);

                city.addArea(area1);
                city.addArea(area2);
                city.addArea(area3);
            }
        };
    }
}