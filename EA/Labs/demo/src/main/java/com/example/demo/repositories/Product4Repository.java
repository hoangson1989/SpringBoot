package com.example.demo.repositories;

import com.example.demo.entities.select.Product2;
import com.example.demo.entities.subselect.Product4;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Product4Repository extends JpaRepository<Product4, Long> {
}
