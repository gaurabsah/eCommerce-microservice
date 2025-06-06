package com.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cart.config.FeignConfig;
import com.cart.dto.UserDTO;

@FeignClient(name = "user-service")
public interface UserServiceClient {
	@GetMapping("/users/{id}")
	UserDTO getUser(@PathVariable("id") String id);
}
