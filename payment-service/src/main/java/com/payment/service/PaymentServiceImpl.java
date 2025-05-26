package com.payment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.payment.dao.PaymentDAO;
import com.payment.dto.CartItemDTO;
import com.payment.dto.OrderDTO;
import com.payment.dto.PaymentDTO;
import com.payment.dto.ProductDTO;
import com.payment.entity.Payment;
import com.payment.exception.ResourcesNotFoundException;
import com.payment.exception.SomethingWentWrongException;
import com.payment.helper.RemoteServiceHelper;

import jakarta.transaction.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
	private final PaymentDAO dao;
	private final ModelMapper modelMapper;
	private final RemoteServiceHelper remoteServiceHelper;

	private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	public PaymentServiceImpl(PaymentDAO dao, ModelMapper modelMapper, RemoteServiceHelper remoteServiceHelper) {
		this.dao = dao;
		this.modelMapper = modelMapper;
		this.remoteServiceHelper = remoteServiceHelper;
	}

	@Transactional
	@Override
	public PaymentDTO processPayment(PaymentDTO dto) {
		log.info("inside processPayment()");

		Payment payment = new Payment();
		payment.setAmount(dto.getAmount());
		payment.setOrderId(dto.getOrderId());
		payment.setUserId(dto.getUserId());
		payment.setTimestamp(LocalDateTime.now());

		try {
			// Step 1: Fetch Order to verify payment amount
			OrderDTO orderDTO = remoteServiceHelper.getOrderById(dto.getOrderId());

			if (dto.getAmount() == orderDTO.getTotalAmount()) {
				payment.setStatus("SUCCESS");
			} else {
				payment.setStatus("FAILED");
			}

			// Step 2: Save payment info
			Payment paid = dao.save(payment);
			log.info("Payment saved with status: {}", paid.getStatus());

			// Step 3: If payment is successful, proceed to update order + cart + stock
			if (paid.getStatus().equalsIgnoreCase("SUCCESS")) {
				// Update order status
				remoteServiceHelper.updateOrderStatus(paid.getOrderId(), paid.getStatus());
				log.info("Order status updated.");

				// Update product stock
				try {
					List<CartItemDTO> cartDetail = remoteServiceHelper.getCartDetail(paid.getUserId());

					for (CartItemDTO item : cartDetail) {
						ProductDTO product = remoteServiceHelper.getProduct(item.getProductId());
						int newStock = product.getStock() - item.getQuantity();
						remoteServiceHelper.updateProductStock(item.getProductId(), newStock);
					}

				} catch (Exception e) {
					log.error("Failed to update product stock: {}", e.getMessage());
				}

				// Clear cart
				try {
					remoteServiceHelper.clearCart(dto.getUserId());
					log.info("Cart cleared successfully.");
				} catch (Exception e) {
					throw new SomethingWentWrongException("Failed to clear cart for user: " + dto.getUserId());
				}
			}

			return modelMapper.map(paid, PaymentDTO.class);

		} catch (Exception e) {
			log.error("Payment processing failed: {}", e.getMessage());
			throw new SomethingWentWrongException("Payment processing failed: " + e.getMessage());
		}
	}

	@Override
	public PaymentDTO getPaymentById(Long paymentId) {
		Payment payment = dao.findById(paymentId)
				.orElseThrow(() -> new ResourcesNotFoundException("Payment Not Found"));
		return modelMapper.map(payment, PaymentDTO.class);
	}

}
