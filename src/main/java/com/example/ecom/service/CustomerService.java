package com.example.ecom.service;

import com.example.ecom.dto.CustomerDto;
import com.example.ecom.model.Area;
import com.example.ecom.model.Customer;
import com.example.ecom.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Get a list of all customers
     *
     * @return return List object containing all Customers
     */
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    /**
     * Add new customer to database
     *
     * @param customerDto data transfer object containing information of customer to be created
     * @return return copy of the added customer for confirmation
     */
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

    /**
     * Method updates a given customer's information
     *
     * @param customer original customer object to be modified
     * @param customerDto data transfer object containing modified information
     * @return return copy of updated customer for confirmation
     */
    public Customer update(Customer customer, CustomerDto customerDto) {
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setDob(customerDto.getDob());
        Area area = new Area();
        area.setId(customerDto.getArea());
        customer.setArea(area);

        return customerRepository.save(customer);
    }

    /**
     * Delete a given customer by their id
     *
     * @param id id of to be deleted customer
     */
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
