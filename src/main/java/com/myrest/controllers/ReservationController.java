package com.myrest.controllers;

import com.myrest.entities.Reservation;
import com.myrest.reps.ReservationRepository;
import org.springframework.expression.ExpressionException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/scooters/{scooterId}/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<Reservation> getScooterReservations(@PathVariable("scooterId") Integer id){
        return reservationRepository.findReservationByScooterId(id);
    }

    record NewReservationRequest(
            String beginDate,
            String endDate,
            Integer customerId
    ){}

    @PostMapping
    public void addScooterReservation(@PathVariable("scooterId") Integer sid, @RequestBody NewReservationRequest request){
        Date beginDate;
        Date endDate;
        String formatTemplate = "dd.MM.yyyy";
        try{
            beginDate = new SimpleDateFormat(formatTemplate).parse(request.beginDate);
            endDate = new SimpleDateFormat(formatTemplate).parse(request.endDate);
        }catch (Exception e){
            throw new ExpressionException("Wrong date format!!! Try \"dd.MM.yyyy\"");
        }

        List<Reservation> reservations = reservationRepository.findReservationByScooterId(sid);

        if(Reservation.isReservationStartAndEndValid(beginDate, endDate) &&
                (Reservation.checkIfResevationExistsInList(reservations, beginDate, endDate, formatTemplate))) {
            Reservation reservation = new Reservation();
            reservation.setBeginDate(request.beginDate);
            reservation.setEndDate(request.endDate);
            reservation.setCustomerId(request.customerId);
            reservation.setScooterId(sid);
            reservationRepository.save(reservation);
        }else{
            throw new ExpressionException("Reservation is not possible for this date");
        }
    }
}
