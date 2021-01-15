package com.technest.needfood.driver.delivery;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.technest.needfood.admin.pesanan.detail.DetailPesananActivity;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterAdditional;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterBahanPaket;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterKategoriAlat;
import com.technest.needfood.admin.pesanan.detail.adapter.AdapterPaket;
import com.technest.needfood.driver.DashboardDriverActivity;
import com.technest.needfood.models.pesanan.Additional;
import com.technest.needfood.models.pesanan.AlatPesanan;
import com.technest.needfood.models.pesanan.BahanPesanan;
import com.technest.needfood.models.pesanan.Paket;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technest.needfood.intro.LoginActivity.my_shared_preferences;

public class DetailItemDeliveryActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    LatLng latLngzoom;
    public static final String EXTRA_DATA = "EXTRA_DATA";
    private RelativeLayout continer_map;
    private RelativeLayout rl_btn_bukti;
    private ImageView img_chevron;
    private CardView cv_transaksi;
    private CardView cv_transaksi2;
    private CardView cv_close_panel;

    private TextView tv_tanggal;
    private TextView tv_status_pesanan;
    private TextView tv_alamat;
    private TextView tv_kode_pesanan;
    private TextView tv_nama;
    private TextView tv_telpon;
    private TextView tv_tanggal_antar;
    private TextView tv_waktu;
    private TextView tv_catatatn;
    private TextView tv_cv_sampai;

    private SlidingUpPanelLayout sliding_layout;
    private ImageView img_alat;
    private ImageView img_bahan;
    private RelativeLayout container_alat;
    private RelativeLayout container_bahan;

    private RecyclerView rv_paket;
    private RecyclerView rv_additional;
    private RecyclerView rv_sliding_paket;
    private RecyclerView rv_sliding_continer_alat;

    private TextView tv_kosong_paket;
    private TextView tv_kosong_additional;
    private TextView tv_kosong_sliding_paket;
    private TextView tv_kosong_sliding_alat;

    private Pesanan pesanan_parcelable;
    private String status_pesanan;

    private CardView cv_close_detail_pesanan;

    private ImageView img_whatsapp;
    private String tanggal;
    private String tanggal_now;
    private String id_pesanan;
    private SharedPreferences mPreferences;

    private TextView tv_kode_pesanan_bahan;
    private TextView tv_kode_pesanan_bahan3;
//    private RelativeLayout container_list_alat;
//    private RelativeLayout container_kamera;
//    private RelativeLayout rl_btn_sampai;
//    private CardView cv_close_detail_pesanan;
//    private ImageView img_call;
//    private ImageView img_photo;
//    private ImageView img_list_alat;
//    private ImageView img_close_list_alat;
//    private ImageView img_foto;
//
//    private TextView tv_ambil;
//    private TextView tv_batal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_delivery);

        Context context = DetailItemDeliveryActivity.this;
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

        tv_kosong_additional = findViewById(R.id.tv_kosong_additional);
        tv_kosong_additional.setVisibility(View.GONE);
        tv_kosong_paket = findViewById(R.id.tv_kosong_paket);
        tv_kosong_paket.setVisibility(View.GONE);
        tv_kosong_sliding_paket = findViewById(R.id.tv_kosong_sliding_paket);
        tv_kosong_sliding_paket.setVisibility(View.GONE);
        tv_kosong_sliding_alat = findViewById(R.id.tv_kosong_sliding_alat);
        tv_kosong_sliding_alat.setVisibility(View.GONE);

        // container pannel sliding
            //bahan
        container_bahan = findViewById(R.id.container_bahan);
        tv_kode_pesanan_bahan = findViewById(R.id.tv_kode_pesanan_bahan);
        rv_sliding_paket = findViewById(R.id.rv_sliding_paket);
            //alat
        container_alat = findViewById(R.id.container_alat);
        tv_kode_pesanan_bahan3 = findViewById(R.id.tv_kode_pesanan_bahan3);
        rv_sliding_continer_alat = findViewById(R.id.rv_sliding_continer_alat);

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
        tv_kode_pesanan = findViewById(R.id.tv_kode_pesanan);
        tv_nama = findViewById(R.id.tv_nama);
        tv_telpon = findViewById(R.id.tv_telpon);
        tv_tanggal_antar = findViewById(R.id.tv_tanggal_antar);
        tv_waktu = findViewById(R.id.tv_waktu);
        tv_catatatn = findViewById(R.id.tv_catatatn);

        img_alat = findViewById(R.id.img_alat);
        img_alat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenContainerSliding();
                showPanel();
                container_alat.setVisibility(View.VISIBLE);
                setDataAlatPesanan((ArrayList<AlatPesanan>) pesanan_parcelable.getAlat());
            }
        });
        img_bahan = findViewById(R.id.img_bahan);
        img_bahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenContainerSliding();
                showPanel();
                container_bahan.setVisibility(View.VISIBLE);
                setDataBahanPaket((ArrayList<BahanPesanan>) pesanan_parcelable.getBahan());
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

        pesanan_parcelable = getIntent().getParcelableExtra(EXTRA_DATA);
        assert pesanan_parcelable != null;
        setStatus(pesanan_parcelable.getStatus());
        id_pesanan = String.valueOf(pesanan_parcelable.getId());
        tv_tanggal.setText(getDate(pesanan_parcelable.getCreated_at()));
        tv_kode_pesanan.setText(pesanan_parcelable.getKd_pemesanan());
        tv_kode_pesanan_bahan.setText(pesanan_parcelable.getKd_pemesanan());
        tv_kode_pesanan_bahan3.setText(pesanan_parcelable.getKd_pemesanan());
        tv_nama.setText(pesanan_parcelable.getNama());
        tv_telpon.setText(pesanan_parcelable.getNo_telepon());
        tv_alamat.setText(pesanan_parcelable.getDeskripsi_lokasi());
        String tgl = getDate(pesanan_parcelable.getTanggal_antar());
        tanggal = getDate(pesanan_parcelable.getTanggal_antar());
        tv_tanggal_antar.setText(tgl);
        status_pesanan = pesanan_parcelable.getStatus();
        tv_waktu.setText(pesanan_parcelable.getWaktu_antar());
        tv_catatatn.setText(pesanan_parcelable.getCatatan());
        setDataPaker((ArrayList<Paket>) pesanan_parcelable.getPaket());
        setDataAddiyional((ArrayList<Additional>) pesanan_parcelable.getAdditional());
        setMapLokasi(pesanan_parcelable.getLatitude(), pesanan_parcelable.getLogitude());
        String tlpon = pesanan_parcelable.getNo_wa();


        tv_cv_sampai = findViewById(R.id.tv_cv_sampai);
        if (status_pesanan.equals("Delivery")) {
            tv_cv_sampai.setText("Sampai");
        } else {
            tv_cv_sampai.setText("Selesai");
        }
        rl_btn_bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                Date date = new Date();
                tanggal_now = dateFormat.format(date);

                if (status_pesanan.equals("Delivery")) {
                    if (tanggal.equals(tanggal_now)) {
                        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Loading");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        SweetAlertDialog warning = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                        warning.setTitleText("Sampai ?");
                        warning.setContentText("Anda sampai di Tujuan");
                        warning.setCancelable(false);
                        warning.setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pDialog.dismiss();
                            }
                        });
                        warning.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pDialog.show();

                                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                                Call<ResponsePesanan> pesananCall = apiInterface.updateSatusPesanan(
                                        "Bearer " + BuildConfig.TOKEN,
                                        id_pesanan, "Arrived"
                                );
                                pesananCall.enqueue(new Callback<ResponsePesanan>() {
                                    @Override
                                    public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                                        pDialog.dismiss();
                                        if (response.isSuccessful()) {
                                            if (response.body().getmSuccess()) {
                                                setIdAntar(id_pesanan);
                                                SweetAlertDialog success = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                                success.setTitleText("Success..");
                                                success.setCancelable(false);
                                                success.setContentText("Penyetujuan Berhasil");
                                                success.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismiss();
                                                        pDialog.dismiss();
                                                        startActivity(new Intent(DetailItemDeliveryActivity.this, DashboardDriverActivity.class));
                                                        finish();
                                                    }
                                                });
                                                success.show();
                                            } else {
                                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("Mohon Maaf...")
                                                        .setContentText("Terjadi Kesalahan!")
                                                        .show();
                                            }
                                        } else {
                                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Oops...")
                                                    .setContentText("Permintaan Gagal, Periksa Koneksi Internet!")
                                                    .show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                                        pDialog.dismiss();
                                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Permintaan Gagal, Periksa Koneksi Internet!")
                                                .show();
                                    }
                                });

                            }
                        });
                        warning.show();
                    } else {

                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Maaf..")
                                .hideConfirmButton()
                                .setContentText("Tanggal Pengantaran Tidak Sesuai.")
                                .setCancelButton("Tutup", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                    }

                } else {

                    SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    SweetAlertDialog warning = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    warning.setTitleText("Selesai ?");
                    warning.setContentText("Pesanan Telah dijemput");
                    warning.setCancelable(false);
                    warning.setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            pDialog.dismiss();
                        }
                    });
                    warning.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            pDialog.show();

                            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                            Call<ResponsePesanan> pesananCall = apiInterface.updateSatusPesanan(
                                    "Bearer " + BuildConfig.TOKEN,
                                    id_pesanan, "Done"
                            );
                            pesananCall.enqueue(new Callback<ResponsePesanan>() {
                                @Override
                                public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                                    pDialog.dismiss();
                                    if (response.isSuccessful()) {
                                        if (response.body().getmSuccess()) {
                                            setIdJemput(id_pesanan);
                                            SweetAlertDialog success = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                            success.setTitleText("Success..");
                                            success.setCancelable(false);
                                            success.setContentText("Penyetujuan Berhasil");
                                            success.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                    pDialog.dismiss();
                                                    startActivity(new Intent(DetailItemDeliveryActivity.this, DashboardDriverActivity.class));
                                                    finish();
                                                }
                                            });
                                            success.show();
                                        } else {
                                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Mohon Maaf...")
                                                    .setContentText("Terjadi Kesalahan!")
                                                    .show();
                                        }
                                    } else {
                                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Permintaan Gagal, Periksa Koneksi Internet!")
                                                .show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                                    pDialog.dismiss();
                                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Permintaan Gagal, Periksa Koneksi Internet!")
                                            .show();
                                }
                            });

                        }
                    });
                    warning.show();
                }


            }
        });

        img_whatsapp = findViewById(R.id.img_whatsapp);
        img_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DetailItemDeliveryActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Pesan WhatsApp")
                        .setContentText("Ingin Mengirim Pesan WhatsApp ?")
                        .setConfirmText("Ya")
                        .setCancelText("Batal")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                try {
                                    // Check if whatsapp is installed
                                    getPackageManager().getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/" + tlpon));
                                    startActivity(intent);
                                } catch (PackageManager.NameNotFoundException e) {
                                    Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .show();
            }
        });

    }


    private void setIdJemput(String id_pesanan) {
        mPreferences = getApplicationContext().getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        String id_driver = mPreferences.getString("ID", "");
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.updateDriverPesanan("Bearer " + BuildConfig.TOKEN,
                id_pesanan, id_driver, "penjemputan");
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<ResponsePesanan> call, Throwable t) {

            }
        });

    }


    private void setDataAlatPesanan(ArrayList<AlatPesanan> alat) {
        if (alat.isEmpty()) {
            tv_kosong_sliding_alat.setVisibility(View.VISIBLE);
        } else {
            AdapterKategoriAlat adapterPaket = new AdapterKategoriAlat(DetailItemDeliveryActivity.this, alat);
            rv_sliding_continer_alat.setLayoutManager(new LinearLayoutManager(DetailItemDeliveryActivity.this));
            rv_sliding_continer_alat.setAdapter(adapterPaket);
        }
    }

    private void setDataBahanPaket(ArrayList<BahanPesanan> bahan) {
        if (bahan.isEmpty()) {
            tv_kosong_sliding_paket.setVisibility(View.VISIBLE);
        } else {
            AdapterBahanPaket adapterPaket = new AdapterBahanPaket(DetailItemDeliveryActivity.this, bahan);
            rv_sliding_paket.setLayoutManager(new LinearLayoutManager(DetailItemDeliveryActivity.this));
            rv_sliding_paket.setAdapter(adapterPaket);
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

    private void hiddenContainerSliding() {
        container_alat.setVisibility(View.GONE);
        container_bahan.setVisibility(View.GONE);
    }



    private void setIdAntar(String id_pesanan) {
        mPreferences = getApplicationContext().getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        String id_driver = mPreferences.getString("ID", "");
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.updateDriverPesanan("Bearer " + BuildConfig.TOKEN,
                id_pesanan, id_driver, "pengantaran");
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<ResponsePesanan> call, Throwable t) {

            }
        });

    }


    private void setDataAddiyional(ArrayList<Additional> additional) {
        if (additional.isEmpty()) {
            tv_kosong_additional.setVisibility(View.VISIBLE);
        } else {
            AdapterAdditional adapterAdditional = new AdapterAdditional(DetailItemDeliveryActivity.this, additional);
            rv_additional.setLayoutManager(new LinearLayoutManager(DetailItemDeliveryActivity.this));
            rv_additional.setAdapter(adapterAdditional);
        }
    }

    private void setDataPaker(ArrayList<Paket> paket) {
        if (paket.isEmpty()) {
            tv_kosong_paket.setVisibility(View.VISIBLE);
        } else {
            AdapterPaket adapterPaket = new AdapterPaket(DetailItemDeliveryActivity.this, paket);
            rv_paket.setLayoutManager(new LinearLayoutManager(DetailItemDeliveryActivity.this));
            rv_paket.setAdapter(adapterPaket);
        }

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
