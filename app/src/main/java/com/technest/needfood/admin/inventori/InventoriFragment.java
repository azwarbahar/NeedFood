package com.technest.needfood.admin.inventori;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.admin.stok.item_stok.adapter.ItemStokBahanAdapter;
import com.technest.needfood.admin.stok.item_stok.model.ItemStokBahanModel;

import java.util.ArrayList;

public class InventoriFragment extends Fragment {

    View v;

    private String[] itemStokDataTitle;
    private String[] itemStokDataKuantitas;
    private TypedArray itemStokDataImage;

    private RecyclerView rv_alat_dapur;

    private ArrayList<ItemStokBahanModel> itemStokBahanModels;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_inventori, container, false);
        rv_alat_dapur = v.findViewById(R.id.rv_alat_dapur);
//        showRecyclerview();
        return  v;
    }

//    private void showRecyclerview() {
//
//        itemStokBahanModels = tambahItemItemStok();
//
//        ItemStokBahanAdapter itemStokBahanAdapter = new ItemStokBahanAdapter(getActivity(), itemStokBahanModels);
//        rv_alat_dapur.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rv_alat_dapur.setAdapter(itemStokBahanAdapter);
//
//    }
//
//    private ArrayList<ItemStokBahanModel> tambahItemItemStok(){
//
//        itemStokDataTitle = getResources().getStringArray(R.array.title_item_stok_dapur);
//        itemStokDataKuantitas = getResources().getStringArray(R.array.kuantitas_item_stok_dapur);
//        itemStokDataImage = getResources().obtainTypedArray(R.array.image_item_stok_dapur);
//
//        ArrayList<ItemStokBahanModel> itemStokBahanModels = new ArrayList<>();
//
//        for(int a = 0; a < itemStokDataTitle.length; a++){
//            ItemStokBahanModel stokModel = new ItemStokBahanModel();
//            stokModel.setTitle_item_stok(itemStokDataTitle[a]);
//            stokModel.setKuantitas_item_stok(itemStokDataKuantitas[a]);
//            stokModel.setImage_item_stok(itemStokDataImage.getResourceId(a, -1));
//            itemStokBahanModels.add(stokModel);
//        }
//        return itemStokBahanModels;
//    }


}
