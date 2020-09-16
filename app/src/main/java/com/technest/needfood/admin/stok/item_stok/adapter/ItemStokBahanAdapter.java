package com.technest.needfood.admin.stok.item_stok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.model.ItemStokBahanModel;

import java.util.ArrayList;

public class ItemStokBahanAdapter extends RecyclerView.Adapter<ItemStokBahanAdapter.MyHolderView> {

    private Context mContext;
    private ArrayList<ItemStokBahanModel> itemStokBahanModels;

    public ItemStokBahanAdapter(Context mContext, ArrayList<ItemStokBahanModel> itemStokBahanModels) {
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
    public void onBindViewHolder(@NonNull ItemStokBahanAdapter.MyHolderView holder, int position) {

        holder.tv_title_item_stok.setText(itemStokBahanModels.get(position).getTitle_item_stok());
        holder.tv_kuantitas_item_stok.setText(itemStokBahanModels.get(position).getKuantitas_item_stok());
        Glide.with(mContext)
                .load(itemStokBahanModels.get(position).getImage_item_stok())
                .into(holder.img_item_stok);


    }

    @Override
    public int getItemCount() {
        return itemStokBahanModels.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_title_item_stok;
        private TextView tv_kuantitas_item_stok;
        private ImageView img_item_stok;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_kuantitas_item_stok = itemView.findViewById(R.id.tv_kuantitas_item_stok);
            tv_title_item_stok = itemView.findViewById(R.id.tv_title_item_stok);
            img_item_stok = itemView.findViewById(R.id.img_item_stok);

        }
    }
}
