package com.sarahgraham.bookclub.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginUser {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format, try 'example@email.com'")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    public LoginUser() {
    }

	public LoginUser(
			@NotBlank(message = "Email is required") @Email(message = "Invalid email format, try 'example@email.com'") String email,
			@NotBlank(message = "Password is required") String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

	