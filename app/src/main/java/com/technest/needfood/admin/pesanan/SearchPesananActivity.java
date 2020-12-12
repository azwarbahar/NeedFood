package com.technest.needfood.admin.pesanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPesananActivity extends AppCompatActivity {


    private RecyclerView rv_pesanan;
    private ArrayList<Pesanan> pesanans;
    private ProgressBar progressBar;
    private LinearLayout ll_kosong;
    private AdapterPesananPencarian adapterPesananPencarian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pesanan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ll_kosong = findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        rv_pesanan = findViewById(R.id.rv_pesanan);

        loadDataPesanan();

    }

    private void loadDataPesanan() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesanan("Bearer " + BuildConfig.TOKEN);
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                rv_pesanan.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getmSuccess()) {
                        pesanans = (ArrayList<Pesanan>) response.body().getmPesanan();
                        if (pesanans.isEmpty()) {
                            ll_kosong.setVisibility(View.VISIBLE);
                            rv_pesanan.setVisibility(View.GONE);
                        } else {
                            adapterPesananPencarian = new AdapterPesananPencarian(SearchPesananActivity.this, pesanans);
                            rv_pesanan.setLayoutManager(new LinearLayoutManager(SearchPesananActivity.this));
                            rv_pesanan.setItemAnimator(new DefaultItemAnimator());
                            rv_pesanan.setAdapter(adapterPesananPencarian);
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
                progressBar.setVisibility(View.GONE);
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
                for (Pesanan pesanan : pesanans){
                    String nama = pesanan.getNama().toLowerCase();
                    String telpon = pesanan.getNo_telepon().toLowerCase();
                    String kode = pesanan.getKd_pemesanan().toLowerCase();
                    String status = pesanan.getStatus().toLowerCase();

                    if (nama.contains(newText) || telpon.contains(newText) ||  kode.contains(newText) || status.contains(newText)){
                        dataFilter.add(pesanan);
                    }

                }
                adapterPesananPencarian.setFilter(dataFilter);
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