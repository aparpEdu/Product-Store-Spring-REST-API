package com.example.productstoreapp.product;

import com.example.productstoreapp.transaction.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM  Product p WHERE p.name LIKE CONCAT('%', :query, '%')" +
            " OR p.description LIKE CONCAT('%', :query, '%')")
    Page<Product> searchProducts(String query, Pageable pageable);

    List<Product> findByOrder(Order order);
}
