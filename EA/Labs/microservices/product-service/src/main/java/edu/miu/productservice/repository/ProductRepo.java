package edu.miu.productservice.repository;

import edu.miu.productservice.entity.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends ListCrudRepository<Product,Integer> {
}
