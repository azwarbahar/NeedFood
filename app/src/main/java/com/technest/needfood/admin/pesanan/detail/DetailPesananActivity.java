package com.technest.needfood.admin.pesanan.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterAdditional;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterBahanPaket;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterKategoriAlat;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterPaket;
import com.technest.needfood.models.pesanan.Additional;
import com.technest.needfood.models.pesanan.AlatPesanan;
import com.technest.needfood.models.pesanan.BahanPesanan;
import com.technest.needfood.models.pesanan.Paket;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesananID;
import com.technest.needfood.models.pesanan.Transaksi;
import com.technest.needfood.models.user.Driver;
import com.technest.needfood.models.user.ResponDriver;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;
import com.technest.needfood.utils.Constanta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPesananActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    LatLng latLngzoom;
    public static final String EXTRA_DATA = "extra_data";
    private RelativeLayout continer_map;
    private RelativeLayout rl_btn_bukti;
    private ImageView img_chevron;
    private CardView cv_transaksi;
    private CardView cv_transaksi2;
    private CardView cv_close_panel;

    private TextView tv_status_pesanan;
    private TextView tv_alamat;
    private TextView tv_kode_pesanan;
    private TextView tv_nama;
    private TextView tv_telpon;
    private TextView tv_tanggal_antar;
    private TextView tv_waktu;
    private TextView tv_catatatn;
    private TextView tv_tanggal;
    private TextView tv_cod;

    // PanelUp
    private TextView tv_total_harga;
    private TextView tv_harga_lainnya;
    private TextView tv_biaya_pengiriman;
    private TextView tv_harga_additional;
    private TextView tv_harga_paket;
    private TextView tv_kode_pesanan2;
    private TextView tv_nama_pelanggan2;
    private ImageView img_bukti_pembayaran;
    private PhotoView img_zoom;

    private TextView tv_kode_pesanan_bahan;
    private TextView tv_kode_pesanan_bahan3;

    private CardView cv_close_detail_pesanan;
    private ImageView img_close;

    private SlidingUpPanelLayout sliding_layout;
    private RelativeLayout containerImageZoom;
    private RelativeLayout rl_transaksi;
    private RelativeLayout rl_alat;
    private RelativeLayout rl_bahan;
    private RelativeLayout rl_driver;

    private RelativeLayout container_transaksi;
    private RelativeLayout container_alat;
    private RelativeLayout container_bahan;
    private RelativeLayout container_driver;

    private RecyclerView rv_paket;
    private RecyclerView rv_additional;
    private RecyclerView rv_sliding_paket;
    private RecyclerView rv_sliding_continer_alat;

    private TextView tv_kosong_paket;
    private TextView tv_kosong_additional;
    private TextView tv_kosong_sliding_paket;
    private TextView tv_kosong_sliding_alat;

    private Pesanan pesanan_parcelable;
    private Pesanan pesanan_bundle;

    private ImageButton img_foto_driver_antar;
    private TextView tv_nama_driver_antar;
    private TextView status_driver_antar;
    private TextView tv_kosong_antar;
    private ImageButton img_foto_driver_jemput;
    private TextView tv_nama_driver_jemput;
    private TextView status_driver_jemput;
    private TextView tv_kosong_jemput;

    private Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        Context context = DetailPesananActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
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

        img_foto_driver_antar = findViewById(R.id.img_foto_driver_antar);
        tv_nama_driver_antar = findViewById(R.id.tv_nama_driver_antar);
        status_driver_antar = findViewById(R.id.status_driver_antar);
        tv_kosong_antar = findViewById(R.id.tv_kosong_antar);
        img_foto_driver_jemput = findViewById(R.id.img_foto_driver_jemput);
        tv_nama_driver_jemput = findViewById(R.id.tv_nama_driver_jemput);
        status_driver_jemput = findViewById(R.id.status_driver_jemput);
        tv_kosong_jemput = findViewById(R.id.tv_kosong_jemput);

        tv_kosong_additional = findViewById(R.id.tv_kosong_additional);
        tv_kosong_additional.setVisibility(View.GONE);
        tv_kosong_paket = findViewById(R.id.tv_kosong_paket);
        tv_kosong_paket.setVisibility(View.GONE);
        tv_kosong_sliding_paket = findViewById(R.id.tv_kosong_sliding_paket);
        tv_kosong_sliding_paket.setVisibility(View.GONE);
        tv_kosong_sliding_alat = findViewById(R.id.tv_kosong_sliding_alat);
        tv_kosong_sliding_alat.setVisibility(View.GONE);

        // container pannel sliding
        container_driver = findViewById(R.id.container_driver);

        container_bahan = findViewById(R.id.container_bahan);
        tv_kode_pesanan_bahan = findViewById(R.id.tv_kode_pesanan_bahan);
        rv_sliding_paket = findViewById(R.id.rv_sliding_paket);

        container_alat = findViewById(R.id.container_alat);
        tv_kode_pesanan_bahan3 = findViewById(R.id.tv_kode_pesanan_bahan3);
        rv_sliding_continer_alat = findViewById(R.id.rv_sliding_continer_alat);
        container_transaksi = findViewById(R.id.container_transaksi);

        hiddenContainerSliding();

        rv_additional = findViewById(R.id.rv_additional);
        rv_paket = findViewById(R.id.rv_paket);
        cv_close_panel = findViewById(R.id.cv_close_panel);
        cv_transaksi2 = findViewById(R.id.cv_transaksi2);
        cv_transaksi = findViewById(R.id.cv_transaksi);
        sliding_layout = findViewById(R.id.sliding_layout);
        rl_btn_bukti = findViewById(R.id.rl_btn_bukti);
        tv_status_pesanan = findViewById(R.id.tv_status_pesanan);
        tv_alamat = findViewById(R.id.tv_alamat);
        tv_tanggal = findViewById(R.id.tv_tanggal);
        tv_cod = findViewById(R.id.tv_cod);
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
                hiddenContainerSliding();
                showPanel();
                container_alat.setVisibility(View.VISIBLE);
            }
        });
        rl_bahan = findViewById(R.id.rl_bahan);
        rl_bahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenContainerSliding();
                showPanel();
                container_bahan.setVisibility(View.VISIBLE);
            }
        });
        rl_transaksi = findViewById(R.id.rl_transaksi);
        rl_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenContainerSliding();
                showPanel();
                container_transaksi.setVisibility(View.VISIBLE);
            }
        });
        rl_driver = findViewById(R.id.rl_driver);
        rl_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenContainerSliding();
                showPanel();
                container_driver.setVisibility(View.VISIBLE);
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

        pesanan_parcelable = getIntent().getParcelableExtra(EXTRA_DATA);
        if (pesanan_parcelable != null) {
            setStatus(pesanan_parcelable.getStatus());
            String metode_bayar = pesanan_parcelable.getMetode_bayar();
            if (metode_bayar.equals("Transfer Bank")){
                tv_cod.setVisibility(View.GONE);
            } else {
                tv_cod.setVisibility(View.VISIBLE);
            }
            tv_kode_pesanan.setText(pesanan_parcelable.getKd_pemesanan());
            tv_kode_pesanan_bahan.setText(pesanan_parcelable.getKd_pemesanan());
            tv_kode_pesanan_bahan3.setText(pesanan_parcelable.getKd_pemesanan());
            tv_tanggal.setText(getDate(pesanan_parcelable.getCreated_at()));
            tv_nama.setText(pesanan_parcelable.getNama());
            tv_telpon.setText(pesanan_parcelable.getNo_telepon());
            tv_alamat.setText(pesanan_parcelable.getDeskripsi_lokasi());
            String tgl = getDate(pesanan_parcelable.getTanggal_antar());
            tv_tanggal_antar.setText(tgl);
            tv_waktu.setText(pesanan_parcelable.getWaktu_antar());
            tv_catatatn.setText(pesanan_parcelable.getCatatan());
            setDataPanelUp(pesanan_parcelable.getTransaksi(), pesanan_parcelable.getNama(), pesanan_parcelable.getKd_pemesanan(), pesanan_parcelable.getBukti_pembayaran());
            setDataPaker((ArrayList<Paket>) pesanan_parcelable.getPaket());
            setDataAddiyional((ArrayList<Additional>) pesanan_parcelable.getAdditional());
            setMapLokasi(pesanan_parcelable.getLatitude(), pesanan_parcelable.getLogitude());
            setDataAlatPesanan((ArrayList<AlatPesanan>) pesanan_parcelable.getAlat());
            setDataBahanPaket((ArrayList<BahanPesanan>) pesanan_parcelable.getBahan());
            String id_antar = String.valueOf(pesanan_parcelable.getPengantaran());
            if (id_antar == null) {
                tv_kosong_antar.setVisibility(View.VISIBLE);
                tv_nama_driver_antar.setVisibility(View.GONE);
                img_foto_driver_antar.setVisibility(View.GONE);
                status_driver_antar.setVisibility(View.GONE);
            } else {
                setDataDriverPengantaran(id_antar);
            }
            String id_jemput = String.valueOf(pesanan_parcelable.getPenjemputan());
            if (id_jemput == null) {
                tv_kosong_jemput.setVisibility(View.VISIBLE);
                tv_nama_driver_jemput.setVisibility(View.GONE);
                img_foto_driver_jemput.setVisibility(View.GONE);
                status_driver_jemput.setVisibility(View.GONE);
            } else {
                setDataDriverPenjemputan(id_jemput);
            }
        } else {
            Bundle bundle = getIntent().getExtras();
            String id_pesanan = bundle.getString("ID_PESANAN");

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponsePesananID> pesananIDCall = apiInterface.getPesananId("Bearer " + BuildConfig.TOKEN, id_pesanan);
            pesananIDCall.enqueue(new Callback<ResponsePesananID>() {
                @Override
                public void onResponse(Call<ResponsePesananID> call, Response<ResponsePesananID> response) {
                    assert response.body() != null;
                    pesanan_bundle = response.body().getmPesanan();
                    setBundle(pesanan_bundle);
                }

                @Override
                public void onFailure(Call<ResponsePesananID> call, Throwable t) {

                }
            });

        }

    }

    private void setBundle(Pesanan pesanan_bundle) {
        setStatus(pesanan_bundle.getStatus());
        String metode_bayar = pesanan_bundle.getMetode_bayar();
        if (metode_bayar.equals("Transfer Bank")){
            tv_cod.setVisibility(View.GONE);
        } else {
            tv_cod.setVisibility(View.VISIBLE);
        }
        tv_kode_pesanan.setText(pesanan_bundle.getKd_pemesanan());
        tv_kode_pesanan_bahan.setText(pesanan_bundle.getKd_pemesanan());
        tv_kode_pesanan_bahan3.setText(pesanan_bundle.getKd_pemesanan());
        tv_tanggal.setText(getDate(pesanan_bundle.getCreated_at()));
        tv_nama.setText(pesanan_bundle.getNama());
        tv_telpon.setText(pesanan_bundle.getNo_telepon());
        tv_alamat.setText(pesanan_bundle.getDeskripsi_lokasi());
        String tgl = getDate(pesanan_bundle.getTanggal_antar());
        tv_tanggal_antar.setText(tgl);
        tv_waktu.setText(pesanan_bundle.getWaktu_antar());
        tv_catatatn.setText(pesanan_bundle.getCatatan());
        setDataPanelUp(pesanan_bundle.getTransaksi(), pesanan_bundle.getNama(), pesanan_bundle.getKd_pemesanan(), pesanan_bundle.getBukti_pembayaran());
        setDataPaker((ArrayList<Paket>) pesanan_bundle.getPaket());
        setDataAddiyional((ArrayList<Additional>) pesanan_bundle.getAdditional());
        setMapLokasi(pesanan_bundle.getLatitude(), pesanan_bundle.getLogitude());
        setDataAlatPesanan((ArrayList<AlatPesanan>) pesanan_bundle.getAlat());
        setDataBahanPaket((ArrayList<BahanPesanan>) pesanan_bundle.getBahan());
        String id_antar = String.valueOf(pesanan_bundle.getPengantaran());
        if (id_antar == null) {
            tv_kosong_antar.setVisibility(View.VISIBLE);
            tv_nama_driver_antar.setVisibility(View.GONE);
            img_foto_driver_antar.setVisibility(View.GONE);
            status_driver_antar.setVisibility(View.GONE);
        } else {
            setDataDriverPengantaran(id_antar);
        }
        String id_jemput = String.valueOf(pesanan_bundle.getPenjemputan());
        if (id_jemput == null) {
            tv_kosong_jemput.setVisibility(View.VISIBLE);
            tv_nama_driver_jemput.setVisibility(View.GONE);
            img_foto_driver_jemput.setVisibility(View.GONE);
            status_driver_jemput.setVisibility(View.GONE);
        } else {
            setDataDriverPenjemputan(id_jemput);
        }

    }

    private void setDataDriverPengantaran(String pengantaran) {
        if (pengantaran == null) {
            tv_kosong_antar.setVisibility(View.VISIBLE);
            tv_nama_driver_antar.setVisibility(View.GONE);
            img_foto_driver_antar.setVisibility(View.GONE);
            status_driver_antar.setVisibility(View.GONE);
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponDriver> pengantaranCall = apiInterface.getDriver("Bearer " + BuildConfig.TOKEN, String.valueOf(pengantaran));
            pengantaranCall.enqueue(new Callback<ResponDriver>() {
                @Override
                public void onResponse(Call<ResponDriver> call, Response<ResponDriver> response) {
                    if (response.isSuccessful()) {
                        driver = response.body().getDriver();
                        if (driver == null) {
                            tv_kosong_antar.setVisibility(View.VISIBLE);
                            tv_nama_driver_antar.setVisibility(View.GONE);
                            img_foto_driver_antar.setVisibility(View.GONE);
                            status_driver_antar.setVisibility(View.GONE);
                        } else {
                            tv_kosong_antar.setVisibility(View.GONE);
                            tv_nama_driver_antar.setVisibility(View.VISIBLE);
                            img_foto_driver_antar.setVisibility(View.VISIBLE);
                            status_driver_antar.setVisibility(View.VISIBLE);
                            tv_nama_driver_antar.setText(driver.getNama());
                            String status = driver.getStatus();
                            if (status.equals("active")) {
                                status_driver_antar.setText(status);
                                status_driver_antar.setTextColor(ContextCompat.getColor(DetailPesananActivity.this, R.color.doneText));
                                status_driver_antar.setBackground(ContextCompat.getDrawable(DetailPesananActivity.this, R.drawable.bg_status_done));
                            } else {
                                status_driver_antar.setText(status);
                                status_driver_antar.setTextColor(ContextCompat.getColor(DetailPesananActivity.this, R.color.refuseText));
                                status_driver_antar.setBackground(ContextCompat.getDrawable(DetailPesananActivity.this, R.drawable.bg_status_refuse));
                            }
                            Glide.with(DetailPesananActivity.this)
                                    .load(Constanta.url_foto_foto_driver + driver.getFoto())
                                    .placeholder(R.drawable.loading_animation)
                                    .error(R.drawable.ic_broken_image)
                                    .into(img_foto_driver_antar);
                        }
                    } else {
                        tv_kosong_antar.setVisibility(View.VISIBLE);
                        tv_nama_driver_antar.setVisibility(View.GONE);
                        img_foto_driver_antar.setVisibility(View.GONE);
                        status_driver_antar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ResponDriver> call, Throwable t) {
                    tv_kosong_antar.setVisibility(View.VISIBLE);
                    tv_nama_driver_antar.setVisibility(View.GONE);
                    img_foto_driver_antar.setVisibility(View.GONE);
                    status_driver_antar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void setDataDriverPenjemputan(String penjemputan) {
        if (penjemputan == null) {
            tv_kosong_jemput.setVisibility(View.VISIBLE);
            tv_nama_driver_jemput.setVisibility(View.GONE);
            img_foto_driver_jemput.setVisibility(View.GONE);
            status_driver_jemput.setVisibility(View.GONE);
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponDriver> penjemputanCall = apiInterface.getDriver("Bearer " + BuildConfig.TOKEN, String.valueOf(penjemputan));
            penjemputanCall.enqueue(new Callback<ResponDriver>() {
                @Override
                public void onResponse(Call<ResponDriver> call, Response<ResponDriver> response) {
                    if (response.isSuccessful()) {
                        driver = response.body().getDriver();
                        if (driver == null) {
                            tv_kosong_jemput.setVisibility(View.VISIBLE);
                            tv_nama_driver_jemput.setVisibility(View.GONE);
                            img_foto_driver_jemput.setVisibility(View.GONE);
                            status_driver_jemput.setVisibility(View.GONE);
                        } else {
                            tv_kosong_jemput.setVisibility(View.GONE);
                            tv_nama_driver_jemput.setVisibility(View.VISIBLE);
                            img_foto_driver_jemput.setVisibility(View.VISIBLE);
                            status_driver_jemput.setVisibility(View.VISIBLE);
                            tv_nama_driver_jemput.setText(driver.getNama());
                            String status = driver.getStatus();
                            if (status.equals("active")) {
                                status_driver_jemput.setText(status);
                                status_driver_jemput.setTextColor(ContextCompat.getColor(DetailPesananActivity.this, R.color.doneText));
                                status_driver_jemput.setBackground(ContextCompat.getDrawable(DetailPesananActivity.this, R.drawable.bg_status_done));
                            } else {
                                status_driver_jemput.setText(status);
                                status_driver_jemput.setTextColor(ContextCompat.getColor(DetailPesananActivity.this, R.color.refuseText));
                                status_driver_jemput.setBackground(ContextCompat.getDrawable(DetailPesananActivity.this, R.drawable.bg_status_refuse));
                            }
                            Glide.with(DetailPesananActivity.this)
                                    .load(Constanta.url_foto_foto_driver + driver.getFoto())
                                    .placeholder(R.drawable.loading_animation)
                                    .error(R.drawable.ic_broken_image)
                                    .into(img_foto_driver_jemput);
                        }
                    } else {
                        tv_kosong_jemput.setVisibility(View.VISIBLE);
                        tv_nama_driver_jemput.setVisibility(View.GONE);
                        img_foto_driver_jemput.setVisibility(View.GONE);
                        status_driver_jemput.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ResponDriver> call, Throwable t) {
                    tv_kosong_jemput.setVisibility(View.VISIBLE);
                    tv_nama_driver_jemput.setVisibility(View.GONE);
                    img_foto_driver_jemput.setVisibility(View.GONE);
                    status_driver_jemput.setVisibility(View.GONE);
                }
            });
        }


    }

    private void setDataAlatPesanan(ArrayList<AlatPesanan> alat) {
        if (alat.isEmpty()) {
            tv_kosong_sliding_alat.setVisibility(View.VISIBLE);
        } else {
            AdapterKategoriAlat adapterPaket = new AdapterKategoriAlat(DetailPesananActivity.this, alat);
            rv_sliding_continer_alat.setLayoutManager(new LinearLayoutManager(DetailPesananActivity.this));
            rv_sliding_continer_alat.setAdapter(adapterPaket);
        }
    }

    private void setDataBahanPaket(ArrayList<BahanPesanan> bahan) {
        if (bahan.isEmpty()) {
            tv_kosong_sliding_paket.setVisibility(View.VISIBLE);
        } else {
            AdapterBahanPaket adapterPaket = new AdapterBahanPaket(DetailPesananActivity.this, bahan);
            rv_sliding_paket.setLayoutManager(new LinearLayoutManager(DetailPesananActivity.this));
            rv_sliding_paket.setAdapter(adapterPaket);
        }
    }

    private void hiddenContainerSliding() {
        container_alat.setVisibility(View.GONE);
        container_bahan.setVisibility(View.GONE);
        container_driver.setVisibility(View.GONE);
        container_transaksi.setVisibility(View.GONE);
    }

    private void setStatus(String status) {
        switch (status) {
            case "New":
                tv_status_pesanan.setText("Pesanana Baru");
                tv_status_pesanan.setTextColor(ContextCompat.getColor(this, R.color.newText));
                tv_status_pesanan.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_new));
                break;
            case "Accept":
                tv_status_pesanan.setText("Selesai Bayar");
                tv_status_pesanan.setTextColor(ContextCompat.getColor(this, R.color.acceptText));
                tv_status_pesanan.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_accept));
                break;
            case "Proccess":
                tv_status_pesanan.setText("Proses Dapur");
                tv_status_pesanan.setTextColor(ContextCompat.getColor(this, R.color.proccessText));
                tv_status_pesanan.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_proccess));
                break;
            case "Delivery":
                tv_status_pesanan.setText("Pesanan Diantar");
                tv_status_pesanan.setTextColor(ContextCompat.getColor(this, R.color.deliveryText));
                tv_status_pesanan.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_delivery));
                break;
            case "Arrived":
                tv_status_pesanan.setText("Pesanan Sampai");
                tv_status_pesanan.setTextColor(ContextCompat.getColor(this, R.color.arrivedText));
                tv_status_pesanan.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_arrived));
                break;
            case "Taking":
                tv_status_pesanan.setText("Pesanan Dijemput");
                tv_status_pesanan.setTextColor(ContextCompat.getColor(this, R.color.deliveryText));
                tv_status_pesanan.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_delivery));
                break;
            case "Done":
                tv_status_pesanan.setText("Pesanan Selesai");
                tv_status_pesanan.setTextColor(ContextCompat.getColor(this, R.color.doneText));
                tv_status_pesanan.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_done));
                break;
            case "Refuse":
                tv_status_pesanan.setText("Pesanan Ditolak");
                tv_status_pesanan.setTextColor(ContextCompat.getColor(this, R.color.refuseText));
                tv_status_pesanan.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_refuse));
                break;
            case "Cancel":
                tv_status_pesanan.setText("Pesanan Batal");
                tv_status_pesanan.setTextColor(ContextCompat.getColor(this, R.color.cancelText));
                tv_status_pesanan.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_cancel));
                break;
        }
    }

    private void setMapLokasi(String latitude, String longitude) {
        latLngzoom = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    private BitmapDescriptor bitmapDescriptor(Context context) {
        int height = 70;
        int width = 50;
        Drawable vectorDrawble = ContextCompat.getDrawable(context, R.drawable.ic_icon_lokasi_tujuan);
        assert vectorDrawble != null;
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

    private void setDataPanelUp(Transaksi transaksi, String nama, String kd_pemesanan, String bukti_pembayaran) {

        tv_total_harga = findViewById(R.id.tv_total_harga);
        tv_harga_lainnya = findViewById(R.id.tv_harga_lainnya);
        tv_biaya_pengiriman = findViewById(R.id.tv_biaya_pengiriman);
        tv_harga_additional = findViewById(R.id.tv_harga_additional);
        tv_harga_paket = findViewById(R.id.tv_harga_paket);
        tv_kode_pesanan2 = findViewById(R.id.tv_kode_pesanan2);
        tv_nama_pelanggan2 = findViewById(R.id.tv_nama_pelanggan2);
        img_bukti_pembayaran = findViewById(R.id.img_bukti_pembayaran);
        img_zoom = findViewById(R.id.img_zoom);

        tv_nama_pelanggan2.setText(nama);
        tv_kode_pesanan2.setText(kd_pemesanan);
        tv_harga_paket.setText(String.valueOf(transaksi.getHargaPaket()));
        tv_harga_additional.setText(String.valueOf(transaksi.getHargaAdditional()));
        tv_biaya_pengiriman.setText(String.valueOf(transaksi.getBiayaPengiriman()));
        tv_harga_lainnya.setText(String.valueOf(transaksi.getHargaLainnya()));
        tv_total_harga.setText(String.valueOf(transaksi.getTotalHarga()));

        if (bukti_pembayaran != null) {
            Glide.with(DetailPesananActivity.this)
                    .load(Constanta.url_foto_bukti_pesanan + bukti_pembayaran)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(img_bukti_pembayaran);

            Glide.with(DetailPesananActivity.this)
                    .load(Constanta.url_foto_bukti_pesanan + bukti_pembayaran)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(img_zoom);
        }

    }

    private void showPanel() {
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            hiddenContainerSliding();
        } else {
            assert sliding_layout != null;
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
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        assert date != null;
        return dateFormatter.format(date);
    }

//    private void actionNotConnection() {
//        Snackbar.make(findViewById(android.R.id.content), "Koneksi Tidak Ada!", Snackbar.LENGTH_INDEFINITE)
//                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
//                .setAction("Close", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        v.setVisibility(View.GONE);
//                    }
//                })
//                .show();
//    }

    private void animationMaps() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
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

        if (latLngzoom != null) {
            map.clear();
            googleMap.addMarker(new MarkerOptions().title("Lokasi Pesanan")
                    .icon(bitmapDescriptor(getApplicationContext()))
                    .position(latLngzoom));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngzoom, 13));
        }
    }
}