package com.technest.needfood.models.keuangan;

import java.util.List;

public class ResponseKeuangan{
	private List<ResultItem> result;
	private boolean success;
	private String message;
	private DataKas data_kas;

	public List<ResultItem> getResult(){
		return result;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}

	public DataKas getDataKas(){
		return data_kas;
	}
}