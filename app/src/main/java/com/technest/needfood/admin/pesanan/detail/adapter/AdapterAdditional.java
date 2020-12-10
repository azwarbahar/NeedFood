package com.technest.needfood.admin.pesanan.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.models.pesanan.Additional;

import java.util.ArrayList;

public class AdapterAdditional extends RecyclerView.Adapter<AdapterAdditional.MyHolderView> {

    private Context context;
    private ArrayList<Additional> additionals;

    public AdapterAdditional(Context context, ArrayList<Additional> additionals) {
        this.context = context;
        this.additionals = additionals;
    }

    @NonNull
    @Override
    public AdapterAdditional.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_list_additional, parent, false);
        AdapterAdditional.MyHolderView vHolder = new AdapterAdditional.MyHolderView(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdditional.MyHolderView holder, int position) {

        String nama = additionals.get(position).getNama_daging();
        String berat = additionals.get(position).getBerat();
        holder.tv_nama.setText(nama+" "+berat);
        holder.tv_harga.setText(additionals.get(position).getHarga());
        holder.tv_jumlah.setText(additionals.get(position).getJumlah());

    }

    @Override
    public int getItemCount() {
        return additionals.size();
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
