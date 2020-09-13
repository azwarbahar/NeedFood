package com.technest.needfood.admin.pesanan;

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
                Toast.makeText(getActivity(), "Klik Buttom Pesanan Pending!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // aksi tombol pesanan lunas
        cv_pesanan_lunsa = v.findViewById(R.id.cv_pesanan_lunsa);
        cv_pesanan_lunsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Klik Buttom Pesanan Lunas!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // aksi tombol proses dapur
        cv_pesanan_dapur = v.findViewById(R.id.cv_pesanan_dapur);
        cv_pesanan_dapur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Klik Buttom Proses Dapur!",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // aksi tombol pesanan delivery
        cv_pesanan_delivery = v.findViewById(R.id.cv_pesanan_delivery);
        cv_pesanan_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Klik Buttom Pesanan Delivery!",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // aksi tombol pesanan sampau
        cv_pesanan_sampai = v.findViewById(R.id.cv_pesanan_sampai);
        cv_pesanan_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Klik Buttom Pesanan Sampai!",
                        Toast.LENGTH_SHORT).show();

            }
        });

        return  v;
    }

}
