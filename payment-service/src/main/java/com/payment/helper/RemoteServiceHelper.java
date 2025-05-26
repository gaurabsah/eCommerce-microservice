package com.payment.helper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.client.CartServiceClient;
import com.payment.client.OrderServiceClient;
import com.payment.client.ProductServiceClient;
import com.payment.dto.CartItemDTO;
import com.payment.dto.OrderDTO;
import com.payment.dto.ProductDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class RemoteServiceHelper {

	@Autowired
	private OrderServiceClient orderServiceClient;

	@Autowired
	private CartServiceClient cartServiceClient;

	@Autowired
	private ProductServiceClient productServiceClient;

	private static final Logger log = LoggerFactory.getLogger(RemoteServiceHelper.class);

	@CircuitBreaker(name = "orderServiceCB", fallbackMethod = "fallbackUpdateStatus")
	@Retry(name = "orderServiceRetry")
	public OrderDTO updateOrderStatus(Long orderId, String status) {
		OrderDTO updateOrderStatus = orderServiceClient.updateOrderStatus(orderId, status);
		return updateOrderStatus;
	}

	public OrderDTO fallbackUpdateStatus(Long orderId, String status, Throwable ex) {
		throw new IllegalStateException("Order service is unavailable for order ID: " + orderId, ex);
	}

	@CircuitBreaker(name = "cartServiceCB", fallbackMethod = "fallbackClearCart")
	@Retry(name = "cartServiceRetry")
	public void clearCart(String userId) {
		log.info("inside RemoteServiceHelper:: clearCart()...");
		cartServiceClient.clearCart(userId);
	}

	public void fallbackClearCart(String userId, Throwable ex) {
		throw new IllegalStateException("Cart service is unavailable for user: " + userId, ex);
	}

	@CircuitBreaker(name = "cartServiceCB", fallbackMethod = "fallbackGetCartDetail")
	@Retry(name = "cartServiceRetry")
	public List<CartItemDTO> getCartDetail(String userId) {
		log.info("inside RemoteServiceHelper:: getCartDetail()...");
		List<CartItemDTO> cartItems = cartServiceClient.getCartItems(userId);
		return cartItems;
	}

	public List<CartItemDTO> fallbackGetCartDetail(String userId, Throwable ex) {
		throw new IllegalStateException("Cart service is unavailable for user: " + userId, ex);
	}

	@CircuitBreaker(name = "productServiceCB", fallbackMethod = "fallbackUpdateProductStock")
	@Retry(name = "productServiceRetry")
	public void updateProductStock(Long productId, int stock) {
		log.info("inside RemoteServiceHelper:: updateProductStock()...");
		productServiceClient.updateProductStock(productId, stock);
	}

	public void fallbackUpdateProductStock(Long productId, int stock, Throwable ex) {
		throw new IllegalStateException("Product service is unavailable for product ID: " + productId, ex);
	}

	@CircuitBreaker(name = "productServiceCB", fallbackMethod = "fallbackGetProduct")
	@Retry(name = "productServiceRetry")
	public ProductDTO getProduct(Long productId) {
		log.info("inside RemoteServiceHelper:: getProduct()...");
		ProductDTO product = productServiceClient.getProduct(productId);
		return product;
	}

	public ProductDTO fallbackGetProduct(Long productId, Throwable ex) {
		throw new IllegalStateException("Product service is unavailable for product ID: " + productId, ex);
	}

	@CircuitBreaker(name = "orderServiceCB", fallbackMethod = "fallbackGetOrder")
	@Retry(name = "orderServiceRetry")
	public OrderDTO getOrderById(Long orderId) {
		OrderDTO order = orderServiceClient.getOrderById(orderId);
		return order;
	}

	public OrderDTO fallbackGetOrder(Long orderId, Throwable ex) {
		throw new IllegalStateException("Order service is unavailable for order ID: " + orderId, ex);
	}

}
