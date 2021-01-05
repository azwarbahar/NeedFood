package com.technest.needfood.admin.stok.item_stok.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.detail.adapter.RiwayarStokBahanAdapter;
import com.technest.needfood.models.bahan.Bahan;
import com.technest.needfood.models.bahan.ResponseBahan;
import com.technest.needfood.models.bahan.RiwayatBeli;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailItemStokActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";

    private ImageView item_stok_toolbar_image;
    private Toolbar item_stok_toolbar;
    private TextView tv_satuan;
    private TextView tv_kuantitas_stok;

    private String satuan;
    private LinearLayout ll_kosong;
    private CardView cvProgressBar;

    private RecyclerView rv_riwayat_item_stok;
    private Bahan bahans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stok);

        Context context = DetailItemStokActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        cvProgressBar = findViewById(R.id.cvProgressBar);
        cvProgressBar.setVisibility(View.VISIBLE);
        ll_kosong = findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);

        rv_riwayat_item_stok = findViewById(R.id.rv_riwayat_item_stok);
        tv_kuantitas_stok = findViewById(R.id.tv_kuantitas_stok);
        tv_satuan = findViewById(R.id.tv_satuan);

        item_stok_toolbar_image = findViewById(R.id.item_stok_toolbar_image);
        item_stok_toolbar = findViewById(R.id.item_stok_toolbar);

        // support toolbar
//        setSupportActionBar(item_stok_toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bahan bahan = getIntent().getParcelableExtra(EXTRA_DATA);

        Glide.with(this)
                .load(Constanta.url_foto_bahan + bahan.getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(item_stok_toolbar_image);
        item_stok_toolbar.setTitle(bahan.getNama());
        tv_kuantitas_stok.setText(String.valueOf(bahan.getJumlahBahan()));
        tv_satuan.setText(String.valueOf(bahan.getSatuan()));
        satuan = String.valueOf(bahan.getSatuan());


        // check Koneksi
        if (ConnectionDetector.isInternetAvailble()) {
            getDataBahan(String.valueOf(bahan.getId()));
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

    private void getDataBahan(String id) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBahan> responseBahanCall = apiInterface.getBahan("Bearer " + BuildConfig.TOKEN, id);
        responseBahanCall.enqueue(new Callback<ResponseBahan>() {
            @Override
            public void onResponse(Call<ResponseBahan> call, Response<ResponseBahan> response) {
                cvProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        ll_kosong.setVisibility(View.GONE);
                        bahans = response.body().getResult();
                        setList(bahans);
                    } else {
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                } else {
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBahan> call, Throwable t) {
                cvProgressBar.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
            }
        });


    }

    private void setList(Bahan bahans) {
        cvProgressBar.setVisibility(View.GONE);
        RiwayarStokBahanAdapter riwayarStokBahanAdapter = new RiwayarStokBahanAdapter(this, (ArrayList<RiwayatBeli>) bahans.getRiwayatBeli(), satuan);
        rv_riwayat_item_stok.setLayoutManager(new LinearLayoutManager(this));
        rv_riwayat_item_stok.setAdapter(riwayarStokBahanAdapter);
    }


//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        finish();
//        return super.onSupportNavigateUp();
//    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
