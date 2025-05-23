package com.payment.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.payment.dao.PaymentDAO;
import com.payment.dto.PaymentDTO;
import com.payment.entity.Payment;
import com.payment.exception.ResourcesNotFoundException;
import com.payment.exception.SomethingWentWrongException;
import com.payment.helper.RemoteServiceHelper;

@Service
public class PaymentServiceImpl implements PaymentService {
	private final PaymentDAO dao;
	private final ModelMapper modelMapper;
	private final RemoteServiceHelper remoteServiceHelper;

	public PaymentServiceImpl(PaymentDAO dao, ModelMapper modelMapper, RemoteServiceHelper remoteServiceHelper) {
		this.dao = dao;
		this.modelMapper = modelMapper;
		this.remoteServiceHelper = remoteServiceHelper;
	}

	@Override
	public PaymentDTO processPayment(PaymentDTO dto) {
//		Payment payment = modelMapper.map(dto, Payment.class);
		Payment payment = new Payment();
		payment.setAmount(dto.getAmount());
		payment.setOrderId(dto.getOrderId());
		payment.setUserId(dto.getUserId());
		payment.setTimestamp(LocalDateTime.now());

		// Simulate payment processing
		if (payment.getAmount() > 0) {
			payment.setStatus("SUCCESS");
		} else {
			payment.setStatus("FAILED");
		}
		Payment paid = dao.save(payment);

		// Update order status after successful payment
		try {
			remoteServiceHelper.updateOrderStatus(payment.getOrderId(), payment.getStatus());
		} catch (Exception e) {
			throw new SomethingWentWrongException("Failed to update order status: " + e.getMessage());
		}

		return modelMapper.map(paid, PaymentDTO.class);
	}

	@Override
	public PaymentDTO getPaymentById(Long paymentId) {
		Payment payment = dao.findById(paymentId)
				.orElseThrow(() -> new ResourcesNotFoundException("Payment Not Found"));
		return modelMapper.map(payment, PaymentDTO.class);
	}

}
