package com.myrest.controllers;

import com.myrest.entities.Comment;
import com.myrest.reps.CommentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scooters/{scooterId}/comments")
public class CommentController {
    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    record NewCommentRequest(
            Integer customerId,
            String commentValue
    ) {}
    @PostMapping
    public Comment addCommentToScooterByCustomer(@PathVariable("scooterId") Integer scooterId, @RequestBody NewCommentRequest request) {
        Comment newComment = new Comment();
        newComment.setCustomerId(request.customerId);
        newComment.setScooterId(scooterId);
        newComment.setCommentValue(request.commentValue);
        return commentRepository.save(newComment);
    }

    @GetMapping
    public List<Comment> getCommentsByCustomer(@PathVariable("scooterId") Integer customerId) {
        return commentRepository.findByScooterId(customerId);
    }
}


