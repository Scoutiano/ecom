package com.example.ecom.repository;

import com.example.ecom.model.Attribute;
import com.example.ecom.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
}
