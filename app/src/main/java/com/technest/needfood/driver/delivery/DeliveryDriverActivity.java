package com.technest.needfood.driver.delivery;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.technest.needfood.R;
import com.technest.needfood.admin.pesanan.detail.DetailPesananActivity;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.network.ConnectionDetector;

public class DeliveryDriverActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    LatLng latLngzoom;
    public static final String EXTRA_DATA = "extra_data";

    private ImageView btn_close;
    private ImageView img_call;
    private CardView cv_detail_pesanan;

    private TextView tv_lokasi_tujuan;
    private TextView tv_nama;
    private TextView tv_kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_driver);


        Context context = DeliveryDriverActivity.this;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        tv_kode = findViewById(R.id.tv_kode);
        tv_nama = findViewById(R.id.tv_nama);
        tv_lokasi_tujuan = findViewById(R.id.tv_lokasi_tujuan);

        cv_detail_pesanan = findViewById(R.id.cv_detail_pesanan);
        cv_detail_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DeliveryDriverActivity.this, DetailItemDeliveryActivity.class));

            }
        });


        img_call = findViewById(R.id.img_accept);

        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Pesanan pesanan = getIntent().getParcelableExtra(EXTRA_DATA);
        assert pesanan != null;
        tv_lokasi_tujuan.setText(pesanan.getDeskripsi_lokasi());
        tv_nama.setText(": "+pesanan.getNama());
        tv_kode.setText(": "+pesanan.getKd_pemesanan());
        String tlpon = pesanan.getNo_telepon();
        loadMaps(pesanan);
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tlpon));
                startActivity(intent);
            }
        });


    }

    private void loadMaps(Pesanan pesanan) {
        double latitud = Double.parseDouble(pesanan.getLatitude());
        double longitud = Double.parseDouble(pesanan.getLogitude());
        map.addMarker(new MarkerOptions().title("Rumah Pesanan")
                .icon(bitmapDescriptor(this))
                .position(new LatLng(latitud, longitud)));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }

    private BitmapDescriptor bitmapDescriptor(Context context) {
        int height = 90;
        int width = 60;
        Drawable vectorDrawble = ContextCompat.getDrawable(context, R.drawable.ic_icon_lokasi_tujuan);
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
        map.setPadding(0, 0, 0, 200);
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

        LatLng latLngzoom = new LatLng(-5.160892, 119.456143);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngzoom, 12));

    }
}
