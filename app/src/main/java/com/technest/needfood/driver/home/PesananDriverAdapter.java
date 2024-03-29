package com.technest.needfood.driver.home;

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
import com.technest.needfood.models.pesanan.Pesanan;

import java.util.ArrayList;

public class PesananDriverAdapter extends RecyclerView.Adapter<PesananDriverAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<Pesanan> arrayList;

    public PesananDriverAdapter(Context mContext, ArrayList<Pesanan> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PesananDriverAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_pesanan_home_driver, parent, false);
        PesananDriverAdapter.MyHolderView holderView = new PesananDriverAdapter.MyHolderView(view);


        return holderView;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, int position) {

        holder.tv_alamat.setText(arrayList.get(position).getDeskripsi_lokasi());
        holder.tv_nama.setText(arrayList.get(position).getNama());
        holder.tv_jam.setText("Waktu : " + arrayList.get(position).getWaktu_antar());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DeliveryDriverActivity.class);
                intent.putExtra(DeliveryDriverActivity.EXTRA_DATA, arrayList.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_alamat;
        private TextView tv_nama;
        private TextView tv_jam;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_jam = itemView.findViewById(R.id.tv_jam);

        }
    }
}
