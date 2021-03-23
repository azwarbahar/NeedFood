package com.technest.needfood.admin.stok.item_stok;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.adapter.ItemStokBahanAdapter;
import com.technest.needfood.models.bahan.Bahan;
import com.technest.needfood.models.bahan.ResponseAllBahan;
import com.technest.needfood.models.kategori.Kategori;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemStokActivity extends AppCompatActivity {


    public static final String EXTRA_DATA = "extra_data";

    private ImageView item_stok_toolbar_image;
    private Toolbar item_stok_toolbar;
    private LinearLayout ll_kosong;

    private RecyclerView rv_item_stok;
    private ArrayList<Bahan> bahans;
    private CardView cvProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stok2);

        Toolbar toolbar = findViewById(R.id.item_stok_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Context context = ItemStokActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        cvProgressBar = findViewById(R.id.cvProgressBar);
        ll_kosong = findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        cvProgressBar.setVisibility(View.GONE);
        cvProgressBar.setVisibility(View.VISIBLE);
        rv_item_stok = findViewById(R.id.rv_item_stok);

        item_stok_toolbar_image = findViewById(R.id.item_stok_toolbar_image);
        item_stok_toolbar = findViewById(R.id.item_stok_toolbar);

        Kategori kategori = getIntent().getParcelableExtra(EXTRA_DATA);
        // check Koneksi
        if (ConnectionDetector.isInternetAvailble()) {
            getData(String.valueOf(kategori.getId()));
        } else {
            cvProgressBar.setVisibility(View.GONE);
            actionNotConnection();
            ll_kosong.setVisibility(View.VISIBLE);
        }

        Glide.with(this)
                .load(Constanta.url_foto_kategori + kategori.getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(item_stok_toolbar_image);
        item_stok_toolbar.setTitle(kategori.getKategori());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void actionNotConnection() {
        Snackbar.make(findViewById(android.R.id.content), "Koneksi Tidak Ada!", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                    }
                })
                .show();
    }

    private void getData(String id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAllBahan> responseAllBahanCall = apiInterface.getAllBahan("Bearer " + BuildConfig.TOKEN, id);
        responseAllBahanCall.enqueue(new Callback<ResponseAllBahan>() {
            @Override
            public void onResponse(Call<ResponseAllBahan> call, Response<ResponseAllBahan> response) {
                if (response.isSuccessful()) {
                    ll_kosong.setVisibility(View.GONE);
                    cvProgressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getSuccess()) {
                        ll_kosong.setVisibility(View.GONE);
                        bahans = (ArrayList<Bahan>) response.body().getResult();

                        ItemStokBahanAdapter itemStokBahanAdapter = new ItemStokBahanAdapter(ItemStokActivity.this, bahans);
                        rv_item_stok.setLayoutManager(new LinearLayoutManager(ItemStokActivity.this));
                        rv_item_stok.setAdapter(itemStokBahanAdapter);

                    } else {
                        cvProgressBar.setVisibility(View.GONE);
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                    Log.d("Respon", "Message = " + response.body().getMessage());
                } else {
                    cvProgressBar.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseAllBahan> call, Throwable t) {
                cvProgressBar.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
                Log.d("ERROR", "Respon : " + t.getMessage());
            }
        });

    }

}
