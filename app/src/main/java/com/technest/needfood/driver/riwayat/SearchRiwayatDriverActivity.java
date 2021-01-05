package com.technest.needfood.driver.riwayat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.driver.riwayat.adapter.RiwayatDriverAdapter;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technest.needfood.intro.LoginActivity.my_shared_preferences;

public class SearchRiwayatDriverActivity extends AppCompatActivity {

    private RecyclerView rv_pesanan;
    private ArrayList<Pesanan> pesanans;
    private CardView cvProgressBarr;
    private LinearLayout ll_kosong;
    private RiwayatDriverAdapter riwayatDriverAdapter;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_riwayat_driver);

        String session_status = getIntent().getStringExtra("DATA");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(session_status);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ll_kosong = findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        cvProgressBarr = findViewById(R.id.cvProgressBarr);
        cvProgressBarr.setVisibility(View.VISIBLE);
        rv_pesanan = findViewById(R.id.rv_pesanan);
        loadDataPesanan(session_status);


    }

    private void loadDataPesanan(String session_status) {
        String status_send = "pengantaran";
        if (session_status.equals("Pengantaran")) {
            status_send = "pengantaran";
        } else if (session_status.equals("Penjemputan")) {
            status_send = "penjemputan";
        }

        mPreferences = getApplicationContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        String driver_id = mPreferences.getString("ID", "");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesananDriver("Bearer "
                + BuildConfig.TOKEN, driver_id, status_send);
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                rv_pesanan.setVisibility(View.VISIBLE);
                cvProgressBarr.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getmSuccess()) {
                        pesanans = (ArrayList<Pesanan>) response.body().getmPesanan();
                        if (pesanans.isEmpty()) {
                            ll_kosong.setVisibility(View.VISIBLE);
                            rv_pesanan.setVisibility(View.GONE);
                        } else {
                            riwayatDriverAdapter = new RiwayatDriverAdapter(SearchRiwayatDriverActivity.this, pesanans);
                            rv_pesanan.setLayoutManager(new LinearLayoutManager(SearchRiwayatDriverActivity.this));
                            rv_pesanan.setItemAnimator(new DefaultItemAnimator());
                            rv_pesanan.setAdapter(riwayatDriverAdapter);
                        }
                    } else {
                        rv_pesanan.setVisibility(View.GONE);
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                    Log.d("Respon", "Message = " + response.body().getmMessage());
                } else {
                    rv_pesanan.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                cvProgressBarr.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
                Log.d("ERROR", "Respon : " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchIem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchIem.getActionView();
        searchView.setQueryHint(Html.fromHtml("<font color = #A3A2A2>" + getResources().getString(R.string.hintSearchMess) + "</font>"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
//                Toast.makeText(SearchPesananActivity.this, "ubah", Toast.LENGTH_SHORT).show();
                ArrayList<Pesanan> dataFilter = new ArrayList<>();
                for (Pesanan pesanan : pesanans) {
                    String nama = pesanan.getNama().toLowerCase();
                    String kode = pesanan.getKd_pemesanan().toLowerCase();

                    if (nama.contains(newText) || kode.contains(newText)) {
                        dataFilter.add(pesanan);
                    }

                }
                riwayatDriverAdapter.setFilter(dataFilter);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
