package com.technest.needfood.driver.delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.driver.riwayat.adapter.RiwayatDriverAdapter;
import com.technest.needfood.models.alat.CekAlatPesanan;
import com.technest.needfood.models.alat.ResponseAlatPesanan;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekAlatActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rv_cekAlat;
    private CekAlatAdapter cekAlatAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CardView cvProgressBar;
    private LinearLayout ll_kosong;
    private ArrayList<CekAlatPesanan> cekAlatPesanans;

    private String id_pesanan;
    private String kode_pesanan;
    private String nama_pesanan;
    private String alamat_pesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_alat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Context context = this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        ll_kosong = findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        cvProgressBar = findViewById(R.id.cvProgressBar);
        cvProgressBar.setVisibility(View.VISIBLE);
        rv_cekAlat = findViewById(R.id.rv_cekAlat);

        id_pesanan = getIntent().getStringExtra("ID_PESANAN");
        kode_pesanan = getIntent().getStringExtra("KODE_PESANAN");
        nama_pesanan = getIntent().getStringExtra("NAMA_PESANAN");
        alamat_pesanan = getIntent().getStringExtra("ALAMAT_PESANAN");

        TextView tv_kode = findViewById(R.id.tv_kode);
        TextView tv_nama = findViewById(R.id.tv_nama);
        TextView tv_alamat = findViewById(R.id.tv_alamat);

        tv_kode.setText(kode_pesanan);
        tv_nama.setText(nama_pesanan);
        tv_alamat.setText(alamat_pesanan);



        mSwipeRefreshLayout = findViewById(R.id.swipe_continer);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                // check Koneksi
                if (ConnectionDetector.isInternetAvailble()) {
                    loadData(id_pesanan);
                } else {
                    rv_cekAlat.setVisibility(View.GONE);
                    cvProgressBar.setVisibility(View.GONE);
                    actionNotConnection();
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void loadData(String id_pesanan) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAlatPesanan> responseAlatPesananCall = apiInterface.getAlatPesanan("Bearer "
                + BuildConfig.TOKEN, id_pesanan);
        responseAlatPesananCall.enqueue(new Callback<ResponseAlatPesanan>() {
            @Override
            public void onResponse(Call<ResponseAlatPesanan> call, Response<ResponseAlatPesanan> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    cvProgressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        cekAlatPesanans = (ArrayList<CekAlatPesanan>) response.body().getResult();
                        if (cekAlatPesanans.isEmpty()) {
                            ll_kosong.setVisibility(View.VISIBLE);
                            rv_cekAlat.setVisibility(View.GONE);
                        } else {
                            cekAlatAdapter = new CekAlatAdapter(CekAlatActivity.this, id_pesanan, cekAlatPesanans);
                            rv_cekAlat.setLayoutManager(new LinearLayoutManager(CekAlatActivity.this));
                            rv_cekAlat.setAdapter(cekAlatAdapter);

                        }
                    } else {
                        rv_cekAlat.setVisibility(View.GONE);
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                    Log.d("Respon", "Message = " + response.body().getMessage());
                } else {
                    rv_cekAlat.setVisibility(View.GONE);
                    cvProgressBar.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseAlatPesanan> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void onRefresh() {

        Context context = this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());
        // check Koneksi
        if (ConnectionDetector.isInternetAvailble()) {
            loadData(id_pesanan);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            actionNotConnection();
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
}