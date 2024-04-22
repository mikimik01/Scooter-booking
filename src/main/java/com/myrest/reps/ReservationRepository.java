package com.myrest.reps;

import com.myrest.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository
        extends JpaRepository<Reservation, Integer> {
    List<Reservation> findReservationByScooterId(Integer sId);
}
