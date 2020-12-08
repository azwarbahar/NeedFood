package com.technest.needfood.models.intro;

public class LoginModel{
	private String role;
	private boolean success;
	private int id;
	private String message;

	public String getRole(){
		return role;
	}

	public boolean isSuccess(){
		return success;
	}

	public int getId(){
		return id;
	}

	public String getMessage(){
		return message;
	}
}
