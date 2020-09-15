package com.technest.needfood.admin.stok.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KategoriStokModel implements Parcelable {

    private String title_kategori_stok;
    private int image_kategori_stok;

    public KategoriStokModel(String title_kategori_stok, int image_kategori_stok) {
        this.title_kategori_stok = title_kategori_stok;
        this.image_kategori_stok = image_kategori_stok;
    }

    public KategoriStokModel() {

    }

    protected KategoriStokModel(Parcel in) {
        title_kategori_stok = in.readString();
        image_kategori_stok = in.readInt();
    }

    public static final Creator<KategoriStokModel> CREATOR = new Creator<KategoriStokModel>() {
        @Override
        public KategoriStokModel createFromParcel(Parcel in) {
            return new KategoriStokModel(in);
        }

        @Override
        public KategoriStokModel[] newArray(int size) {
            return new KategoriStokModel[size];
        }
    };

    public String getTitle_kategori_stok() {
        return title_kategori_stok;
    }

    public void setTitle_kategori_stok(String title_kategori_stok) {
        this.title_kategori_stok = title_kategori_stok;
    }

    public int getImage_kategori_stok() {
        return image_kategori_stok;
    }

    public void setImage_kategori_stok(int image_kategori_stok) {
        this.image_kategori_stok = image_kategori_stok;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title_kategori_stok);
        dest.writeInt(image_kategori_stok);
    }
}
