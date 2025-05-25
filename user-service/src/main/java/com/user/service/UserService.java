package com.user.service;

import java.util.Map;

import com.user.dto.UserDTO;

public interface UserService {

	String registerUser(UserDTO user);

	Map<String, Object> login(String username, String password);

	UserDTO getUser(String id);
}
