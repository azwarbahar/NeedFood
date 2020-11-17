
package com.technest.needfood.models.kategori;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponKategori {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private List<Kategori> mKategori;
    @SerializedName("success")
    private Boolean mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Kategori> getResult() {
        return mKategori;
    }

    public void setResult(List<Kategori> kategori) {
        mKategori = kategori;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
