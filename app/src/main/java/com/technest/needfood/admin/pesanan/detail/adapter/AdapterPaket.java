package com.technest.needfood.admin.pesanan.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.admin.inventori.adapter.KategoriInventoriAdapter;
import com.technest.needfood.models.pesanan.Paket;

import java.util.ArrayList;

public class AdapterPaket extends RecyclerView.Adapter<AdapterPaket.MyHolderView> {

    private Context context;
    private ArrayList<Paket> pakets;

    public AdapterPaket(Context context, ArrayList<Paket> pakets) {
        this.context = context;
        this.pakets = pakets;
    }

    @NonNull
    @Override
    public AdapterPaket.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_list_paket, parent, false);
        AdapterPaket.MyHolderView vHolder = new AdapterPaket.MyHolderView(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPaket.MyHolderView holder, int position) {

        holder.tv_nama.setText(pakets.get(position).getNamaPaket());
        holder.tv_jumlah.setText(String.valueOf(pakets.get(position).getJumlah()));
        holder.tv_harga.setText(String.valueOf(pakets.get(position).getHarga()));

    }

    @Override
    public int getItemCount() {
        return pakets.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_nama;
        private TextView tv_harga;
        private TextView tv_jumlah;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            tv_jumlah = itemView.findViewById(R.id.tv_jumlah);

        }
    }
}
