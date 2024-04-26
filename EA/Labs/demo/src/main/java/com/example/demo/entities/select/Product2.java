package com.example.demo.entities.select;

import com.example.demo.entities.join.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
public class Product2 {
    @Id
    private Long id;
    private String name;
    private String color;

    @JoinColumn(name = "product_id")
    @OneToMany(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    private List<Review2> reviews;
}
