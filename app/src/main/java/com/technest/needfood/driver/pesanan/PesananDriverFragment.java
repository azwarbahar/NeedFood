package com.technest.needfood.driver.pesanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import com.technest.needfood.R;
import com.technest.needfood.driver.pesanan.adapter.PesananDriverPagerAdapter;
import com.technest.needfood.driver.pesanan.pengantaran.PengantaranFragment;
import com.technest.needfood.driver.pesanan.penjemputan.PenjemputanFragment;

public class PesananDriverFragment extends Fragment {

    View v;

    private ViewPager pesanan_driver_viewpager;
    private TabLayout pesanan_driver_tablayout;

    private PesananDriverPagerAdapter pesananDriverPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pesanan_driver, container, false);

        pesanan_driver_viewpager = v.findViewById(R.id.pesanan_driver_viewpager);
        pesanan_driver_tablayout = v.findViewById(R.id.pesanan_driver_tablayout);

        pesananDriverPagerAdapter = new PesananDriverPagerAdapter(getActivity().getSupportFragmentManager());
        pesananDriverPagerAdapter.AddFragment(new PengantaranFragment(), "Pengantaran");
        pesananDriverPagerAdapter.AddFragment(new PenjemputanFragment(), "Penjemputan");
        pesanan_driver_viewpager.setAdapter(pesananDriverPagerAdapter);
        pesanan_driver_tablayout.setupWithViewPager(pesanan_driver_viewpager);

        PengantaranFragment pengantaranFragment = new PengantaranFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.pesanan_driver_viewpager, pengantaranFragment);

        fragmentTransaction.commit();

        return v;
    }
}
