package com.technest.needfood.driver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.driver.akun.AkunDriverFragment;
import com.technest.needfood.driver.home.HomeDriverFragment;
import com.technest.needfood.driver.pesanan.PesananDriverFragment;
import com.technest.needfood.driver.riwayat.RiwayatDriverFragment;
import com.technest.needfood.intro.LoginActivity;
import com.technest.needfood.models.user.ResponDriver;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technest.needfood.intro.LoginActivity.my_shared_preferences;

public class DashboardDriverActivity extends AppCompatActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemPesanan;
    private ResideMenuItem itemRiwayat;
    private ResideMenuItem itemAkun;
    private ResideMenuItem itemLogout;

    private SharedPreferences mPreferences;
    private String id;
    private String role;
    private String nama;
    private String alamat;
    private String email;
    private String telepon;
    private String foto;
    private String status;
    private String username;
    private CardView cvProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_driver);

        cvProgressBar = findViewById(R.id.cvProgressBar);
        cvProgressBar.setVisibility(View.VISIBLE);
        mContext = this;
        mPreferences = getApplicationContext().getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        id = mPreferences.getString("ID", "");
        role = mPreferences.getString("ROLE", "");
        ConnectionDetector connectionDetector = new ConnectionDetector(
                mContext.getApplicationContext());

        if (connectionDetector.isInternetAvailble()) {
            loadData(id);
        } else {
            cvProgressBar.setVisibility(View.GONE);
            actionNotConnection();
        }

        setUpMenu();
        changeFragment(new HomeDriverFragment());

    }

    private void loadData(String id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponDriver> responDriverCall = apiInterface.getDriver("Bearer " + BuildConfig.TOKEN, id);
        responDriverCall.enqueue(new Callback<ResponDriver>() {
            @Override
            public void onResponse(Call<ResponDriver> call, Response<ResponDriver> response) {
                cvProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        nama = response.body().getDriver().getNama();
                        alamat = response.body().getDriver().getAlamat();
                        email = response.body().getDriver().getEmail();
                        telepon = response.body().getDriver().getTelepon();
                        foto = response.body().getDriver().getFoto();
                        status = response.body().getDriver().getStatus();
                        username = response.body().getDriver().getUsername();
                    } else {
                        gagalLoadData();
                    }
                } else {
                    actionNotConnection();
                }
            }

            @Override
            public void onFailure(Call<ResponDriver> call, Throwable t) {
                cvProgressBar.setVisibility(View.GONE);
                gagalLoadData();
            }
        });
    }

    private void gagalLoadData() {
        Snackbar.make(findViewById(android.R.id.content), "Gagal Proses Data!", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                    }
                })
                .show();
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

    private void setUpMenu() {

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.bg_main);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setSwipeDirectionDisable(0);
        resideMenu.setSwipeDirectionDisable(1);

        itemHome = new ResideMenuItem(this, R.drawable.ic_icon_home_putih, "Home");
        itemPesanan = new ResideMenuItem(this, R.drawable.ic_icon_pesanan_putih, "Pesanan");
        itemRiwayat = new ResideMenuItem(this, R.drawable.ic_baseline_local_library_24, "Riwayat");
        itemAkun = new ResideMenuItem(this, R.drawable.ic_icon_user, "Akun");
        itemLogout = new ResideMenuItem(this, R.drawable.ic_icon_logout_putih, "Logout");

        itemHome.setOnClickListener(this);
        itemPesanan.setOnClickListener(this);
        itemRiwayat.setOnClickListener(this);
        itemAkun.setOnClickListener(this);
        itemLogout.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemPesanan, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemRiwayat, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAkun, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemLogout, ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });


        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
//            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
//            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment) {

        Bundle bundle = new Bundle();
        bundle.putString("GET_ID", id);
        bundle.putString("GET_NAME", nama);
        bundle.putString("GET_ALAMAT", alamat);
        bundle.putString("GET_EMAIL", email);
        bundle.putString("GET_TELPON", telepon);
        bundle.putString("GET_FOTO", foto);
        bundle.putString("GET_STATUS", status);
        bundle.putString("GET_USERNAME", username);
        targetFragment.setArguments(bundle);

        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_driver_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    @Override
    public void onClick(View v) {

        if (v == itemHome) {
            changeFragment(new HomeDriverFragment());
            resideMenu.closeMenu();
        } else if (v == itemPesanan) {
            changeFragment(new PesananDriverFragment());
            resideMenu.closeMenu();
        } else if (v == itemRiwayat) {
            changeFragment(new RiwayatDriverFragment());
            resideMenu.closeMenu();
        } else if (v == itemAkun) {
            changeFragment(new AkunDriverFragment());
            resideMenu.closeMenu();
        } else if (v == itemLogout) {

            Intent intent = new Intent(DashboardDriverActivity.this, LoginActivity.class);
            startActivity(intent);
            SharedPreferences mPreferences1 = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPreferences1.edit();
            editor.apply();
            editor.clear();
            editor.commit();
            finish();
        }


    }
}
