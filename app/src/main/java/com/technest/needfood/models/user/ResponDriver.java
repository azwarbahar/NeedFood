package com.technest.needfood.models.user;

public class ResponDriver{
	private Driver result;
	private boolean success;
	private String message;

	public Driver getDriver(){
		return result;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}
