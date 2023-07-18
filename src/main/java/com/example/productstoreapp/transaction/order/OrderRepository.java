package com.example.productstoreapp.transaction.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
@Tag(name = "Order Repository")
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Operation(
            summary = "Find order By Tracking number",
            description = "Find order By Tracking number is used to get an order from the database"
    )
    Optional<Order> findOrderByOrderTrackingNumber(String orderTrackingNumber);
}
