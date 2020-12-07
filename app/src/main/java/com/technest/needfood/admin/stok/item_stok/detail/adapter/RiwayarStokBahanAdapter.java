package com.technest.needfood.admin.stok.item_stok.detail.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.detail.model.RiwayarStokBahanModel;
import com.technest.needfood.models.bahan.RiwayatBeli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RiwayarStokBahanAdapter extends RecyclerView.Adapter<RiwayarStokBahanAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<RiwayatBeli> riwayatBelis;
    private String satuan;

    public RiwayarStokBahanAdapter(Context mContext, ArrayList<RiwayatBeli> riwayatBelis, String satuan) {
        this.mContext = mContext;
        this.riwayatBelis = riwayatBelis;
        this.satuan = satuan;
    }

    @NonNull
    @Override
    public RiwayarStokBahanAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_riwayat_stok, parent, false);
        RiwayarStokBahanAdapter.MyHolderView myHolderView = new RiwayarStokBahanAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayarStokBahanAdapter.MyHolderView holder, int position) {


        holder.img_list_riwayat.setBackgroundResource(R.drawable.arrow_up);
        holder.tv_supplier.setText(riwayatBelis.get(position).getSupplier());
        holder.tv_tanggal_riwayat_stok.setText(getDate(riwayatBelis.get(position).getCreatedAt()));
        holder.tv_kuantitas_list_riwayat.setText(String.valueOf(riwayatBelis.get(position).getJumlahBeli()));
        holder.tv_kuantitas_list_riwayat.setTextColor(Color.parseColor("#077307"));
        holder.tv_satuan.setText(satuan);

    }

    private String getDate(String time) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        Date date = null;
        try {
            date = dateParser.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormatter.format(date);
    }

    @Override
    public int getItemCount() {
        return riwayatBelis.size();
    }

    public static class MyHolderView extends RecyclerView.ViewHolder {

        private ImageView img_list_riwayat;
        private TextView tv_tanggal_riwayat_stok;
        private TextView tv_kuantitas_list_riwayat;
        private TextView tv_satuan;
        private TextView tv_supplier;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            img_list_riwayat = itemView.findViewById(R.id.img_list_riwayat);
            tv_tanggal_riwayat_stok = itemView.findViewById(R.id.tv_tanggal_riwayat_stok);
            tv_kuantitas_list_riwayat = itemView.findViewById(R.id.tv_kuantitas_list_riwayat);
            tv_satuan = itemView.findViewById(R.id.tv_satuan);
            tv_supplier = itemView.findViewById(R.id.tv_supplier);

        }
    }
}
