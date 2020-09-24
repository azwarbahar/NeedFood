package com.technest.needfood.driver.home;

public class PesananDriverModel {

    private String nama;
    private String alamat;
    private String jam;

    public PesananDriverModel(String nama, String alamat, String jam) {
        this.nama = nama;
        this.alamat = alamat;
        this.jam = jam;
    }

    public PesananDriverModel() {

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

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }
}
