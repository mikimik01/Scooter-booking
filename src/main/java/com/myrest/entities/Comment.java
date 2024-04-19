package com.myrest.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
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
    Integer id;
    Integer uid;
    String text;

    public Comment(Integer id, Integer uid, String text) {
        this.id = id;
        this.uid = uid;
        this.text = text;
    }

    public Comment(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(uid, comment.uid) && Objects.equals(text, comment.text);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, uid, text);
    }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", uid=" + uid +
                ", text='" + text + '\'' +
                '}';
    }


}
