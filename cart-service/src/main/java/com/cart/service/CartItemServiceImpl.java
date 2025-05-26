package com.cart.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cart.dto.CartItemDTO;
import com.cart.dto.ProductDTO;
import com.cart.entity.CartItem;
import com.cart.exception.ResourcesNotFoundException;
import com.cart.helper.RemoteServiceHelper;
import com.cart.repository.CartItemRepository;

import jakarta.ws.rs.BadRequestException;

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
		public CartItemDTO addToCart(CartItemDTO cartItemDTO) {
		    log.info("Inside addToCart() for user: {}", cartItemDTO.getUserId());

		    ProductDTO product = remoteServiceHelper.getProductById(cartItemDTO.getProductId());
		    if (product == null) {
		        throw new ResourcesNotFoundException("Product not found with ID: " + cartItemDTO.getProductId());
		    }

		    if (product.getStock() < cartItemDTO.getQuantity()) {
		        throw new BadRequestException("Not enough stock available for product: " + product.getProductName());
		    }

		    Optional<CartItem> existingItemOpt = cartItemRepository.findByUserIdAndProductId(
		        cartItemDTO.getUserId(), cartItemDTO.getProductId());

		    CartItem cartItem;
		    if (existingItemOpt.isPresent()) {
		        cartItem = existingItemOpt.get();
		        int newQuantity = cartItem.getQuantity() + cartItemDTO.getQuantity();

		        if (newQuantity > product.getStock()) {
		            throw new BadRequestException("Exceeds available stock for product: " + product.getProductName());
		        }

		        cartItem.setQuantity(newQuantity);
		    } else {
		        cartItem = new CartItem();
		        cartItem.setUserId(cartItemDTO.getUserId());
		        cartItem.setProductId(cartItemDTO.getProductId());
		        cartItem.setQuantity(cartItemDTO.getQuantity());
		    }

		    CartItem saved = cartItemRepository.save(cartItem);
		    log.info("Item added/updated in cart: {}", saved);

		    return modelMapper.map(saved, CartItemDTO.class);
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
		log.info("Items fetched for user {}: {}", userId, items.size());
		log.info("Item removed from Cart Successfully...");
		cartItemRepository.deleteAll(items);
	}

}
