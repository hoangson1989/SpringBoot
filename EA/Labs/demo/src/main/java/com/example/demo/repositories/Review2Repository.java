package com.example.demo.repositories;

import com.example.demo.entities.join.Review;
import com.example.demo.entities.select.Review2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Review2Repository extends JpaRepository<Review2, Long> {
}
