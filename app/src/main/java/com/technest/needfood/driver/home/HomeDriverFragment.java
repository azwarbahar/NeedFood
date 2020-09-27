package com.technest.needfood.driver.home;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.technest.needfood.R;
import com.technest.needfood.admin.pesanan.PesananFragment;
import com.technest.needfood.driver.pesanan.PesananDriverFragment;

import java.util.ArrayList;

public class HomeDriverFragment extends Fragment implements OnMapReadyCallback {

    private View v;

    private GoogleMap googleMap;

    private ImageView btn_location;

    private String[] nama;
    private String[] alamat;
    private String[] jam;

    private SlidingUpPanelLayout sliding_layout;

    private RelativeLayout rv_btn_hariini;
    private CardView cv_pesanan_home_driver;

    private RecyclerView rv_pesanan_home_driver;
    private ArrayList<PesananDriverModel> pesananDriverModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home_driver, container, false);

        rv_pesanan_home_driver = v.findViewById(R.id.rv_pesanan_home_driver);
        sliding_layout = v.findViewById(R.id.sliding_layout);
        rv_btn_hariini = v.findViewById(R.id.rv_btn_hariini);
        cv_pesanan_home_driver = v.findViewById(R.id.cv_pesanan_home_driver);
        btn_location = v.findViewById(R.id.btn_my_location);

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cv_pesanan_home_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment pesananFragment = new PesananDriverFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_driver_fragment, pesananFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        rv_btn_hariini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPanel();

            }
        });

        PesananDriverAdapter pesananDriverAdapter = new PesananDriverAdapter(getActivity(), pesananDriverModels);
        rv_pesanan_home_driver.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_pesanan_home_driver.setAdapter(pesananDriverAdapter);

        return  v;
    }

    private void showPanel() {

        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == PanelState.EXPANDED || sliding_layout.getPanelState() == PanelState.ANCHORED)) {
            sliding_layout.setPanelState(PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(PanelState.ANCHORED);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_pekerja);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(HomeDriverFragment.this);

    }

    private ArrayList<PesananDriverModel> tambahItemPesanan(){

        nama = getResources().getStringArray(R.array.nama_pesanan);
        alamat = getResources().getStringArray(R.array.alamat_pesanan);
        jam = getResources().getStringArray(R.array.jam_pesanan);

        ArrayList<PesananDriverModel> length = new ArrayList<>();

        for(int a = 0; a < nama.length; a++){
            PesananDriverModel model = new PesananDriverModel();
            model.setNama(nama[a]);
            model.setAlamat(alamat[a]);
            model.setJam(jam[a]);
            length.add(model);
        }
        return length;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pesananDriverModels = tambahItemPesanan();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
