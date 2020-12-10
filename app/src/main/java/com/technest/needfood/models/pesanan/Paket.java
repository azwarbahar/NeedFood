
package com.technest.needfood.models.pesanan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Paket implements Parcelable {

    @SerializedName("harga")
    private String mHarga;
    @SerializedName("jumlah")
    private Long mJumlah;
    @SerializedName("nama_paket")
    private String mNamaPaket;
    @SerializedName("paket_id")
    private Long mPaketId;
    @SerializedName("pemesanan_id")
    private Long mPemesananId;
    @SerializedName("total_harga")
    private Long mTotalHarga;

    protected Paket(Parcel in) {
        mHarga = in.readString();
        if (in.readByte() == 0) {
            mJumlah = null;
        } else {
            mJumlah = in.readLong();
        }
        mNamaPaket = in.readString();
        if (in.readByte() == 0) {
            mPaketId = null;
        } else {
            mPaketId = in.readLong();
        }
        if (in.readByte() == 0) {
            mPemesananId = null;
        } else {
            mPemesananId = in.readLong();
        }
        if (in.readByte() == 0) {
            mTotalHarga = null;
        } else {
            mTotalHarga = in.readLong();
        }
    }

    public static final Creator<Paket> CREATOR = new Creator<Paket>() {
        @Override
        public Paket createFromParcel(Parcel in) {
            return new Paket(in);
        }

        @Override
        public Paket[] newArray(int size) {
            return new Paket[size];
        }
    };

    public String getHarga() {
        return mHarga;
    }

    public void setHarga(String harga) {
        mHarga = harga;
    }

    public Long getJumlah() {
        return mJumlah;
    }

    public void setJumlah(Long jumlah) {
        mJumlah = jumlah;
    }

    public String getNamaPaket() {
        return mNamaPaket;
    }

    public void setNamaPaket(String namaPaket) {
        mNamaPaket = namaPaket;
    }

    public Long getPaketId() {
        return mPaketId;
    }

    public void setPaketId(Long paketId) {
        mPaketId = paketId;
    }

    public Long getPemesananId() {
        return mPemesananId;
    }

    public void setPemesananId(Long pemesananId) {
        mPemesananId = pemesananId;
    }

    public Long getTotalHarga() {
        return mTotalHarga;
    }

    public void setTotalHarga(Long totalHarga) {
        mTotalHarga = totalHarga;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mHarga);
        if (mJumlah == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mJumlah);
        }
        dest.writeString(mNamaPaket);
        if (mPaketId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mPaketId);
        }
        if (mPemesananId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mPemesananId);
        }
        if (mTotalHarga == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mTotalHarga);
        }
    }
}
