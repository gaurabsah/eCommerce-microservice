package com.payment.service;

import com.payment.dto.PaymentDTO;

public interface PaymentService {

	PaymentDTO processPayment(PaymentDTO dto);

	PaymentDTO getPaymentById(Long paymentId);

}
