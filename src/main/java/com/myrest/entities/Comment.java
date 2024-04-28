package com.myrest.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@ToString
public class Comment {
        @Id
        @SequenceGenerator(
                name = "comment_id_sequence",
                sequenceName = "comment_id_sequence",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "comment_id_sequence"
        )
        private Integer id;

        private Integer customerId;

        private Integer scooterId;

        private String commentValue;

        public Comment() {}

        public Comment(Integer id, Integer customerId, Integer scooterId, String commentValue) {
                this.id = id;
                this.customerId = customerId;
                this.scooterId = scooterId;
                this.commentValue = commentValue;
        }
}
