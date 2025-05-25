package com.product.security;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			try {
				Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

				List<String> roles = claims.get("role", List.class);
				List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

				String userId = claims.get("userId", String.class);

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null,
						authorities);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);

			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

}