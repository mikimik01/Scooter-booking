package com.myrest.controllers;

import com.myrest.entities.Customer;
import com.myrest.exceptions.CustomException;
import com.myrest.exceptions.DoesNotExistException;
import com.myrest.reps.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;
    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}

    // CONSTRUCTORS

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // GET
    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }
    @GetMapping("{customerId}")
    public Customer getCustomerWithId(@PathVariable("customerId") Integer customerId){
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new DoesNotExistException("Customer not found!!!"));
    }

    // POST
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        addGivenCustomer(request, customer);
    }

    // PUT
    @PutMapping("{customerId}")
    public void putChangesToCustomer(@RequestBody NewCustomerRequest request, @PathVariable("customerId") Integer customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new DoesNotExistException("Customer does not exist!!! (Add first to put changes)"));
        addGivenCustomer(request, customer);
    }

    private void addGivenCustomer(@RequestBody NewCustomerRequest request, Customer customer) {
        if(request.name == null || request.age == null || request.email == null)
            throw new CustomException("No field can be empty!!!");
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    // PATCH
    @PatchMapping("{customerId}")
    public void patchChangesToCustomer(@RequestBody NewCustomerRequest request, @PathVariable("customerId") Integer customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new DoesNotExistException("Customer does not exist!!! (Add first to put changes)"));
        if(request.name != null) customer.setName(request.name);
        if(request.age != null) customer.setAge(request.age);
        if(request.email != null) customer.setEmail(request.email);
        customerRepository.save(customer);
    }
    // DELETE

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer customerId){
        customerRepository.findById(customerId)
                        .orElseThrow(() -> new DoesNotExistException("Non existing customer cannot be deleted!!!"));
        customerRepository.deleteById(customerId);
    }


}
