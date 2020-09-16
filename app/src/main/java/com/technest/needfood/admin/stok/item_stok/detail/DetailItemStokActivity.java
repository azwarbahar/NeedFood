package com.technest.needfood.admin.stok.item_stok.detail;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.model.ItemStokBahanModel;

public class DetailItemStokActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";

    private ImageView item_stok_toolbar_image;
    private Toolbar item_stok_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stok);

        item_stok_toolbar_image = findViewById(R.id.item_stok_toolbar_image);
        item_stok_toolbar = findViewById(R.id.item_stok_toolbar);

        // support toolbar
//        setSupportActionBar(item_stok_toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ItemStokBahanModel itemStokBahanModel = getIntent().getParcelableExtra(EXTRA_DATA);

        item_stok_toolbar_image.setImageResource(itemStokBahanModel.getImage_item_stok());
        item_stok_toolbar.setTitle(itemStokBahanModel.getTitle_item_stok());


    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        finish();
//        return super.onSupportNavigateUp();
//    }

}
