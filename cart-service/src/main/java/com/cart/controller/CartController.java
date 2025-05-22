package com.cart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.dto.CartItemDTO;
import com.cart.service.CartItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartItemService cartItemService;

	@PostMapping("/add")
	public ResponseEntity<CartItemDTO> addToCart(@RequestBody CartItemDTO item) {
		CartItemDTO addToCart = cartItemService.addToCart(item);
		return new ResponseEntity<CartItemDTO>(addToCart, HttpStatus.CREATED);
	}
/*
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Long userId) {
		List<CartItemDTO> cartItems = cartItemService.getCartItems(userId);
		return new ResponseEntity<>(cartItems, HttpStatus.OK);
	}

	@DeleteMapping("/clear/{userId}")
	public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
		cartItemService.clearCart(userId);
		return ResponseEntity.ok().build();
	}
*/
}
