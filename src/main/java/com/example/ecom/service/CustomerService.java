package com.example.ecom.service;

import com.example.ecom.controller.exception.CustomerIdNotFoundException;
import com.example.ecom.dto.CustomerDto;
import com.example.ecom.dto.CustomerOrderReport;
import com.example.ecom.dto.OrderReportQuery;
import com.example.ecom.model.Area;
import com.example.ecom.model.Customer;
import com.example.ecom.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     * Get a specific customer through their id
     *
     * @param id id of customer to be retrieved
     * @return return requested customer
     */
    public Customer get(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(!optionalCustomer.isPresent()) {
            throw new CustomerIdNotFoundException();
        }
        Customer customer = optionalCustomer.get();

        return customer;
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
     * @param customerDto data transfer object containing modified information
     * @return return copy of updated customer for confirmation
     */
    public Customer update(CustomerDto customerDto) {

        Optional<Customer> optionalCustomer = customerRepository.findById(customerDto.getId());
        if(!optionalCustomer.isPresent()){
            throw new CustomerIdNotFoundException();
        }
        Customer customer = optionalCustomer.get();

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

    public CustomerOrderReport orderReportQueryToCustomer(OrderReportQuery orderReportQuery) {
        CustomerOrderReport customer = new CustomerOrderReport();
        customer.setId(orderReportQuery.getCustomerId());
        customer.setFirstName(orderReportQuery.getFirstName());
        customer.setLastName(orderReportQuery.getLastName());

        return customer;
    }

}
