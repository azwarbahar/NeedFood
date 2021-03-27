package com.technest.needfood.admin.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.admin.pesanan.detail.DetailPesananBaruActivity;
import com.technest.needfood.models.pesanan.Pesanan;

import java.util.ArrayList;

public class PesananBelumBayarAdapter extends RecyclerView.Adapter<PesananBelumBayarAdapter.MyHolderView> {

    private Context context;
    private ArrayList<Pesanan> pesananArrayList;

    public PesananBelumBayarAdapter(Context context, ArrayList<Pesanan> pesananArrayList) {
        this.context = context;
        this.pesananArrayList = pesananArrayList;
    }

    @NonNull
    @Override
    public PesananBelumBayarAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_pesanan_baru, parent, false);
        PesananBelumBayarAdapter.MyHolderView myHolderView = new PesananBelumBayarAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull PesananBelumBayarAdapter.MyHolderView holder, int position) {

        String metode_bayar = pesananArrayList.get(position).getMetode_bayar();

        if (metode_bayar.equals("Transfer Bank")){
            holder.tv_cod.setVisibility(View.GONE);
        } else {
            holder.tv_cod.setText("COD");
            holder.tv_cod.setVisibility(View.VISIBLE);
        }

        holder.tv_alamat.setText(pesananArrayList.get(position).getDeskripsi_lokasi());
        holder.tv_kode_pesanan.setText(pesananArrayList.get(position).getKd_pemesanan());
        holder.tv_nama_pelanggan.setText(pesananArrayList.get(position).getNama());
        holder.img_refuse.setVisibility(View.GONE);
        holder.img_accept.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPesananBaruActivity.class);
                intent.putExtra(DetailPesananBaruActivity.EXTRA_DATA, pesananArrayList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pesananArrayList.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_kode_pesanan;
        private TextView tv_nama_pelanggan;
        private TextView tv_alamat;
        private TextView tv_cod;
        private ImageView img_accept;
        private ImageView img_refuse;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_kode_pesanan = itemView.findViewById(R.id.tv_kode_pesanan);
            tv_nama_pelanggan = itemView.findViewById(R.id.tv_nama_pelanggan);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_cod = itemView.findViewById(R.id.tv_cod);
            img_accept = itemView.findViewById(R.id.img_accept);
            img_refuse = itemView.findViewById(R.id.img_refuse);

        }
    }
}
