package com.myrest.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Scooter {
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
    String name;
    String description;


    public Scooter(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Scooter(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scooter scooter = (Scooter) o;
        return Objects.equals(id, scooter.id) && Objects.equals(name, scooter.name) && Objects.equals(description, scooter.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Scooter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
