package com.example.demo.entities.select;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review2 {
    @Id
    private Long id;

    private String comment;
}
