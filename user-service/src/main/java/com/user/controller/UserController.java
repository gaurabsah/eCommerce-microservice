package com.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.UserDTO;
import com.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserDTO dto) {
		String registerUser = userService.registerUser(dto);
		return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
		return ResponseEntity.ok(userService.login(username, password));
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") String id){
		log.info("Authenticated user: {}", SecurityContextHolder.getContext().getAuthentication());
		log.info("Roles: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return ResponseEntity.ok(userService.getUser(id));
	}

}
