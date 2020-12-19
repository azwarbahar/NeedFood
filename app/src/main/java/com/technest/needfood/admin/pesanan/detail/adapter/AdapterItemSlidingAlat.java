package com.technest.needfood.admin.pesanan.detail.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.models.alat.Alat;
import com.technest.needfood.models.alat.ResponseAlat;
import com.technest.needfood.models.pesanan.AlatPilihanPesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterItemSlidingAlat extends RecyclerView.Adapter<AdapterItemSlidingAlat.MyHolderView> {

    private Context context;
    private ArrayList<AlatPilihanPesanan> alatPilihanPesanans;
    private Alat alat;

    public AdapterItemSlidingAlat(Context context, ArrayList<AlatPilihanPesanan> alatPilihanPesanans) {
        this.context = context;
        this.alatPilihanPesanans = alatPilihanPesanans;
    }

    @NonNull
    @Override
    public AdapterItemSlidingAlat.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_bahan_alat_sliding_pesanan, parent, false);
        AdapterItemSlidingAlat.MyHolderView vHolder = new AdapterItemSlidingAlat.MyHolderView(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemSlidingAlat.MyHolderView holder, int position) {

        String id = String.valueOf(alatPilihanPesanans.get(position).getAlat_id());
        String nama = alatPilihanPesanans.get(position).getNama_alat();
        String jumlah = alatPilihanPesanans.get(position).getJumlah();

        holder.tv_jumlah.setText(jumlah);
        holder.tv_nama.setText(nama);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAlat> responseAlatCall = apiInterface.getAlat("Bearer " + BuildConfig.TOKEN, id);
        responseAlatCall.enqueue(new Callback<ResponseAlat>() {
            @Override
            public void onResponse(Call<ResponseAlat> call, Response<ResponseAlat> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        alat = response.body().getResult();
                        Glide.with(context)
                                .load(Constanta.url_foto_alat+alat.getFoto())
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.ic_broken_image)
                                .into(holder.img_item);

                    } else {
                        Glide.with(context)
                                .load(R.drawable.ic_broken_image)
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.ic_broken_image)
                                .into(holder.img_item);
                    }
                } else {
                    Glide.with(context)
                            .load(R.drawable.ic_broken_image)
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                            .into(holder.img_item);
                }
            }

            @Override
            public void onFailure(Call<ResponseAlat> call, Throwable t) {
                Log.d("Respon Adapter Pesanan ", " "+t);
                Glide.with(context)
                        .load(R.drawable.ic_broken_image)
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                        .into(holder.img_item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return alatPilihanPesanans.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        private ImageButton img_item;
        private TextView tv_nama;
        private TextView tv_jumlah;
        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            img_item = itemView.findViewById(R.id.img_item);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_jumlah = itemView.findViewById(R.id.tv_jumlah);
        }
    }
}
