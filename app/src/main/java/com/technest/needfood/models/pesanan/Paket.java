
package com.technest.needfood.models.pesanan;

import com.google.gson.annotations.SerializedName;

public class Paket {

    @SerializedName("harga")
    private String mHarga;
    @SerializedName("jumlah")
    private Long mJumlah;
    @SerializedName("nama_paket")
    private String mNamaPaket;
    @SerializedName("paket_id")
    private Long mPaketId;
    @SerializedName("pemesanan_id")
    private Long mPemesananId;
    @SerializedName("total_harga")
    private Long mTotalHarga;

    public String getHarga() {
        return mHarga;
    }

    public void setHarga(String harga) {
        mHarga = harga;
    }

    public Long getJumlah() {
        return mJumlah;
    }

    public void setJumlah(Long jumlah) {
        mJumlah = jumlah;
    }

    public String getNamaPaket() {
        return mNamaPaket;
    }

    public void setNamaPaket(String namaPaket) {
        mNamaPaket = namaPaket;
    }

    public Long getPaketId() {
        return mPaketId;
    }

    public void setPaketId(Long paketId) {
        mPaketId = paketId;
    }

    public Long getPemesananId() {
        return mPemesananId;
    }

    public void setPemesananId(Long pemesananId) {
        mPemesananId = pemesananId;
    }

    public Long getTotalHarga() {
        return mTotalHarga;
    }

    public void setTotalHarga(Long totalHarga) {
        mTotalHarga = totalHarga;
    }

}
