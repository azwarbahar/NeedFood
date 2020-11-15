package com.technest.needfood.admin.stok.item_stok.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.detail.adapter.RiwayarStokBahanAdapter;
import com.technest.needfood.admin.stok.item_stok.detail.model.RiwayarStokBahanModel;
import com.technest.needfood.admin.stok.item_stok.model.ItemStokBahanModel;
import com.technest.needfood.models.bahan.Bahan;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

public class DetailItemStokActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";

    private ImageView item_stok_toolbar_image;
    private Toolbar item_stok_toolbar;
    private TextView tv_satuan;
    private TextView tv_kuantitas_stok;

    // image zoom
    private TextView lihat_gambar;
    private RelativeLayout containerImageZoom;
    private ImageView img_zoom;
    private ImageView img_close;

    private String[] statusRiwayat;
    private String[] tanggalRiwayat;
    private String[] kuantitasRiwayat;

    private RecyclerView rv_riwayat_item_stok;
    private ArrayList<RiwayarStokBahanModel> stokBahanModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stok);

        img_zoom = findViewById(R.id.img_zoom);
        img_close = findViewById(R.id.img_close);
        containerImageZoom = findViewById(R.id.containerImageZoom);
        containerImageZoom.setVisibility(View.GONE);
        lihat_gambar = findViewById(R.id.lihat_gambar);
        lihat_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerImageZoom.setVisibility(View.VISIBLE);
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerImageZoom.setVisibility(View.GONE);
            }
        });

        rv_riwayat_item_stok = findViewById(R.id.rv_riwayat_item_stok);
        tv_kuantitas_stok = findViewById(R.id.tv_kuantitas_stok);
        tv_satuan = findViewById(R.id.tv_satuan);

        item_stok_toolbar_image = findViewById(R.id.item_stok_toolbar_image);
        item_stok_toolbar = findViewById(R.id.item_stok_toolbar);

        // support toolbar
//        setSupportActionBar(item_stok_toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bahan bahan = getIntent().getParcelableExtra(EXTRA_DATA);

        Glide.with(this)
                .load(Constanta.url_foto_bahan+bahan.getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(item_stok_toolbar_image);

        Glide.with(this)
                .load(Constanta.url_foto_bahan+bahan.getFoto())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(img_zoom);
        item_stok_toolbar.setTitle(bahan.getNama());
        tv_kuantitas_stok.setText(String.valueOf(bahan.getJumlahBahan()));
        tv_satuan.setText(String.valueOf(bahan.getSatuan()));

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

    @Override
    public void onBackPressed() {
        if (containerImageZoom.getVisibility()==View.VISIBLE){
            containerImageZoom.setVisibility(View.GONE);
        } else {
            finish();
        }
    }
}
