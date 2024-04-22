package com.myrest.reps;

import com.myrest.entities.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScooterRepository
        extends JpaRepository<Scooter, Integer> {

}
