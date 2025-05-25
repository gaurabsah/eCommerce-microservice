package com.cart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes servletRequestAttributes) {
            String token = servletRequestAttributes.getRequest().getHeader("Authorization");
            if (token != null) {
                template.header("Authorization", token);
                System.out.println("Passing JWT token to user-service: " + token);
            } else {
                System.out.println("JWT token not found in request attributes.");
            }
        }
    }
}

