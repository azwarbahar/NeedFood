package com.technest.needfood.driver.home;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.snackbar.Snackbar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.driver.pesanan.PesananDriverFragment;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDriverFragment extends Fragment implements OnMapReadyCallback {

    private View v;

    private GoogleMap map;

    private ImageView btn_location;

    private SlidingUpPanelLayout sliding_layout;

    private RelativeLayout rv_btn_hariini;
    private CardView cv_pesanan_home_driver;

    private RecyclerView rv_pesanan_home_driver;

    private ProgressBar progressBar;
    private LinearLayout ll_kosong;
    private ArrayList<Pesanan> pesanans;

    private TextView total_hari_ini;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home_driver, container, false);

        Context context = getActivity();
        assert context != null;
        ConnectionDetector ConnectionDetector = new ConnectionDetector(
                context.getApplicationContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        total_hari_ini = v.findViewById(R.id.total_hari_ini);

        ll_kosong = v.findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        rv_pesanan_home_driver = v.findViewById(R.id.rv_pesanan_home_driver);
        sliding_layout = v.findViewById(R.id.sliding_layout);
        rv_btn_hariini = v.findViewById(R.id.rv_btn_hariini);
        cv_pesanan_home_driver = v.findViewById(R.id.cv_pesanan_home_driver);
        btn_location = v.findViewById(R.id.btn_my_location);

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cv_pesanan_home_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment pesananFragment = new PesananDriverFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_driver_fragment, pesananFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        rv_btn_hariini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPanel();

            }
        });


        // check Koneksi
        if (ConnectionDetector.isInternetAvailble()) {
            loadData();
            SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            assert supportMapFragment != null;
            supportMapFragment.getMapAsync(HomeDriverFragment.this);
        } else {
            rv_pesanan_home_driver.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            actionNotConnection();
            ll_kosong.setVisibility(View.VISIBLE);
        }


        return v;
    }

    private void loadData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesananHariIni("Bearer " + BuildConfig.TOKEN, "Delivery");
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getmSuccess()) {
                        pesanans = (ArrayList<Pesanan>) response.body().getmPesanan();
                        setJumlah(pesanans);
                        if (pesanans.isEmpty()) {
                            ll_kosong.setVisibility(View.VISIBLE);
                            rv_pesanan_home_driver.setVisibility(View.GONE);
                        } else {
                            setMarkerPesanan(pesanans);
                            PesananDriverAdapter pesananDriverAdapter = new PesananDriverAdapter(getActivity(), pesanans);
                            rv_pesanan_home_driver.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv_pesanan_home_driver.setAdapter(pesananDriverAdapter);
                        }
                    } else {
                        rv_pesanan_home_driver.setVisibility(View.GONE);
                        ll_kosong.setVisibility(View.VISIBLE);
                    }
                    Log.d("Respon", "Message = " + response.body().getmMessage());
                } else {
                    rv_pesanan_home_driver.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    ll_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                ll_kosong.setVisibility(View.VISIBLE);
                Log.d("ERROR", "Respon : " + t.getMessage());
            }
        });
    }

    private void setMarkerPesanan(ArrayList<Pesanan> pesanans) {
        for (int a = 0; a < pesanans.size(); a++) {

            double latit = Double.parseDouble(pesanans.get(a).getLatitude());
            double longit = Double.parseDouble(pesanans.get(a).getLogitude());
            map.addMarker(new MarkerOptions().title("Kode : "+pesanans.get(a).getKd_pemesanan())
                    .icon(bitmapDescriptor(getActivity()))
                    .snippet("Nama : " + pesanans.get(a).getNama()+
                            "\nWaktu : "+pesanans.get(a).getWaktu_antar())
                    .position(new LatLng(latit, longit)));

        }
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

    private void setJumlah(ArrayList<Pesanan> pesanans) {
        total_hari_ini.setText(String.valueOf(pesanans.size()));
    }

    private void showPanel() {

        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == PanelState.EXPANDED || sliding_layout.getPanelState() == PanelState.ANCHORED)) {
            sliding_layout.setPanelState(PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(PanelState.ANCHORED);
        }

    }

    private void actionNotConnection() {
        Snackbar.make(getActivity().findViewById(android.R.id.content), "Koneksi Tidak Ada!", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                    }
                })
                .show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.setPadding(0, 0, 0, 210);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.maps_style_grey));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }

        LatLng latLngzoom = new LatLng(-5.157265, 119.436625);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngzoom, 12));

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

//                View view = null;
                Context context = getActivity(); //or getActivity(), YourActivity.this, etc.
//                v = getLayoutInflater().inflate(R.layout.costuem_window_info_marker, null);
//                v.setBackground(ContextCompat.getDrawable(getActivity(), R.color.colorPrimary));
                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);
//                info.setBackground(ContextCompat.getDrawable(getActivity(), R.color.colorPrimary));
//
                TextView title = new TextView(context);
//                title.setText(marker.getTitle());
//                kode.setText(marker.getSnippet());
                title.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                title.setPadding(20, 30, 20, 3);
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setPadding(20, 3, 20, 10);
                snippet.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                snippet.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                snippet.setText(marker.getSnippet());

                Button btn = new Button(context);
                btn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_status_arrived));
                btn.setText("Detail");
                btn.setTextColor(ContextCompat.getColor(getActivity(), R.color.arrivedText));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20,50,20,20);
                btn.setLayoutParams(layoutParams);

                info.addView(title);
                info.addView(snippet);
                info.addView(btn);

                return info;
            }
        });
    }
}
