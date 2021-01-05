package com.technest.needfood.driver.pesanan;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.technest.needfood.driver.pesanan.adapter.PengantraanDriverAdapter;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengantaranFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View v;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CardView cvProgressBar;
    private LinearLayout ll_kosong;
    private RecyclerView rv_pengantaran;
    private ArrayList<Pesanan> pesanans;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.pengantaran_driver_fragment, container, false);

        Context context = getActivity();
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

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

    private void loadData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesananSatus("Bearer "
                + BuildConfig.TOKEN, "Delivery");
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
                            ll_kosong.setVisibility(View.VISIBLE);
                            rv_pengantaran.setVisibility(View.GONE);
                        } else {
                            PengantraanDriverAdapter pengantraanDriverAdapter = new PengantraanDriverAdapter(getContext(), pesanans);
                            rv_pengantaran.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv_pengantaran.setAdapter(pengantraanDriverAdapter);

                        }
                    } else {
                        rv_pengantaran.setVisibility(View.GONE);
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                    Log.d("Respon", "Message = " + response.body().getmMessage());
                } else {
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
