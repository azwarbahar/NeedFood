package com.technest.needfood.driver.pesanan.pengantaran.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.driver.delivery.DeliveryDriverActivity;
import com.technest.needfood.driver.pesanan.pengantaran.model.PengantaranDriverModel;

import java.util.ArrayList;

public class PengantraanDriverAdapter extends RecyclerView.Adapter<PengantraanDriverAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<PengantaranDriverModel> pengantaranDriverModels;

    public PengantraanDriverAdapter(Context mContext, ArrayList<PengantaranDriverModel> pengantaranDriverModels) {
        this.mContext = mContext;
        this.pengantaranDriverModels = pengantaranDriverModels;
    }

    @NonNull
    @Override
    public PengantraanDriverAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_pengantaran_driver, parent, false);
        PengantraanDriverAdapter.MyHolderView myHolderView = new PengantraanDriverAdapter.MyHolderView(view);

        return myHolderView;

    }

    @Override
    public void onBindViewHolder(@NonNull PengantraanDriverAdapter.MyHolderView holder, int position) {

        holder.tv_kode_pesanan.setText(pengantaranDriverModels.get(position).getKode());
        holder.tv_nama_pelanggan.setText(": "+pengantaranDriverModels.get(position).getNama());
        holder.tv_alamat.setText(": "+pengantaranDriverModels.get(position).getAlamat());
        holder.tv_tanggal.setText(pengantaranDriverModels.get(position).getTanggal());
        holder.tv_jam.setText(pengantaranDriverModels.get(position).getJam());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, DeliveryDriverActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return pengantaranDriverModels.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_kode_pesanan;
        private TextView tv_nama_pelanggan;
        private TextView tv_alamat;
        private TextView tv_tanggal;
        private TextView tv_jam;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_kode_pesanan = itemView.findViewById(R.id.tv_kode_pesanan);
            tv_nama_pelanggan = itemView.findViewById(R.id.tv_nama_pelanggan);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_jam = itemView.findViewById(R.id.tv_jam);

        }
    }
}
