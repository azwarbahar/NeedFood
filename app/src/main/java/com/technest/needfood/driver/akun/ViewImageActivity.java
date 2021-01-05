package com.technest.needfood.driver.akun;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.technest.needfood.R;
import com.technest.needfood.utils.Constanta;

public class ViewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        PhotoView photoView = (PhotoView) findViewById(R.id.img_zoom);
        ImageView img_close = findViewById(R.id.img_close);
        Bundle bundle = getIntent().getExtras();
        String foto = bundle.getString("foto");
        Glide.with(this)
                .load(Constanta.url_foto_foto_driver + foto)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(photoView);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}