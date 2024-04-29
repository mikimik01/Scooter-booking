package com.myrest.controllers;

import com.myrest.entities.Comment;
import com.myrest.exceptions.DoesNotExistException;
import com.myrest.logs.LogsHandler;
import com.myrest.reps.CommentRepository;
import com.myrest.reps.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/comments")
public class CustomerCommentController {
    private final CommentRepository commentRepository;
    private final CustomerRepository customerRepository;
    private Integer customerId;

    public CustomerCommentController(CommentRepository commentRepository, CustomerRepository customerRepository) {
        this.commentRepository = commentRepository;
        this.customerRepository = customerRepository;
    }

    @ModelAttribute
    public void init(@PathVariable("customerId")Integer customerId){
        customerRepository.findById(customerId)
                .orElseThrow(() -> new DoesNotExistException("Customer does not exist!!!"));
        this.customerId = customerId;
    }

    // GET

    @GetMapping
    public List<Comment> getCustomerComments(){
        return commentRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new DoesNotExistException("User does not have any comment!!!"));
    }

    @GetMapping("{commentId}")
    public Comment getCustomerCommentById(@PathVariable("commentId")Integer commentId){
        return commentRepository.findCommentByIdAndCustomerId(commentId, customerId)
                .orElseThrow(() -> new DoesNotExistException("Comment does not exist!!!"));
    }

    // DELETE
    @DeleteMapping("{commentId}")
    public void deleteCustomerCommentById(@PathVariable("commentId")Integer commentId){
        commentRepository.findCommentByIdAndCustomerId(commentId, customerId)
                .orElseThrow(() -> new DoesNotExistException("Comment does not exist!!!"));
        commentRepository.deleteById(commentId);
    }
}
