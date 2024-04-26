package com.example.demo.repositories;

import com.example.demo.entities.join.Review;
import com.example.demo.entities.subselect.Review4;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Review4Repository extends JpaRepository<Review4, Long> {
}
