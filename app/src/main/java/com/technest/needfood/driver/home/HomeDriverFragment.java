package com.technest.needfood.driver.home;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.driver.MapsHelper;
import com.technest.needfood.driver.delivery.DeliveryDriverActivity;
import com.technest.needfood.driver.pesanan.PesananDriverFragment;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDriverFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        MapsHelper {

    private View v;

    private String id;
    private String role;
    private String nama;
    private String alamat;
    private String email;
    private String telepon;
    private String foto;
    private String status;
    private String username;

    private boolean firstTime = true;

    private GoogleMap map;
    private GoogleMap mapDriver;
    private MarkerOptions markerOptionsDriver;

    private Location currentLocation;
    private LocationManager mLocationManager;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    private String tanggal_now;

    Location mLastLocation;
    Marker mCurrLocationMarker;

    private GoogleApiClient mGoogleApiClient;
    private boolean isPermission;
    private long UPDATE_INTERVAL = 2000;
    private long FASTES_INTERVAL = 5000;

    private Polyline currentPolyline;

//    private ImageView btn_location;
    private View dialogView;

    private SlidingUpPanelLayout sliding_layout;

    private RelativeLayout rv_btn_hariini;
    private CardView cv_pesanan_home_driver;

    private RecyclerView rv_pesanan_home_driver;

    private ProgressBar progressBar;
    private LinearLayout ll_kosong;
    private ArrayList<Pesanan> pesanans;
    private ArrayList<Pesanan> pesanansAntar;

    private TextView total_hari_ini;

    private LatLng latLng_driver;

    HashMap<String, Pesanan> markerMap = new HashMap<String, Pesanan>();

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

        loadBundle();

        total_hari_ini = v.findViewById(R.id.total_hari_ini);

        ll_kosong = v.findViewById(R.id.ll_kosong);
        ll_kosong.setVisibility(View.GONE);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ImageView btn_help = v.findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelp();
            }
        });

        ImageView btn_jenis_map = v.findViewById(R.id.btn_jenis_map);
        btn_jenis_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogJenisMap();
            }
        });

        rv_pesanan_home_driver = v.findViewById(R.id.rv_pesanan_home_driver);
        sliding_layout = v.findViewById(R.id.sliding_layout);
        rv_btn_hariini = v.findViewById(R.id.rv_btn_hariini);
        cv_pesanan_home_driver = v.findViewById(R.id.cv_pesanan_home_driver);
//        btn_location = v.findViewById(R.id.btn_my_location);
//
//        btn_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                LatLng latLngzoom = new LatLng(location.getLatitude(), location.getLongitude());
//                mapDriver.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng_driver, 13));
//            }
//        });

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
            loadDataJemput();
        } else {
            rv_pesanan_home_driver.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            actionNotConnection();
            ll_kosong.setVisibility(View.VISIBLE);
        }

        if (requestSinglePermission()) {

            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
            mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            checkLocation();
        }
        return v;
    }

    private void dialogHelp() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_help_map, null);
        dialog.setView(dialogView);
        dialog.setIcon(R.drawable.ic_baseline_help_outline_24);
        dialog.setCancelable(true);
        dialog.setTitle("Info Marker");
        dialog.show();
    }

    private void loadDataJemput() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        tanggal_now = dateFormat.format(date);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesananHariIni("Bearer " + BuildConfig.TOKEN, "Taking", tanggal_now);
        responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
            @Override
            public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getmSuccess()) {
                        pesanansAntar = (ArrayList<Pesanan>) response.body().getmPesanan();
                        setMarkerPesananPesanan(pesanansAntar);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePesanan> call, Throwable t) {
            }
        });

    }

    private void setMarkerPesananPesanan(ArrayList<Pesanan> pesanansAntar) {
        for (int a = 0; a < pesanansAntar.size(); a++) {

            double latit = Double.parseDouble(pesanansAntar.get(a).getLatitude());
            double longit = Double.parseDouble(pesanansAntar.get(a).getLogitude());
            Marker marker = map.addMarker(new MarkerOptions().title("Kode : " + pesanansAntar.get(a).getKd_pemesanan())
                    .icon(bitmapDescriptor(getActivity(), R.drawable.ic_icon_lokasi_jemput))
                    .snippet("Nama : " + pesanansAntar.get(a).getNama() +
                            "\nWaktu : " + pesanansAntar.get(a).getWaktu_antar())
                    .position(new LatLng(latit, longit)));

            String idmark = marker.getId();
            markerMap.put(idmark, pesanansAntar.get(a));

            SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            assert supportMapFragment != null;
            supportMapFragment.getMapAsync(HomeDriverFragment.this);
        }
    }

    private void loadBundle() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        if (!bundle.isEmpty()) {
            id = bundle.getString("GET_ID");
            nama = bundle.getString("GET_NAME");
            alamat = bundle.getString("GET_ALAMAT");
            telepon = bundle.getString("GET_USERNAME");
            foto = bundle.getString("GET_USERNAME");
            status = bundle.getString("GET_USERNAME");
            username = bundle.getString("GET_USERNAME");
        }
    }

    private void dialogJenisMap() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_jenis_maps, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Jenis Maps");
        dialog.show();

        LinearLayout ll_maps_default = dialogView.findViewById(R.id.ll_maps_default);
        LinearLayout ll_maps_satelit = dialogView.findViewById(R.id.ll_maps_satelit);

        ImageView img_maps_default = dialogView.findViewById(R.id.img_maps_default);
        ImageView img_maps_satelit = dialogView.findViewById(R.id.img_maps_satelit);

        TextView tv_maps_default = dialogView.findViewById(R.id.tv_maps_default);
        TextView tv_maps_satelit = dialogView.findViewById(R.id.tv_maps_satelit);

        if (map.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {

            img_maps_satelit.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_trans_merah));
            img_maps_satelit.setPadding(6, 6, 6, 6);
            tv_maps_satelit.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

            tv_maps_default.setBackground(null);
            tv_maps_default.setPadding(0, 0, 0, 0);
            tv_maps_default.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));
        } else {
            img_maps_default.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_trans_merah));
            img_maps_default.setPadding(6, 6, 6, 6);
            tv_maps_default.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

            img_maps_satelit.setBackground(null);
            img_maps_satelit.setPadding(0, 0, 0, 0);
            tv_maps_satelit.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));
        }

        ll_maps_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(map.MAP_TYPE_NORMAL);
                img_maps_default.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_trans_merah));
                img_maps_default.setPadding(6, 6, 6, 6);
                tv_maps_default.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

                img_maps_satelit.setBackground(null);
                img_maps_satelit.setPadding(0, 0, 0, 0);
                tv_maps_satelit.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));

            }
        });

        ll_maps_satelit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(map.MAP_TYPE_SATELLITE);
                img_maps_satelit.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_trans_merah));
                img_maps_satelit.setPadding(6, 6, 6, 6);
                tv_maps_satelit.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

                img_maps_default.setBackground(null);
                img_maps_default.setPadding(0, 0, 0, 0);
                tv_maps_default.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));

            }
        });


    }

    private void loadData() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
//        Toast.makeText(getActivity(), dateFormat.format(date), Toast.LENGTH_SHORT).show();
        tanggal_now = dateFormat.format(date);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePesanan> responsePesananCall = apiInterface.getPesananHariIni("Bearer " + BuildConfig.TOKEN, "Delivery", tanggal_now);
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
            Marker marker = map.addMarker(new MarkerOptions().title("Kode : " + pesanans.get(a).getKd_pemesanan())
                    .icon(bitmapDescriptor(getActivity(), R.drawable.ic_icon_lokasi_tujuan))
                    .snippet("Nama : " + pesanans.get(a).getNama() +
                            "\nWaktu : " + pesanans.get(a).getWaktu_antar())
                    .position(new LatLng(latit, longit)));

            String idmark = marker.getId();
            markerMap.put(idmark, pesanans.get(a));

            SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            assert supportMapFragment != null;
            supportMapFragment.getMapAsync(HomeDriverFragment.this);
        }
    }

    private BitmapDescriptor bitmapDescriptor(Context context, int vactorResid) {
        int height = 90;
        int width = 60;
        Drawable vectorDrawble = ContextCompat.getDrawable(context, vactorResid);
        assert vectorDrawble != null;
        vectorDrawble.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawble.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BitmapDescriptor bitmapDescriptorDriver(Context context, int vactorResid) {
        int height = 70;
        int width = 70;
        Drawable vectorDrawble = ContextCompat.getDrawable(context, vactorResid);
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
        map.setPadding(0, 0, 0, 200);
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

//        map.setMapType(map.MAP_TYPE_SATELLITE);
        map.setOnInfoWindowClickListener(this);
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context context = getActivity(); //or getActivity(), YourActivity.this, etc.
                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                title.setPadding(20, 30, 20, 3);
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setPadding(20, 3, 20, 10);
                snippet.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                snippet.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey));
                snippet.setText(marker.getSnippet());

                Button btn = new Button(context);
                btn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_status_arrived));
                btn.setText("Detail");
                btn.setTextColor(ContextCompat.getColor(getActivity(), R.color.arrivedText));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20, 35, 20, 20);
                btn.setLayoutParams(layoutParams);

                info.addView(title);
                info.addView(snippet);
                info.addView(btn);

                return info;
            }
        });
        mapDriver = googleMap;


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
//                buildGoogleApiClient();
                map.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
//            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.app.AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (markerMap.get(marker.getId())==null){
            marker.hideInfoWindow();
            Toast.makeText(getActivity(), "Marker Lokasi Driver!", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(getActivity(), "ID"+markerMap.get(marker.getId()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), DeliveryDriverActivity.class);
            intent.putExtra(DeliveryDriverActivity.EXTRA_DATA, markerMap.get(marker.getId()));
            getActivity().startActivity(intent);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdate();
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (currentLocation == null) {
            startLocationUpdate();
        }
    }

    private void startLocationUpdate() {

        progressBar.setVisibility(View.GONE);
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTES_INTERVAL);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        latLng_driver = new LatLng(location.getLatitude(), location.getLongitude());

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database1.getReference("live_location").child("driver");
        String live_latitude = String.valueOf(location.getLatitude());
        String live_longitude = String.valueOf(location.getLongitude());
        myRef.child(id).child("latitude").setValue(live_latitude);
        myRef.child(id).child("longitude").setValue(live_longitude);
//        mapDriver.clear();
//        if (markerOptionsDriver == null) {
//            markerOptionsDriver = new MarkerOptions().position(latLng_driver).icon(bitmapDescriptorDriver(getActivity(),
//                    R.drawable.ic_icon_lokasi_driver));
//            mapDriver.addMarker(markerOptionsDriver);
//        } else {
//            markerOptionsDriver = new MarkerOptions().position(latLng_driver).icon(bitmapDescriptorDriver(getActivity(),
//                    R.drawable.ic_icon_lokasi_driver));
//        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

//        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        assert supportMapFragment != null;
//        supportMapFragment.getMapAsync(HomeDriverFragment.this);

        //move map camera
        if (firstTime) {
            firstTime = false;
            LatLng latLngzoom = new LatLng(location.getLatitude(), location.getLongitude());
            mapDriver.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngzoom, 13));
        }
    }

    private boolean isLocationEnable() {
        locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean checkLocation() {

        if (!isLocationEnable()) {
            showAlert();
        }
        return isLocationEnable();
    }

    private void showAlert() {
        final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Yout Locations Settings is set to 'off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();
    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        return isPermission;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}
