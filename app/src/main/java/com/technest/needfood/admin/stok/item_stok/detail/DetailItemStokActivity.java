package com.technest.needfood.admin.stok.item_stok.detail;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.detail.adapter.RiwayarStokBahanAdapter;
import com.technest.needfood.admin.stok.item_stok.detail.model.RiwayarStokBahanModel;
import com.technest.needfood.admin.stok.item_stok.model.ItemStokBahanModel;

import java.util.ArrayList;

public class DetailItemStokActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";

    private ImageView item_stok_toolbar_image;
    private Toolbar item_stok_toolbar;

    private String[] statusRiwayat;
    private String[] tanggalRiwayat;
    private String[] kuantitasRiwayat;

    private RecyclerView rv_riwayat_item_stok;
    private ArrayList<RiwayarStokBahanModel> stokBahanModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stok);

        rv_riwayat_item_stok = findViewById(R.id.rv_riwayat_item_stok);

        item_stok_toolbar_image = findViewById(R.id.item_stok_toolbar_image);
        item_stok_toolbar = findViewById(R.id.item_stok_toolbar);

        // support toolbar
//        setSupportActionBar(item_stok_toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ItemStokBahanModel itemStokBahanModel = getIntent().getParcelableExtra(EXTRA_DATA);

        item_stok_toolbar_image.setImageResource(itemStokBahanModel.getImage_item_stok());
        item_stok_toolbar.setTitle(itemStokBahanModel.getTitle_item_stok());

        getDataRiwayat();

    }

    private void getDataRiwayat() {

        stokBahanModels = tambahItemItemStok();

        RiwayarStokBahanAdapter riwayarStokBahanAdapter = new RiwayarStokBahanAdapter(this,stokBahanModels);
        rv_riwayat_item_stok.setLayoutManager(new LinearLayoutManager(this));
        rv_riwayat_item_stok.setAdapter(riwayarStokBahanAdapter);

    }

    private ArrayList<RiwayarStokBahanModel> tambahItemItemStok(){

        statusRiwayat = getResources().getStringArray(R.array.status_riwayat_bahan);
        tanggalRiwayat = getResources().getStringArray(R.array.tanggal_riwayat_bahan);
        kuantitasRiwayat = getResources().getStringArray(R.array.kuantitas_riwayat_bahan);

        ArrayList<RiwayarStokBahanModel> riwayarStokBahanModels = new ArrayList<>();

        for(int a = 0; a < statusRiwayat.length; a++){
            RiwayarStokBahanModel model = new RiwayarStokBahanModel();
            model.setStatus(statusRiwayat[a]);
            model.setTanggal_riwayat(tanggalRiwayat[a]);
            model.setKuantitas_riwayat(kuantitasRiwayat[a]);
            riwayarStokBahanModels.add(model);
        }
        return riwayarStokBahanModels;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        finish();
//        return super.onSupportNavigateUp();
//    }

}
