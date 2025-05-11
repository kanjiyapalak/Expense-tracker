package javaproject.com.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javaproject.com.expensetracker.entities.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
