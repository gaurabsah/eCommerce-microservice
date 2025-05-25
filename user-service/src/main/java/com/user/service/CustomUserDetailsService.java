package com.user.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.user.dao.UserDAO;
import com.user.entity.Users;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users userEntity = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return new User(userEntity.getUsername(), userEntity.getPassword(),
				Arrays.stream(userEntity.getRole().split("\\|")).map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList()));
	}
}