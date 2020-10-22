package com.technest.needfood.admin.pesanan.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.driver.pesanan.pengantaran.adapter.PengantraanDriverAdapter;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyHolderView> {

    private Context mContext;
    private ArrayList<ItemPesananModel> itemPesananModels;

    public Adapter(Context mContext, ArrayList<ItemPesananModel> itemPesananModels) {
        this.mContext = mContext;
        this.itemPesananModels = itemPesananModels;
    }

    @NonNull
    @Override
    public Adapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_pengantaran_driver, parent, false);
        Adapter.MyHolderView myHolderView = new Adapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyHolderView holder, int position) {

        holder.tv_kode_pesanan.setText(itemPesananModels.get(position).getKode());
        holder.tv_nama_pelanggan.setText(": "+itemPesananModels.get(position).getNama());
        holder.tv_alamat.setText(": "+itemPesananModels.get(position).getAlamat());
        holder.tv_tanggal.setText(itemPesananModels.get(position).getTanggal());
        holder.tv_jam.setText(itemPesananModels.get(position).getJam());



    }

    @Override
    public int getItemCount() {
        return itemPesananModels.size();
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
