package com.technest.needfood.dapur.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technest.needfood.R;
import com.technest.needfood.admin.pesanan.PesananFragment;
import com.technest.needfood.dapur.home.item.ItemPesananActivity;
import com.technest.needfood.dapur.inventori.InventoriDapurFragment;
import com.technest.needfood.dapur.pesanan.PesananDapurFragment;
import com.technest.needfood.dapur.stok.StokDapurFragment;

public class HomeDapurFragment extends Fragment {

    View v;

    private CardView cv_pesanan;
    private CardView cv_stok;
    private CardView cv_inventory;
    private RelativeLayout rl_pesan_terbaru;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home_dapur, container, false);

        rl_pesan_terbaru = v.findViewById(R.id.rl_pesan_terbaru);
        rl_pesan_terbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItemPesananActivity.class);
                intent.putExtra("extra_data", "Pesanan Terbaru");
                startActivity(intent);
            }
        });


        cv_inventory = v.findViewById(R.id.cv_inventory);
        cv_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment inventoriDapurFragment = new InventoriDapurFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment, inventoriDapurFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cv_pesanan = v.findViewById(R.id.cv_pesanan);
        cv_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment pesananDapurFragment = new PesananDapurFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment, pesananDapurFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cv_stok = v.findViewById(R.id.cv_stok);
        cv_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment stokDapurFragment = new StokDapurFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment, stokDapurFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return  v;
    }

}
