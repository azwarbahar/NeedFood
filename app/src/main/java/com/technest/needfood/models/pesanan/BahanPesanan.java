package com.technest.needfood.models.pesanan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BahanPesanan implements Parcelable {

    @SerializedName("bahan_id")
    private int bahan_id;

    @SerializedName("nama_bahan")
    private String nama_bahan;

    @SerializedName("jumlah_bahan")
    private String jumlah_bahan;

    protected BahanPesanan(Parcel in) {
        bahan_id = in.readInt();
        nama_bahan = in.readString();
        jumlah_bahan = in.readString();
    }

    public static final Creator<BahanPesanan> CREATOR = new Creator<BahanPesanan>() {
        @Override
        public BahanPesanan createFromParcel(Parcel in) {
            return new BahanPesanan(in);
        }

        @Override
        public BahanPesanan[] newArray(int size) {
            return new BahanPesanan[size];
        }
    };

    public int getBahan_id() {
        return bahan_id;
    }

    public void setBahan_id(int bahan_id) {
        this.bahan_id = bahan_id;
    }

    public String getNama_bahan() {
        return nama_bahan;
    }

    public void setNama_bahan(String nama_bahan) {
        this.nama_bahan = nama_bahan;
    }

    public String getJumlah_bahan() {
        return jumlah_bahan;
    }

    public void setJumlah_bahan(String jumlah_bahan) {
        this.jumlah_bahan = jumlah_bahan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bahan_id);
        dest.writeString(nama_bahan);
        dest.writeString(jumlah_bahan);
    }
}
