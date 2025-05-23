package com.order.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userId;
	private double totalAmount;
	private LocalDateTime createdAt;
	private String status;
	@ElementCollection
	private List<Long> productIds;

	public Order() {
	}

	public Order(Long id, String userId, double totalAmount, LocalDateTime createdAt, String status,
			List<Long> productIds) {
		this.id = id;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
		this.status = status;
		this.productIds = productIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "Order [id=" + id + ", userId=" + userId + ", totalAmount=" + totalAmount + ", createdAt=" + createdAt
				+ ", status=" + status + ", productIds=" + productIds + "]";
	}

}
