package com.technest.needfood.driver.delivery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.technest.needfood.R;

public class DetailItemDeliveryActivity extends AppCompatActivity {

    private RelativeLayout container_list_alat;
    private RelativeLayout rl_btn_sampai;

    private CardView cv_close_detail_pesanan;

    private ImageView img_call;
    private ImageView img_list_alat;
    private ImageView img_close_list_alat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_delivery);

        container_list_alat = findViewById(R.id.container_list_alat);
        container_list_alat.setVisibility(View.GONE);

        rl_btn_sampai = findViewById(R.id.rl_btn_sampai);
        rl_btn_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailItemDeliveryActivity.this, "Pesanan Sampai di Tujuan", Toast.LENGTH_SHORT).show();
            }
        });

        cv_close_detail_pesanan = findViewById(R.id.cv_close_detail_pesanan);
        cv_close_detail_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_call = findViewById(R.id.img_call);
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:085123456789"));
                startActivity(intent);

            }
        });

        img_list_alat = findViewById(R.id.img_list_alat);
        img_list_alat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container_list_alat.getVisibility()== View.GONE){
                    container_list_alat.setVisibility(View.VISIBLE);
                } else {
                    container_list_alat.setVisibility(View.GONE);
                }
            }
        });

        img_close_list_alat = findViewById(R.id.img_close_list_alat);
        img_close_list_alat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container_list_alat.setVisibility(View.GONE);
            }
        });




    }
}
