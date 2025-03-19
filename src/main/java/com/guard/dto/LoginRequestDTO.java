package com.guard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
	private String username;
	private String password;
	private String redirectUri;
	private String clientId;
}
