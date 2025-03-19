package com.guard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AccessTokenRequestDTO {
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String code;
}
