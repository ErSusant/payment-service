package com.ebook.management.paymentservice.mapper;

import com.ebook.management.paymentservice.domain.Payment;
import com.ebook.management.paymentservice.dto.PaymentDto;

public class PaymentMapper {

  public static PaymentDto toDto(Payment payment) {
    if (payment == null) {
      return null;
    }
    PaymentDto dto = new PaymentDto();
    dto.setId(payment.getId());
    dto.setOrderId(payment.getOrderId());
    dto.setPaymentProvider(payment.getPaymentProvider());
    dto.setProviderPaymentId(payment.getProviderPaymentId());
    dto.setAmount(payment.getAmount());
    dto.setCurrency(payment.getCurrency());
    dto.setStatus(payment.getStatus());
    dto.setPaymentMethod(payment.getPaymentMethod());
    dto.setReceiptUrl(payment.getReceiptUrl());
    return dto;
  }

  public static Payment toEntity(PaymentDto dto) {
    if (dto == null) {
      return null;
    }
    Payment payment = new Payment();
    payment.setId(dto.getId());
    payment.setOrderId(dto.getOrderId());
    payment.setPaymentProvider(dto.getPaymentProvider());
    payment.setProviderPaymentId(dto.getProviderPaymentId());
    payment.setAmount(dto.getAmount());
    payment.setCurrency(dto.getCurrency());
    payment.setStatus(dto.getStatus());
    payment.setPaymentMethod(dto.getPaymentMethod());
    payment.setReceiptUrl(dto.getReceiptUrl());
    return payment;
  }
}
