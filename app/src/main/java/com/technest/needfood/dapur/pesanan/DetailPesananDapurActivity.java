package com.technest.needfood.dapur.pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.technest.needfood.R;

public class DetailPesananDapurActivity extends AppCompatActivity {

    private RelativeLayout rl_peralatan_pesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_dapur);

        rl_peralatan_pesanan = findViewById(R.id.rl_peralatan_pesanan);
        rl_peralatan_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}