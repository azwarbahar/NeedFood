package com.technest.needfood.models.keuangan;

public class ReslutSingle {
    private long nominal;
    private String updated_at;
    private String jenis;
    private String uraian;
    private String created_at;
    private long id;
    private String tanggal;
    private String pemesanan_id;

    public long getNominal() {
        return nominal;
    }

    public void setNominal(long nominal) {
        this.nominal = nominal;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getUraian() {
        return uraian;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPemesanan_id() {
        return pemesanan_id;
    }

    public void setPemesanan_id(String pemesanan_id) {
        this.pemesanan_id = pemesanan_id;
    }
}
