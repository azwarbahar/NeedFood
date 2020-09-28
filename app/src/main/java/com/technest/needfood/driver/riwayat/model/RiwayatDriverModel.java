package com.technest.needfood.driver.riwayat.model;

public class RiwayatDriverModel {

    private String kode;
    private String nama;
    private String alamat;
    private String tanggal;
    private String jam;
    private String status;

    public RiwayatDriverModel(String kode, String nama, String alamat, String tanggal, String jam,
                              String status) {
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
        this.tanggal = tanggal;
        this.jam = jam;
        this.status = status;
    }

    public RiwayatDriverModel() {

    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
