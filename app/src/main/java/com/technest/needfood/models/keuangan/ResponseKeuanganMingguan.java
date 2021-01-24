package com.technest.needfood.models.keuangan;

import java.util.List;

public class ResponseKeuanganMingguan{
	private List<ResultMingguan> result;
	private boolean success;
	private String message;

	public List<ResultMingguan> getResult() {
		return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}
