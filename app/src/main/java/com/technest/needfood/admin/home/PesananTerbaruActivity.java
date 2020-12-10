package com.technest.needfood.admin.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.home.adapter.PesananTerbaruAdapter;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananTerbaruActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView tv_title_halaman;
    private RecyclerView rv_item_pesanan_baru;
    private ProgressBar progressBar;
    private LinearLayout ll_kosong;
    private ImageView img_back;
    private ArrayList<Pesanan> pesanans;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_terbaru);

        Context context = this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        ll_kosong = findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        rv_item_pesanan_baru = findViewById(R.id.rv_item_pesanan_baru);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("extra_data");
            tv_title_halaman = findViewById(R.id.tv_title_halaman);
            tv_title_halaman.setText(title);
        }


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
                    loadData();
                } else {
                    rv_item_pesanan_baru.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    actionNotConnection();
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }
        });


        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private void loadData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesananSatus("Bearer " + BuildConfig.TOKEN, "new");
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getmSuccess()) {
                        pesanans = (ArrayList<Pesanan>) response.body().getmPesanan();
                        if (pesanans.isEmpty()) {
                            ll_kosong.setVisibility(View.VISIBLE);
                            rv_item_pesanan_baru.setVisibility(View.GONE);
                        } else {
                            PesananTerbaruAdapter pesananTerbaruAdapter = new PesananTerbaruAdapter(PesananTerbaruActivity.this, pesanans);
                            rv_item_pesanan_baru.setLayoutManager(new LinearLayoutManager(PesananTerbaruActivity.this));
                            rv_item_pesanan_baru.setAdapter(pesananTerbaruAdapter);
                        }
                    } else {
                        rv_item_pesanan_baru.setVisibility(View.GONE);
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                    Log.d("Respon", "Message = " + response.body().getmMessage());
                } else {
                    rv_item_pesanan_baru.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
                Log.d("ERROR", "Respon : " + t.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        Context context = this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());
        // check Koneksi
        if (ConnectionDetector.isInternetAvailble()) {
            loadData();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            actionNotConnection();
        }
    }
}