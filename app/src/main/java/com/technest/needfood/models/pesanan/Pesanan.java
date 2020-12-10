
package com.technest.needfood.models.pesanan;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Pesanan implements Parcelable {

    @SerializedName("additional")
    private List<Additional> additional;
    @SerializedName("catatan")
    private String catatan;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("deskripsi_lokasi")
    private String deskripsi_lokasi;
    @SerializedName("driver_id")
    private String driver_id;
    @SerializedName("id")
    private Long id;
    @SerializedName("kd_pemesanan")
    private String kd_pemesanan;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("logitude")
    private String logitude;
    @SerializedName("nama")
    private String nama;
    @SerializedName("no_telepon")
    private String no_telepon;
    @SerializedName("no_wa")
    private String no_wa;
    @SerializedName("paket")
    private List<Paket> paket;
    @SerializedName("status")
    private String status;
    @SerializedName("tanggal_antar")
    private String tanggal_antar;
    @SerializedName("transaksi")
    private Transaksi transaksi;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("waktu_antar")
    private String waktu_antar;

    protected Pesanan(Parcel in) {
        additional = in.createTypedArrayList(Additional.CREATOR);
        catatan = in.readString();
        created_at = in.readString();
        deskripsi_lokasi = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        kd_pemesanan = in.readString();
        latitude = in.readString();
        logitude = in.readString();
        nama = in.readString();
        no_telepon = in.readString();
        no_wa = in.readString();
        paket = in.createTypedArrayList(Paket.CREATOR);
        status = in.readString();
        tanggal_antar = in.readString();
        transaksi = in.readParcelable(Transaksi.class.getClassLoader());
        updated_at = in.readString();
        waktu_antar = in.readString();
    }

    public static final Creator<Pesanan> CREATOR = new Creator<Pesanan>() {
        @Override
        public Pesanan createFromParcel(Parcel in) {
            return new Pesanan(in);
        }

        @Override
        public Pesanan[] newArray(int size) {
            return new Pesanan[size];
        }
    };

    public List<Additional> getAdditional() {
        return additional;
    }

    public void setAdditional(List<Additional> additional) {
        this.additional = additional;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDeskripsi_lokasi() {
        return deskripsi_lokasi;
    }

    public void setDeskripsi_lokasi(String deskripsi_lokasi) {
        this.deskripsi_lokasi = deskripsi_lokasi;
    }

    public Object getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKd_pemesanan() {
        return kd_pemesanan;
    }

    public void setKd_pemesanan(String kd_pemesanan) {
        this.kd_pemesanan = kd_pemesanan;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getNo_wa() {
        return no_wa;
    }

    public void setNo_wa(String no_wa) {
        this.no_wa = no_wa;
    }

    public List<Paket> getPaket() {
        return paket;
    }

    public void setPaket(List<Paket> paket) {
        this.paket = paket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal_antar() {
        return tanggal_antar;
    }

    public void setTanggal_antar(String tanggal_antar) {
        this.tanggal_antar = tanggal_antar;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getWaktu_antar() {
        return waktu_antar;
    }

    public void setWaktu_antar(String waktu_antar) {
        this.waktu_antar = waktu_antar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(additional);
        dest.writeString(catatan);
        dest.writeString(created_at);
        dest.writeString(deskripsi_lokasi);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(kd_pemesanan);
        dest.writeString(latitude);
        dest.writeString(logitude);
        dest.writeString(nama);
        dest.writeString(no_telepon);
        dest.writeString(no_wa);
        dest.writeTypedList(paket);
        dest.writeString(status);
        dest.writeString(tanggal_antar);
        dest.writeParcelable(transaksi, flags);
        dest.writeString(updated_at);
        dest.writeString(waktu_antar);
    }
}
