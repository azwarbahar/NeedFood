package com.technest.needfood.driver.riwayat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.technest.needfood.R;
import com.technest.needfood.driver.riwayat.adapter.RiwayatDriverPagerAdapter;
import com.technest.needfood.driver.riwayat.fragment.PengantaranFragmentRiwayat;
import com.technest.needfood.driver.riwayat.fragment.PenjemputanFragmentRiwayat;

public class RiwayatDriverFragment extends Fragment {



    private CardView cv_search;
    View v;

    private ViewPager riwayat_driver_viewpager;
    private TabLayout riwayat_driver_tablayout;
    private RiwayatDriverPagerAdapter riwayatDriverPagerAdapter;
    private RecyclerView rv_riwayat_driver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_riwayat_driver, container, false);

        riwayat_driver_viewpager = v.findViewById(R.id.riwayat_driver_viewpager);
        riwayat_driver_tablayout = v.findViewById(R.id.riwayat_driver_tablayout);

        riwayatDriverPagerAdapter = new RiwayatDriverPagerAdapter(getActivity().getSupportFragmentManager());
        riwayatDriverPagerAdapter.AddFragmentRiwayat(new PengantaranFragmentRiwayat(), "Pengantaran");
        riwayatDriverPagerAdapter.AddFragmentRiwayat(new PenjemputanFragmentRiwayat(), "Penjemputan");
        riwayat_driver_viewpager.setAdapter(riwayatDriverPagerAdapter);
        riwayat_driver_tablayout.setupWithViewPager(riwayat_driver_viewpager);

        PengantaranFragmentRiwayat pengantaranFragmentRiwayat = new PengantaranFragmentRiwayat();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.riwayat_driver_viewpager, pengantaranFragmentRiwayat);

        fragmentTransaction.commit();

//        rv_riwayat_driver = v.findViewById(R.id.rv_riwayat_driver);
//        RiwayatDriverAdapter riwayatDriverAdapter = new RiwayatDriverAdapter(getContext(),riwayatDriverModels);
//        rv_riwayat_driver.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rv_riwayat_driver.setAdapter(riwayatDriverAdapter);
//
//        cv_search = v.findViewById(R.id.cv_search);
//        cv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(getActivity(),SearchRiwayatDriverActivity.class));
//
//            }
//        });

        return  v;
    }


}
