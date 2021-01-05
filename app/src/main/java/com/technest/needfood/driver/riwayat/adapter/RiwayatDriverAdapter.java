package com.technest.needfood.driver.riwayat.adapter;

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
import com.technest.needfood.driver.delivery.DetailItemDeliveryActivity;
import com.technest.needfood.driver.riwayat.DetailRwayatDriverActivity;
import com.technest.needfood.models.pesanan.Pesanan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RiwayatDriverAdapter extends RecyclerView.Adapter<RiwayatDriverAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<Pesanan> pesanans;

    public RiwayatDriverAdapter(Context mContext, ArrayList<Pesanan> pesanans) {
        this.mContext = mContext;
        this.pesanans = pesanans;
    }

    @NonNull
    @Override
    public RiwayatDriverAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(mContext).inflate(R.layout.item_riwayat_pesanan_driver, parent, false);
        RiwayatDriverAdapter.MyHolderView myHolderView = new RiwayatDriverAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatDriverAdapter.MyHolderView holder, int position) {

        holder.tv_kode_pesanan.setText(pesanans.get(position).getKd_pemesanan());
        holder.tv_nama.setText(pesanans.get(position).getNama());
        String tanggal = getDate(pesanans.get(position).getTanggal_antar());
        holder.tv_tanggal.setText(tanggal);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailRwayatDriverActivity.class);
                intent.putExtra("EXTRA_DATA", pesanans.get(position));
                mContext.startActivity(intent);
            }
        });


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
    public int getItemCount() {
        return pesanans.size();
    }


    public void setFilter(ArrayList<Pesanan> filterList) {
        pesanans = new ArrayList<>();
        pesanans.addAll(filterList);
        notifyDataSetChanged();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_kode_pesanan;
        private TextView tv_nama;
        private TextView tv_tanggal;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_kode_pesanan = itemView.findViewById(R.id.tv_kode_pesanan);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);

        }
    }
}
