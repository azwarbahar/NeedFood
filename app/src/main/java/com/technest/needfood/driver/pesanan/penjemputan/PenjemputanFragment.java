package com.technest.needfood.driver.pesanan.penjemputan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.driver.pesanan.pengantaran.adapter.PengantraanDriverAdapter;
import com.technest.needfood.driver.pesanan.pengantaran.model.PengantaranDriverModel;
import com.technest.needfood.driver.pesanan.penjemputan.adapter.PenjemputanDriverAdapter;
import com.technest.needfood.driver.pesanan.penjemputan.model.PenjemputanDriverModel;

import java.util.ArrayList;

public class PenjemputanFragment extends Fragment {

    View v;

    private String[] kodepesanan;
    private String[] nama;
    private String[] alamat;
    private String[] tanggal;
    private String[] jam;

    private RecyclerView rv_penjemputan;
    private ArrayList<PenjemputanDriverModel> penjemputanDriverModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.penjemputan_driver_fragment, container, false);


        rv_penjemputan = v.findViewById(R.id.rv_penjemputan);
        PenjemputanDriverAdapter penjemputanDriverAdapter = new PenjemputanDriverAdapter(getContext(),penjemputanDriverModels);
        rv_penjemputan.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_penjemputan.setAdapter(penjemputanDriverAdapter);

        return  v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        penjemputanDriverModels = tambahData();

    }


    private ArrayList<PenjemputanDriverModel> tambahData(){

        kodepesanan = getResources().getStringArray(R.array.kode_pesanan);
        nama = getResources().getStringArray(R.array.nama_pesanan);
        alamat = getResources().getStringArray(R.array.alamat_pesanan);
        tanggal = getResources().getStringArray(R.array.tanggal_pesanan);
        jam = getResources().getStringArray(R.array.jam_pesanan);

        ArrayList<PenjemputanDriverModel> listnyakota = new ArrayList<>();

        for(int a = 0; a < kodepesanan.length; a++){
            PenjemputanDriverModel stokModel = new PenjemputanDriverModel();
            stokModel.setKode(kodepesanan[a]);
            stokModel.setNama(nama[a]);
            stokModel.setAlamat(alamat[a]);
            stokModel.setTanggal(tanggal[a]);
            stokModel.setJam(jam[a]);
            listnyakota.add(stokModel);
        }
        return listnyakota;
    }

}
