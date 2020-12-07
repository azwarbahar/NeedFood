package com.technest.needfood.admin.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technest.needfood.R;
import com.technest.needfood.admin.dompet.DompetFragment;
import com.technest.needfood.admin.inventori.InventoriFragment;
import com.technest.needfood.admin.pesanan.PesananFragment;
import com.technest.needfood.admin.pesanan.item.ItemPesananActivity;
import com.technest.needfood.admin.stok.StokFragment;

public class HomeFragment extends Fragment {

    View v;

    private CardView cv_pesanan;
    private CardView cv_stok;
    private CardView cv_inventory;
    private CardView cv_keuangan;

    private TextView tv_nama;

    private RelativeLayout cv_pesan_terbaru;

    private String id;
    private String role;
    private String nama;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        tv_nama = v.findViewById(R.id.tv_nama);

        loadBundle();
        // aksi tombol pesan langsung
        cv_pesan_terbaru = v.findViewById(R.id.cv_pesan_terbaru);
        cv_pesan_terbaru.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItemPesananActivity.class);
                intent.putExtra("extra_data", "Pesanan Terbaru");
                startActivity(intent);
            }

        });

        // aksi tombol pesanan
        cv_pesanan = v.findViewById(R.id.cv_pesanan);
        cv_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment pesananFragment = new PesananFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment, pesananFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        // aksi tombol stok
        cv_stok = v.findViewById(R.id.cv_stok);
        cv_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment stokFragment = new StokFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment, stokFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        // aksi tombol inventory
        cv_inventory = v.findViewById(R.id.cv_inventory);
        cv_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment inventoryFragment = new InventoriFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment, inventoryFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        // aksi tombol keuangan
        cv_keuangan = v.findViewById(R.id.cv_keuangan);
        cv_keuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment keuanganFragment = new DompetFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment, keuanganFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return v;
    }

    private void loadBundle() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        if (!bundle.isEmpty()) {
            id = bundle.getString("GET_ID");
            nama = bundle.getString("GET_NAME");
            username = bundle.getString("GET_USERNAME");

            tv_nama.setText(nama);
        }
    }
}
