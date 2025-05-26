package com.payment.dto;

import jakarta.validation.constraints.NotNull;

public class CartItemDTO {

	@NotNull(message = "User ID can't be null")
	private String userId;

	@NotNull(message = "Product ID can't be null")
	private Long productId;

	@NotNull(message = "Quantity can't be null")
	private int quantity;

	public CartItemDTO() {

	}

	public CartItemDTO(String userId, Long productId, int quantity) {
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartItemDTO [userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + "]";
	}

}
