package com.technest.needfood.driver.riwayat.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.driver.riwayat.SearchRiwayatDriverActivity;
import com.technest.needfood.driver.riwayat.adapter.RiwayatDriverAdapter;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technest.needfood.intro.LoginActivity.my_shared_preferences;

public class PenjemputanFragmentRiwayat extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View v;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CardView cvProgressBar;
    private LinearLayout ll_kosong;
    private RecyclerView rv_pengantaran;
    private ArrayList<Pesanan> pesanans;
    private SharedPreferences mPreferences;
    private CardView cv_search;
    private EditText et_cari;
    RiwayatDriverAdapter riwayatDriverAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.riwayat_pengantaran_driver_fragment, container, false);
        Context context = getActivity();
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        et_cari = v.findViewById(R.id.et_cari);
        et_cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty() || editable.toString().equals("")) {

                } else {
                    filter(editable.toString());
                }
            }
        });

//        cv_search = v.findViewById(R.id.cv_search);
//        cv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SearchRiwayatDriverActivity.class);
//                intent.putExtra("DATA", "Penjemputan");
//                startActivity(intent);
//            }
//        });

        ll_kosong = v.findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        cvProgressBar = v.findViewById(R.id.cvProgressBar);
        cvProgressBar.setVisibility(View.VISIBLE);
        rv_pengantaran = v.findViewById(R.id.rv_pengantaran);

        mSwipeRefreshLayout = v.findViewById(R.id.swipe_continer);
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
                    rv_pengantaran.setVisibility(View.GONE);
                    cvProgressBar.setVisibility(View.GONE);
                    actionNotConnection();
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }
        });

        return v;
    }


    private void filter(String text) {
        ArrayList<Pesanan> filteredList = new ArrayList<>();
        for (Pesanan pesanan : pesanans) {
            String nama = pesanan.getNama().toLowerCase();
            String telpon = pesanan.getNo_telepon().toLowerCase();
            String kode = pesanan.getKd_pemesanan().toLowerCase();
            String status = pesanan.getStatus().toLowerCase();

            if (nama.contains(text) || telpon.contains(text) || kode.contains(text) || status.contains(text)) {
                filteredList.add(pesanan);
            }

        }
        riwayatDriverAdapter.setFilter(filteredList);
    }

    private void loadData() {

        mPreferences = getActivity().getApplicationContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        String driver_id = mPreferences.getString("ID", "");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesananDriver("Bearer "
                + BuildConfig.TOKEN, driver_id, "penjemputan");
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {

                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    cvProgressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getmSuccess()) {
                        pesanans = (ArrayList<Pesanan>) response.body().getmPesanan();
                        if (pesanans.isEmpty()) {
                            et_cari.setEnabled(false);
                            ll_kosong.setVisibility(View.VISIBLE);
                            rv_pengantaran.setVisibility(View.GONE);
                        } else {
                            et_cari.setEnabled(true);
                            riwayatDriverAdapter = new RiwayatDriverAdapter(getContext(), pesanans);
                            rv_pengantaran.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv_pengantaran.setAdapter(riwayatDriverAdapter);

                        }
                    } else {
                        et_cari.setEnabled(false);
                        rv_pengantaran.setVisibility(View.GONE);
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                    Log.d("Respon", "Message = " + response.body().getmMessage());
                } else {
                    et_cari.setEnabled(false);
                    rv_pengantaran.setVisibility(View.GONE);
                    cvProgressBar.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                cvProgressBar.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
                Log.d("ERROR", "Respon : " + t.getMessage());
            }
        });
    }

    private void actionNotConnection() {
        Snackbar.make(v.findViewById(android.R.id.content), "Koneksi Tidak Ada!", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                    }
                })
                .show();
    }


    @Override
    public void onRefresh() {
        Context context = getActivity();
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
