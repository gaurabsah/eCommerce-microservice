//package com.api.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.api.client.UserServiceClient;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//	@Autowired
//	private UserServiceClient userService;
//
//	@Autowired
//	private JwtUtil jwtUtil;
//
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
//		UserDTO user = userService.getUserByEmail(request.getEmail());
//
//		if (user == null || !user.getPassword().equals(request.getPassword())) {
//			throw new BadCredentialsException("Invalid credentials");
//		}
//
//		String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
//		return ResponseEntity.ok(new AuthResponse(token));
//	}
//}
