package com.technest.needfood.admin.home.pintasan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.technest.needfood.R;
import com.technest.needfood.admin.home.adapter.PagerAdapterPesananTerbaru;

public class PesananTerbaruActivity extends AppCompatActivity {

    private ImageView img_back;

    private ViewPager pesanan_viewpager;
    private TabLayout pesanan_tablayout;
    private PagerAdapterPesananTerbaru pagerAdapterPesananTerbaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_terbaru);

        pesanan_viewpager = findViewById(R.id.pesanan_viewpager);
        pesanan_tablayout = findViewById(R.id.pesanan_tablayout);

        pagerAdapterPesananTerbaru = new PagerAdapterPesananTerbaru(getSupportFragmentManager());
        pagerAdapterPesananTerbaru.AddFragment(new SudahBayarFragment(), "Sudah Transaksi");
        pagerAdapterPesananTerbaru.AddFragment(new BelumBayarFragment(), "Belum Transaksi");
        pesanan_viewpager.setAdapter(pagerAdapterPesananTerbaru);
        pesanan_tablayout.setupWithViewPager(pesanan_viewpager);

        startfragment();
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void startfragment() {
        SudahBayarFragment sudahBayarFragment = new SudahBayarFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.pesanan_viewpager, sudahBayarFragment);
        fragmentTransaction.commit();
    }

}