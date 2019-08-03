package com.luv2code.springdemo.rest;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    // Autowire the customer Service
    @Autowired
    private CustomerService customerService;

    // Add mapping for GET customers
    @GetMapping("/customers")
    private List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/customers/{customerId}")
    private Customer getCustomer(@PathVariable int customerId) {
        Customer theCustomer = customerService.getCustomer(customerId);
        if (theCustomer == null) {
            throw new CustomerNotFoundException("Customer id not found - " + customerId);
        }
        return theCustomer;
        // We want a 404 if customer is not found, jackson by default gives us a blank 200.
    }

    @PostMapping("/customers")
    private Customer addCustomer(@RequestBody Customer theCustomer) {
        // Also just in case we want to set the id to 0 as we're doing saveOrUpdate in the background.
        theCustomer.setId(0);
        customerService.saveCustomer(theCustomer);
        return theCustomer;
    }

    @PutMapping("/customers/{customerId}")
    private Customer updateCustomer(@RequestBody Customer theCustomer, @PathVariable int customerId) {
        Customer currentCustomer = customerService.getCustomer(customerId);
        if (currentCustomer == null) {
            throw new CustomerNotFoundException("Customer id not found - " + customerId);
        }
        theCustomer.setId(customerId);
        customerService.saveCustomer(theCustomer);
        return theCustomer;
    }

    @DeleteMapping("/customers/{customerId}")
    private Customer deleteCustomer(@PathVariable int customerId) {
        Customer currentCustomer = customerService.getCustomer(customerId);
        if (currentCustomer == null) {
            throw new CustomerNotFoundException("Customer id not found - " + customerId);
        }
        customerService.deleteCustomer(customerId);
        return currentCustomer;
    }
}
