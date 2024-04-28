package com.myrest.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@ToString
public class Scooter {
    @Id
    @SequenceGenerator(
            name = "scooter_id_sequence",
            sequenceName = "scooter_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "scooter_id_sequence"
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
}
