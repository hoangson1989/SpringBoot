package com.example.demo.repositories;

import com.example.demo.entities.batch.Review5;
import com.example.demo.entities.join.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Review5Repository extends JpaRepository<Review5, Long> {
}
