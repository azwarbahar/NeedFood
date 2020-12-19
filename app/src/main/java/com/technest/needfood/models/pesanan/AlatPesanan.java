package com.technest.needfood.models.pesanan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AlatPesanan implements Parcelable {

    @SerializedName("kategori_alat_id")
    private int kategori_alat_id;

    @SerializedName("kategori_alat")
    private String kategori_alat;

    @SerializedName("jumlah_alat")
    private String jumlah_alat;

    @SerializedName("alat_dipilih")
    private ArrayList<AlatPilihanPesanan> alat_dipilih;

    public AlatPesanan() {
    }

    protected AlatPesanan(Parcel in) {
        kategori_alat_id = in.readInt();
        kategori_alat = in.readString();
        jumlah_alat = in.readString();
        alat_dipilih = in.createTypedArrayList(AlatPilihanPesanan.CREATOR);
    }

    public static final Creator<AlatPesanan> CREATOR = new Creator<AlatPesanan>() {
        @Override
        public AlatPesanan createFromParcel(Parcel in) {
            return new AlatPesanan(in);
        }

        @Override
        public AlatPesanan[] newArray(int size) {
            return new AlatPesanan[size];
        }
    };

    public int getKategori_alat_id() {
        return kategori_alat_id;
    }

    public void setKategori_alat_id(int kategori_alat_id) {
        this.kategori_alat_id = kategori_alat_id;
    }

    public String getKategori_alat() {
        return kategori_alat;
    }

    public void setKategori_alat(String kategori_alat) {
        this.kategori_alat = kategori_alat;
    }

    public String getJumlah_alat() {
        return jumlah_alat;
    }

    public void setJumlah_alat(String jumlah_alat) {
        this.jumlah_alat = jumlah_alat;
    }

    public ArrayList<AlatPilihanPesanan> getAlat_dipilih() {
        return alat_dipilih;
    }

    public void setAlat_dipilih(ArrayList<AlatPilihanPesanan> alat_dipilih) {
        this.alat_dipilih = alat_dipilih;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(kategori_alat_id);
        dest.writeString(kategori_alat);
        dest.writeString(jumlah_alat);
        dest.writeTypedList(alat_dipilih);
    }
}
