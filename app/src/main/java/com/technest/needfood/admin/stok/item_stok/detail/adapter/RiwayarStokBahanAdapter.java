package com.technest.needfood.admin.stok.item_stok.detail.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_riwayat_stok, parent,false);
        RiwayarStokBahanAdapter.MyHolderView myHolderView = new RiwayarStokBahanAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayarStokBahanAdapter.MyHolderView holder, int position) {



        String status = riwayarStokBahanModels.get(position).getStatus();
        if (status.equals("out")){

            holder.img_list_riwayat.setBackgroundResource(R.drawable.arrow_down);
            holder.tv_tanggal_riwayat_stok.setText(riwayarStokBahanModels.get(position).getTanggal_riwayat());
            holder.tv_kuantitas_list_riwayat.setText(riwayarStokBahanModels.get(position).getKuantitas_riwayat());
            holder.tv_kuantitas_list_riwayat.setTextColor(Color.parseColor("#F10808"));
            holder.tv_satuan.setTextColor(Color.parseColor("#F10808"));

        } else {

            holder.img_list_riwayat.setBackgroundResource(R.drawable.arrow_up);
            holder.tv_tanggal_riwayat_stok.setText(riwayarStokBahanModels.get(position).getTanggal_riwayat());
            holder.tv_kuantitas_list_riwayat.setText(riwayarStokBahanModels.get(position).getKuantitas_riwayat());
            holder.tv_kuantitas_list_riwayat.setTextColor(Color.parseColor("#077307"));
            holder.tv_satuan.setTextColor(Color.parseColor("#077307"));

        }

    }

    @Override
    public int getItemCount() {
        return riwayarStokBahanModels.size();
    }

    public static class MyHolderView extends RecyclerView.ViewHolder {

        private ImageView img_list_riwayat;
        private TextView tv_tanggal_riwayat_stok;
        private TextView tv_kuantitas_list_riwayat;
        private TextView tv_satuan;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            img_list_riwayat = itemView.findViewById(R.id.img_list_riwayat);
            tv_tanggal_riwayat_stok = itemView.findViewById(R.id.tv_tanggal_riwayat_stok);
            tv_kuantitas_list_riwayat = itemView.findViewById(R.id.tv_kuantitas_list_riwayat);
            tv_satuan = itemView.findViewById(R.id.tv_satuan);

        }
    }
}
