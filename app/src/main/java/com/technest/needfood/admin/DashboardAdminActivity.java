package com.technest.needfood.admin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.home.HomeFragment;
import com.technest.needfood.admin.inventori.InventoriFragment;
import com.technest.needfood.admin.pesanan.PesananFragment;
import com.technest.needfood.admin.setting.SettingFragment;
import com.technest.needfood.admin.stok.StokFragment;
import com.technest.needfood.driver.DashboardDriverActivity;
import com.technest.needfood.intro.LoginActivity;
import com.technest.needfood.models.user.Administrasi;
import com.technest.needfood.models.user.ResponAdmin;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.network.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technest.needfood.intro.LoginActivity.my_shared_preferences;

public class
DashboardAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemPesanan;
    private ResideMenuItem itemStokBahan;
    private ResideMenuItem itemInventori;
    private ResideMenuItem itemLogout;

    private SharedPreferences mPreferences;

    private String id;
    private String role;
    private String nama;
    private String username;
    private CardView cvProgressBar;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL_ID,
//                    "NotifyApps", NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            manager.createNotificationChannel(channel);
//        }

        setContentView(R.layout.activity_dashboard_admin);
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

//        getCurrentFirebaseToken();

//        Bundle bundle = getIntent().getExtras();
//
//        if(bundle != null)  {
//            Toast.makeText(mContext, "Title : "+bundle.getString("title")+" and Message : "+ bundle.getString("message"), Toast.LENGTH_SHORT).show();
//        }

        setUpMenu();
        changeFragment(new HomeFragment());
    }

    private void loadData(String id) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponAdmin> responAdminCall = apiInterface.getAdmin("Bearer " + BuildConfig.TOKEN, id);
        responAdminCall.enqueue(new Callback<ResponAdmin>() {
            @Override
            public void onResponse(Call<ResponAdmin> call, Response<ResponAdmin> response) {
                cvProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
//                        Log.d("DASHBOARD", "Respon : "+ response.body().getAdministrasi());
                        nama = response.body().getAdministrasi().getNama();
                        username = response.body().getAdministrasi().getUsername();
                        changeFragment(new HomeFragment());
                        Log.d("DASHBOARD", "Respon : " + response.body().getAdministrasi().getNama());
                    } else {
                        gagalLoadData();
                    }
                } else {
                    actionNotConnection();
                }
            }

            @Override
            public void onFailure(Call<ResponAdmin> call, Throwable t) {
                cvProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getCurrentFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("currentToken", token);

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TAG", msg);
//                        Toast.makeText(DashboardAdminActivity.this, msg, Toast.LENGTH_SHORT).show();
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

        itemHome = new ResideMenuItem(this, R.drawable.ic_icon_home_putih, "Home");
        itemPesanan = new ResideMenuItem(this, R.drawable.ic_icon_pesanan_putih, "Pesanan");
        itemStokBahan = new ResideMenuItem(this, R.drawable.ic_icon_stok_putih, "Stok");
        itemInventori = new ResideMenuItem(this, R.drawable.ic_icon_inventori_putih, "Alat");
        itemLogout = new ResideMenuItem(this, R.drawable.ic_icon_logout_putih, "Logout");

        itemHome.setOnClickListener(this);
        itemPesanan.setOnClickListener(this);
        itemStokBahan.setOnClickListener(this);
        itemInventori.setOnClickListener(this);
        itemLogout.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemPesanan, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemStokBahan, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemInventori, ResideMenu.DIRECTION_LEFT);
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
        bundle.putString("GET_ROLE", role);
        bundle.putString("GET_NAME", nama);
        bundle.putString("GET_USERNAME", username);
        targetFragment.setArguments(bundle);
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    @Override
    public void onClick(View view) {

        if (view == itemHome) {
            changeFragment(new HomeFragment());
        } else if (view == itemPesanan) {
            changeFragment(new PesananFragment());
        } else if (view == itemStokBahan) {
            changeFragment(new StokFragment());
        } else if (view == itemInventori) {
            changeFragment(new InventoriFragment());
//        } else if (view == itemSetting) {
//            changeFragment(new SettingFragment());
        } else if (view == itemLogout) {
            Intent intent = new Intent(DashboardAdminActivity.this, LoginActivity.class);
            startActivity(intent);
            SharedPreferences mPreferences1 = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPreferences1.edit();
            editor.apply();
            editor.clear();
            editor.commit();
            finish();
        }

        resideMenu.closeMenu();

    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }
}
