package com.example.productstoreapp.transaction.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
