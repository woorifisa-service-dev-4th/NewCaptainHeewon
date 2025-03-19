package com.guard.model;


import lombok.*;

import java.util.Set;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "client_id") // 외래 키 설정
	private Client client;
}
