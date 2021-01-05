package com.technest.needfood.admin.pesanan.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.technest.needfood.R;

import java.util.ArrayList;

public class ItemPesananActivity extends AppCompatActivity {


    private String[] kodepesanan;
    private String[] nama;
    private String[] alamat;
    private String[] tanggal;
    private String[] jam;

    private TextView tv_title_halaman;

    private RecyclerView rv_item;
    private ArrayList<ItemPesananModel> itemPesananModels;

    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pesanan);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("extra_data");
            tv_title_halaman = findViewById(R.id.tv_title_halaman);
            tv_title_halaman.setText(title);
        }

        itemPesananModels = tambahData();

        rv_item = findViewById(R.id.rv_item);
        Adapter adapter = new Adapter(this,itemPesananModels);
        rv_item.setLayoutManager(new LinearLayoutManager(this));
        rv_item.setAdapter(adapter);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private ArrayList<ItemPesananModel> tambahData(){

        kodepesanan = getResources().getStringArray(R.array.kode_pesanan);
        nama = getResources().getStringArray(R.array.nama_pesanan);
        alamat = getResources().getStringArray(R.array.alamat_pesanan);
        tanggal = getResources().getStringArray(R.array.tanggal_pesanan);
        jam = getResources().getStringArray(R.array.jam_pesanan);

        ArrayList<ItemPesananModel> listnyakota = new ArrayList<>();

        for(int a = 0; a < kodepesanan.length; a++){
            ItemPesananModel stokModel = new ItemPesananModel();
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