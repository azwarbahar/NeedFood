package com.technest.needfood.admin.pesanan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.technest.needfood.R;
import com.technest.needfood.admin.pesanan.item.ItemPesananActivity;
import com.technest.needfood.admin.stok.item_stok.ItemStokActivity;

public class PesananFragment extends Fragment {

    View v;

    private CardView cv_pesanan_pending;
    private CardView cv_pesanan_lunsa;
    private CardView cv_pesanan_dapur;
    private CardView cv_pesanan_delivery;
    private CardView cv_pesanan_sampai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pesanan, container, false);

        // aksi tombol pesanan pending
        cv_pesanan_pending = v.findViewById(R.id.cv_pesanan_pending);
        cv_pesanan_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ItemPesananActivity.class);
                intent.putExtra("extra_data", "Pesanan Pending");
                getActivity().startActivity(intent);
            }
        });

        // aksi tombol pesanan lunas
        cv_pesanan_lunsa = v.findViewById(R.id.cv_pesanan_lunsa);
        cv_pesanan_lunsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ItemPesananActivity.class);
                intent.putExtra("extra_data", "Pesanan Lunas");
                getActivity().startActivity(intent);
            }
        });

        // aksi tombol proses dapur
        cv_pesanan_dapur = v.findViewById(R.id.cv_pesanan_dapur);
        cv_pesanan_dapur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ItemPesananActivity.class);
                intent.putExtra("extra_data", "Proses Dapur");
                getActivity().startActivity(intent);
            }
        });

        // aksi tombol pesanan delivery
        cv_pesanan_delivery = v.findViewById(R.id.cv_pesanan_delivery);
        cv_pesanan_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ItemPesananActivity.class);
                intent.putExtra("extra_data", "Pesanan Delivery");
                getActivity().startActivity(intent);
            }
        });

        // aksi tombol pesanan sampau
        cv_pesanan_sampai = v.findViewById(R.id.cv_pesanan_sampai);
        cv_pesanan_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ItemPesananActivity.class);
                intent.putExtra("extra_data", "Pesanan Sampai");
                getActivity().startActivity(intent);

            }
        });

        return  v;
    }

}
