package com.technest.needfood.models.keuangan;

public class ResultItem {
    private long nominal;
    private String updated_at;
    private String jenis;
    private String uraian;
    private String created_at;
    private long id;
    private String tanggal;

    public long getNominal() {
        return nominal;
    }

    public String getupdated_at() {
        return updated_at;
    }

    public String getJenis() {
        return jenis;
    }

    public String getUraian() {
        return uraian;
    }

    public String getcreated_at() {
        return created_at;
    }

    public long getId() {
        return id;
    }

    public String getTanggal() {
        return tanggal;
    }
}
