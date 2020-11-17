
package com.technest.needfood.models.kategori;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Kategori implements Parcelable {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("foto")
    private String mFoto;
    @SerializedName("id")
    private Long mId;
    @SerializedName("jenis")
    private String mJenis;
    @SerializedName("kategori")
    private String mKategori;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    protected Kategori(Parcel in) {
        mCreatedAt = in.readString();
        mFoto = in.readString();
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mJenis = in.readString();
        mKategori = in.readString();
        mUpdatedAt = in.readString();
    }

    public static final Creator<Kategori> CREATOR = new Creator<Kategori>() {
        @Override
        public Kategori createFromParcel(Parcel in) {
            return new Kategori(in);
        }

        @Override
        public Kategori[] newArray(int size) {
            return new Kategori[size];
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

    public String getJenis() {
        return mJenis;
    }

    public void setJenis(String jenis) {
        mJenis = jenis;
    }

    public String getKategori() {
        return mKategori;
    }

    public void setKategori(String kategori) {
        mKategori = kategori;
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
        dest.writeString(mJenis);
        dest.writeString(mKategori);
        dest.writeString(mUpdatedAt);
    }
}
