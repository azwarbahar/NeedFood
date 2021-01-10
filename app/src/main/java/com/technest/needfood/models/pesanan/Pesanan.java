
package com.technest.needfood.models.pesanan;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Pesanan implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("kd_pemesanan")
    private String kd_pemesanan;
    @SerializedName("nama")
    private String nama;
    @SerializedName("no_telepon")
    private String no_telepon;
    @SerializedName("no_wa")
    private String no_wa;
    @SerializedName("tanggal_antar")
    private String tanggal_antar;
    @SerializedName("waktu_antar")
    private String waktu_antar;
    @SerializedName("deskripsi_lokasi")
    private String deskripsi_lokasi;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String logitude;
    @SerializedName("catatan")
    private String catatan;
    @SerializedName("status")
    private String status;
    @SerializedName("pengantaran")
    private int pengantaran;
    @SerializedName("penjemputan")
    private int penjemputan;
    @SerializedName("bukti_pembayaran")
    private String bukti_pembayaran;
    @SerializedName("foto_pesanan")
    private String foto_pesanan;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("paket")
    private List<Paket> paket;
    @SerializedName("additional")
    private List<Additional> additional;
    @SerializedName("transaksi")
    private Transaksi transaksi;
    @SerializedName("bahan")
    private List<BahanPesanan> bahan;
    @SerializedName("alat")
    private List<AlatPesanan> alat;

    public Pesanan(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKd_pemesanan() {
        return kd_pemesanan;
    }

    public void setKd_pemesanan(String kd_pemesanan) {
        this.kd_pemesanan = kd_pemesanan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getNo_wa() {
        return no_wa;
    }

    public void setNo_wa(String no_wa) {
        this.no_wa = no_wa;
    }

    public String getTanggal_antar() {
        return tanggal_antar;
    }

    public void setTanggal_antar(String tanggal_antar) {
        this.tanggal_antar = tanggal_antar;
    }

    public String getWaktu_antar() {
        return waktu_antar;
    }

    public void setWaktu_antar(String waktu_antar) {
        this.waktu_antar = waktu_antar;
    }

    public String getDeskripsi_lokasi() {
        return deskripsi_lokasi;
    }

    public void setDeskripsi_lokasi(String deskripsi_lokasi) {
        this.deskripsi_lokasi = deskripsi_lokasi;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPengantaran() {
        return pengantaran;
    }

    public void setPengantaran(int pengantaran) {
        this.pengantaran = pengantaran;
    }

    public int getPenjemputan() {
        return penjemputan;
    }

    public void setPenjemputan(int penjemputan) {
        this.penjemputan = penjemputan;
    }

    public String getBukti_pembayaran() {
        return bukti_pembayaran;
    }

    public void setBukti_pembayaran(String bukti_pembayaran) {
        this.bukti_pembayaran = bukti_pembayaran;
    }

    public String getFoto_pesanan() {
        return foto_pesanan;
    }

    public void setFoto_pesanan(String foto_pesanan) {
        this.foto_pesanan = foto_pesanan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<Paket> getPaket() {
        return paket;
    }

    public void setPaket(List<Paket> paket) {
        this.paket = paket;
    }

    public List<Additional> getAdditional() {
        return additional;
    }

    public void setAdditional(List<Additional> additional) {
        this.additional = additional;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public List<BahanPesanan> getBahan() {
        return bahan;
    }

    public void setBahan(List<BahanPesanan> bahan) {
        this.bahan = bahan;
    }

    public List<AlatPesanan> getAlat() {
        return alat;
    }

    public void setAlat(List<AlatPesanan> alat) {
        this.alat = alat;
    }

    protected Pesanan(Parcel in) {
        id = in.readInt();
        kd_pemesanan = in.readString();
        nama = in.readString();
        no_telepon = in.readString();
        no_wa = in.readString();
        tanggal_antar = in.readString();
        waktu_antar = in.readString();
        deskripsi_lokasi = in.readString();
        latitude = in.readString();
        logitude = in.readString();
        catatan = in.readString();
        status = in.readString();
        pengantaran = in.readInt();
        penjemputan = in.readInt();
        bukti_pembayaran = in.readString();
        foto_pesanan = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        paket = in.createTypedArrayList(Paket.CREATOR);
        additional = in.createTypedArrayList(Additional.CREATOR);
        transaksi = in.readParcelable(Transaksi.class.getClassLoader());
        bahan = in.createTypedArrayList(BahanPesanan.CREATOR);
        alat = in.createTypedArrayList(AlatPesanan.CREATOR);
    }

    public static final Creator<Pesanan> CREATOR = new Creator<Pesanan>() {
        @Override
        public Pesanan createFromParcel(Parcel in) {
            return new Pesanan(in);
        }

        @Override
        public Pesanan[] newArray(int size) {
            return new Pesanan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(kd_pemesanan);
        dest.writeString(nama);
        dest.writeString(no_telepon);
        dest.writeString(no_wa);
        dest.writeString(tanggal_antar);
        dest.writeString(waktu_antar);
        dest.writeString(deskripsi_lokasi);
        dest.writeString(latitude);
        dest.writeString(logitude);
        dest.writeString(catatan);
        dest.writeString(status);
        dest.writeInt(pengantaran);
        dest.writeInt(penjemputan);
        dest.writeString(bukti_pembayaran);
        dest.writeString(foto_pesanan);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeTypedList(paket);
        dest.writeTypedList(additional);
        dest.writeParcelable(transaksi, flags);
        dest.writeTypedList(bahan);
        dest.writeTypedList(alat);
    }
}
