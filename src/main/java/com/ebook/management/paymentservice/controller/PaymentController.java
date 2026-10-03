package com.ebook.management.paymentservice.controller;

import com.ebook.management.paymentservice.dto.PaymentDto;
import com.ebook.management.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping
  public ResponseEntity<List<PaymentDto>> getPayments(
      @RequestParam(required = false) Long orderId,
      @RequestParam(required = false) String provider) {
    if (orderId != null) {
      return ResponseEntity.ok(paymentService.findPaymentsByOrderId(orderId));
    }
    if (provider != null) {
      return ResponseEntity.ok(paymentService.findPaymentsByProvider(provider));
    }
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentDto> getPayment(@PathVariable Long id) {
    return ResponseEntity.ok(paymentService.findById(id));
  }

  @PostMapping
  public ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentDto dto) {
    return ResponseEntity.ok(paymentService.createPayment(dto));
  }

  @PutMapping("/{id}/refund")
  public ResponseEntity<PaymentDto> refundPayment(@PathVariable Long id,
      @RequestBody(required = false) PaymentDto dto) {
    String refundReason = dto != null ? dto.getRefundReason() : null;
    return ResponseEntity.ok(paymentService.refundPayment(id, refundReason));
  }
}
