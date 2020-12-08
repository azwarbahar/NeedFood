package com.technest.needfood.models.alat;

import com.google.gson.annotations.SerializedName;

public class RiwayatBeliItem {

	@SerializedName("updated_at")
	private String updated_at;

	@SerializedName("supplier")
	private String supplier;

	@SerializedName("alat_id")
	private int alat_id;

	@SerializedName("created_at")
	private String created_at;

	@SerializedName("total_harga")
	private int total_harga;

	@SerializedName("id")
	private int id;

	@SerializedName("jumlah_beli")
	private int jumlah_beli;

	@SerializedName("supplier_id")
	private int supplier_id;

	@SerializedName("kd_beli")
	private String kd_beli;

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public int getAlat_id() {
		return alat_id;
	}

	public void setAlat_id(int alat_id) {
		this.alat_id = alat_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getTotal_harga() {
		return total_harga;
	}

	public void setTotal_harga(int total_harga) {
		this.total_harga = total_harga;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJumlah_beli() {
		return jumlah_beli;
	}

	public void setJumlah_beli(int jumlah_beli) {
		this.jumlah_beli = jumlah_beli;
	}

	public int getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getKd_beli() {
		return kd_beli;
	}

	public void setKd_beli(String kd_beli) {
		this.kd_beli = kd_beli;
	}
}
