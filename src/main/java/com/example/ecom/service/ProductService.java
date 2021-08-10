package com.example.ecom.service;

import com.example.ecom.model.Product;
import com.example.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Get a list of all products
     *
     * @return return a List object containing all Products
     */
    public List<Product> getALl() {
        return productRepository.findAll();
    }

    /**
     * Add a new product to database
     *
     * @param product product to be added to database
     * @return return copy off added product for confirmation
     */
    public Product create(Product product){
        return productRepository.save(product);
    }

    /**
     *
     *
     * @param product original product to be modified
     * @param productDto data transfer object to containing information to be modified
     * @return return copy of updated product for confirmation
     */
    public Product update(Product product, Product productDto) {
        product.setProductName(productDto.getProductName());
        product.setAlertQuantity(productDto.getAlertQuantity());
        product.setQuantity(productDto.getQuantity());
        product.setMaxPerCustomer(productDto.getMaxPerCustomer());
        product.setMinPerCustomer(productDto.getMinPerCustomer());

        return productRepository.save(product);
    }

    /**
     * Delete a given product by its id
     *
     * @param id id of product to be deleted
     */
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
