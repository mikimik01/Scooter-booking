package com.myrest.reps;

import com.myrest.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

}
