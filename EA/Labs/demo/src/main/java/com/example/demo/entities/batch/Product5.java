package com.example.demo.entities.batch;

import com.example.demo.entities.subselect.Review4;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
public class Product5 {
    @Id
    private Long id;
    private String name;
    private String color;

    @JoinColumn(name = "product_id")
    @OneToMany
    @Fetch(value = FetchMode.SELECT)
    @BatchSize(size = 10)
    private List<Review5> reviews;
}
