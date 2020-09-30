package com.technest.needfood.admin.stok.item_stok;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.adapter.ItemStokBahanAdapter;
import com.technest.needfood.admin.stok.item_stok.model.ItemStokBahanModel;
import com.technest.needfood.admin.stok.model.KategoriStokModel;

import java.util.ArrayList;

public class ItemStokActivity extends AppCompatActivity {



    public static final String EXTRA_DATA = "extra_data";

    private ImageView item_stok_toolbar_image;
    private Toolbar item_stok_toolbar;

    private String[] itemStokDataTitle;
    private String[] itemStokDataKuantitas;
    private TypedArray itemStokDataImage;

    private RecyclerView rv_item_stok;
    private ArrayList<ItemStokBahanModel> itemStokBahanModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stok2);

        rv_item_stok = findViewById(R.id.rv_item_stok);

        item_stok_toolbar_image = findViewById(R.id.item_stok_toolbar_image);
        item_stok_toolbar = findViewById(R.id.item_stok_toolbar);

        KategoriStokModel kategoriStokModel = getIntent().getParcelableExtra(EXTRA_DATA);

        item_stok_toolbar_image.setImageResource(kategoriStokModel.getImage_kategori_stok());
        item_stok_toolbar.setTitle(kategoriStokModel.getTitle_kategori_stok());

        showRecyclerview();


    }

    private void showRecyclerview() {

        itemStokBahanModels = tambahItemItemStok();

        ItemStokBahanAdapter itemStokBahanAdapter = new ItemStokBahanAdapter(this, itemStokBahanModels);
        rv_item_stok.setLayoutManager(new LinearLayoutManager(this));
        rv_item_stok.setAdapter(itemStokBahanAdapter);

    }

    private ArrayList<ItemStokBahanModel> tambahItemItemStok(){

        itemStokDataTitle = getResources().getStringArray(R.array.title_item_stok);
        itemStokDataKuantitas = getResources().getStringArray(R.array.kuantitas_item_stok);
        itemStokDataImage = getResources().obtainTypedArray(R.array.image_item_stok);

        ArrayList<ItemStokBahanModel> itemStokBahanModels = new ArrayList<>();

        for(int a = 0; a < itemStokDataTitle.length; a++){
            ItemStokBahanModel stokModel = new ItemStokBahanModel();
            stokModel.setTitle_item_stok(itemStokDataTitle[a]);
            stokModel.setKuantitas_item_stok(itemStokDataKuantitas[a]);
            stokModel.setImage_item_stok(itemStokDataImage.getResourceId(a, -1));
            itemStokBahanModels.add(stokModel);
        }
        return itemStokBahanModels;
    }

}
