package com.example.demo.controllers;

import com.example.demo.entities.batch.Product5;
import com.example.demo.entities.join.Product;
import com.example.demo.entities.select.Product2;
import com.example.demo.entities.select.Product3;
import com.example.demo.entities.subselect.Product4;
import com.example.demo.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository repository;
    private final Product2Repository repository2;
    private final Product3Repository repository3;
    private final Product4Repository repository4;
    private final Product5Repository repository5;

    @GetMapping("/join")
    List<Product> getAll() {
        return  repository.findAllWithReviews();
    }

    @GetMapping("/select-lazy")
    List<Product2> getAll2() {
        return  repository2.findAll();
    }

    @GetMapping("/select-eager")
    List<Product3> getAll3() {
        return  repository3.findAll();
    }

    @GetMapping("/subselect")
    List<Product4> getAll4() {
        return  repository4.findAll();
    }

    @GetMapping("/batch")
    List<Product5> getAll5() {
        return  repository5.findAll();
    }
}
