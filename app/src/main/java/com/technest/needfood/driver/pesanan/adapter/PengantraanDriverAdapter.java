package com.technest.needfood.driver.pesanan.adapter;

import android.annotation.SuppressLint;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PengantraanDriverAdapter extends RecyclerView.Adapter<PengantraanDriverAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<Pesanan> pesanans;

    public PengantraanDriverAdapter(Context mContext, ArrayList<Pesanan> pesanans) {
        this.mContext = mContext;
        this.pesanans = pesanans;
    }

    @NonNull
    @Override
    public PengantraanDriverAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_pengantaran_driver, parent, false);
        PengantraanDriverAdapter.MyHolderView myHolderView = new PengantraanDriverAdapter.MyHolderView(view);

        return myHolderView;

    }

    private String getDate(String time) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = dateParser.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        assert date != null;
        return dateFormatter.format(date);
    }

    @Override
    public void onBindViewHolder(@NonNull PengantraanDriverAdapter.MyHolderView holder, int position) {

        holder.tv_kode_pesanan.setText(pesanans.get(position).getKd_pemesanan());
        holder.tv_nama_pelanggan.setText(": "+pesanans.get(position).getNama());
        holder.tv_alamat.setText(": "+pesanans.get(position).getDeskripsi_lokasi());
        String tgl = getDate(pesanans.get(position).getTanggal_antar());
        holder.tv_tanggal.setText(tgl);
        holder.tv_jam.setText("Waktu : "+pesanans.get(position).getWaktu_antar());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DeliveryDriverActivity.class);
                intent.putExtra(DeliveryDriverActivity.EXTRA_DATA, pesanans.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pesanans.size();
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
