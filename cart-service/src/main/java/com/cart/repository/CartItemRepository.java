package com.cart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	List<CartItem> findByUserId(String userId);

	Optional<CartItem> findByUserIdAndProductId(String userId, Long productId);
}
