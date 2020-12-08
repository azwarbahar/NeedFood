package com.technest.needfood.models.user;

public class ResponAdmin {
	private Administrasi result;
	private boolean success;
	private String message;

	public Administrasi getAdministrasi(){
		return result;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}
