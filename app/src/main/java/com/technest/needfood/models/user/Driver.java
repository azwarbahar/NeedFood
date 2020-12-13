package com.technest.needfood.models.user;

public class Driver {
	private String nama;
	private String foto;
	private String updated_at;
	private String telepon;
	private String created_at;
	private int id;
	private String email;
	private String alamat;
	private String status;
	private String username;

	public String getNama(){
		return nama;
	}

	public String getFoto(){
		return foto;
	}

	public String getUpdatedAt(){
		return updated_at;
	}

	public String getTelepon(){
		return telepon;
	}

	public String getCreatedAt(){
		return created_at;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getAlamat(){
		return alamat;
	}

	public String getStatus(){
		return status;
	}

	public String getUsername(){
		return username;
	}
}
