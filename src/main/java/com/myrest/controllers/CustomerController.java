package com.myrest.controllers;

import com.myrest.entities.Customer;
import com.myrest.reps.CustomerRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.expression.ExpressionException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }
    @GetMapping("{customerId}")
    public Customer getCustomerWithId(@PathVariable("customerId") Integer id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Customer not found!!!"));
        return customer;
    }

    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }


}
