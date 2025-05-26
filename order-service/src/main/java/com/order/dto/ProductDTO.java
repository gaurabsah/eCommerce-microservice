package com.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	@NotBlank(message = "Product name cannot be blank")
	@Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
	private String productName;

	@NotBlank(message = "Product description cannot be blank")
	@Size(min = 3, max = 1000, message = "Product description must be between 3 and 1000 characters")
	private String productDescription;

	@NotNull(message = "Product Quantity cannot be null")
	private double productPrice;

	@NotNull(message = "Product active cannot be null")
	private boolean active;

	@NotNull(message = "Product stock cannot be null")
	private int stock;

	public ProductDTO() {
	}

	public ProductDTO(String productName, String productDescription, double productPrice, boolean active, int stock) {
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.active = active;
		this.stock = stock;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "ProductDTO{" +
				"productName='" + productName + '\'' +
				", productDescription='" + productDescription + '\'' +
				", productPrice=" + productPrice +
				", active=" + active +
				", stock=" + stock +
				'}';
	}
}
