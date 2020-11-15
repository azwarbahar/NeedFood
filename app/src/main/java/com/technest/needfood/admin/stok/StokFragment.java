package com.technest.needfood.admin.stok;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.models.kategori.Kategori;
import com.technest.needfood.models.kategori.ResponKategori;
import com.technest.needfood.admin.stok.adapter.KategoriStokAdapter;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StokFragment extends Fragment {

    private View v;

    private static final String jenisKategori = "bahan";

    private RecyclerView rv_kategori_stok;
    private ArrayList<Kategori> kategoris;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_stok, container, false);

        rv_kategori_stok = v.findViewById(R.id.rv_kategori_stok);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // cek koneksi dulu
        loadDataKategori();

        return v;
    }

    private void loadDataKategori() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponKategori> kategoriCall = apiInterface.getKategori("Bearer "+BuildConfig.TOKEN, StokFragment.jenisKategori);
        kategoriCall.enqueue(new Callback<ResponKategori>() {
            @Override
            public void onResponse(Call<ResponKategori> call, Response<ResponKategori> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getSuccess()){
                        kategoris = (ArrayList<Kategori>) response.body().getResult();
                        for (int a = 0 ; a < kategoris.size(); a++){
                            Log.d("Cek", "Respon : "+kategoris.get(a).getKategori());
                        }
                        KategoriStokAdapter kotaAdapter = new KategoriStokAdapter(getContext(), kategoris);
                        rv_kategori_stok.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        rv_kategori_stok.setAdapter(kotaAdapter);
                    }
                    Log.d("Respon", "Message = "+response.body().getMessage() );
                }
            }

            @Override
            public void onFailure(Call<ResponKategori> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("ERROR" ,"Respon : "+t.getMessage() );
            }
        });
    }

}
