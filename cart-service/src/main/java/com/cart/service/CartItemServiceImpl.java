package com.cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cart.dto.CartItemDTO;
import com.cart.dto.ProductDTO;
import com.cart.dto.UserDTO;
import com.cart.entity.CartItem;
import com.cart.exception.InsufficientResourcesException;
import com.cart.exception.ResourcesNotFoundException;
import com.cart.helper.RemoteServiceHelper;
import com.cart.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

	private final CartItemRepository cartItemRepository;
	private final ModelMapper modelMapper;
	private static final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);
	private final RemoteServiceHelper remoteServiceHelper;

	public CartItemServiceImpl(CartItemRepository cartItemRepository, ModelMapper modelMapper,
			RemoteServiceHelper remoteServiceHelper) {
		this.cartItemRepository = cartItemRepository;
		this.modelMapper = modelMapper;
		this.remoteServiceHelper = remoteServiceHelper;
	}

	@Override
	public CartItemDTO addToCart(CartItemDTO item) {
		log.info("inside addToCart()");
		log.info("product id: {}", item.getProductId());
		log.info("user id: {}", item.getUserId());
		ProductDTO product = remoteServiceHelper.getProductById(item.getProductId());
		log.info("fetched product {}", product);
		if (product.getStock() < item.getQuantity()) {
			log.info("Insufficient stock for product {}", product.getProductName());
			throw new InsufficientResourcesException("Insufficient stock for product: " + product.getProductName());
		}
		try {
			log.info("fetching user...");
			UserDTO user = remoteServiceHelper.getUserById(item.getUserId());
			log.info("fetched user {}", user.getEmail());
		} catch (Exception e) {
			log.error("Error fetching user by ID {}: {}", item.getUserId(), e.getMessage(), e);
			throw new ResourcesNotFoundException("Invalid user ID: " + item.getUserId());
		}
//		CartItem cartItem = modelMapper.map(item, CartItem.class);
		CartItem cartItem = new CartItem();
		cartItem.setProductId(item.getProductId());
		cartItem.setUserId(item.getUserId());
		cartItem.setQuantity(item.getQuantity());
		CartItem addedItems = cartItemRepository.save(cartItem);
		log.info("Item Added to Cart Successfully...");
		return modelMapper.map(addedItems, CartItemDTO.class);
	}

	@Override
	public List<CartItemDTO> getCartItems(String userId) {
		log.info("inside getCartItems()");
		List<CartItem> items = cartItemRepository.findByUserId(userId);
		log.info("Items fetched Successfully...");
		return items.stream().map(i -> modelMapper.map(i, CartItemDTO.class)).collect(Collectors.toList());
	}

	@Override
	public void clearCart(String userId) {
		log.info("inside clearCart()");
		List<CartItem> items = cartItemRepository.findByUserId(userId);
		log.info("Item removed from Cart Successfully...");
		cartItemRepository.deleteAll(items);
	}

}
