package com.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.payment.config.FeignClientConfig;
import com.payment.dto.OrderDTO;

@FeignClient(name = "order-service", configuration = FeignClientConfig.class)
public interface OrderServiceClient {

	@PutMapping("/orders/update-status")
	OrderDTO updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("status") String status);

	@GetMapping("/orders/{orderId}")
	OrderDTO getOrderById(@PathVariable Long orderId);
}
