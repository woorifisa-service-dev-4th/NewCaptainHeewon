package com.guard.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String accessToken;

	@Column(nullable = false)
	private String clientId;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private Long expiresAt; // 만료 시간 (Epoch Time)

	public boolean isExpired() {
		return System.currentTimeMillis() > expiresAt;
	}
}
