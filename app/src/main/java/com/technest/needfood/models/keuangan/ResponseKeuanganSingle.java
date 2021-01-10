package com.technest.needfood.models.keuangan;

import java.util.List;

public class ResponseKeuanganSingle {
    private ReslutSingle result;
    private boolean success;
    private String message;

    public ReslutSingle getResult() {
        return result;
    }

    public void setResult(ReslutSingle result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
