package com.payment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
	private Long orderId;
	private Long userId;
	private double amount;
	private String status; // e.g., SUCCESS, FAILED
	private LocalDateTime timestamp;

}
