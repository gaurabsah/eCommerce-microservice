package com.user.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.user.entity.Users;

public interface UserDAO extends MongoRepository<Users, String> {

	Optional<Users> findByUsername(String username);

}
