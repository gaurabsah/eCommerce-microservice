package com.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.dao.OrderDAO;
import com.order.dto.OrderDTO;
import com.order.dto.OrderItemDTO;
import com.order.dto.ProductDTO;
import com.order.exception.ResourcesNotFoundException;
import com.order.helper.RemoteServiceHelper;
import com.order.model.Order;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderDAO orderDAO;
	private final ModelMapper modelMapper;

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	public OrderServiceImpl(OrderDAO orderDAO, ModelMapper modelMapper) {
		this.orderDAO = orderDAO;
		this.modelMapper = modelMapper;
	}
	
	@Autowired
	private RemoteServiceHelper remoteServiceHelper;

	@Override
	public OrderDTO createOrder(OrderDTO orderDTO) {
		log.info("inside createOrder()");

		double total = 0;

		for (OrderItemDTO item : orderDTO.getItems()) {
			ProductDTO product = remoteServiceHelper.getProduct(item.getProductId());
			total += product.getProductPrice() * item.getQuantity();
		}

		Order order = new Order();
		order.setUserId(orderDTO.getUserId());
		order.setCreatedAt(LocalDateTime.now());
		order.setStatus("PENDING");
		order.setTotalAmount(total);
		order.setProductIds(orderDTO.getItems()
				.stream().map(OrderItemDTO::getProductId)
				.collect(Collectors.toList()));

		Order savedOrder = orderDAO.save(order);
		log.info("Order saved successfully...");

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
	public OrderDTO updateOrderStatus(Long orderId, String status) {
		Order order = orderDAO.findById(orderId).orElseThrow(() -> new ResourcesNotFoundException("Order not found"));
		order.setStatus(status);
		Order updatedOrder = orderDAO.save(order);
		return modelMapper.map(updatedOrder, OrderDTO.class);
	}

}
