package com.example.productstoreapp.transaction.payment;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
@Tag(name = "Payment Repository")
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
