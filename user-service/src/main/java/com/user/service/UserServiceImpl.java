package com.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.dao.UserDAO;
import com.user.dto.UserDTO;
import com.user.entity.Users;
import com.user.exception.ResourcesNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	private final UserDAO userDAO;
	private final ModelMapper modelMapper;
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	public UserServiceImpl(UserDAO userDAO, ModelMapper modelMapper) {
		this.userDAO = userDAO;
		this.modelMapper = modelMapper;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Override
	public String registerUser(UserDTO user) {
		log.info("inside registerUser()");
		Optional<Users> userEntity = userDAO.findByUsername(user.getUsername());
//				.orElseThrow(() -> new UsernameNotFoundException("Username not Found"));
		if (userEntity.isPresent()) {
			log.info("Username already taken");
			return "Username already taken";
		}

		Users users = modelMapper.map(user, Users.class);
		users.setPassword(passwordEncoder.encode(user.getPassword()));
		users.setRole("ROLE_USER");
		userDAO.save(users);
		log.info("User Registered successfully");

		return "User Registered successfully";
	}

	@Override
	public Map<String, Object> login(String username, String password) {
		Map<String, Object> response = new HashMap<>();
		Optional<Users> userEntity = userDAO.findByUsername(username);
		if (!userEntity.isPresent()) {
			response.put("status", "User Not Found");
			return response;
		}
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if (authentication.isAuthenticated()) {
			String accessToken = jwtService.generateToken(userEntity.get());
			response.put("access_token", accessToken);
			response.put("expires_in", System.currentTimeMillis() + 1000 * 60 * 30);
			return response;
		} else {
			throw new RuntimeException("invalid access");
		}

	}
	
	public UserDTO getUser(String id) {
		log.info("inside getUser()");
		Users user = userDAO.findById(id).orElseThrow(()-> new ResourcesNotFoundException("User Not Found"));
		log.info("user fetched: {}",user);
		return modelMapper.map(user, UserDTO.class);
	}

}
