package com.example.Repository;

import com.example.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product ,Integer> {

    public boolean existsByProductName(String productName);

    public List<Product> findByProductNameIn(List<String> productNames);
}
