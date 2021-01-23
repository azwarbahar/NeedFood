package com.technest.needfood.models.keuangan;

import  com.technest.needfood.models.keuangan.day.Fri;
import  com.technest.needfood.models.keuangan.day.Mon;
import  com.technest.needfood.models.keuangan.day.Sat;
import  com.technest.needfood.models.keuangan.day.Sun;
import  com.technest.needfood.models.keuangan.day.Thu;
import  com.technest.needfood.models.keuangan.day.Tue;
import  com.technest.needfood.models.keuangan.day.Wed;

public class ResultMingguan {
	private Thu Thu;
	private Tue Tue;
	private Wed Wed;
	private Sat Sat;
	private Fri Fri;
	private Sun Sun;
	private Mon Mon;

	public Thu getThu() {
		return Thu;
	}

	public void setThu(Thu thu) {
		Thu = thu;
	}

	public Tue getTue() {
		return Tue;
	}

	public void setTue(Tue tue) {
		Tue = tue;
	}

	public Wed getWed() {
		return Wed;
	}

	public void setWed( Wed wed) {
		Wed = wed;
	}

	public  Sat getSat() {
		return Sat;
	}

	public void setSat( Sat sat) {
		Sat = sat;
	}

	public  Fri getFri() {
		return Fri;
	}

	public void setFri( Fri fri) {
		Fri = fri;
	}

	public  Sun getSun() {
		return Sun;
	}

	public void setSun( Sun sun) {
		Sun = sun;
	}

	public  Mon getMon() {
		return Mon;
	}

	public void setMon( Mon mon) {
		Mon = mon;
	}
}
