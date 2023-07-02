package com.example.productstoreapp.transaction.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByOrderTrackingNumber(String orderTrackingNumber);
}
