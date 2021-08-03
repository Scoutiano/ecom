package com.example.ecom.controller;

import com.example.ecom.controller.exception.BadRequestException;
import com.example.ecom.controller.exception.CustomerIdNotFoundException;
import com.example.ecom.controller.exception.NullDTOException;
import com.example.ecom.controller.exception.NullIdException;
import com.example.ecom.dto.CustomerDto;
import com.example.ecom.model.Area;
import com.example.ecom.model.Customer;
import com.example.ecom.model.Entity;
import com.example.ecom.repository.CustomerRepository;
import com.example.ecom.service.CustomerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerManager customerManager;

    @GetMapping
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    /**
     * {@code GET /Customer/:id} Get a specific customer by its id
     *
     * @param id id of the requested customer
     * @return return the requested Customer object
     * @throws NullIdException when given id is null
     * @throws CustomerIdNotFoundException when the given customer id does not exist
     */
    @GetMapping("/{id}")
    public Customer get(@PathVariable Long id) {
        if(id == null) {
            throw new NullIdException(Entity.CUSTOMER);
        }

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(!optionalCustomer.isPresent()) {
            throw new CustomerIdNotFoundException();
        }

        return optionalCustomer.get();
    }

    /**
     * {@code POST /customer/} create a new customer and save it to database
     *
     * @param customerDto new customer with its information
     * @return return saved customer to confirm its creation
     */
    @PostMapping
    public Customer create(@RequestBody CustomerDto customerDto) {
        if(customerDto == null) {
            throw new NullDTOException(Entity.CUSTOMER);
        }
        return customerManager.create(customerDto);
    }

    /**
     * {@code PUT /customer/:id} Update an existing customer by its id
     *
     * @param id id used to retrieve the customer to be updated
     * @param customerDto new product with updated values
     * @return return new customer to confirm update
     * @throws NullIdException when the given customer id is null
     * @throws CustomerIdNotFoundException when the given customer id does not exist
     */
    @PutMapping("{id}")
    public Customer update(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        // Null check for id
        if(id == null) {
            throw new NullIdException(Entity.CUSTOMER);
        }

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(!optionalCustomer.isPresent()){
            throw new CustomerIdNotFoundException();
        }

        Customer customer = optionalCustomer.get();

        return customerManager.update(customer,customerDto);

    }

    /**
     * {@code DELETE /customer/:id} delete a customer by its given id
     *
     * @param id id of customer to be deleted (Set active to false)
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        // Null check for id
        if(id == null) {
            throw new NullIdException(Entity.CUSTOMER);
        }
        customerRepository.deleteById(id);
    }
}
