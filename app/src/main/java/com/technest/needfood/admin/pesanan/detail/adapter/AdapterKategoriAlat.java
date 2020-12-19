package com.technest.needfood.admin.pesanan.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.models.pesanan.AlatPesanan;
import com.technest.needfood.models.pesanan.AlatPilihanPesanan;

import java.util.ArrayList;

public class AdapterKategoriAlat extends RecyclerView.Adapter<AdapterKategoriAlat.MyHolderView> {

    private Context context;
    private ArrayList<AlatPesanan> alatPesanans;
    private ArrayList<AlatPilihanPesanan> alatPilihanPesanans;

    public AdapterKategoriAlat(Context context, ArrayList<AlatPesanan> alatPesanans) {
        this.context = context;
        this.alatPesanans = alatPesanans;
    }

    @NonNull
    @Override
    public AdapterKategoriAlat.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_sliding_container_alat, parent, false);
        AdapterKategoriAlat.MyHolderView vHolder = new AdapterKategoriAlat.MyHolderView(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKategoriAlat.MyHolderView holder, int position) {

        String nama_kategori = alatPesanans.get(position).getKategori_alat();
        String jumlah = alatPesanans.get(position).getJumlah_alat();
        holder.tv_kategori_alat.setText(nama_kategori);
        alatPilihanPesanans = (ArrayList<AlatPilihanPesanan>) alatPesanans.get(position).getAlat_dipilih();
        if (alatPilihanPesanans.isEmpty()){
            holder.tv_kosong_item_sliding_alat.setVisibility(View.VISIBLE);
        } else {
            holder.tv_kosong_item_sliding_alat.setVisibility(View.GONE);
        }
        AdapterItemSlidingAlat adapterItemSlidingAlat = new AdapterItemSlidingAlat(context, alatPilihanPesanans);
        holder.rv_item_sliding_alat.setLayoutManager(new LinearLayoutManager(context));
        holder.rv_item_sliding_alat.setAdapter(adapterItemSlidingAlat);
        holder.rv_item_sliding_alat.setNestedScrollingEnabled(true);
    }

    @Override
    public int getItemCount() {
        return alatPesanans.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_kategori_alat;
        private TextView tv_kosong_item_sliding_alat;
        private RecyclerView rv_item_sliding_alat;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_kategori_alat = itemView.findViewById(R.id.tv_kategori_alat);
            tv_kosong_item_sliding_alat = itemView.findViewById(R.id.tv_kosong_item_sliding_alat);
            rv_item_sliding_alat = itemView.findViewById(R.id.rv_item_sliding_alat);

        }
    }
}
