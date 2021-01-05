package com.technest.needfood.driver.taking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.driver.DashboardDriverActivity;
import com.technest.needfood.driver.delivery.DeliveryDriverActivity;
import com.technest.needfood.driver.delivery.DetailItemDeliveryActivity;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technest.needfood.intro.LoginActivity.my_shared_preferences;

public class TakingDriverActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private LatLng latLngzoom;
    private LatLng latlingPesanan;
    private MarkerOptions markerOptionsPesanan;
    public static final String EXTRA_DATA = "extra_data";
    private String id_pesanan;

    private ImageView btn_close;
    private ImageView img_whatsapp;
    private CardView cv_detail_pesanan;
    private CardView cv_selesai;

    private TextView tv_lokasi_tujuan;
    private TextView tv_nama;
    private TextView tv_kode;
    private Pesanan pesanan_parce;
    private String tanggal;
    private String tanggal_now;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_driver);

        pesanan_parce = getIntent().getParcelableExtra(EXTRA_DATA);

        Context context = TakingDriverActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        // check Koneksi
        if (ConnectionDetector.isInternetAvailble()) {
            Log.d("Internet ", "Connec");
        } else {
            actionNotConnection();
        }

        tv_kode = findViewById(R.id.tv_kode);
        tv_nama = findViewById(R.id.tv_nama);
        tv_lokasi_tujuan = findViewById(R.id.tv_lokasi_tujuan);

        cv_detail_pesanan = findViewById(R.id.cv_detail_pesanan);
        cv_detail_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TakingDriverActivity.this, DetailItemTakingActivity.class);
                intent.putExtra("EXTRA_DATA", pesanan_parce);
                startActivity(intent);
            }
        });

        img_whatsapp = findViewById(R.id.img_whatsapp);

        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        assert pesanan_parce != null;
        id_pesanan = String.valueOf(pesanan_parce.getId());
        tv_lokasi_tujuan.setText(pesanan_parce.getDeskripsi_lokasi());
        tv_nama.setText(": " + pesanan_parce.getNama());
        tv_kode.setText(": " + pesanan_parce.getKd_pemesanan());
        tanggal = getDate(pesanan_parce.getTanggal_antar());
        String tlpon = pesanan_parce.getNo_wa();
        double latitud = Double.parseDouble(pesanan_parce.getLatitude());
        double longitud = Double.parseDouble(pesanan_parce.getLogitude());
        latlingPesanan = new LatLng(latitud, longitud);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        cv_selesai = findViewById(R.id.cv_selesai);
        cv_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                Date date = new Date();
                tanggal_now = dateFormat.format(date);

                if (tanggal.equals(tanggal_now)) {

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
                                                    startActivity(new Intent(TakingDriverActivity.this, DashboardDriverActivity.class));
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

            }
        });


        img_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(TakingDriverActivity.this, SweetAlertDialog.WARNING_TYPE)
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

    private void setIdAntar(String id_pesanan) {
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

    private BitmapDescriptor bitmapDescriptor(Context context) {
        int height = 90;
        int width = 60;
        Drawable vectorDrawble = ContextCompat.getDrawable(context, R.drawable.ic_icon_lokasi_jemput);
        assert vectorDrawble != null;
        vectorDrawble.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawble.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setPadding(0, 0, 0, 500);
        markerOptionsPesanan = new MarkerOptions().title("Rumah Pesanan")
                .icon(bitmapDescriptor(this))
                .position(latlingPesanan);
        map.addMarker(markerOptionsPesanan);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.maps_style_grey));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }

//        LatLng latLngzoom = new LatLng(-5.160892, 119.456143);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlingPesanan, 15));


    }
}