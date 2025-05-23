package com.user.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.user.dao.UserDAO;
import com.user.dto.UserDTO;
import com.user.entity.User;
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

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
		user.setId(UUID.randomUUID().toString());
		User newUser = userDAO.save(user);
		return modelMapper.map(newUser, UserDTO.class);
	}

	@Override
	public UserDTO getUserById(String Id) {
		log.info("inside getUserById()...");
		User user = userDAO.findById(Id).orElseThrow(() -> new ResourcesNotFoundException("User Not Found"));
		log.info("fetching user details: {}", user);
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		log.info("inside getUserByEmail()...");
		User user = userDAO.findByEmail(email).orElseThrow(() -> new ResourcesNotFoundException("User Not Found"));
		return modelMapper.map(user, UserDTO.class);
	}

}
