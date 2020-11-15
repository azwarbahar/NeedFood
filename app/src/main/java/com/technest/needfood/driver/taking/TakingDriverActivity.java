package com.technest.needfood.driver.taking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.technest.needfood.R;

public class TakingDriverActivity extends AppCompatActivity {

    private ImageView btn_close;
    private ImageView img_call;
    private CardView cv_detail_pesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_driver);

        cv_detail_pesanan = findViewById(R.id.cv_detail_pesanan);
        cv_detail_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(TakingDriverActivity.this, DetailItemTakingActivity.class));

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

        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}