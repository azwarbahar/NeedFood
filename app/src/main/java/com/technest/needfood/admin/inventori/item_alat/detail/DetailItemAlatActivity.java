package com.technest.needfood.admin.inventori.item_alat.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.models.alat.Alat;
import com.technest.needfood.models.alat.ResponseAlat;
import com.technest.needfood.models.alat.RiwayatBeliItem;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailItemAlatActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";
    private ImageView item_alat_toolbar_image;
    private Toolbar item_alat_toolbar;
    private TextView tv_satuan;
    private TextView tv_satuan1;
    private TextView tv_kuantitas_alat;
    private TextView tv_kuantitas_alat_keluar;
    // image zoom
    private TextView lihat_gambar;
    private RelativeLayout containerImageZoom;
    private ImageView img_zoom;
    private ImageView img_close;
    private String satuan;
    private LinearLayout ll_kosong;
    private ProgressBar progressBar;
    private RecyclerView rv_riwayat_item_alat;
    private Alat alats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_alat);

        Context context = DetailItemAlatActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        ll_kosong = findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        img_zoom = findViewById(R.id.img_zoom);
        img_close = findViewById(R.id.img_close);
        containerImageZoom = findViewById(R.id.containerImageZoom);
        containerImageZoom.setVisibility(View.GONE);
        lihat_gambar = findViewById(R.id.lihat_gambar);
        lihat_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerImageZoom.setVisibility(View.VISIBLE);
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerImageZoom.setVisibility(View.GONE);
            }
        });

        rv_riwayat_item_alat = findViewById(R.id.rv_riwayat_item_alat);
        tv_kuantitas_alat = findViewById(R.id.tv_kuantitas_alat);
        tv_kuantitas_alat_keluar = findViewById(R.id.tv_kuantitas_alat_keluar);
        tv_satuan = findViewById(R.id.tv_satuan);
        tv_satuan1 = findViewById(R.id.tv_satuan1);

        item_alat_toolbar_image = findViewById(R.id.item_alat_toolbar_image);
        item_alat_toolbar = findViewById(R.id.item_alat_toolbar);

        Alat alat = getIntent().getParcelableExtra(EXTRA_DATA);


        Glide.with(this)
                .load(Constanta.url_foto_alat + alat.getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(item_alat_toolbar_image);

        Glide.with(this)
                .load(Constanta.url_foto_alat + alat.getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(img_zoom);
        item_alat_toolbar.setTitle(alat.getNama());
        tv_kuantitas_alat.setText(String.valueOf(alat.getSisa_alat()));
        tv_kuantitas_alat_keluar.setText(String.valueOf(alat.getAlatKeluar()));
        tv_satuan.setText("Pcs");
        tv_satuan1.setText("Pcs");
//        satuan = String.valueOf(alat.getSatuan());

        // check Koneksi
        if (ConnectionDetector.isInternetAvailble()) {
            getDataAlat(String.valueOf(alat.getId()));
        } else {
            progressBar.setVisibility(View.GONE);
            actionNotConnection("Koneksi Tidak Ada!");
            ll_kosong.setVisibility(View.VISIBLE);
        }

    }

    private void getDataAlat(String id) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAlat> responseAlatCall = apiInterface.getAlat("Bearer " + BuildConfig.TOKEN, id);
        responseAlatCall.enqueue(new Callback<ResponseAlat>() {
            @Override
            public void onResponse(Call<ResponseAlat> call, Response<ResponseAlat> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        ll_kosong.setVisibility(View.GONE);
                        alats = response.body().getResult();
                        setList(alats);

                    } else {
                        ll_kosong.setVisibility(View.VISIBLE);
                        actionNotConnection("Data Tidak Ditemukan");
                    }
                } else {
                    ll_kosong.setVisibility(View.VISIBLE);
                    actionNotConnection("Gagal load data!");
                }
            }

            @Override
            public void onFailure(Call<ResponseAlat> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
                actionNotConnection("Gagal load data!");
            }
        });

    }

    private void setList(Alat alats) {
        progressBar.setVisibility(View.GONE);
        RiwayarAlatAdapter riwayarAlatAdapter = new RiwayarAlatAdapter(this, (ArrayList<RiwayatBeliItem>) alats.mRiwayatBeliItem(), satuan);
        rv_riwayat_item_alat.setLayoutManager(new LinearLayoutManager(this));
        rv_riwayat_item_alat.setAdapter(riwayarAlatAdapter);
    }

    private void actionNotConnection(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
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