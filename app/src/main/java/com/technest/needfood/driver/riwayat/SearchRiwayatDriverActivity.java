package com.technest.needfood.driver.riwayat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.technest.needfood.R;

public class SearchRiwayatDriverActivity extends AppCompatActivity {

    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_riwayat_driver);

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SearchRiwayatDriverActivity.this,DetailRwayatDriverActivity.class));
                finish();

            }
        });

    }
}
