package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.dto.PaymentDTO;
import com.payment.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	public ResponseEntity<PaymentDTO> processPayment(@RequestBody PaymentDTO payment) {
		PaymentDTO processPayment = paymentService.processPayment(payment);
		return new ResponseEntity<PaymentDTO>(processPayment, HttpStatus.CREATED);
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) {
		PaymentDTO paymentById = paymentService.getPaymentById(paymentId);
		return new ResponseEntity<PaymentDTO>(paymentById, HttpStatus.FOUND);
	}

}
