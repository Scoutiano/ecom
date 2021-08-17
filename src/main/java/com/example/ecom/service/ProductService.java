package com.example.ecom.service;

import com.example.ecom.controller.exception.ProductIdNotFoundException;
import com.example.ecom.dto.AttributeDto;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.model.Attribute;
import com.example.ecom.model.Product;
import com.example.ecom.repository.AttributeRepository;
import com.example.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;

import javax.persistence.metamodel.ListAttribute;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeService attributeService;

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
     * @param productDto product information to be added to database
     * @return return copy off added product for confirmation
     */
    public Product create(ProductDto productDto){
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setAlertQuantity(productDto.getAlertQuantity());
        product.setQuantity(productDto.getQuantity());
        product.setMaxPerCustomer(productDto.getMaxPerCustomer());
        product.setMinPerCustomer(productDto.getMinPerCustomer());
        product.setPrice(productDto.getPrice());

        productRepository.save(product);

        createAttributes(productDto.getAttributes(), product.getId());

        return product;
    }

    /**
     * Update a specific product's information and attributes
     *
     * @param productDto data transfer object to containing information to be modified
     * @return return copy of updated product for confirmation
     */
    public Product update(ProductDto productDto) {

        Optional<Product> optionalProduct = productRepository.findById(productDto.getId());
        if(!optionalProduct.isPresent()){
            throw new ProductIdNotFoundException();
        }

        Product product = optionalProduct.get();

        product.setProductName(productDto.getProductName());
        product.setAlertQuantity(productDto.getAlertQuantity());
        product.setQuantity(productDto.getQuantity());
        product.setMaxPerCustomer(productDto.getMaxPerCustomer());
        product.setMinPerCustomer(productDto.getMinPerCustomer());

        productRepository.save(product);

        updateAttributes(productDto.getAttributes(), product.getId());

        return product;
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Get list of attributes for a given product
     *
     * @param id id of product for its attributes to be retrieved
     * @return return array of product attributes
     */
    public List<Attribute> getProductAttributes(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()) {
            throw new ProductIdNotFoundException();
        }
        Product product = optionalProduct.get();

        return productRepository.getProductAttributes(product);
    }

    public void updateProductQuantity(Product product, Integer quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    public void createAttributes(List<AttributeDto> attributes, Long productId) {
        Product product = new Product();
        product.setId(productId);

        for(AttributeDto attributeDto:attributes) {
            Attribute attribute = new Attribute();
            attribute.setProduct(product);
            attribute.setName(attributeDto.getName());
            attribute.setPosition(attributeDto.getPosition());
            attribute.setValue(attributeDto.getValue());

            attributeService.create(attribute);
        }
    }

    public void updateAttributes(List<AttributeDto> attributes, Long productId) {
        Product product = new Product();
        product.setId(productId);

        Map<Long, Attribute> attributeHashMap = new HashMap<>();

        List<Attribute> originalAttributes = productRepository.getProductAttributes(product);
        for(AttributeDto attributeDto:attributes) {
            if(attributeDto.getId() == null) {
                Attribute attribute = new Attribute();
                attribute.setProduct(product);
                attribute.setName(attributeDto.getName());
                attribute.setPosition(attributeDto.getPosition());
                attribute.setValue(attributeDto.getValue());
                attributeService.create(attribute);
            } else {
                Attribute originalAttribute = attributeService.get(attributeDto.getId());
                attributeService.update(originalAttribute,attributeDto);

                attributeHashMap.put(originalAttribute.getId(),originalAttribute);
            }
        }

        if(originalAttributes != null) {
            for(Attribute attribute:originalAttributes) {
                if(attributeHashMap.get(attribute.getId()) == null) {
                    attributeService.delete(attribute.getId());
                }
            }
        }
    }

}
