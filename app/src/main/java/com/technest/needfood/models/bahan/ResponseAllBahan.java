
package com.technest.needfood.models.bahan;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class ResponseAllBahan {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private List<Bahan> mBahan;
    @SerializedName("success")
    private Boolean mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Bahan> getResult() {
        return mBahan;
    }

    public void setResult(List<Bahan> bahan) {
        mBahan = bahan;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
