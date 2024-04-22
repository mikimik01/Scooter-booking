package com.myrest.reps;

import com.myrest.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCustomerId(Integer uId);
    List<Comment> findByScooterId(Integer sId);
}