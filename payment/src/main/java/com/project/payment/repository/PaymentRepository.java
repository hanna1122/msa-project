package com.project.payment.repository;

import com.project.payment.domain.Order;
import com.project.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
