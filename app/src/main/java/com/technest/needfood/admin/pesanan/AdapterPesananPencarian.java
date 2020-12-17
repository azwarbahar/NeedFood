package com.technest.needfood.admin.pesanan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.admin.home.adapter.PesananTerbaruAdapter;
import com.technest.needfood.admin.pesanan.detail.DetailPesananActivity;
import com.technest.needfood.admin.pesanan.detail.DetailPesananBaruActivity;
import com.technest.needfood.models.pesanan.Pesanan;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdapterPesananPencarian extends RecyclerView.Adapter<AdapterPesananPencarian.MyHolderView> {

    private Context context;
    private ArrayList<Pesanan> pesanans;

    public AdapterPesananPencarian(Context context, ArrayList<Pesanan> pesanans) {
        this.context = context;
        this.pesanans = pesanans;
    }

    @NonNull
    @Override
    public AdapterPesananPencarian.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_pesanan, parent, false);
        AdapterPesananPencarian.MyHolderView myHolderView = new AdapterPesananPencarian.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPesananPencarian.MyHolderView holder, int position) {

        holder.tv_nama_pelanggan.setText(pesanans.get(position).getNama());
        holder.tv_telpon_pesanan.setText("Tel : "+ pesanans.get(position).getNo_telepon());
        holder.tv_kode_pesanan.setText(pesanans.get(position).getKd_pemesanan());
        holder.tv_status_pesanan.setText(pesanans.get(position).getStatus());
        String status = pesanans.get(position).getStatus();

        if (status.equals("New")){
            holder.tv_status_pesanan.setTextColor(ContextCompat.getColor(context,R.color.newText));
            holder.tv_status_pesanan.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_new));
        } else if (status.equals("Accept")){
            holder.tv_status_pesanan.setTextColor(ContextCompat.getColor(context,R.color.acceptText));
            holder.tv_status_pesanan.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_accept));
        } else if (status.equals("Proccess")){
            holder.tv_status_pesanan.setTextColor(ContextCompat.getColor(context,R.color.proccessText));
            holder.tv_status_pesanan.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_proccess));
        } else if (status.equals("Delivery")){
            holder.tv_status_pesanan.setTextColor(ContextCompat.getColor(context,R.color.deliveryText));
            holder.tv_status_pesanan.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_delivery));
        } else if (status.equals("Arrived")){
            holder.tv_status_pesanan.setTextColor(ContextCompat.getColor(context,R.color.arrivedText));
            holder.tv_status_pesanan.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_arrived));
        } else if (status.equals("Taking")){
            holder.tv_status_pesanan.setTextColor(ContextCompat.getColor(context,R.color.deliveryText));
            holder.tv_status_pesanan.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_delivery));
        } else if (status.equals("Done")){
            holder.tv_status_pesanan.setTextColor(ContextCompat.getColor(context,R.color.doneText));
            holder.tv_status_pesanan.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_done));
        } else if (status.equals("Refuse")){
            holder.tv_status_pesanan.setTextColor(ContextCompat.getColor(context,R.color.refuseText));
            holder.tv_status_pesanan.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_refuse));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("New")){
                    Intent intent = new Intent(context, DetailPesananBaruActivity.class);
                    intent.putExtra(DetailPesananBaruActivity.EXTRA_DATA, pesanans.get(position));
                    context.startActivity(intent);
                } else {
//                    context.startActivity(new Intent(context, DetailPesananActivity.class));

                    Intent intent = new Intent(context, DetailPesananActivity.class);
                    intent.putExtra(DetailPesananBaruActivity.EXTRA_DATA, pesanans.get(position));
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pesanans.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_nama_pelanggan;
        private TextView tv_telpon_pesanan;
        private TextView tv_kode_pesanan;
        private TextView tv_status_pesanan;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_nama_pelanggan = itemView.findViewById(R.id.tv_nama_pelanggan);
            tv_telpon_pesanan = itemView.findViewById(R.id.tv_telpon_pesanan);
            tv_kode_pesanan = itemView.findViewById(R.id.tv_kode_pesanan);
            tv_status_pesanan = itemView.findViewById(R.id.tv_status_pesanan);

        }
    }

    void setFilter(ArrayList<Pesanan> filterList){
        pesanans = new ArrayList<>();
        pesanans.addAll(filterList);
        notifyDataSetChanged();
    }

}
