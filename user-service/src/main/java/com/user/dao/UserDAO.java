package com.user.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.user.entity.User;

public interface UserDAO extends MongoRepository<User, String>{

	Optional<User> findByEmail(String email);

}
