//package com.api.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//	@Bean
//	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
//			JwtAuthenticationWebFilter jwtAuthFilter) {
//		return http.csrf().disable().httpBasic().disable().formLogin().disable()
//				.addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION).authorizeExchange()
//				.pathMatchers("/admin/**").hasRole("ADMIN").pathMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//				.pathMatchers("/public/**").permitAll().anyExchange().authenticated().and().build();
//	}
//
//}
