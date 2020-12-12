package com.technest.needfood.admin.stok;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.stok.adapter.KategoriStokAdapter;
import com.technest.needfood.models.kategori.Kategori;
import com.technest.needfood.models.kategori.ResponKategori;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StokFragment extends Fragment {

    public static final String jenis_kategori_bahan = "bahan";

    private LinearLayout ll_kosong;
    private RecyclerView rv_kategori_stok;
    private ArrayList<Kategori> kategoris;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stok, container, false);

        Context context = getActivity();
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        rv_kategori_stok = v.findViewById(R.id.rv_kategori_stok);
        ll_kosong = v.findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // check Koneksi
        if (ConnectionDetector.isInternetAvailble()) {
            loadDataKategori();
        } else {
            progressBar.setVisibility(View.GONE);
            actionNotConnection();
            ll_kosong.setVisibility(View.VISIBLE);
        }

        return v;
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

    private void loadDataKategori() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponKategori> kategoriCall = apiInterface.getKategori("Bearer " + BuildConfig.TOKEN, jenis_kategori_bahan);
        kategoriCall.enqueue(new Callback<ResponKategori>() {
            @Override
            public void onResponse(Call<ResponKategori> call, Response<ResponKategori> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getSuccess()) {
                        kategoris = (ArrayList<Kategori>) response.body().getResult();
                        for (int a = 0; a < kategoris.size(); a++) {
                            Log.d("Cek", "Respon : " + kategoris.get(a).getKategori());
                        }
                        KategoriStokAdapter kotaAdapter = new KategoriStokAdapter(getContext(), kategoris);
                        rv_kategori_stok.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        rv_kategori_stok.setAdapter(kotaAdapter);
                    } else {
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                    Log.d("Respon", "Message = " + response.body().getMessage());
                } else {
                    progressBar.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponKategori> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
                Log.d("ERROR", "Respon : " + t.getMessage());
            }
        });
    }

}
