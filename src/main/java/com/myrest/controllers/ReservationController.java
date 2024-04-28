package com.myrest.controllers;

import com.myrest.entities.Reservation;
import com.myrest.exceptions.DoesNotExistException;
import com.myrest.exceptions.InvalidDateFormatException;
import com.myrest.exceptions.ReservationNotPossibleException;
import com.myrest.reps.ReservationRepository;
import com.myrest.reps.ScooterRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/scooters/{scooterId}/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ScooterRepository scooterRepository;
    private Integer scooterId;

    record NewReservationRequest(
            String beginDate,
            String endDate,
            Integer customerId
    ){}

    record NewBeginOrEndDateRequest(
            String beginDate,
            String endDate
    ){}
    public ReservationController(ReservationRepository reservationRepository, ScooterRepository scooterRepository){
        this.reservationRepository = reservationRepository;
        this.scooterRepository = scooterRepository;
    }
    @ModelAttribute
    public void setAndValidateScooterId(@PathVariable("scooterId") Integer scooterId) {
        this.scooterId = scooterId;
        scooterRepository.findById(scooterId)
                .orElseThrow(() -> new DoesNotExistException("Scooter does not exist!!!"));
    }


    //Get
    @GetMapping
    public List<Reservation> getScooterReservations(){
        return reservationRepository.findReservationByScooterId(scooterId);
    }

    @GetMapping("{reservationId}")
    public Reservation getReservationById(@PathVariable("reservationId")Integer reservationId){
        return reservationRepository.findReservationByIdAndScooterId(reservationId, scooterId)
                .orElseThrow(() -> new DoesNotExistException("Reservation does not exist!!!"));
    }

    //Post

    @PostMapping
    public void addScooterReservation(@RequestBody NewReservationRequest request){


        List<Reservation> reservations = reservationRepository.findReservationByScooterId(scooterId);

        if(Reservation.isReservationStartAndEndValid(request.beginDate, request.endDate) &&
                !(Reservation.checkIfResevationExistsInList(reservations, request.beginDate, request.endDate, null))) {
            Reservation reservation = new Reservation();
            reservation.setBeginDate(request.beginDate);
            reservation.setEndDate(request.endDate);
            reservation.setCustomerId(request.customerId);
            reservation.setScooterId(scooterId);
            reservationRepository.save(reservation);
        }else{
            throw new ReservationNotPossibleException("Reservation is not possible for this date");
        }
    }

    //Patch

    @PatchMapping("{reservationId}")
    public void patchReservationBeginOrEnd(@PathVariable("reservationId")Integer reservationId, @RequestBody NewBeginOrEndDateRequest newBeginOrEndDateRequest){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new DoesNotExistException("Reservation does not exist!!!"));
        String beginDate = reservation.getBeginDate();
        String endDate = reservation.getEndDate();
        if(newBeginOrEndDateRequest.beginDate != null) beginDate = newBeginOrEndDateRequest.beginDate;
        if(newBeginOrEndDateRequest.endDate != null) endDate = newBeginOrEndDateRequest.endDate;

        List<Reservation> reservations = reservationRepository.findReservationByScooterId(scooterId);

        if(Reservation.isReservationStartAndEndValid(beginDate, endDate) &&
                !(Reservation.checkIfResevationExistsInList(reservations, beginDate, endDate, reservation.getId()))){
            reservation.setBeginDate(beginDate);
            reservation.setEndDate(endDate);
            reservationRepository.save(reservation);
        }
    }


    //delete

    @DeleteMapping("{reservationId}")
    public void deleteReservationById(@PathVariable("reservationId") Integer reservationId){
        reservationRepository.findReservationByIdAndScooterId(reservationId, scooterId)
                .orElseThrow(() -> new DoesNotExistException("Reservation does not exist!!!"));
        reservationRepository.deleteById(reservationId);
    }

}
