package com.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.OrderDTO;
import com.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
		OrderDTO createOrder = orderService.createOrder(order);
		return new ResponseEntity<OrderDTO>(createOrder, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable String userId) {
		List<OrderDTO> ordersByUser = orderService.getOrdersByUser(userId);
		return new ResponseEntity<>(ordersByUser, HttpStatus.FOUND);
	}

	@GetMapping("/{orderId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
		OrderDTO orderById = orderService.getOrderById(orderId);
		return new ResponseEntity<OrderDTO>(orderById, HttpStatus.OK);
	}

	@PutMapping("/update-status")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<OrderDTO> updateOrderStatus(@RequestParam Long orderId, @RequestParam String status) {
		OrderDTO updateOrderStatus = orderService.updateOrderStatus(orderId, status);
		return new ResponseEntity<OrderDTO>(updateOrderStatus, HttpStatus.OK);
	}

}
