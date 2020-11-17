
package com.technest.needfood.models.bahan;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Bahan implements Parcelable {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("foto")
    private String mFoto;
    @SerializedName("id")
    private Long mId;
    @SerializedName("jumlah_bahan")
    private Long mJumlahBahan;
    @SerializedName("kategori")
    private String mKategori;
    @SerializedName("kategori_id")
    private Long mKategoriId;
    @SerializedName("kd_bahan")
    private String mKdBahan;
    @SerializedName("nama")
    private String mNama;
    @SerializedName("riwayat_beli")
    private List<RiwayatBeli> mRiwayatBeli;
    @SerializedName("satuan")
    private String mSatuan;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    protected Bahan(Parcel in) {
        mCreatedAt = in.readString();
        mFoto = in.readString();
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        if (in.readByte() == 0) {
            mJumlahBahan = null;
        } else {
            mJumlahBahan = in.readLong();
        }
        mKategori = in.readString();
        if (in.readByte() == 0) {
            mKategoriId = null;
        } else {
            mKategoriId = in.readLong();
        }
        mKdBahan = in.readString();
        mNama = in.readString();
        mSatuan = in.readString();
        mUpdatedAt = in.readString();
    }

    public static final Creator<Bahan> CREATOR = new Creator<Bahan>() {
        @Override
        public Bahan createFromParcel(Parcel in) {
            return new Bahan(in);
        }

        @Override
        public Bahan[] newArray(int size) {
            return new Bahan[size];
        }
    };

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getFoto() {
        return mFoto;
    }

    public void setFoto(String foto) {
        mFoto = foto;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getJumlahBahan() {
        return mJumlahBahan;
    }

    public void setJumlahBahan(Long jumlahBahan) {
        mJumlahBahan = jumlahBahan;
    }

    public String getKategori() {
        return mKategori;
    }

    public void setKategori(String kategori) {
        mKategori = kategori;
    }

    public Long getKategoriId() {
        return mKategoriId;
    }

    public void setKategoriId(Long kategoriId) {
        mKategoriId = kategoriId;
    }

    public String getKdBahan() {
        return mKdBahan;
    }

    public void setKdBahan(String kdBahan) {
        mKdBahan = kdBahan;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        mNama = nama;
    }

    public List<RiwayatBeli> getRiwayatBeli() {
        return mRiwayatBeli;
    }

    public void setRiwayatBeli(List<RiwayatBeli> riwayatBeli) {
        mRiwayatBeli = riwayatBeli;
    }

    public String getSatuan() {
        return mSatuan;
    }

    public void setSatuan(String satuan) {
        mSatuan = satuan;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCreatedAt);
        dest.writeString(mFoto);
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mId);
        }
        if (mJumlahBahan == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mJumlahBahan);
        }
        dest.writeString(mKategori);
        if (mKategoriId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mKategoriId);
        }
        dest.writeString(mKdBahan);
        dest.writeString(mNama);
        dest.writeString(mSatuan);
        dest.writeString(mUpdatedAt);
    }
}
