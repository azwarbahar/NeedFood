package com.technest.needfood.models.alat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAllAlat{
	@SerializedName("message")
	private String mMessage;
	@SerializedName("result")
	private List<Alat> mResult;
	@SerializedName("success")
	private Boolean mSuccess;

	public String getmMessage() {
		return mMessage;
	}

	public void setmMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public List<Alat> getmResult() {
		return mResult;
	}

	public void setmResult(List<Alat> mResult) {
		this.mResult = mResult;
	}

	public Boolean getmSuccess() {
		return mSuccess;
	}

	public void setmSuccess(Boolean mSuccess) {
		this.mSuccess = mSuccess;
	}
}