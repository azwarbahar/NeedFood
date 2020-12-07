package com.technest.needfood.admin.inventori.item_alat;

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
import com.technest.needfood.admin.stok.item_stok.adapter.ItemStokBahanAdapter;
import com.technest.needfood.admin.stok.item_stok.detail.DetailItemStokActivity;
import com.technest.needfood.models.alat.Alat;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

public class ItemAlatAdapter extends RecyclerView.Adapter<ItemAlatAdapter.MyHolderView> {

    private Context context;
    private ArrayList<Alat> alatArrayList;

    public ItemAlatAdapter(Context context, ArrayList<Alat> alatArrayList) {
        this.context = context;
        this.alatArrayList = alatArrayList;
    }

    @NonNull
    @Override
    public ItemAlatAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.item_sisa_stok, parent,false);
        ItemAlatAdapter.MyHolderView myHolderView = new ItemAlatAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAlatAdapter.MyHolderView holder, int position) {

        holder.tv_title_item_stok.setText(alatArrayList.get(position).getNama());
        holder.tv_kuantitas_item_stok.setText(String.valueOf(alatArrayList.get(position).getSisaAlat()));
        holder.tv_satuan.setText("Pcs");
        Glide.with(context)
                .load(Constanta.url_foto_alat+alatArrayList.get(position).getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(holder.img_item_stok);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, DetailItemStokActivity.class);
//                intent.putExtra(ItemStokActivity.EXTRA_DATA, itemStokBahanModels.get(position));
//                mContext.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return alatArrayList.size();
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
