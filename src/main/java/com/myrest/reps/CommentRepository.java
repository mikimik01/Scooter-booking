package com.myrest.reps;

import com.myrest.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByScooterId(Integer sId);
    Optional<Comment> findCommentByIdAndScooterId(Integer commentId, Integer scooterId);

}