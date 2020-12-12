package com.technest.needfood.admin.pesanan.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.technest.needfood.R;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterAdditional;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterPaket;
import com.technest.needfood.models.pesanan.Additional;
import com.technest.needfood.models.pesanan.Paket;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.Transaksi;
import com.technest.needfood.network.ConnectionDetector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailPesananActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    public static final String EXTRA_DATA = "extra_data";
    private RelativeLayout continer_map;
    private RelativeLayout rl_btn_bukti;
    private ImageView img_chevron;
    private CardView cv_transaksi;
    private CardView cv_transaksi2;
    private CardView cv_close_panel;

    private TextView tv_alamat;
    private TextView tv_kode_pesanan;
    private TextView tv_nama;
    private TextView tv_telpon;
    private TextView tv_tanggal_antar;
    private TextView tv_waktu;
    private TextView tv_catatatn;

    // PanelUp
    private TextView tv_total_harga;
    private TextView tv_harga_lainnya;
    private TextView tv_biaya_pengiriman;
    private TextView tv_harga_additional;
    private TextView tv_harga_paket;
    private TextView tv_kode_pesanan2;
    private TextView tv_nama_pelanggan2;

    private CardView cv_close_detail_pesanan;
    private ImageView img_close;

    private SlidingUpPanelLayout sliding_layout;
    private RelativeLayout containerImageZoom;
    private RelativeLayout rl_transaksi;
    private RelativeLayout rl_alat;
    private RelativeLayout rl_bahan;
    private RelativeLayout rl_driver;

    private RecyclerView rv_paket;
    private RecyclerView rv_additional;

    private TextView tv_kosong_paket;
    private TextView tv_kosong_additional;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        Context context = DetailPesananActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        cv_close_detail_pesanan = findViewById(R.id.cv_close_detail_pesanan);
        cv_close_detail_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        containerImageZoom = findViewById(R.id.containerImageZoom);
        containerImageZoom.setVisibility(View.GONE);

        tv_kosong_paket = findViewById(R.id.tv_kosong_paket);
        tv_kosong_additional = findViewById(R.id.tv_kosong_additional);
        tv_kosong_additional.setVisibility(View.GONE);
        tv_kosong_paket.setVisibility(View.GONE);

        rv_additional = findViewById(R.id.rv_additional);
        rv_paket = findViewById(R.id.rv_paket);
        cv_close_panel = findViewById(R.id.cv_close_panel);
        cv_transaksi2 = findViewById(R.id.cv_transaksi2);
        cv_transaksi = findViewById(R.id.cv_transaksi);
        sliding_layout = findViewById(R.id.sliding_layout);
        rl_btn_bukti = findViewById(R.id.rl_btn_bukti);
        tv_alamat = findViewById(R.id.tv_alamat);
        tv_kode_pesanan = findViewById(R.id.tv_kode_pesanan);
        tv_nama = findViewById(R.id.tv_nama);
        tv_telpon = findViewById(R.id.tv_telpon);
        tv_tanggal_antar = findViewById(R.id.tv_tanggal_antar);
        tv_waktu = findViewById(R.id.tv_waktu);
        tv_catatatn = findViewById(R.id.tv_catatatn);
        rl_alat = findViewById(R.id.rl_alat);
        rl_alat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Klik Alat", Toast.LENGTH_SHORT).show();
            }
        });

        rl_bahan = findViewById(R.id.rl_bahan);
        rl_bahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Klik Bahan", Toast.LENGTH_SHORT).show();
            }
        });
        rl_transaksi = findViewById(R.id.rl_transaksi);
        rl_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPanel();
            }
        });

        rl_driver = findViewById(R.id.rl_driver);
        rl_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Klik Driver", Toast.LENGTH_SHORT).show();
            }
        });


        continer_map = findViewById(R.id.continer_map);
        img_chevron = findViewById(R.id.img_chevron);
        tv_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationMaps();
            }
        });
        img_chevron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationMaps();
            }
        });

        img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerImageZoom.setVisibility(View.GONE);
                if (containerImageZoom.getVisibility() == View.GONE) {
                    cv_close_panel.setVisibility(View.VISIBLE);
                    cv_transaksi2.setVisibility(View.VISIBLE);
                    cv_transaksi.setVisibility(View.VISIBLE);
                }
            }
        });
        cv_transaksi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                containerImageZoom.setVisibility(View.VISIBLE);
                if (containerImageZoom.getVisibility() == View.VISIBLE) {
                    cv_close_panel.setVisibility(View.GONE);
                    cv_transaksi2.setVisibility(View.GONE);
                    cv_transaksi.setVisibility(View.GONE);
                }
            }
        });

        Pesanan pesanan = getIntent().getParcelableExtra(EXTRA_DATA);
        assert pesanan != null;
        tv_kode_pesanan.setText(pesanan.getKd_pemesanan());
        tv_nama.setText(pesanan.getNama());
        tv_telpon.setText(pesanan.getNo_telepon());
        tv_alamat.setText(pesanan.getDeskripsi_lokasi());
        String tgl = getDate(pesanan.getTanggal_antar());
        tv_tanggal_antar.setText(tgl);
        tv_waktu.setText(pesanan.getWaktu_antar());
        tv_catatatn.setText(pesanan.getCatatan());
        setDataPanelUp(pesanan.getTransaksi(), pesanan.getNama(), pesanan.getKd_pemesanan());
        setDataPaker((ArrayList<Paket>) pesanan.getPaket());
        setDataAddiyional((ArrayList<Additional>) pesanan.getAdditional());
//        setMapLokasi(Long.valueOf(pesanan.getLatitude()), Long.valueOf(pesanan.getLogitude()), pesanan.getDeskripsi_lokasi());
//        setMapLokasi(pesanan.getDeskripsi_lokasi());

    }


    private void setMapLokasi(String alamat) {
        LatLng latLngzoom = new LatLng(-5.16961, 119.438505);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngzoom, 13));
        map.addMarker(new MarkerOptions().title(alamat)
                .icon(bitmapDescriptor(getApplicationContext(), R.drawable.ic_icon_lokasi_tujuan))
                .position(new LatLng(-5.16961, 119.438505)));
    }

    private BitmapDescriptor bitmapDescriptor(Context context, int vactorResid) {

        int height = 70;
        int width = 50;
        Drawable vectorDrawble = ContextCompat.getDrawable(context, vactorResid);
        vectorDrawble.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawble.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void setDataAddiyional(ArrayList<Additional> additional) {
        if (additional.isEmpty()) {
            tv_kosong_additional.setVisibility(View.VISIBLE);
        } else {
            AdapterAdditional adapterAdditional = new AdapterAdditional(DetailPesananActivity.this, additional);
            rv_additional.setLayoutManager(new LinearLayoutManager(DetailPesananActivity.this));
            rv_additional.setAdapter(adapterAdditional);
        }
    }

    private void setDataPaker(ArrayList<Paket> paket) {
        if (paket.isEmpty()) {
            tv_kosong_paket.setVisibility(View.VISIBLE);
        } else {
            AdapterPaket adapterPaket = new AdapterPaket(DetailPesananActivity.this, paket);
            rv_paket.setLayoutManager(new LinearLayoutManager(DetailPesananActivity.this));
            rv_paket.setAdapter(adapterPaket);
        }

    }

    private void setDataPanelUp(Transaksi transaksi, String nama, String kd_pemesanan) {

        tv_total_harga = findViewById(R.id.tv_total_harga);
        tv_harga_lainnya = findViewById(R.id.tv_harga_lainnya);
        tv_biaya_pengiriman = findViewById(R.id.tv_biaya_pengiriman);
        tv_harga_additional = findViewById(R.id.tv_harga_additional);
        tv_harga_paket = findViewById(R.id.tv_harga_paket);
        tv_kode_pesanan2 = findViewById(R.id.tv_kode_pesanan2);
        tv_nama_pelanggan2 = findViewById(R.id.tv_nama_pelanggan2);

        tv_nama_pelanggan2.setText(nama);
        tv_kode_pesanan2.setText(kd_pemesanan);
        tv_harga_paket.setText(String.valueOf(transaksi.getHargaPaket()));
        tv_harga_additional.setText(String.valueOf(transaksi.getHargaAdditional()));
        tv_biaya_pengiriman.setText(String.valueOf(transaksi.getBiayaPengiriman()));
        tv_harga_lainnya.setText(String.valueOf(transaksi.getHargaLainnya()));
        tv_total_harga.setText(String.valueOf(transaksi.getTotalHarga()));

    }

    private void showPanel() {
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        }

    }


    private String getDate(String time) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = dateParser.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormatter.format(date);
    }

    private void actionNotConnection() {
        Snackbar.make(findViewById(android.R.id.content), "Koneksi Tidak Ada!", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                    }
                })
                .show();
    }

    private void animationMaps() {
        if (continer_map.getVisibility() == View.VISIBLE) {
            continer_map.animate()
                    .translationY(0)
                    .alpha(0.0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            continer_map.setVisibility(View.GONE);
                        }
                    });
        } else {
            continer_map.setVisibility(View.VISIBLE);
            continer_map.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setDuration(900)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {

        if (containerImageZoom.getVisibility() == View.VISIBLE) {
            cv_close_panel.setVisibility(View.VISIBLE);
            cv_transaksi2.setVisibility(View.VISIBLE);
            cv_transaksi.setVisibility(View.VISIBLE);
            containerImageZoom.setVisibility(View.GONE);
        } else if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            finish();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().title("Lokasi Pesanan")
                .icon(bitmapDescriptor(getApplicationContext(),
                        R.drawable.ic_icon_lokasi_tujuan))
                .position(new LatLng(-5.169610, 119.438505)));
        LatLng latLngzoom = new LatLng(-5.169610, 119.438505);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngzoom, 13));
    }
}