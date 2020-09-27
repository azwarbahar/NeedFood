package com.technest.needfood.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.technest.needfood.R;
import com.technest.needfood.admin.home.HomeFragment;
import com.technest.needfood.admin.inventori.InventoriFragment;
import com.technest.needfood.admin.pesanan.PesananFragment;
import com.technest.needfood.admin.setting.SettingFragment;
import com.technest.needfood.admin.stok.StokFragment;
import com.technest.needfood.intro.LoginActivity;

public class DashboardAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemPesanan;
    private ResideMenuItem itemStokBahan;
    private ResideMenuItem itemInventori;
//    private ResideMenuItem itemKeuangan;
    private ResideMenuItem itemSetting;
    private ResideMenuItem itemLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        mContext = this;

        setUpMenu();
        changeFragment(new HomeFragment());
    }

    private void setUpMenu() {

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.bg_main);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f);

        itemHome     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Home");
        itemPesanan     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Pesanan");
        itemStokBahan     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Stok");
        itemInventori     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Inventori");
//        itemKeuangan     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Dompet");
        itemSetting     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Setting");
        itemLogout     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Logout");

        itemHome.setOnClickListener(this);
        itemPesanan.setOnClickListener(this);
        itemStokBahan.setOnClickListener(this);
        itemInventori.setOnClickListener(this);
//        itemKeuangan.setOnClickListener(this);
        itemSetting.setOnClickListener(this);
        itemLogout.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemPesanan, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemStokBahan, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemInventori, ResideMenu.DIRECTION_LEFT);
//        resideMenu.addMenuItem(itemKeuangan, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSetting, ResideMenu.DIRECTION_RIGHT);
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
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
        }else if (view == itemPesanan){
            changeFragment(new PesananFragment());
        }else if (view == itemStokBahan){
            changeFragment(new StokFragment());
        }else if (view == itemInventori){
            changeFragment(new InventoriFragment());
        }else if (view == itemSetting){
            changeFragment(new SettingFragment());
        }else if (view == itemLogout){
            Intent intent = new Intent(DashboardAdminActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        resideMenu.closeMenu();

    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
