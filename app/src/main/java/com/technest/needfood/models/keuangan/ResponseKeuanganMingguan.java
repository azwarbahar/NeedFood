package com.technest.needfood.models.keuangan;

public class ResponseKeuanganMingguan{
	private ResultMingguan result;
	private boolean success;
	private String message;

	public ResultMingguan getResult() {
		return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}
