package com.cart.service;

import java.util.List;

import com.cart.dto.CartItemDTO;

public interface CartItemService {

	CartItemDTO addToCart(CartItemDTO item);

	List<CartItemDTO> getCartItems(String userId);

	void clearCart(String userId);

}
