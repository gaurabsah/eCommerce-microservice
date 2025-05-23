package com.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service",url = "http://localhost:8084")
public interface OrderServiceClient {

    @PutMapping("/orders/update-status")
    void updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("status") String status);
}
