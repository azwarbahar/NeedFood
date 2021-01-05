package com.technest.needfood.models.pesanan;

import com.google.gson.annotations.SerializedName;

public class ResponsePesananID {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private Pesanan mPesanan;
    @SerializedName("success")
    private Boolean mSuccess;

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public Pesanan getmPesanan() {
        return mPesanan;
    }

    public void setmPesanan(Pesanan mPesanan) {
        this.mPesanan = mPesanan;
    }

    public Boolean getmSuccess() {
        return mSuccess;
    }

    public void setmSuccess(Boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
