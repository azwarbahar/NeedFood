package com.technest.needfood.models.alat;

import com.google.gson.annotations.SerializedName;

public class ResponseAlat{

	@SerializedName("result")
	private Alat result;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setResult(Alat result){
		this.result = result;
	}

	public Alat getResult(){
		return result;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}
