package com.myrest.controllers;

import com.myrest.entities.Comment;
import com.myrest.entities.Reservation;
import com.myrest.exceptions.CommentNotPossibleException;
import com.myrest.exceptions.DoesNotExistException;
import com.myrest.reps.CommentRepository;
import com.myrest.reps.ReservationRepository;
import com.myrest.reps.ScooterRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scooters/{scooterId}/comments")
public class ScooterCommentController {
    private final CommentRepository commentRepository;
    private final ReservationRepository reservationRepository;
    private final ScooterRepository scooterRepository;
    private Integer scooterId;

    public ScooterCommentController(CommentRepository commentRepository, ReservationRepository reservationRepository, ScooterRepository scooterRepository) {
        this.commentRepository = commentRepository;
        this.reservationRepository = reservationRepository;
        this.scooterRepository = scooterRepository;
    }

    @ModelAttribute
    public void init(@PathVariable("scooterId") Integer scooterId) {
        scooterRepository.findById(scooterId)
                .orElseThrow(() -> new DoesNotExistException("Scooter does not exist!!!"));
        this.scooterId = scooterId;
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

    @GetMapping("{commentId}")
    public Comment getCommentByItsIdAndScooterId(@PathVariable("commentId")Integer commentId){
        return commentRepository.findCommentByIdAndScooterId(commentId, scooterId)
                .orElseThrow(() -> new DoesNotExistException("Comment does not exist!!!"));
    }

    /*
     * POST
     */

    @PostMapping
    public void addCommentToScooterByCustomer(@PathVariable("scooterId") Integer scooterId, @RequestBody NewCommentRequest request) {
        reservationRepository.findReservationByCustomerIdAndScooterId(request.customerId, scooterId)
                .orElseThrow(() -> new CommentNotPossibleException("To put a comment, you must have reserved the scooter!!!"));
        if(request.customerId != null && request.commentValue != null) {
            Comment newComment = new Comment(null, request.customerId, scooterId, request.commentValue);
            commentRepository.save(newComment);
        }else{
            throw new CommentNotPossibleException("Comment or Customer Id cannot be empty!!!");
        }
    }

    // Patch

    @PatchMapping("{commentId}")
    public void patchCommentChangeByItsId(@PathVariable("commentId")Integer commentId, @RequestBody NewCommentRequest newCommentRequest){
        Comment comment = commentRepository.findCommentByIdAndScooterId(commentId, scooterId)
                .orElseThrow(() -> new DoesNotExistException("Post the comment first to patch it!!!"));
        if(newCommentRequest.commentValue != null){
            comment.setCommentValue(newCommentRequest.commentValue);
            commentRepository.save(comment);
        }else {
            throw new CommentNotPossibleException("The comment text cannot be empty!!!");
        }

    }

    /*
     * DELETE
     */

    @DeleteMapping("{commentId}")
    public void deleteCommentById(@PathVariable("commentId")Integer commentId){
        commentRepository.findCommentByIdAndScooterId(commentId, scooterId)
                        .orElseThrow(() -> new DoesNotExistException("Cannot delete comment - it doesnt exist!!!"));
        commentRepository.deleteById(commentId);
    }



}


