package com.guard.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.guard.model.Client;
import com.guard.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
	private final ClientRepository clientRepository;

	public boolean validateClient(String clientId, String clientSecret, String redirectUri) {
		Client client = clientRepository.findByClientId(clientId)
			.orElseThrow(() -> new RuntimeException("Client not found"));

		return client.getClientSecret().equals(clientSecret) && client.getRedirectUri().equals(redirectUri);
	}
}
