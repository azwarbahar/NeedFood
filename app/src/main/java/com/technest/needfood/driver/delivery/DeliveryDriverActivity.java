package com.technest.needfood.driver.delivery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.technest.needfood.R;
import com.technest.needfood.admin.pesanan.detail.DetailPesananActivity;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.network.ConnectionDetector;

public class DeliveryDriverActivity extends AppCompatActivity {

    GoogleMap map;
    LatLng latLngzoom;
    public static final String EXTRA_DATA = "extra_data";

    private ImageView btn_close;
    private ImageView img_call;
    private CardView cv_detail_pesanan;

    private TextView tv_lokasi_tujuan;
    private TextView tv_nama;
    private TextView tv_kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_driver);


        Context context = DeliveryDriverActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        tv_kode = findViewById(R.id.tv_kode);
        tv_nama = findViewById(R.id.tv_nama);
        tv_lokasi_tujuan = findViewById(R.id.tv_lokasi_tujuan);

        cv_detail_pesanan = findViewById(R.id.cv_detail_pesanan);
        cv_detail_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DeliveryDriverActivity.this, DetailItemDeliveryActivity.class));

            }
        });


        img_call = findViewById(R.id.img_accept);

        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Pesanan pesanan = getIntent().getParcelableExtra(EXTRA_DATA);
        assert pesanan != null;
        tv_lokasi_tujuan.setText(pesanan.getDeskripsi_lokasi());
        tv_nama.setText(": "+pesanan.getNama());
        tv_kode.setText(": "+pesanan.getKd_pemesanan());
        String tlpon = pesanan.getNo_telepon();
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tlpon));
                startActivity(intent);
            }
        });


    }
}
