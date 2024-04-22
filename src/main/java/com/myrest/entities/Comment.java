package com.myrest.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Comment {
    @Id
    @SequenceGenerator(
            name = "customer_id_sequence",
            sequenceName = "customer_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_sequence"
    )
    private Long id;

    private Integer customerId;

    private Integer scooterId;

    private String commentValue;

    public Comment() {}

    public Comment(Long id, Integer customerId, Integer scooterId, String commentValue) {
        this.id = id;
        this.customerId = customerId;
        this.scooterId = scooterId;
        this.commentValue = commentValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer uId) {
        this.customerId = uId;
    }

    public Integer getScooterId() {
        return scooterId;
    }

    public void setScooterId(Integer sId) {
        this.scooterId = sId;
    }

    public String getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(String commentValue) {
        this.commentValue = commentValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(customerId, comment.customerId) && Objects.equals(scooterId, comment.scooterId) && Objects.equals(commentValue, comment.commentValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, scooterId, commentValue);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", scooterId=" + scooterId +
                ", commentValue='" + commentValue + '\'' +
                '}';
    }
}
