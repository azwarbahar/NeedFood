
package com.technest.needfood.models.pesanan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Pesanan {

    @SerializedName("additional")
    private List<Additional> mAdditional;
    @SerializedName("catatan")
    private String mCatatan;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deskripsi_lokasi")
    private String mDeskripsiLokasi;
    @SerializedName("driver_id")
    private Object mDriverId;
    @SerializedName("id")
    private Long mId;
    @SerializedName("kd_pemesanan")
    private String mKdPemesanan;
    @SerializedName("latitude")
    private String mLatitude;
    @SerializedName("logitude")
    private String mLogitude;
    @SerializedName("nama")
    private String mNama;
    @SerializedName("no_telepon")
    private String mNoTelepon;
    @SerializedName("no_wa")
    private String mNoWa;
    @SerializedName("paket")
    private List<Paket> mPaket;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("tanggal_antar")
    private String mTanggalAntar;
    @SerializedName("transaksi")
    private Transaksi mTransaksi;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("waktu_antar")
    private String mWaktuAntar;

    public List<Additional> getAdditional() {
        return mAdditional;
    }

    public void setAdditional(List<Additional> additional) {
        mAdditional = additional;
    }

    public String getCatatan() {
        return mCatatan;
    }

    public void setCatatan(String catatan) {
        mCatatan = catatan;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDeskripsiLokasi() {
        return mDeskripsiLokasi;
    }

    public void setDeskripsiLokasi(String deskripsiLokasi) {
        mDeskripsiLokasi = deskripsiLokasi;
    }

    public Object getDriverId() {
        return mDriverId;
    }

    public void setDriverId(Object driverId) {
        mDriverId = driverId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getKdPemesanan() {
        return mKdPemesanan;
    }

    public void setKdPemesanan(String kdPemesanan) {
        mKdPemesanan = kdPemesanan;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLogitude() {
        return mLogitude;
    }

    public void setLogitude(String logitude) {
        mLogitude = logitude;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        mNama = nama;
    }

    public String getNoTelepon() {
        return mNoTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        mNoTelepon = noTelepon;
    }

    public String getNoWa() {
        return mNoWa;
    }

    public void setNoWa(String noWa) {
        mNoWa = noWa;
    }

    public List<Paket> getPaket() {
        return mPaket;
    }

    public void setPaket(List<Paket> paket) {
        mPaket = paket;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTanggalAntar() {
        return mTanggalAntar;
    }

    public void setTanggalAntar(String tanggalAntar) {
        mTanggalAntar = tanggalAntar;
    }

    public Transaksi getTransaksi() {
        return mTransaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        mTransaksi = transaksi;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getWaktuAntar() {
        return mWaktuAntar;
    }

    public void setWaktuAntar(String waktuAntar) {
        mWaktuAntar = waktuAntar;
    }

}
