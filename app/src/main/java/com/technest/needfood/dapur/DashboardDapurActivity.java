package com.technest.needfood.dapur;

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
import com.technest.needfood.dapur.home.HomeDapurFragment;
import com.technest.needfood.dapur.inventori.InventoriDapurFragment;
import com.technest.needfood.dapur.pesanan.PesananDapurFragment;
import com.technest.needfood.dapur.stok.StokDapurFragment;
import com.technest.needfood.intro.LoginActivity;

public class DashboardDapurActivity extends AppCompatActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemPesanan;
    private ResideMenuItem itemStokBahan;
    private ResideMenuItem itemInventori;
    private ResideMenuItem itemLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_dapur);

        mContext = this;

        setUpMenu();
        changeFragment(new HomeDapurFragment());

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
        itemInventori     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Alat");
        itemLogout     = new ResideMenuItem(this, R.drawable.ic_android_putih, "Logout");

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
            changeFragment(new HomeDapurFragment());
        }else if (view == itemPesanan){
            changeFragment(new PesananDapurFragment());
        }else if (view == itemStokBahan){
            changeFragment(new StokDapurFragment());
        }else if (view == itemInventori){
            changeFragment(new InventoriDapurFragment());
        }else if (view == itemLogout){
            Intent intent = new Intent(DashboardDapurActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        resideMenu.closeMenu();

    }
}
