package com.example.ecom.repository;

import com.example.ecom.model.Attribute;
import com.example.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT a FROM Attribute a WHERE a.product = ?1")
    List<Attribute> getProductAttributes(Product product);
}
