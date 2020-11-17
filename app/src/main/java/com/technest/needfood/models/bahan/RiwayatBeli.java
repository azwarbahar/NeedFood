
package com.technest.needfood.models.bahan;

import com.google.gson.annotations.SerializedName;

public class RiwayatBeli {

    @SerializedName("bahan_id")
    private Long mBahanId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("jumlah_beli")
    private Long mJumlahBeli;
    @SerializedName("kd_beli")
    private String mKdBeli;
    @SerializedName("supplier")
    private String mSupplier;
    @SerializedName("supplier_id")
    private Long mSupplierId;
    @SerializedName("total_harga")
    private Long mTotalHarga;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public Long getBahanId() {
        return mBahanId;
    }

    public void setBahanId(Long bahanId) {
        mBahanId = bahanId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getJumlahBeli() {
        return mJumlahBeli;
    }

    public void setJumlahBeli(Long jumlahBeli) {
        mJumlahBeli = jumlahBeli;
    }

    public String getKdBeli() {
        return mKdBeli;
    }

    public void setKdBeli(String kdBeli) {
        mKdBeli = kdBeli;
    }

    public String getSupplier() {
        return mSupplier;
    }

    public void setSupplier(String supplier) {
        mSupplier = supplier;
    }

    public Long getSupplierId() {
        return mSupplierId;
    }

    public void setSupplierId(Long supplierId) {
        mSupplierId = supplierId;
    }

    public Long getTotalHarga() {
        return mTotalHarga;
    }

    public void setTotalHarga(Long totalHarga) {
        mTotalHarga = totalHarga;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
