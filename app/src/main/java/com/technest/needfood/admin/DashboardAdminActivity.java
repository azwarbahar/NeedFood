package com.technest.needfood.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.technest.needfood.R;

public class DashboardAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemPesanan;
    private ResideMenuItem itemStokBahan;
    private ResideMenuItem itemInventori;
    private ResideMenuItem itemKeuangan;
    private ResideMenuItem itemSetting;
    private ResideMenuItem itemLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        mContext = this;

        setUpMenu();

    }

    private void setUpMenu() {

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.bg_main);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setSwipeDirectionDisable(0);
        resideMenu.setSwipeDirectionDisable(1);

        itemHome     = new ResideMenuItem(this, R.drawable.ic_android_putih, "  Home");
        itemPesanan     = new ResideMenuItem(this, R.drawable.ic_android_putih, "  Pesanan");
        itemStokBahan     = new ResideMenuItem(this, R.drawable.ic_android_putih, "  Stok Bahan");
        itemInventori     = new ResideMenuItem(this, R.drawable.ic_android_putih, "  Inventori");
        itemKeuangan     = new ResideMenuItem(this, R.drawable.ic_android_putih, "  Keuangan");
        itemSetting     = new ResideMenuItem(this, R.drawable.ic_android_putih, "  Setting");
        itemLogout     = new ResideMenuItem(this, R.drawable.ic_android_putih, "  Logout");

        itemHome.setOnClickListener(this);
        itemPesanan.setOnClickListener(this);
        itemStokBahan.setOnClickListener(this);
        itemInventori.setOnClickListener(this);
        itemKeuangan.setOnClickListener(this);
        itemSetting.setOnClickListener(this);
        itemLogout.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
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
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {

    }
}
