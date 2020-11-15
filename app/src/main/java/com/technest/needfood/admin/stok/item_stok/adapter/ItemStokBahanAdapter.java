package com.technest.needfood.admin.stok.item_stok.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.ItemStokActivity;
import com.technest.needfood.admin.stok.item_stok.detail.DetailItemStokActivity;
import com.technest.needfood.admin.stok.item_stok.model.ItemStokBahanModel;
import com.technest.needfood.models.bahan.Bahan;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

public class ItemStokBahanAdapter extends RecyclerView.Adapter<ItemStokBahanAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<Bahan> itemStokBahanModels;

    public ItemStokBahanAdapter(Context mContext, ArrayList<Bahan> itemStokBahanModels) {
        this.mContext = mContext;
        this.itemStokBahanModels = itemStokBahanModels;
    }

    @NonNull
    @Override
    public ItemStokBahanAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(mContext).inflate(R.layout.item_sisa_stok, parent,false);
        ItemStokBahanAdapter.MyHolderView myHolderView = new ItemStokBahanAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemStokBahanAdapter.MyHolderView holder, final int position) {

        holder.tv_title_item_stok.setText(itemStokBahanModels.get(position).getNama());
        holder.tv_kuantitas_item_stok.setText(String.valueOf(itemStokBahanModels.get(position).getJumlahBahan()));
        holder.tv_satuan.setText(itemStokBahanModels.get(position).getSatuan());
        Glide.with(mContext)
                .load(Constanta.url_foto_bahan+itemStokBahanModels.get(position).getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(holder.img_item_stok);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailItemStokActivity.class);
                intent.putExtra(ItemStokActivity.EXTRA_DATA, itemStokBahanModels.get(position));
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemStokBahanModels.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_satuan;
        private TextView tv_title_item_stok;
        private TextView tv_kuantitas_item_stok;
        private ImageView img_item_stok;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_satuan = itemView.findViewById(R.id.tv_satuan);
            tv_kuantitas_item_stok = itemView.findViewById(R.id.tv_kuantitas_item_stok);
            tv_title_item_stok = itemView.findViewById(R.id.tv_title_item_stok);
            img_item_stok = itemView.findViewById(R.id.img_item_stok);

        }
    }
}
