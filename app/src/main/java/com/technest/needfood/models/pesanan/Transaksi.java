
package com.technest.needfood.models.pesanan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Transaksi implements Parcelable {

    @SerializedName("biaya_pengiriman")
    private Long mBiayaPengiriman;
    @SerializedName("harga_additional")
    private Long mHargaAdditional;
    @SerializedName("harga_lainnya")
    private Long mHargaLainnya;
    @SerializedName("harga_paket")
    private Long mHargaPaket;
    @SerializedName("id")
    private Long mId;
    @SerializedName("pemesanan_id")
    private Long mPemesananId;
    @SerializedName("total_harga")
    private Long mTotalHarga;

    protected Transaksi(Parcel in) {
        if (in.readByte() == 0) {
            mBiayaPengiriman = null;
        } else {
            mBiayaPengiriman = in.readLong();
        }
        if (in.readByte() == 0) {
            mHargaAdditional = null;
        } else {
            mHargaAdditional = in.readLong();
        }
        if (in.readByte() == 0) {
            mHargaLainnya = null;
        } else {
            mHargaLainnya = in.readLong();
        }
        if (in.readByte() == 0) {
            mHargaPaket = null;
        } else {
            mHargaPaket = in.readLong();
        }
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
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

    public static final Creator<Transaksi> CREATOR = new Creator<Transaksi>() {
        @Override
        public Transaksi createFromParcel(Parcel in) {
            return new Transaksi(in);
        }

        @Override
        public Transaksi[] newArray(int size) {
            return new Transaksi[size];
        }
    };

    public Long getBiayaPengiriman() {
        return mBiayaPengiriman;
    }

    public void setBiayaPengiriman(Long biayaPengiriman) {
        mBiayaPengiriman = biayaPengiriman;
    }

    public Long getHargaAdditional() {
        return mHargaAdditional;
    }

    public void setHargaAdditional(Long hargaAdditional) {
        mHargaAdditional = hargaAdditional;
    }

    public Long getHargaLainnya() {
        return mHargaLainnya;
    }

    public void setHargaLainnya(Long hargaLainnya) {
        mHargaLainnya = hargaLainnya;
    }

    public Long getHargaPaket() {
        return mHargaPaket;
    }

    public void setHargaPaket(Long hargaPaket) {
        mHargaPaket = hargaPaket;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
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
        if (mBiayaPengiriman == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mBiayaPengiriman);
        }
        if (mHargaAdditional == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mHargaAdditional);
        }
        if (mHargaLainnya == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mHargaLainnya);
        }
        if (mHargaPaket == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mHargaPaket);
        }
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mId);
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
