package com.technest.needfood.admin.stok;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.R;
import com.technest.needfood.admin.stok.adapter.KategoriStokAdapter;
import com.technest.needfood.admin.stok.model.KategoriStokModel;

import java.util.ArrayList;

public class StokFragment extends Fragment {

    private View v;

    private String[] kategoriStokDataTitle;
    private TypedArray kategoriStokDataImage;

    private RecyclerView rv_kategori_stok;
    private ArrayList<KategoriStokModel> kategoriStokModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_stok, container, false);

        rv_kategori_stok = v.findViewById(R.id.rv_kategori_stok);

        KategoriStokAdapter kotaAdapter = new KategoriStokAdapter(getContext(), kategoriStokModels);
        rv_kategori_stok.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_kategori_stok.setAdapter(kotaAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        kategoriStokModels = tambahItemKategoriStok();

    }

    private ArrayList<KategoriStokModel> tambahItemKategoriStok() {

        kategoriStokDataTitle = getResources().getStringArray(R.array.title_kategori_stok);
        kategoriStokDataImage = getResources().obtainTypedArray(R.array.image_kategori_stok);

        ArrayList<KategoriStokModel> listnyakota = new ArrayList<>();

        for (int a = 0; a < kategoriStokDataTitle.length; a++) {
            KategoriStokModel stokModel = new KategoriStokModel();
            stokModel.setTitle_kategori_stok(kategoriStokDataTitle[a]);
            stokModel.setImage_kategori_stok(kategoriStokDataImage.getResourceId(a, -1));
            listnyakota.add(stokModel);
        }
        return listnyakota;
    }

}
