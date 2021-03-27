package com.technest.needfood.models.alat;

import com.google.gson.annotations.SerializedName;

public class CekAlatPesanan {

	@SerializedName("pemesanan_id")
	private int pemesananId;

	@SerializedName("jumlah")
	private int jumlah;

	@SerializedName("alat_id")
	private int alatId;

	@SerializedName("nama_alat")
	private String namaAlat;

	@SerializedName("kategori_alat_id")
	private int kategoriAlatId;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public int getPemesananId(){
		return pemesananId;
	}

	public int getJumlah(){
		return jumlah;
	}

	public int getAlatId(){
		return alatId;
	}

	public String getNamaAlat(){
		return namaAlat;
	}

	public int getKategoriAlatId(){
		return kategoriAlatId;
	}

	public int getId(){
		return id;
	}

	public String getStatus(){
		return status;
	}
}