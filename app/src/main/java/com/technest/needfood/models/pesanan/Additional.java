package com.technest.needfood.models.pesanan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Additional implements Parcelable {

    @SerializedName("pemesanan_id")
    private String pemesanan_id;

    @SerializedName("additional_id")
    private String additional_id;

    @SerializedName("nama_daging")
    private String nama_daging;

    @SerializedName("harga")
    private String harga;

    @SerializedName("berat")
    private String berat;

    @SerializedName("jumlah")
    private String jumlah;

    @SerializedName("total_harga")
    private String total_harga;

    protected Additional(Parcel in) {
        pemesanan_id = in.readString();
        additional_id = in.readString();
        nama_daging = in.readString();
        harga = in.readString();
        berat = in.readString();
        jumlah = in.readString();
        total_harga = in.readString();
    }

    public static final Creator<Additional> CREATOR = new Creator<Additional>() {
        @Override
        public Additional createFromParcel(Parcel in) {
            return new Additional(in);
        }

        @Override
        public Additional[] newArray(int size) {
            return new Additional[size];
        }
    };

    public String getPemesanan_id() {
        return pemesanan_id;
    }

    public void setPemesanan_id(String pemesanan_id) {
        this.pemesanan_id = pemesanan_id;
    }

    public String getAdditional_id() {
        return additional_id;
    }

    public void setAdditional_id(String additional_id) {
        this.additional_id = additional_id;
    }

    public String getNama_daging() {
        return nama_daging;
    }

    public void setNama_daging(String nama_daging) {
        this.nama_daging = nama_daging;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pemesanan_id);
        dest.writeString(additional_id);
        dest.writeString(nama_daging);
        dest.writeString(harga);
        dest.writeString(berat);
        dest.writeString(jumlah);
        dest.writeString(total_harga);
    }
}
