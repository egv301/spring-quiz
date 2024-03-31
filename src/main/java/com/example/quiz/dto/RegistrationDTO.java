package com.example.quiz.dto;

import javax.validation.constraints.NotBlank;

public class RegistrationDTO {
	
	@NotBlank(message = "Username cannot be empty")
	private String username;
	@NotBlank(message = "Password cannot be empty")
	private String password;
	@NotBlank(message = "Confirm password cannot be empty")
	private String passwordConfirm;
	
	public RegistrationDTO() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
