package com.guard.config;



import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests()
			.mvcMatchers("/api/auth/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.oauth2ResourceServer().jwt();
		return http.build();
	}
	@Bean
	public JwtDecoder jwtDecoder(@Value("${jwt.secret}") String secretKey) {
		SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HMACSHA256");
		return NimbusJwtDecoder.withSecretKey(keySpec).build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.builder()
			.username("admin")
			.password("{noop}password")
			.roles("USER")
			.build();
		return new InMemoryUserDetailsManager(user);
	}




}
