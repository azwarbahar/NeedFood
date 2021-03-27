package com.technest.needfood.driver.delivery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.driver.DashboardDriverActivity;
import com.technest.needfood.models.pesanan.AlatPesanan;
import com.technest.needfood.models.pesanan.AlatPilihanPesanan;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.models.pesanan.ResponsePesananID;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technest.needfood.intro.LoginActivity.my_shared_preferences;

public class DeliveryDriverActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private LatLng latLngzoom;
    private LatLng latlingPesanan;
    private MarkerOptions markerOptionsPesanan;
    public static final String EXTRA_DATA = "extra_data";
    private String id_pesanan;

    private ImageView btn_close;
    private ImageView img_whatsapp;
    private CardView cv_detail_pesanan;
    private CardView cv_sampai;
    private CardView cv_cekalat;

    private TextView tv_lokasi_tujuan;
    private TextView tv_nama;
    private TextView tv_kode;
    private TextView tv_cv_sampai;
    private Pesanan pesanan_parce;
    private String status_pesanan;
    private String tanggal;
    private String tanggal_now;

    private Drawable vectorDrawble;
    private Bitmap bitmap;

    private boolean ready_foto_pesanan;
    private CardView cv_foto_pesanan;
    private CardView cv_falid_date;
    private TextView btn_foto_kirim;
    private TextView tv_tanggal_warning;
    private TextView btn_hide;

    private Button test;

    private ArrayList<AlatPilihanPesanan> alatPilihanPesanans;
    private MyArrayList alat_id = new MyArrayList();
    private MyArrayList alat_status = new MyArrayList();
    private MyArrayList alat_jumlah = new MyArrayList();

    private SharedPreferences mPreferences;
    private static final String TAG = DeliveryDriverActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_driver);

        pesanan_parce = getIntent().getParcelableExtra(EXTRA_DATA);

        Context context = DeliveryDriverActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        // check Koneksi
        if (ConnectionDetector.isInternetAvailble()) {
            Log.d("Internet ", "Connec");
        } else {
            actionNotConnection();
        }

        test = findViewById(R.id.test);
        test.setVisibility(View.GONE);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alat_status.contains("used")){
                    Toast.makeText(context, "belu dikembalikan..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_hide = findViewById(R.id.btn_hide);
        tv_tanggal_warning = findViewById(R.id.tv_tanggal_warning);
        btn_foto_kirim = findViewById(R.id.btn_foto_kirim);
        cv_foto_pesanan = findViewById(R.id.cv_foto_pesanan);
        cv_falid_date = findViewById(R.id.cv_falid_date);
        cv_falid_date.setVisibility(View.GONE);
        tv_kode = findViewById(R.id.tv_kode);
        tv_nama = findViewById(R.id.tv_nama);
        tv_lokasi_tujuan = findViewById(R.id.tv_lokasi_tujuan);

        img_whatsapp = findViewById(R.id.img_whatsapp);
        cv_cekalat = findViewById(R.id.cv_cekalat);
        cv_cekalat.setVisibility(View.GONE);

        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_foto_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(DeliveryDriverActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    new SweetAlertDialog(DeliveryDriverActivity.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("Kirim Foto")
                                            .setContentText("Mengirim foto berarti telah menentukan pengantaran!")
                                            .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                    showImagePickerOptions();
                                                }
                                            })
                                            .show();
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        assert pesanan_parce != null;
        id_pesanan = String.valueOf(pesanan_parce.getId());
        status_pesanan = pesanan_parce.getStatus();
        loadDataPesanan(id_pesanan);
        if (pesanan_parce.getFoto_pesanan() == null) {
            ready_foto_pesanan = true;
        } else {
            ready_foto_pesanan = false;
        }
        if (status_pesanan.equals("Delivery")){
            if (ready_foto_pesanan) {
                cv_foto_pesanan.setVisibility(View.VISIBLE);
            } else {
                cv_foto_pesanan.setVisibility(View.GONE);
            }
        } else {
            cv_foto_pesanan.setVisibility(View.GONE);
        }
        tv_lokasi_tujuan.setText(pesanan_parce.getDeskripsi_lokasi());
        tv_nama.setText(": " + pesanan_parce.getNama());
        tv_kode.setText(": " + pesanan_parce.getKd_pemesanan());
        tanggal = getDate(pesanan_parce.getTanggal_antar());
        tv_tanggal_warning.setText(tanggal);
        if (status_pesanan.equals("Taking")){
            cekAlat((ArrayList<AlatPesanan>) pesanan_parce.getAlat());
        }
        String tlpon = pesanan_parce.getNo_wa();
        double latitud = Double.parseDouble(pesanan_parce.getLatitude());
        double longitud = Double.parseDouble(pesanan_parce.getLogitude());
        latlingPesanan = new LatLng(latitud, longitud);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        cv_detail_pesanan = findViewById(R.id.cv_detail_pesanan);
        cv_detail_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryDriverActivity.this, DetailItemDeliveryActivity.class);
                intent.putExtra("EXTRA_DATA", pesanan_parce);
                startActivity(intent);
            }
        });
        cv_sampai = findViewById(R.id.cv_sampai);
        tv_cv_sampai = findViewById(R.id.tv_cv_sampai);
        if (status_pesanan.equals("Delivery")) {
            tv_cv_sampai.setText("Sampai");
        } else {
            tv_cv_sampai.setText("Selesai");
        }

        cv_cekalat.setOnClickListener(this::clickCekAlat);

        cv_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (status_pesanan.equals("Delivery")) {
                    if (!ready_foto_pesanan) {
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
                                                        startActivity(new Intent(DeliveryDriverActivity.this, DashboardDriverActivity.class));
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
                                                    .setContentText("Terjadi Kesalahan!")
                                                    .show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                                        pDialog.dismiss();
                                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Terjadi Kesalahan!")
                                                .show();
                                    }
                                });

                            }
                        });
                        warning.show();
                    } else {
                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Gagal")
                                .hideConfirmButton()
                                .setContentText("Foto Pengantaran Pesanan Belum Dikirimkan")
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
                                                    startActivity(new Intent(DeliveryDriverActivity.this, DashboardDriverActivity.class));
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

        img_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DeliveryDriverActivity.this, SweetAlertDialog.WARNING_TYPE)
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

        btn_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv_falid_date.setVisibility(View.GONE);
            }
        });

        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Date date = new Date();
        tanggal_now = dateFormat.format(date);

        if (tanggal.equals(tanggal_now)) {
            cv_falid_date.setVisibility(View.GONE);
        } else {
            cv_falid_date.setVisibility(View.VISIBLE);
        }

        ImagePickerDeliveryActivity.clearCache(this);
    }

    private void clickCekAlat(View view) {
        Intent intent = new Intent(DeliveryDriverActivity.this, CekAlatActivity.class);
        intent.putExtra("ID_PESANAN", id_pesanan);
        intent.putExtra("KODE_PESANAN", pesanan_parce.getKd_pemesanan());
        intent.putExtra("NAMA_PESANAN", pesanan_parce.getNama());
        intent.putExtra("ALAMAT_PESANAN", pesanan_parce.getDeskripsi_lokasi());
        startActivity(intent);
    }

    private void cekAlat(ArrayList<AlatPesanan> alat) {
        for (int a = 0; a<alat.size(); a++){
            alatPilihanPesanans = alat.get(a).getAlat_dipilih();
            for (int b = 0; b<alatPilihanPesanans.size(); b++){
                alat_id.add(alatPilihanPesanans.get(b).getAlat_id());
                alat_status.add(alatPilihanPesanans.get(b).getStatus());
                alat_jumlah.add(alatPilihanPesanans.get(b).getJumlah());
            }
        }
        if (alat_status.contains("used")){
            cv_cekalat.setVisibility(View.VISIBLE);
        } else {
            cv_cekalat.setVisibility(View.GONE);
        }
    }

    private void loadDataPesanan(String id_pesanan) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesananID> responsePesananCall = apiInterface.getPesananId(
                "Bearer " + BuildConfig.TOKEN, id_pesanan);
        responsePesananCall.enqueue(new Callback<ResponsePesananID>() {
            @Override
            public void onResponse(Call<ResponsePesananID> call, Response<ResponsePesananID> response) {
                pesanan_parce = response.body().getmPesanan();

                assert pesanan_parce != null;
                status_pesanan = pesanan_parce.getStatus();
                if (pesanan_parce.getFoto_pesanan() == null) {
                    ready_foto_pesanan = true;
                } else {
                    ready_foto_pesanan = false;
                }
                if (status_pesanan.equals("Delivery")){
                    if (ready_foto_pesanan) {
                        cv_foto_pesanan.setVisibility(View.VISIBLE);
                    } else {
                        cv_foto_pesanan.setVisibility(View.GONE);
                    }
                } else {
                    cekAlat((ArrayList<AlatPesanan>) pesanan_parce.getAlat());
                    cv_foto_pesanan.setVisibility(View.GONE);
                }
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
                mapFragment.getMapAsync(DeliveryDriverActivity.this);
            }

            @Override
            public void onFailure(Call<ResponsePesananID> call, Throwable t) {

            }
        });
    }

    private void showImagePickerOptions() {
        ImagePickerDeliveryActivity.showImagePickerOptions(this, new ImagePickerDeliveryActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(DeliveryDriverActivity.this, ImagePickerDeliveryActivity.class);
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerDeliveryActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(DeliveryDriverActivity.this, ImagePickerDeliveryActivity.class);
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerDeliveryActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerDeliveryActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryDriverActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Log.e(TAG, "Image URI: " + uri.toString());
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    setIdAntar(id_pesanan);
                    startUpdatePhoto(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startUpdatePhoto(Uri uri) {

        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        File file = new File(uri.getPath());
        RequestBody foto = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part foto_send = MultipartBody.Part.createFormData("foto", file.getName(), foto);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.setFotoPesanan("Bearer " + BuildConfig.TOKEN,
                foto_send, id_pesanan);
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                pDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getmSuccess()) {
                        SweetAlertDialog success = new SweetAlertDialog(DeliveryDriverActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        success.setTitleText("Success..");
                        success.setCancelable(false);
                        success.setContentText("Foto Pesanan Berhasil Dikirim..");
                        success.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pDialog.dismiss();
                                ready_foto_pesanan = false;
                                cv_foto_pesanan.setVisibility(View.GONE);
                                loadDataPesanan(id_pesanan);
                            }
                        });
                        success.show();
                        Log.e("SEND IMAGE", "Success : " + response.body().getmMessage());
                    } else {
                        cv_foto_pesanan.setVisibility(View.VISIBLE);
                        ready_foto_pesanan = true;
                        new SweetAlertDialog(DeliveryDriverActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Mohon Maaf...")
                                .setContentText("Terjadi Kesalahan!")
                                .show();
                        Log.e("SEND IMAGE", "Gagal : " + response.body().getmMessage());
                    }

                } else {
                    cv_foto_pesanan.setVisibility(View.VISIBLE);
                    ready_foto_pesanan = true;
                    new SweetAlertDialog(DeliveryDriverActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Mohon Maaf...")
                            .setContentText("Terjadi Kesalahan!")
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    loadDataPesanan(id_pesanan);
                                }
                            })
                            .show();
                    Log.e("SEND IMAGE", "Gagal : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                Log.e("SEND IMAGE", "FAILUR : " + t);
                pDialog.dismiss();
                cv_foto_pesanan.setVisibility(View.VISIBLE);
                ready_foto_pesanan = true;
                new SweetAlertDialog(DeliveryDriverActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Mohon Maaf...")
                        .setContentText("Terjadi Kesalahan!")
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                loadDataPesanan(id_pesanan);
                            }
                        })
                        .show();

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
        if (status_pesanan.equals("Delivery")) {
            vectorDrawble = ContextCompat.getDrawable(context, R.drawable.ic_icon_lokasi_tujuan);
        } else {
            vectorDrawble = ContextCompat.getDrawable(context, R.drawable.ic_icon_lokasi_jemput);
        }
        assert vectorDrawble != null;
        vectorDrawble.setBounds(0, 0, width, height);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
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

    @Override
    protected void onResume() {
        super.onResume();
        loadDataPesanan(id_pesanan);
    }
}
