package com.technest.needfood.models.alat;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.technest.needfood.models.bahan.RiwayatBeli;

import java.util.List;

public class Alat implements Parcelable {

	@SerializedName("kategori_id")
	private int kategoriId;

	@SerializedName("kd_alat")
	private String kdAlat;

	@SerializedName("nama")
	private String nama;

	@SerializedName("foto")
	private String foto;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("alat_keluar")
	private int alatKeluar;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("id")
	private int id;

	@SerializedName("riwayat_beli")
	private List<RiwayatBeliItem> mRiwayatBeliItem;

	@SerializedName("jumlah_alat")
	private int jumlahAlat;

	@SerializedName("sisa_alat")
	private int sisa_alat;

	protected Alat(Parcel in) {
		kategoriId = in.readInt();
		kdAlat = in.readString();
		nama = in.readString();
		foto = in.readString();
		updatedAt = in.readString();
		alatKeluar = in.readInt();
		createdAt = in.readString();
		kategori = in.readString();
		id = in.readInt();
		jumlahAlat = in.readInt();
		sisa_alat = in.readInt();
	}

	public static final Creator<Alat> CREATOR = new Creator<Alat>() {
		@Override
		public Alat createFromParcel(Parcel in) {
			return new Alat(in);
		}

		@Override
		public Alat[] newArray(int size) {
			return new Alat[size];
		}
	};

	public int getKategoriId() {
		return kategoriId;
	}

	public void setKategoriId(int kategoriId) {
		this.kategoriId = kategoriId;
	}

	public String getKdAlat() {
		return kdAlat;
	}

	public void setKdAlat(String kdAlat) {
		this.kdAlat = kdAlat;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getAlatKeluar() {
		return alatKeluar;
	}

	public void setAlatKeluar(int alatKeluar) {
		this.alatKeluar = alatKeluar;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<RiwayatBeliItem> mRiwayatBeliItem() {
		return mRiwayatBeliItem;
	}

	public void setmRiwayatBeli(List<RiwayatBeliItem> mRiwayatBeliItem) {
		this.mRiwayatBeliItem = mRiwayatBeliItem;
	}

	public int getJumlahAlat() {
		return jumlahAlat;
	}

	public void setJumlahAlat(int jumlahAlat) {
		this.jumlahAlat = jumlahAlat;
	}

	public int getSisa_alat() {
		return sisa_alat;
	}

	public void setSisa_alat(int sisa_alat) {
		this.sisa_alat = sisa_alat;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(kategoriId);
		dest.writeString(kdAlat);
		dest.writeString(nama);
		dest.writeString(foto);
		dest.writeString(updatedAt);
		dest.writeInt(alatKeluar);
		dest.writeString(createdAt);
		dest.writeString(kategori);
		dest.writeInt(id);
		dest.writeInt(jumlahAlat);
		dest.writeInt(sisa_alat);
	}
}
