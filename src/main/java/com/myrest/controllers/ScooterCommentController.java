package com.myrest.controllers;

import com.myrest.entities.Comment;
import com.myrest.entities.Reservation;
import com.myrest.entities.Scooter;
import com.myrest.exceptions.CommentNotPossibleException;
import com.myrest.exceptions.DoesNotExistException;
import com.myrest.reps.CommentRepository;
import com.myrest.reps.ReservationRepository;
import com.myrest.reps.ScooterRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scooters/{scooterId}/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final ReservationRepository reservationRepository;
    private final ScooterRepository scooterRepository;
    private Integer scooterId;

    public CommentController(CommentRepository commentRepository, ReservationRepository reservationRepository, ScooterRepository scooterRepository) {
        this.commentRepository = commentRepository;
        this.reservationRepository = reservationRepository;
        this.scooterRepository = scooterRepository;
    }

    @ModelAttribute
    public void setAndValidateScooterId(@PathVariable("scooterId") Integer scooterId) {
        this.scooterId = scooterId;
        scooterRepository.findById(scooterId)
                .orElseThrow(() -> new DoesNotExistException("Scooter does not exist!!!"));
    }
    record NewCommentRequest(
            Integer customerId,
            String commentValue
    ) {}

    /*
    * GET
    */

    @GetMapping
    public List<Comment> getCommentsByCustomer(@PathVariable("scooterId") Integer scooterId) {
        return commentRepository.findByScooterId(scooterId);
    }

    /*
     * POST
     */

    @PostMapping
    public Comment addCommentToScooterByCustomer(@PathVariable("scooterId") Integer scooterId, @RequestBody NewCommentRequest request) {
        List<Reservation> reservations = reservationRepository.findReservationByCustomerIdAndScooterId(request.customerId, scooterId);
        if(reservations.isEmpty()) throw new CommentNotPossibleException("Komentować hulajnogę mogą tylko osoby, które jej używały!!!");
        Comment newComment = new Comment(null, request.customerId, scooterId, request.commentValue);
        return commentRepository.save(newComment);
    }
    



}


