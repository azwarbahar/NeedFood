package com.technest.needfood.models.pesanan;

import com.google.gson.annotations.SerializedName;

public class Additional {

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
}
