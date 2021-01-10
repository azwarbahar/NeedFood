package com.technest.needfood.models.keuangan;

public class DataKas{
	private long uang_kas;
	private long total_debit;
	private long total_kredit;

	public long getUang_kas() {
		return uang_kas;
	}

	public void setUang_kas(long uang_kas) {
		this.uang_kas = uang_kas;
	}

	public long getTotal_debit() {
		return total_debit;
	}

	public void setTotal_debit(long total_debit) {
		this.total_debit = total_debit;
	}

	public long getTotal_kredit() {
		return total_kredit;
	}

	public void setTotal_kredit(long total_kredit) {
		this.total_kredit = total_kredit;
	}
}
