
package com.technest.needfood.models.pesanan;

import com.google.gson.annotations.SerializedName;

public class Transaksi {

    @SerializedName("biaya_pengiriman")
    private Long mBiayaPengiriman;
    @SerializedName("harga_additional")
    private Long mHargaAdditional;
    @SerializedName("harga_lainnya")
    private Long mHargaLainnya;
    @SerializedName("harga_paket")
    private Long mHargaPaket;
    @SerializedName("id")
    private Long mId;
    @SerializedName("pemesanan_id")
    private Long mPemesananId;
    @SerializedName("total_harga")
    private Long mTotalHarga;

    public Long getBiayaPengiriman() {
        return mBiayaPengiriman;
    }

    public void setBiayaPengiriman(Long biayaPengiriman) {
        mBiayaPengiriman = biayaPengiriman;
    }

    public Long getHargaAdditional() {
        return mHargaAdditional;
    }

    public void setHargaAdditional(Long hargaAdditional) {
        mHargaAdditional = hargaAdditional;
    }

    public Long getHargaLainnya() {
        return mHargaLainnya;
    }

    public void setHargaLainnya(Long hargaLainnya) {
        mHargaLainnya = hargaLainnya;
    }

    public Long getHargaPaket() {
        return mHargaPaket;
    }

    public void setHargaPaket(Long hargaPaket) {
        mHargaPaket = hargaPaket;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
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
