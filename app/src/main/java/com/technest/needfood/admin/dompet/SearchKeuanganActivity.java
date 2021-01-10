package com.technest.needfood.admin.dompet;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.models.keuangan.ResponseKeuangan;
import com.technest.needfood.models.keuangan.ResultItem;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchKeuanganActivity extends AppCompatActivity {

    private RecyclerView rv_keuangan;
    private ArrayList<ResultItem> resultItems;
    private CardView cvProgressBarr;
    private LinearLayout ll_kosong;
    private String tahun_now;
    private AdapterKeuangan adapterKeuangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_keuangan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ll_kosong = findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        cvProgressBarr = findViewById(R.id.cvProgressBarr);
        cvProgressBarr.setVisibility(View.VISIBLE);
        rv_keuangan = findViewById(R.id.rv_keuangan);

        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        tahun_now = dateFormat.format(date);
        Log.e("GET TAHUN", "RESULT : " + tahun_now);
        loadDataKeuangan(tahun_now);
    }

    private void loadDataKeuangan(String tahun_now) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseKeuangan> keuanganCall = apiInterface.getKeuangan("Bearer " + BuildConfig.TOKEN,
                "All", tahun_now);
        keuanganCall.enqueue(new Callback<ResponseKeuangan>() {
            @Override
            public void onResponse(Call<ResponseKeuangan> call, Response<ResponseKeuangan> response) {
                cvProgressBarr.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    resultItems = (ArrayList<ResultItem>) response.body().getResult();
                    if (resultItems.isEmpty()) {
                        ll_kosong.setVisibility(View.VISIBLE);
                        rv_keuangan.setVisibility(View.GONE);
                    } else {
                        adapterKeuangan = new AdapterKeuangan(SearchKeuanganActivity.this, resultItems);
                        rv_keuangan.setLayoutManager(new LinearLayoutManager(SearchKeuanganActivity.this));
                        rv_keuangan.setAdapter(adapterKeuangan);
                    }

                } else {
                    rv_keuangan.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseKeuangan> call, Throwable t) {
                rv_keuangan.setVisibility(View.GONE);
                cvProgressBarr.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);

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
                ArrayList<ResultItem> dataFilter = new ArrayList<>();
                for (ResultItem resultItem : resultItems) {
                    String jenis = resultItem.getJenis().toLowerCase();
                    String uaraian = resultItem.getUraian().toLowerCase();
                    String nominal = String.valueOf(resultItem.getNominal());
                    String tanggal = getDate(resultItem.getTanggal().toLowerCase());

                    if (jenis.contains(newText) || tanggal.contains(newText)
                            || uaraian.contains(newText) || nominal.contains(newText)) {
                        dataFilter.add(resultItem);
                    }

                }
                adapterKeuangan.setFilter(dataFilter);
                return true;
            }
        });
        return true;

    }

    private String getDate(String time) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = dateParser.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        assert date != null;
        return dateFormatter.format(date);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}