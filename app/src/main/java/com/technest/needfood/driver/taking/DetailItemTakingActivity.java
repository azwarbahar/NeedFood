package com.technest.needfood.driver.taking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technest.needfood.R;

public class DetailItemTakingActivity extends AppCompatActivity {

    private RelativeLayout container_kamera;
    private RelativeLayout rl_btn_sampai;
    private CardView cv_close_detail_pesanan;
    private ImageView img_call;
    private ImageView img_photo;
    private ImageView img_list_alat;

    private TextView tv_tutup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_taking);

        container_kamera = findViewById(R.id.container_kamera);
        container_kamera.setVisibility(View.GONE);


        rl_btn_sampai = findViewById(R.id.rl_btn_bukti);
        rl_btn_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailItemTakingActivity.this, "Pesanan Sampai di Tujuan", Toast.LENGTH_SHORT).show();
            }
        });



        cv_close_detail_pesanan = findViewById(R.id.cv_close_detail_pesanan);
        cv_close_detail_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_call = findViewById(R.id.img_accept);
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:085123456789"));
                startActivity(intent);

            }
        });

        img_list_alat = findViewById(R.id.img_call);
        img_list_alat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailItemTakingActivity.this, "Klik", Toast.LENGTH_SHORT).show();
            }
        });

        img_photo = findViewById(R.id.img_refuse);
        img_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container_kamera.getVisibility()== View.GONE){
                    container_kamera.setVisibility(View.VISIBLE);
                } else {
                    container_kamera.setVisibility(View.GONE);
                }
            }
        });


        tv_tutup = findViewById(R.id.tv_tutup);
        tv_tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container_kamera.setVisibility(View.GONE);
            }
        });


    }
}