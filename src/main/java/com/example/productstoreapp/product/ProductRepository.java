package com.example.productstoreapp.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM  Product p WHERE p.name LIKE CONCAT('%', :query, '%')" +
            " OR p.description LIKE CONCAT('%', :query, '%')")
    Page<Product> searchProducts(String query, Pageable pageable);
}
