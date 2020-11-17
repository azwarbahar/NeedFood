
package com.technest.needfood.models.bahan;

import com.google.gson.annotations.SerializedName;

public class ResponseBahan {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private Bahan mBahan;
    @SerializedName("success")
    private Boolean mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Bahan getResult() {
        return mBahan;
    }

    public void setResult(Bahan bahan) {
        mBahan = bahan;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
