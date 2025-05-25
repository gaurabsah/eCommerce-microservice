package com.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import feign.RequestInterceptor;

@Configuration
public class FeignClientConfig {

    	@Bean
        public RequestInterceptor requestInterceptor() {
            return requestTemplate -> {
                var context = SecurityContextHolder.getContext();
                if (context != null && context.getAuthentication() != null) {
                    String token = context.getAuthentication().getCredentials().toString();
                    requestTemplate.header("Authorization", "Bearer " + token);
                }
            };
        }
}
