package com.technest.needfood.admin.inventori.item_alat.detail;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.models.alat.RiwayatBeliItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RiwayarAlatAdapter extends RecyclerView.Adapter<RiwayarAlatAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<RiwayatBeliItem> riwayatBelis;
    private String satuan;

    public RiwayarAlatAdapter(Context mContext, ArrayList<RiwayatBeliItem> riwayatBelis, String satuan) {
        this.mContext = mContext;
        this.riwayatBelis = riwayatBelis;
        this.satuan = satuan;
    }

    @NonNull
    @Override
    public RiwayarAlatAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_riwayat_stok, parent, false);
        RiwayarAlatAdapter.MyHolderView myHolderView = new RiwayarAlatAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayarAlatAdapter.MyHolderView holder, int position) {
        holder.img_list_riwayat.setBackgroundResource(R.drawable.arrow_up);
        holder.tv_supplier.setText(riwayatBelis.get(position).getSupplier());
        holder.tv_tanggal_riwayat_alat.setText(getDate(riwayatBelis.get(position).getCreated_at()));
        holder.tv_kuantitas_list_riwayat.setText(String.valueOf(riwayatBelis.get(position).getJumlah_beli()));
        holder.tv_kuantitas_list_riwayat.setTextColor(Color.parseColor("#077307"));
        holder.tv_satuan.setText(satuan);
    }

    private String getDate(String time) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
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

    public class MyHolderView extends RecyclerView.ViewHolder {

        private ImageView img_list_riwayat;
        private TextView tv_tanggal_riwayat_alat;
        private TextView tv_kuantitas_list_riwayat;
        private TextView tv_satuan;
        private TextView tv_supplier;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            img_list_riwayat = itemView.findViewById(R.id.img_list_riwayat);
            tv_tanggal_riwayat_alat = itemView.findViewById(R.id.tv_tanggal_riwayat_stok);
            tv_kuantitas_list_riwayat = itemView.findViewById(R.id.tv_kuantitas_list_riwayat);
            tv_satuan = itemView.findViewById(R.id.tv_satuan);
            tv_supplier = itemView.findViewById(R.id.tv_supplier);
        }
    }
}
