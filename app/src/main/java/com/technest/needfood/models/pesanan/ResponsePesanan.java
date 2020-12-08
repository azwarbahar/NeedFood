
package com.technest.needfood.models.pesanan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePesanan {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private List<Pesanan> mPesanan;
    @SerializedName("success")
    private Boolean mSuccess;

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public List<Pesanan> getmPesanan() {
        return mPesanan;
    }

    public void setmPesanan(List<Pesanan> mPesanan) {
        this.mPesanan = mPesanan;
    }

    public Boolean getmSuccess() {
        return mSuccess;
    }

    public void setmSuccess(Boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
