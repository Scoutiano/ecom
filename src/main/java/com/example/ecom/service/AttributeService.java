package com.example.ecom.service;

import com.example.ecom.controller.exception.AttributeIdNotFoundException;
import com.example.ecom.dto.AttributeDto;
import com.example.ecom.model.Attribute;
import com.example.ecom.model.Product;
import com.example.ecom.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    public void create(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    public void update(Attribute originalAttribute, AttributeDto attribute) {
        originalAttribute.setName(attribute.getName());
        originalAttribute.setValue(attribute.getValue());
        originalAttribute.setPosition(attribute.getPosition());

        attributeRepository.save(originalAttribute);
    }

    public Attribute get(Long id) {
        Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
        if(!optionalAttribute.isPresent()) {
            throw new AttributeIdNotFoundException();
        }
        return optionalAttribute.get();
    }

    public void delete(Long id) {
        attributeRepository.deleteById(id);
    }
}
