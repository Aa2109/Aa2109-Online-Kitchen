package com.aashif.kitchen.response;

import com.aashif.kitchen.enumUsed.USER_ROLE;

import lombok.Data;

@Data
public class AuthResponse {
	
	private String jwt;
	private String message;
	private USER_ROLE role;
}
