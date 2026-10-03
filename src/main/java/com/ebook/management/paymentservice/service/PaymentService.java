package com.ebook.management.paymentservice.service;

import com.ebook.management.paymentservice.domain.Payment;
import com.ebook.management.paymentservice.dto.PaymentDto;
import com.ebook.management.paymentservice.exception.ResourceNotFoundException;
import com.ebook.management.paymentservice.mapper.PaymentMapper;
import com.ebook.management.paymentservice.repository.PaymentRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;

  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Transactional(readOnly = true)
  public List<PaymentDto> findPaymentsByOrderId(Long orderId) {
    return paymentRepository.findByOrderIdAndDeletedFalse(orderId).stream()
        .map(PaymentMapper::toDto)
        .toList();
  }

  @Transactional(readOnly = true)
  public PaymentDto findById(Long id) {
    return paymentRepository.findById(id)
        .filter(payment -> !payment.isDeleted())
        .map(PaymentMapper::toDto)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
  }

  @Transactional(readOnly = true)
  public List<PaymentDto> findPaymentsByProvider(String provider) {
    return paymentRepository.findByPaymentProviderAndDeletedFalse(provider).stream()
        .map(PaymentMapper::toDto)
        .toList();
  }

  @Transactional
  public PaymentDto createPayment(PaymentDto dto) {
    Payment payment = PaymentMapper.toEntity(dto);
    payment.setDeleted(false);
    payment.setUpdatedAt(Instant.now());
    Payment saved = paymentRepository.save(payment);
    return PaymentMapper.toDto(saved);
  }

  @Transactional
  public PaymentDto refundPayment(Long id, String refundReason) {
    Payment payment = paymentRepository.findById(id)
        .filter(p -> !p.isDeleted())
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

    if (payment.isRefunded()) {
      throw new IllegalStateException("Payment has already been refunded.");
    }

    payment.setRefunded(true);
    payment.setRefundReason(refundReason);
    payment.setStatus("REFUNDED");
    payment.setCompletedAt(Instant.now());
    payment.setUpdatedAt(Instant.now());
    return PaymentMapper.toDto(paymentRepository.save(payment));
  }
}
