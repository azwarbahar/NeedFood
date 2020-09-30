package com.technest.needfood.driver.riwayat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.driver.riwayat.adapter.RiwayatDriverAdapter;
import com.technest.needfood.driver.riwayat.model.RiwayatDriverModel;

import java.util.ArrayList;

public class RiwayatDriverFragment extends Fragment {


    private String[] kodepesanan;
    private String[] nama;
    private String[] alamat;
    private String[] tanggal;
    private String[] jam;
    private String[] status;

    private CardView cv_search;
    View v;
    private RecyclerView rv_riwayat_driver;
    private ArrayList<RiwayatDriverModel> riwayatDriverModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_riwayat_driver, container, false);


        rv_riwayat_driver = v.findViewById(R.id.rv_riwayat_driver);
        RiwayatDriverAdapter riwayatDriverAdapter = new RiwayatDriverAdapter(getContext(),riwayatDriverModels);
        rv_riwayat_driver.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_riwayat_driver.setAdapter(riwayatDriverAdapter);

        cv_search = v.findViewById(R.id.cv_search);
        cv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),SearchRiwayatDriverActivity.class));

            }
        });

        return  v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        riwayatDriverModels = tambahData();

    }

    private ArrayList<RiwayatDriverModel> tambahData(){

        kodepesanan = getResources().getStringArray(R.array.kode_pesanan);
        nama = getResources().getStringArray(R.array.nama_pesanan);
        alamat = getResources().getStringArray(R.array.alamat_pesanan);
        tanggal = getResources().getStringArray(R.array.tanggal_pesanan);
        jam = getResources().getStringArray(R.array.jam_pesanan);
        status = getResources().getStringArray(R.array.status_antar);

        ArrayList<RiwayatDriverModel> listnyakota = new ArrayList<>();

        for(int a = 0; a < kodepesanan.length; a++){
            RiwayatDriverModel stokModel = new RiwayatDriverModel();
            stokModel.setKode(kodepesanan[a]);
            stokModel.setNama(nama[a]);
            stokModel.setAlamat(alamat[a]);
            stokModel.setTanggal(tanggal[a]);
            stokModel.setJam(jam[a]);
            stokModel.setStatus(status[a]);
            listnyakota.add(stokModel);
        }
        return listnyakota;
    }

}
