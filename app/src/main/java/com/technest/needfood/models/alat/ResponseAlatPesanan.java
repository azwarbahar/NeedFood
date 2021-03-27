package com.technest.needfood.models.alat;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAlatPesanan{

	@SerializedName("result")
	private List<CekAlatPesanan> cekAlatPesanans;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<CekAlatPesanan> getResult(){
		return cekAlatPesanans;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}