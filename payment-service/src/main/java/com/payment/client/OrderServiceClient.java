package com.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.payment.config.FeignClientConfig;

@FeignClient(name = "order-service", configuration = FeignClientConfig.class)
public interface OrderServiceClient {

	@PutMapping("/orders/update-status")
	void updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("status") String status);
}
