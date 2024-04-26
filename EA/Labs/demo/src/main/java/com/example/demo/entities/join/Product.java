package com.example.demo.entities.join;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@NamedEntityGraph(name = "product-reviews", attributeNodes = @NamedAttributeNode("reviews"))
public class Product {
    @Id
    private Long id;
    private String name;
    private String color;

    @JoinColumn(name = "product_id")
    @OneToMany
    @Fetch(value = FetchMode.JOIN)
    private List<Review> reviews;
}
