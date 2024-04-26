package com.example.demo.repositories;

import com.example.demo.entities.join.Review;
import com.example.demo.entities.select.Review3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Review3Repository extends JpaRepository<Review3, Long> {
}
