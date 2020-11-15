package com.technest.needfood.admin.stok.item_stok;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.stok.adapter.KategoriStokAdapter;
import com.technest.needfood.models.bahan.Bahan;
import com.technest.needfood.models.bahan.ResponseAllBahan;
import com.technest.needfood.models.kategori.Kategori;
import com.technest.needfood.admin.stok.item_stok.adapter.ItemStokBahanAdapter;
import com.technest.needfood.admin.stok.item_stok.model.ItemStokBahanModel;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemStokActivity extends AppCompatActivity {



    public static final String EXTRA_DATA = "extra_data";

    private ImageView item_stok_toolbar_image;
    private Toolbar item_stok_toolbar;

    private RecyclerView rv_item_stok;
    private ArrayList<Bahan> bahans;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stok2);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        rv_item_stok = findViewById(R.id.rv_item_stok);

        item_stok_toolbar_image = findViewById(R.id.item_stok_toolbar_image);
        item_stok_toolbar = findViewById(R.id.item_stok_toolbar);

        Kategori kategori = getIntent().getParcelableExtra(EXTRA_DATA);
        getData(String.valueOf(kategori.getId()));

        Glide.with(this)
                .load(Constanta.url_foto_kategori+kategori.getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(item_stok_toolbar_image);
        item_stok_toolbar.setTitle(kategori.getKategori());

    }

    private void getData(String id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAllBahan> responseAllBahanCall = apiInterface.getAllBahan("Bearer "+ BuildConfig.TOKEN, id);
        responseAllBahanCall.enqueue(new Callback<ResponseAllBahan>() {
            @Override
            public void onResponse(Call<ResponseAllBahan> call, Response<ResponseAllBahan> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getSuccess()){
                        bahans = (ArrayList<Bahan>) response.body().getResult();

                        ItemStokBahanAdapter itemStokBahanAdapter = new ItemStokBahanAdapter(ItemStokActivity.this, bahans);
                        rv_item_stok.setLayoutManager(new LinearLayoutManager(ItemStokActivity.this));
                        rv_item_stok.setAdapter(itemStokBahanAdapter);

                    }
                    Log.d("Respon", "Message = "+response.body().getMessage() );
                }
            }

            @Override
            public void onFailure(Call<ResponseAllBahan> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("ERROR" ,"Respon : "+t.getMessage() );
            }
        });

    }

}
