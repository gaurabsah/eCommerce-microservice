package com.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
	private String userId;
	private double totalAmount;
	private LocalDateTime createdAt;
	private String status;
	private List<OrderItemDTO> items;

	public OrderDTO() {

	}

	public OrderDTO(String userId, double totalAmount, LocalDateTime createdAt, String status,
			List<OrderItemDTO> items) {
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
		this.status = status;
		this.items = items;
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

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "OrderDTO [userId=" + userId + ", totalAmount=" + totalAmount + ", createdAt=" + createdAt + ", status="
				+ status + ", items=" + items + "]";
	}

}
