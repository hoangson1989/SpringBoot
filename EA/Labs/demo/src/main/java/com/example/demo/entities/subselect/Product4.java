package com.example.demo.entities.subselect;

import com.example.demo.entities.select.Review3;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
public class Product4 {
    @Id
    private Long id;
    private String name;
    private String color;

    @JoinColumn(name = "product_id")
    @OneToMany
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Review4> reviews;
}
