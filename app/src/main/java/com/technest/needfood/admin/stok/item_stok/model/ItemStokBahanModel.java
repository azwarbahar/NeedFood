package com.technest.needfood.admin.stok.item_stok.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemStokBahanModel implements Parcelable {

    private String title_item_stok;
    private String kuantitas_item_stok;
    private int image_item_stok;

    public ItemStokBahanModel(String title_item_stok, String kuantitas_item_stok, int image_item_stok) {
        this.title_item_stok = title_item_stok;
        this.kuantitas_item_stok = kuantitas_item_stok;
        this.image_item_stok = image_item_stok;
    }

    protected ItemStokBahanModel(Parcel in) {
        title_item_stok = in.readString();
        kuantitas_item_stok = in.readString();
        image_item_stok = in.readInt();
    }

    public static final Creator<ItemStokBahanModel> CREATOR = new Creator<ItemStokBahanModel>() {
        @Override
        public ItemStokBahanModel createFromParcel(Parcel in) {
            return new ItemStokBahanModel(in);
        }

        @Override
        public ItemStokBahanModel[] newArray(int size) {
            return new ItemStokBahanModel[size];
        }
    };

    public ItemStokBahanModel() {

    }

    public String getTitle_item_stok() {
        return title_item_stok;
    }

    public void setTitle_item_stok(String title_item_stok) {
        this.title_item_stok = title_item_stok;
    }

    public String getKuantitas_item_stok() {
        return kuantitas_item_stok;
    }

    public void setKuantitas_item_stok(String kuantitas_item_stok) {
        this.kuantitas_item_stok = kuantitas_item_stok;
    }

    public int getImage_item_stok() {
        return image_item_stok;
    }

    public void setImage_item_stok(int image_item_stok) {
        this.image_item_stok = image_item_stok;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title_item_stok);
        dest.writeString(kuantitas_item_stok);
        dest.writeInt(image_item_stok);
    }
}
