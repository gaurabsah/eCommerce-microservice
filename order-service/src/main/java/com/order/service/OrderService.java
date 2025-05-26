package com.order.service;

import java.util.List;

import com.order.dto.OrderDTO;

public interface OrderService {

	OrderDTO createOrder(OrderDTO order);

	List<OrderDTO> getOrdersByUser(String userId);

	OrderDTO getOrderById(Long orderId);

	OrderDTO updateOrderStatus(Long orderId, String status);

}
