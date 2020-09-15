package com.technest.needfood.admin.stok.item_stok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;


import com.google.android.material.tabs.TabLayout;
import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.riwayat.RiwayatItemStokFragment;
import com.technest.needfood.admin.stok.item_stok.sisa.SisaItemStokFragment;
import com.technest.needfood.admin.stok.model.KategoriStokModel;

public class ItemStokActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";

    private ImageView item_stok_toolbar_image;
    private Toolbar item_stok_toolbar;
    private ViewPager item_stok_viewpaget;
    private TabLayout item_stok_tablayout;
    private String a;
    private ItemStokPagerAdapter itemStokPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stok);

        item_stok_tablayout = findViewById(R.id.item_stok_tablayout);
        item_stok_viewpaget = findViewById(R.id.item_stok_viewpaget);

        itemStokPagerAdapter = new ItemStokPagerAdapter(getSupportFragmentManager());
        itemStokPagerAdapter.AddFragmentItemStok(new SisaItemStokFragment(),"Sisa");
        itemStokPagerAdapter.AddFragmentItemStok(new RiwayatItemStokFragment(),"Riwayat");

        item_stok_viewpaget.setAdapter(itemStokPagerAdapter);
        item_stok_tablayout.setupWithViewPager(item_stok_viewpaget);

        item_stok_toolbar_image = findViewById(R.id.item_stok_toolbar_image);
        item_stok_toolbar = findViewById(R.id.item_stok_toolbar);

        // support toolbar
//        setSupportActionBar(item_stok_toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        KategoriStokModel kategoriStokModel = getIntent().getParcelableExtra(EXTRA_DATA);

        item_stok_toolbar_image.setImageResource(kategoriStokModel.getImage_kategori_stok());
        item_stok_toolbar.setTitle(kategoriStokModel.getTitle_kategori_stok());


    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        finish();
//        return super.onSupportNavigateUp();
//    }

}
