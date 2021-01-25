package com.technest.needfood.models.pesanan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AlatPilihanPesanan implements Parcelable {

    @SerializedName("alat_id")
    private String alat_id;

    @SerializedName("nama_alat")
    private String nama_alat;

    @SerializedName("jumlah")
    private String jumlah;

    @SerializedName("status")
    private String status;

    public String getAlat_id() {
        return alat_id;
    }

    public void setAlat_id(String alat_id) {
        this.alat_id = alat_id;
    }

    public String getNama_alat() {
        return nama_alat;
    }

    public void setNama_alat(String nama_alat) {
        this.nama_alat = nama_alat;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected AlatPilihanPesanan(Parcel in) {
        alat_id = in.readString();
        nama_alat = in.readString();
        jumlah = in.readString();
        status = in.readString();
    }

    public static final Creator<AlatPilihanPesanan> CREATOR = new Creator<AlatPilihanPesanan>() {
        @Override
        public AlatPilihanPesanan createFromParcel(Parcel in) {
            return new AlatPilihanPesanan(in);
        }

        @Override
        public AlatPilihanPesanan[] newArray(int size) {
            return new AlatPilihanPesanan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(alat_id);
        dest.writeString(nama_alat);
        dest.writeString(jumlah);
        dest.writeString(status);
    }
}
