package com.order.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.order.dao.OrderDAO;
import com.order.dto.OrderDTO;
import com.order.exception.ResourcesNotFoundException;
import com.order.exception.SomethingWentWrongException;
import com.order.helper.RemoteServiceHelper;
import com.order.model.Order;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderDAO orderDAO;
	private final ModelMapper modelMapper;
	private final RemoteServiceHelper helper;
	
	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	public OrderServiceImpl(OrderDAO orderDAO, ModelMapper modelMapper, RemoteServiceHelper helper) {
		this.orderDAO = orderDAO;
		this.modelMapper = modelMapper;
		this.helper = helper;
	}

	@Transactional
	@Override
	public OrderDTO createOrder(OrderDTO orderDTO) {
//		Order order = modelMapper.map(orderDTO, Order.class);
		log.info("inside createOrder()");
		Order order = new Order();
		order.setProductIds(orderDTO.getProductIds());
		order.setTotalAmount(orderDTO.getTotalAmount());
		order.setUserId(orderDTO.getUserId());
		order.setCreatedAt(LocalDateTime.now());
		order.setStatus("PENDING");
		Order savedOrder = orderDAO.save(order);
		log.info("Order saved successfully...");
		// Clear the cart after placing the order
		try {
			helper.clearCart(orderDTO.getUserId());
			log.info("Cart cleared successfully...)");
		} catch (Exception e) {
			throw new SomethingWentWrongException("Failed to clear cart for user:" + order.getUserId());
		}

		return modelMapper.map(savedOrder, OrderDTO.class);
	}

	@Override
	public List<OrderDTO> getOrdersByUser(String userId) {
		List<Order> orders = orderDAO.findByUserId(userId);
		return orders.stream().map(o -> modelMapper.map(o, OrderDTO.class)).toList();
	}

	@Override
	public OrderDTO getOrderById(Long orderId) {
		Order order = orderDAO.findById(orderId).orElseThrow(() -> new ResourcesNotFoundException("Order not found"));
		return modelMapper.map(order, OrderDTO.class);
	}

	@Override
	public String updateOrderStatus(Long orderId, String status) {
		Order order = orderDAO.findById(orderId).orElseThrow(() -> new ResourcesNotFoundException("Order not found"));
		order.setStatus(status);
		orderDAO.save(order);
		return "Order status updated to" + status;
	}

}
