package com.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
	private String userId;
	private double totalAmount;
	private LocalDateTime createdAt;
	private String status;
	private List<Long> productIds;

	public OrderDTO() {

	}

	public OrderDTO(String userId, double totalAmount, LocalDateTime createdAt, String status, List<Long> productIds) {
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
		this.status = status;
		this.productIds = productIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

	@Override
	public String toString() {
		return "OrderDTO [userId=" + userId + ", totalAmount=" + totalAmount + ", createdAt=" + createdAt + ", status="
				+ status + ", productIds=" + productIds + "]";
	}

}
