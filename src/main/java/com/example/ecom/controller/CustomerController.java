package com.example.ecom.controller;

import com.example.ecom.controller.exception.CustomerIdNotFoundException;
import com.example.ecom.controller.exception.NullDTOException;
import com.example.ecom.controller.exception.NullIdException;
import com.example.ecom.dto.CustomerDto;
import com.example.ecom.model.Customer;
import com.example.ecom.model.Entity;
import com.example.ecom.model.Order;
import com.example.ecom.repository.CustomerRepository;
import com.example.ecom.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    /**
     * {@code GET get a list of all customers}
     *
     * @return List of Customer objects
     */
    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll();
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

        return customerService.get(id);
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
        return customerService.create(customerDto);
    }

    /**
     * {@code PUT /customer/:id} Update an existing customer by its id
     *
     * @param customerDto new product with updated values
     * @return return new customer to confirm update
     * @throws NullIdException when the given customer id is null
     * @throws CustomerIdNotFoundException when the given customer id does not exist
     */
    @PutMapping
    public Customer update(@RequestBody CustomerDto customerDto) {
        // Null check for id
        if(customerDto == null) {
            throw new NullDTOException(Entity.CUSTOMER);
        }

        return customerService.update(customerDto);

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
        customerService.delete(id);
    }
}
