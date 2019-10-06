package com.github.yujiaao;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User {
    private String name;
    private String password;
    private String email;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
}
