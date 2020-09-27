package com.technest.needfood.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.technest.needfood.R;
import com.technest.needfood.driver.akun.AkunDriverFragment;
import com.technest.needfood.driver.home.HomeDriverFragment;
import com.technest.needfood.driver.pesanan.PesananDriverFragment;
import com.technest.needfood.driver.riwayat.RiwayatDriverFragment;
import com.technest.needfood.intro.LoginActivity;

public class DashboardDriverActivity extends AppCompatActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemPesanan;
    private ResideMenuItem itemRiwayat;
    private ResideMenuItem itemAkun;
    private ResideMenuItem itemLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_driver);

        mContext = this;

        setUpMenu();
        changeFragment(new HomeDriverFragment());

    }

    private void setUpMenu() {

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.bg_main);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setSwipeDirectionDisable(0);
        resideMenu.setSwipeDirectionDisable(1);

        itemHome     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Home");
        itemPesanan     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Pesanan");
        itemRiwayat     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Riwayat");
        itemAkun     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Akun");
        itemLogout     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Logout");

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

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_driver_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    @Override
    public void onClick(View v) {

        if (v == itemHome){
            changeFragment(new HomeDriverFragment());
            resideMenu.closeMenu();
        }else if (v == itemPesanan){
            changeFragment(new PesananDriverFragment());
            resideMenu.closeMenu();
        }else if (v == itemRiwayat){
            changeFragment(new RiwayatDriverFragment());
            resideMenu.closeMenu();
        }else if (v == itemAkun){
            changeFragment(new AkunDriverFragment());
            resideMenu.closeMenu();
        }else if (v == itemLogout){
            Intent intent = new Intent(DashboardDriverActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
