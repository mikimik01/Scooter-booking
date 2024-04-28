package com.myrest.controllers;

import com.myrest.entities.Reservation;
import com.myrest.exceptions.DoesNotExistException;
import com.myrest.reps.CustomerRepository;
import com.myrest.reps.ReservationRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scooter-reservation-transfer")
public class TransferReservationController {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    record NewTransferRequest(
            Integer targetCustomerId,
            Integer fromCustomerId,
            Integer reservationId1,
            Integer reservationId2,
            Integer reservationId3
    ){}

    public TransferReservationController(ReservationRepository reservationRepository, CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
    }

    // PATCH
    @PostMapping
    public void transferReservationsToOtherCustomer(@RequestBody NewTransferRequest transferRequest){
        //Check if origin user exists
        customerRepository.findById(transferRequest.fromCustomerId).orElseThrow(() -> new DoesNotExistException("Customer does not exist!!!"));
        //Check if target user exists
        customerRepository.findById(transferRequest.targetCustomerId).orElseThrow(() -> new DoesNotExistException("Customer does not exist!!!"));

        // Create null reservation object
        Reservation res1 = null;
        Reservation res2 = null;
        Reservation res3 = null;

        //if reservation is passed through reqbody, check if it exist and belongs to origin customer
        if (transferRequest.reservationId1 != null)
            res1 = validateReservationTransferAndReturn(transferRequest.reservationId1, transferRequest.fromCustomerId);
        if (transferRequest.reservationId2 != null)
            res2 = validateReservationTransferAndReturn(transferRequest.reservationId2, transferRequest.fromCustomerId);
        if (transferRequest.reservationId3 != null)
            res3 = validateReservationTransferAndReturn(transferRequest.reservationId3, transferRequest.fromCustomerId);

        // Why we have to check if null twice? Because earlier we needed it for validation.
        // We validate every reservation if it is not null, and now really do stuff.
        // We cannot validate and post one. Either everything goes correctly or is rejected.
        if (res1 != null) {
            res1.setCustomerId(transferRequest.targetCustomerId);
            reservationRepository.save(res1);
        }
        if (res2 != null) {
            res2.setCustomerId(transferRequest.targetCustomerId);
            reservationRepository.save(res2);
        }
        if (res3 != null) {
            res3.setCustomerId(transferRequest.targetCustomerId);
            reservationRepository.save(res3);
        }
    }

    private Reservation validateReservationTransferAndReturn(Integer resId, Integer cusId){
        return reservationRepository.findReservationByIdAndCustomerId(resId, cusId)
                .orElseThrow(() -> new DoesNotExistException("Reservation with id " + resId + " does not exist for customer with id: " + cusId + "!!!"));
    }
}
