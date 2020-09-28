package com.technest.needfood.driver.riwayat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.detail.adapter.RiwayarStokBahanAdapter;
import com.technest.needfood.driver.riwayat.model.RiwayatDriverModel;

import java.util.ArrayList;

public class RiwayatDriverAdapter extends RecyclerView.Adapter<RiwayatDriverAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<RiwayatDriverModel> riwayatDriverModels;

    public RiwayatDriverAdapter(Context mContext, ArrayList<RiwayatDriverModel> riwayatDriverModels) {
        this.mContext = mContext;
        this.riwayatDriverModels = riwayatDriverModels;
    }

    @NonNull
    @Override
    public RiwayatDriverAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(mContext).inflate(R.layout.item_pengantaran_driver, parent, false);
        RiwayatDriverAdapter.MyHolderView myHolderView = new RiwayatDriverAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatDriverAdapter.MyHolderView holder, int position) {

        holder.tv_kode_pesanan.setText(riwayatDriverModels.get(position).getKode());
        holder.tv_nama_pelanggan.setText(": "+riwayatDriverModels.get(position).getNama());
        holder.tv_alamat.setText(": "+riwayatDriverModels.get(position).getAlamat());
        holder.tv_tanggal.setText(riwayatDriverModels.get(position).getTanggal());
        holder.tv_jam.setText(riwayatDriverModels.get(position).getJam());

        String status = riwayatDriverModels.get(position).getStatus();

        if (status.equals("Antar")){

            holder.img_status.setBackgroundResource(R.drawable.ic_icon_antar);

        } else {
            holder.img_status.setBackgroundResource(R.drawable.ic_icon_jemput);
        }


    }

    @Override
    public int getItemCount() {
        return riwayatDriverModels.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_kode_pesanan;
        private TextView tv_nama_pelanggan;
        private TextView tv_alamat;
        private TextView tv_tanggal;
        private TextView tv_jam;
        private ImageView img_status;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_kode_pesanan = itemView.findViewById(R.id.tv_kode_pesanan);
            tv_nama_pelanggan = itemView.findViewById(R.id.tv_nama_pelanggan);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_jam = itemView.findViewById(R.id.tv_jam);
            img_status = itemView.findViewById(R.id.img_status);

        }
    }
}
