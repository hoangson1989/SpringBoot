package com.example.demo.repositories;

import com.example.demo.entities.join.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(value = "product-reviews", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT p FROM Product p")
    List<Product> findAllWithReviews();
}
