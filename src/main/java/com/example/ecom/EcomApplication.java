package com.example.ecom;

import com.example.ecom.model.Area;
import com.example.ecom.model.City;
import com.example.ecom.model.Product;
import com.example.ecom.repository.AreaRepository;
import com.example.ecom.repository.CityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EcomApplication {

    public static void main(String[] args) {
        Product product = new Product();
        System.out.println(product);
        Product product1 = new Product();
        System.out.println(product1);
//        SpringApplication.run(EcomApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner runner(AreaRepository areaRepository, CityRepository cityRepository) {
//        return args -> {
//
//            City city1 = new City("Ramallah");
//            City city2 = new City("Nablus");
//            City city3 = new City("Qalqilia");
//
//            cityRepository.save(city1);
//            cityRepository.save(city2);
//            cityRepository.save(city3);
//
//            for(Long i = 1L; i < 4; i++){
//                City city = cityRepository.findById(i).get();
//                Area area1 = new Area("Old City",city);
//                Area area2 = new Area("Second Area",city);
//                Area area3 = new Area("Third Area",city);
//
//                areaRepository.save(area1);
//                areaRepository.save(area2);
//                areaRepository.save(area3);
//            }
//        };
//    }
}