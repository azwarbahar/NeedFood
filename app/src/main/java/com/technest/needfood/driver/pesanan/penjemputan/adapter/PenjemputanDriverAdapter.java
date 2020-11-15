package com.technest.needfood.driver.pesanan.penjemputan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.driver.pesanan.penjemputan.model.PenjemputanDriverModel;
import com.technest.needfood.driver.taking.TakingDriverActivity;

import java.util.ArrayList;

public class PenjemputanDriverAdapter extends RecyclerView.Adapter<PenjemputanDriverAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<PenjemputanDriverModel> penjemputanDriverModels;

    public PenjemputanDriverAdapter(Context mContext, ArrayList<PenjemputanDriverModel> penjemputanDriverModels) {
        this.mContext = mContext;
        this.penjemputanDriverModels = penjemputanDriverModels;
    }

    @NonNull
    @Override
    public PenjemputanDriverAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_pengantaran_driver, parent, false);
        PenjemputanDriverAdapter.MyHolderView myHolderView = new PenjemputanDriverAdapter.MyHolderView(view);

        return myHolderView;

    }

    @Override
    public void onBindViewHolder(@NonNull PenjemputanDriverAdapter.MyHolderView holder, int position) {

        holder.tv_kode_pesanan.setText(penjemputanDriverModels.get(position).getKode());
        holder.tv_nama_pelanggan.setText(": "+ penjemputanDriverModels.get(position).getNama());
        holder.tv_alamat.setText(": "+ penjemputanDriverModels.get(position).getAlamat());
        holder.tv_tanggal.setText(penjemputanDriverModels.get(position).getTanggal());
        holder.tv_jam.setText(penjemputanDriverModels.get(position).getJam());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, TakingDriverActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return penjemputanDriverModels.size();
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
