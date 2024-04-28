package com.myrest.reps;

import com.myrest.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository
        extends JpaRepository<Reservation, Integer> {
    List<Reservation> findReservationByScooterId(Integer sId);
    Optional<Reservation> findReservationByIdAndCustomerId(Integer reservationId, Integer customerId);
    Optional<List<Reservation>> findReservationByCustomerIdAndScooterId(Integer customerId, Integer scooterId);
    Optional<Reservation> findReservationByIdAndScooterId(Integer customerId, Integer scooterId);
}
