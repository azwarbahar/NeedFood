package com.technest.needfood.admin.pesanan.detail.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.models.bahan.Bahan;
import com.technest.needfood.models.bahan.ResponseBahan;
import com.technest.needfood.models.pesanan.BahanPesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterBahanPaket extends RecyclerView.Adapter<AdapterBahanPaket.MyHolderView> {

    private Context context;
    private ArrayList<BahanPesanan> bahanPesanans;
    private Bahan bahans;

    public AdapterBahanPaket(Context context, ArrayList<BahanPesanan> bahanPesanans) {
        this.context = context;
        this.bahanPesanans = bahanPesanans;
    }

    @NonNull
    @Override
    public AdapterBahanPaket.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_bahan_alat_sliding_pesanan, parent, false);
        AdapterBahanPaket.MyHolderView vHolder = new AdapterBahanPaket.MyHolderView(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBahanPaket.MyHolderView holder, int position) {

        String id = String.valueOf(bahanPesanans.get(position).getBahan_id());
        String nama = bahanPesanans.get(position).getNama_bahan();
        String jumlah = bahanPesanans.get(position).getJumlah_bahan();

        holder.tv_nama.setText(nama);
        holder.tv_jumlah.setText(jumlah);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBahan> responseBahanCall = apiInterface.getBahan("Bearer " + BuildConfig.TOKEN, id);
        responseBahanCall.enqueue(new Callback<ResponseBahan>() {
            @Override
            public void onResponse(Call<ResponseBahan> call, Response<ResponseBahan> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        bahans = response.body().getResult();
                        Glide.with(context)
                                .load(Constanta.url_foto_bahan+bahans.getFoto())
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
            public void onFailure(Call<ResponseBahan> call, Throwable t) {
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
        return bahanPesanans.size();
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
