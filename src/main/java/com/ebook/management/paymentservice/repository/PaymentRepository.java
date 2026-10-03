package com.ebook.management.paymentservice.repository;

import com.ebook.management.paymentservice.domain.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
  List<Payment> findByOrderIdAndDeletedFalse(Long orderId);
  List<Payment> findByPaymentProviderAndDeletedFalse(String paymentProvider);
}
