package com.example.ecom.controller;

import com.example.ecom.controller.exception.BadRequestException;
import com.example.ecom.controller.exception.NullDTOException;
import com.example.ecom.controller.exception.NullIdException;
import com.example.ecom.controller.exception.ProductIdNotFoundException;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.model.Attribute;
import com.example.ecom.model.Customer;
import com.example.ecom.model.Entity;
import com.example.ecom.model.Product;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getALl();
    }

    /**
     * {@code GET /product/:id} Get a specific product by its id
     *
     * @param id id of the requested product
     * @return return the requested Product object
     * @throws NullIdException when given id is null
     * @throws ProductIdNotFoundException when the given product id does not exist
     */
    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        if(id == null) {
            throw new NullIdException(Entity.PRODUCT);
        }

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()) {
            throw new ProductIdNotFoundException();
        }

        return optionalProduct.get();
    }

    /**
     * {@code POST /product/} create a new product and save it to database
     *
     * @param productDto new product with its information
     * @return return saved product to confirm its creation
     */
    @PostMapping
    public Product create(@RequestBody ProductDto productDto) {
        if(productDto == null) {
            throw new NullDTOException(Entity.PRODUCT);
        }
        return productService.create(productDto);
    }

    /**
     * {@code PUT /product/:id} Update an existing product by its id
     *
     * @param productDto data transfer object with product information (type Product)
     * @return return new product to confirm update
     * @throws NullIdException when the given product id is null
     * @throws ProductIdNotFoundException when the given product id does not exist
     */
    @PutMapping
    public Product update(@RequestBody ProductDto productDto) {
        // Null check for id
        if(productDto == null) {
            throw new NullIdException(Entity.PRODUCT);
        }

        return productService.update(productDto);
    }

    /**
     * {@code DELETE /product/:id} delete a product by its given id
     *
     * @param id id of product to be deleted (Set active to false)
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        // Null check for id
        if(id == null) {
            throw new NullIdException(Entity.PRODUCT);
        }
        productService.delete(id);
    }
}
