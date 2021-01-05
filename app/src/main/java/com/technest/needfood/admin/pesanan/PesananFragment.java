package com.technest.needfood.admin.pesanan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View v;
    private RecyclerView rv_pesanan;
    private ArrayList<Pesanan> pesanans;
    private CardView cvProgressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout ll_kosong;
    private SlidingUpPanelLayout sliding_layout;

    private CardView cv_filter;
    private CardView cv_search;

    private TextView tv_all;
    private TextView tv_new;
    private TextView tv_accept;
    private TextView tv_proccess;
    private TextView tv_delivery;
    private TextView tv_arrived;
    private TextView tv_taking;
    private TextView tv_done;
    private TextView tv_refuse;

    private TextView tv_titel_status;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pesanan, container, false);

        Context context = getActivity();
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());


        ll_kosong = v.findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        cvProgressBar = v.findViewById(R.id.cvProgressBar);
        cvProgressBar.setVisibility(View.VISIBLE);
        rv_pesanan = v.findViewById(R.id.rv_pesanan);
        sliding_layout = v.findViewById(R.id.sliding_layout);
        cv_filter = v.findViewById(R.id.cv_filter);
        cv_search = v.findViewById(R.id.cv_search);

        tv_titel_status = v.findViewById(R.id.tv_titel_status);

        tv_all = v.findViewById(R.id.tv_all);
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "All";
                tv_titel_status.setText(status);
                rv_pesanan.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.GONE);
                showPanel();
                cvProgressBar.setVisibility(View.VISIBLE);
                loadDataPesanan();
            }
        });

        tv_new = v.findViewById(R.id.tv_new);
        tv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "New";
                tv_titel_status.setText(status);
                rv_pesanan.setVisibility(View.GONE);
                showPanel();
                ll_kosong.setVisibility(View.GONE);
                cvProgressBar.setVisibility(View.VISIBLE);
                loadPesananStatus(status);
            }
        });

        tv_accept = v.findViewById(R.id.tv_accept);
        tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "Accept";
                tv_titel_status.setText(status);
                rv_pesanan.setVisibility(View.GONE);
                showPanel();
                ll_kosong.setVisibility(View.GONE);
                cvProgressBar.setVisibility(View.VISIBLE);
                loadPesananStatus(status);
            }
        });
        tv_proccess = v.findViewById(R.id.tv_proccess);
        tv_proccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "Proccess";
                tv_titel_status.setText(status);
                rv_pesanan.setVisibility(View.GONE);
                showPanel();
                ll_kosong.setVisibility(View.GONE);
                cvProgressBar.setVisibility(View.VISIBLE);
                loadPesananStatus(status);
            }
        });
        tv_delivery = v.findViewById(R.id.tv_delivery);
        tv_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "Delivery";
                tv_titel_status.setText(status);
                rv_pesanan.setVisibility(View.GONE);
                showPanel();
                ll_kosong.setVisibility(View.GONE);
                cvProgressBar.setVisibility(View.VISIBLE);
                loadPesananStatus(status);
            }
        });
        tv_arrived = v.findViewById(R.id.tv_arrived);
        tv_arrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "Arrived";
                tv_titel_status.setText(status);
                rv_pesanan.setVisibility(View.GONE);
                showPanel();
                ll_kosong.setVisibility(View.GONE);
                cvProgressBar.setVisibility(View.VISIBLE);
                loadPesananStatus(status);
            }
        });
        tv_taking = v.findViewById(R.id.tv_taking);
        tv_taking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "Taking";
                tv_titel_status.setText(status);
                rv_pesanan.setVisibility(View.GONE);
                showPanel();
                ll_kosong.setVisibility(View.GONE);
                cvProgressBar.setVisibility(View.VISIBLE);
                loadPesananStatus(status);
            }
        });

        tv_done = v.findViewById(R.id.tv_done);
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "Done";
                tv_titel_status.setText(status);
                rv_pesanan.setVisibility(View.GONE);
                showPanel();
                ll_kosong.setVisibility(View.GONE);
                cvProgressBar.setVisibility(View.VISIBLE);
                loadPesananStatus(status);
            }
        });

        tv_refuse = v.findViewById(R.id.tv_refuse);
        tv_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "Refuse";
                tv_titel_status.setText(status);
                rv_pesanan.setVisibility(View.GONE);
                showPanel();
                ll_kosong.setVisibility(View.GONE);
                cvProgressBar.setVisibility(View.VISIBLE);
                loadPesananStatus(status);
            }
        });

        cv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPanel();
            }
        });

        cv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchPesananActivity.class));
            }
        });

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
                    loadDataPesanan();
                } else {
                    rv_pesanan.setVisibility(View.GONE);
                    actionNotConnection();
                    cvProgressBar.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }
        });

        return v;
    }

    private void loadPesananStatus(String status) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesananSatus("Bearer " + BuildConfig.TOKEN, status);
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                rv_pesanan.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);
                cvProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getmSuccess()) {
                        pesanans = (ArrayList<Pesanan>) response.body().getmPesanan();
                        if (pesanans.isEmpty()) {
                            ll_kosong.setVisibility(View.VISIBLE);
                            rv_pesanan.setVisibility(View.GONE);
                        } else {
                            AdapterPesananPencarian adapterPesananPencarian = new AdapterPesananPencarian(getActivity(), pesanans);
                            rv_pesanan.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                mSwipeRefreshLayout.setRefreshing(false);
                cvProgressBar.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
                Log.d("ERROR", "Respon : " + t.getMessage());

            }
        });
    }

    private void showPanel() {
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        }

    }

    private void actionNotConnection() {
        Snackbar.make(getActivity().findViewById(android.R.id.content), "Koneksi Tidak Ada!", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                    }
                })
                .show();
    }

    private void loadDataPesanan() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesanan("Bearer " + BuildConfig.TOKEN);
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                rv_pesanan.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);
                cvProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getmSuccess()) {
                        pesanans = (ArrayList<Pesanan>) response.body().getmPesanan();
                        if (pesanans.isEmpty()) {
                            ll_kosong.setVisibility(View.VISIBLE);
                            rv_pesanan.setVisibility(View.GONE);
                        } else {
                            Collections.sort(pesanans, new Comparator<Pesanan>() {
                                @Override
                                public int compare(Pesanan o1, Pesanan o2) {
                                    return o1.getWaktu_antar().compareTo(o2.getWaktu_antar());
                                }

                                @Override
                                public boolean equals(Object obj) {
                                    return false;
                                }
                            });
                            AdapterPesananPencarian adapterPesananPencarian = new AdapterPesananPencarian(getActivity(), pesanans);
                            adapterPesananPencarian.notifyDataSetChanged();
                            rv_pesanan.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv_pesanan.setAdapter(adapterPesananPencarian);
                            rv_pesanan.setHasFixedSize(true);
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
            if (tv_titel_status.getText().toString().equals("All")) {
                loadDataPesanan();
            } else if (tv_titel_status.getText().toString().equals("New")) {
                loadPesananStatus("New");
            } else if (tv_titel_status.getText().toString().equals("Accept")) {
                loadPesananStatus("Accept");
            } else if (tv_titel_status.getText().toString().equals("Proccess")) {
                loadPesananStatus("Proccess");
            } else if (tv_titel_status.getText().toString().equals("Delivery")) {
                loadPesananStatus("Delivery");
            } else if (tv_titel_status.getText().toString().equals("Arrived")) {
                loadPesananStatus("Arrived");
            } else if (tv_titel_status.getText().toString().equals("Taking")) {
                loadPesananStatus("Taking");
            } else if (tv_titel_status.getText().toString().equals("Done")) {
                loadPesananStatus("Done");
            } else if (tv_titel_status.getText().toString().equals("Refuse")) {
                loadPesananStatus("Refuse");
            } else {
                loadDataPesanan();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            actionNotConnection();
        }
    }
}
