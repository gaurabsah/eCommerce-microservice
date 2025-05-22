package com.user.service;

import com.user.dto.UserDTO;

public interface UserService {
	
	UserDTO createUser(UserDTO user);
	UserDTO getUserById(String Id);

}
