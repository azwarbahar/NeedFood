package com.technest.needfood.admin.stok.item_stok.detail.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.admin.stok.item_stok.detail.model.RiwayarStokBahanModel;

import java.util.ArrayList;

public class RiwayarStokBahanAdapter extends RecyclerView.Adapter<RiwayarStokBahanAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<RiwayarStokBahanModel> riwayarStokBahanModels;

    public RiwayarStokBahanAdapter(Context mContext, ArrayList<RiwayarStokBahanModel> riwayarStokBahanModels) {
        this.mContext = mContext;
        this.riwayarStokBahanModels = riwayarStokBahanModels;
    }

    @NonNull
    @Override
    public RiwayarStokBahanAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayarStokBahanAdapter.MyHolderView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        public MyHolderView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
