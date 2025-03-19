package com.guard.service;

import com.guard.model.Client;
import com.guard.model.User;
import com.guard.repository.ClientRepository;
import com.guard.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataInitializer {

	private final UserRepository userRepository;
	private final ClientRepository clientRepository;
	private final PasswordEncoder passwordEncoder;

	public DataInitializer(UserRepository userRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.clientRepository = clientRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostConstruct
	public void init() {
		Client client = new Client();
		client.setClientId("client123");
		client.setClientSecret(passwordEncoder.encode("secret123"));
		client.setRedirectUri("http://localhost:3000/oauth");
		clientRepository.save(client);

		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("password123"));
		admin.setClient(client);
		userRepository.save(admin);

		User user = new User();
		user.setUsername("user");
		user.setPassword(passwordEncoder.encode("password123"));
		user.setClient(client);
		userRepository.save(user);
	}
}
