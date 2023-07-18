package com.example.productstoreapp.product;

import com.example.productstoreapp.transaction.order.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
@Tag(name = "Product Repository")
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Operation(
            summary = "Search Products By Name Or Description",
            description = "Search Products By Name Or Description is used to get all " +
                    "matching products paginated from the database"
    )
    @Query("SELECT p FROM  Product p WHERE p.name LIKE CONCAT('%', :query, '%')" +
            " OR p.description LIKE CONCAT('%', :query, '%')")
    Page<Product> searchProducts(String query, Pageable pageable);
    @Operation(
            summary = "Find products by Order",
            description = "Find products by Order is used to get all " + "products from an order  from the database"
    )
    List<Product> findByOrder(Order order);
}
