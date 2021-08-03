package com.example.ecom.service;

import com.example.ecom.dto.CustomerDto;
import com.example.ecom.model.Area;
import com.example.ecom.model.Customer;
import com.example.ecom.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer create(CustomerDto customerDto){
        Customer customer = new Customer();
        Area area = new Area();
        area.setId(customerDto.getArea());
        customer.setArea(area);
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setDob(customerDto.getDob());

        return customerRepository.save(customer);
    }

    public Customer update(Customer customer, CustomerDto customerDto) {
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setDob(customerDto.getDob());
        Area area = new Area();
        area.setId(customerDto.getArea());
        customer.setArea(area);

        return customerRepository.save(customer);
    }
}
