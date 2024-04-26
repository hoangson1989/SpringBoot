package com.example.demo;

import com.example.demo.entities.batch.Product5;
import com.example.demo.entities.batch.Review5;
import com.example.demo.entities.join.Product;
import com.example.demo.entities.join.Review;
import com.example.demo.entities.select.Product2;
import com.example.demo.entities.select.Product3;
import com.example.demo.entities.select.Review2;
import com.example.demo.entities.select.Review3;
import com.example.demo.entities.subselect.Product4;
import com.example.demo.entities.subselect.Review4;
import com.example.demo.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor

public class DemoApplication implements CommandLineRunner {

    private final ReviewRepository reviewRepository;
    private final Review2Repository review2Repository;
    private final Review3Repository review3Repository;
    private final Review4Repository review4Repository;
    private final Review5Repository review5Repository;

    private final ProductRepository productRepository;
    private final Product2Repository product2Repository;
    private final Product3Repository product3Repository;
    private final Product4Repository product4Repository;
    private final Product5Repository product5Repository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0 ; i < 10 ; i++) {
            Product product = new Product();
            product.setId((long) i+1);
            product.setName("Product " + (i+1));
            product.setColor("Red");
            List<Review> reviews = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                Review review = new Review();
                review.setId((long)j + 1);
                review.setComment("Review " + (j+1));
                reviewRepository.save(review);
                reviews.add(review);
            }
            product.setReviews(reviews);
            productRepository.save(product);
        }
        for (int i = 0 ; i < 10 ; i++) {
            Product2 product = new Product2();
            product.setId((long) i+1);
            product.setName("Product " + (i+1));
            product.setColor("Red");
            List<Review2> reviews = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                Review2 review = new Review2();
                review.setId((long)j + 1);
                review.setComment("Review " + (j+1));
                review2Repository.save(review);
                reviews.add(review);
            }
            product.setReviews(reviews);
            product2Repository.save(product);
        }
        for (int i = 0 ; i < 10 ; i++) {
            Product3 product = new Product3();
            product.setId((long) i+1);
            product.setName("Product " + (i+1));
            product.setColor("Red");
            List<Review3> reviews = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                Review3 review = new Review3();
                review.setId((long)j + 1);
                review.setComment("Review " + (j+1));
                review3Repository.save(review);
                reviews.add(review);
            }
            product.setReviews(reviews);
            product3Repository.save(product);
        }
        for (int i = 0 ; i < 10 ; i++) {
            Product4 product = new Product4();
            product.setId((long) i+1);
            product.setName("Product " + (i+1));
            product.setColor("Red");
            List<Review4> reviews = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                Review4 review = new Review4();
                review.setId((long)j + 1);
                review.setComment("Review " + (j+1));
                review4Repository.save(review);
                reviews.add(review);
            }
            product.setReviews(reviews);
            product4Repository.save(product);
        }
        for (int i = 0 ; i < 10 ; i++) {
            Product5 product = new Product5();
            product.setId((long) i+1);
            product.setName("Product " + (i+1));
            product.setColor("Red");
            List<Review5> reviews = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                Review5 review = new Review5();
                review.setId((long)j + 1);
                review.setComment("Review " + (j+1));
                review5Repository.save(review);
                reviews.add(review);
            }
            product.setReviews(reviews);
            product5Repository.save(product);
        }
    }
}
