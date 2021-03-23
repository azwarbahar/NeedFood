package com.technest.needfood.admin.inventori.item_alat;

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
import com.technest.needfood.models.alat.Alat;
import com.technest.needfood.models.alat.ResponseAllAlat;
import com.technest.needfood.models.kategori.Kategori;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAlatActivity extends AppCompatActivity {


    public static final String EXTRA_DATA = "extra_data";

    private ImageView item_alat_toolbar_image;
    private Toolbar item_alat_toolbar;
    private LinearLayout ll_kosong;

    private RecyclerView rv_item_alat;
    private ArrayList<Alat> alats;
    private CardView cvProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_alat);


        Toolbar toolbar = findViewById(R.id.item_alat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Context context = ItemAlatActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        cvProgressBar = findViewById(R.id.cvProgressBar);
        ll_kosong = findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        cvProgressBar.setVisibility(View.VISIBLE);
        rv_item_alat = findViewById(R.id.rv_item_alat);


        Kategori kategori = getIntent().getParcelableExtra(EXTRA_DATA);

        item_alat_toolbar_image = findViewById(R.id.item_alat_toolbar_image);
        item_alat_toolbar = findViewById(R.id.item_alat_toolbar);

        Glide.with(this)
                .load(Constanta.url_foto_kategori + kategori.getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(item_alat_toolbar_image);
        item_alat_toolbar.setTitle(kategori.getKategori());

        // check Koneksi
//         Toast.makeText(context, "ID : "+kategori.getId(), Toast.LENGTH_SHORT).show();
        if (ConnectionDetector.isInternetAvailble()) {
            getData(String.valueOf(kategori.getId()));
        } else {
            cvProgressBar.setVisibility(View.GONE);
            actionNotConnection();
            ll_kosong.setVisibility(View.VISIBLE);
        }

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
        Call<ResponseAllAlat> responseAllAlatCall = apiInterface.getAllAlat("Bearer " + BuildConfig.TOKEN, id);
        responseAllAlatCall.enqueue(new Callback<ResponseAllAlat>() {
            @Override
            public void onResponse(Call<ResponseAllAlat> call, Response<ResponseAllAlat> response) {
                if (response.isSuccessful()) {
                    ll_kosong.setVisibility(View.GONE);
                    cvProgressBar.setVisibility(View.GONE);
                    if (response.body().getmSuccess()) {
                        ll_kosong.setVisibility(View.GONE);
                        alats = (ArrayList<Alat>) response.body().getmResult();
                        for (int a = 0; a < alats.size(); a++) {
                            Log.d("Cek ALAT", "Respon : " + alats.get(a).getNama());
                        }
                        ItemAlatAdapter itemAlatAdapter = new ItemAlatAdapter(ItemAlatActivity.this, alats);
                        rv_item_alat.setLayoutManager(new LinearLayoutManager(ItemAlatActivity.this));
                        rv_item_alat.setAdapter(itemAlatAdapter);
                    } else {
                        cvProgressBar.setVisibility(View.GONE);
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                    Log.d("Respon", "Message1 = " + response.body().getmMessage());
                } else {
                    cvProgressBar.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseAllAlat> call, Throwable t) {
                cvProgressBar.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
                Log.d("ERROR", "Respon : " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}