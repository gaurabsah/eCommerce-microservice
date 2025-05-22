package com.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.model.Order;

public interface OrderDAO extends JpaRepository<Order, Long>{
	List<Order> findByUserId(Long userId);
}
