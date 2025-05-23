//package com.api.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import com.api.security.UserDTO;
//
//@FeignClient("user-service")
//public interface UserServiceClient {
//	@GetMapping("/email/{email}")
//	UserDTO getUserByEmail(@PathVariable String email);
//
//}
