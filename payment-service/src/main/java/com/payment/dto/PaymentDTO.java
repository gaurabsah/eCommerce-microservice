package com.payment.dto;

import java.time.LocalDateTime;

public class PaymentDTO {
	private Long orderId;
	private String userId;
	private double amount;
	private String status; // e.g., SUCCESS, FAILED
	private LocalDateTime timestamp;

	public PaymentDTO() {

	}

	public PaymentDTO(Long orderId, String userId, double amount, String status, LocalDateTime timestamp) {
		this.orderId = orderId;
		this.userId = userId;
		this.amount = amount;
		this.status = status;
		this.timestamp = timestamp;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "PaymentDTO [orderId=" + orderId + ", userId=" + userId + ", amount=" + amount + ", status=" + status
				+ ", timestamp=" + timestamp + "]";
	}

}
