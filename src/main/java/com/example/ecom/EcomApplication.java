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
        SpringApplication.run(EcomApplication.class, args);
    }
}