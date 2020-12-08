package com.technest.needfood.models.user;

import com.google.gson.annotations.SerializedName;

public class Administrasi {
	@SerializedName("role")
	private String role;
	@SerializedName("nama")
	private String nama;
	@SerializedName("updatedAt")
	private String updatedAt;
	@SerializedName("createdAt")
	private String createdAt;
	@SerializedName("username")
	private String username;

	public String getRole(){
		return role;
	}

	public String getNama(){
		return nama;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getUsername(){
		return username;
	}
}
