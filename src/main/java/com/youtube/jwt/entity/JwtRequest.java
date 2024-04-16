package com.youtube.jwt.entity;

public class JwtRequest {

	private String userName;
	private String userPassword;
	
	public JwtRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return "JwtRequest [userName=" + userName + ", userPassword=" + userPassword + "]";
	}
	
	
	
}
