package com.technest.needfood.admin.stok.item_stok.detail.model;

public class RiwayarStokBahanModel {

    private String tanggal_riwayat;
    private String kuantitas_riwayat;
    private String status;

    public RiwayarStokBahanModel(String tanggal_riwayat, String kuantitas_riwayat, String status) {
        this.tanggal_riwayat = tanggal_riwayat;
        this.kuantitas_riwayat = kuantitas_riwayat;
        this.status = status;
    }

    public String getTanggal_riwayat() {
        return tanggal_riwayat;
    }

    public void setTanggal_riwayat(String tanggal_riwayat) {
        this.tanggal_riwayat = tanggal_riwayat;
    }

    public String getKuantitas_riwayat() {
        return kuantitas_riwayat;
    }

    public void setKuantitas_riwayat(String kuantitas_riwayat) {
        this.kuantitas_riwayat = kuantitas_riwayat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
