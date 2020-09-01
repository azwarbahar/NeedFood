package com.technest.needfood.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.technest.needfood.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tab_indikator;
    private RelativeLayout content_login;
    private RelativeLayout rl_footer;
    private Animation fromBottom;
    private SliderAdapter sliderAdapter;

    private int[] bg_footer = null;
    private int[] bg_intro = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        content_login = findViewById(R.id.content_login);
        rl_footer = findViewById(R.id.rl_footer);
        tab_indikator = findViewById(R.id.tab_indikator);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        rl_footer.setAnimation(fromBottom);
        //untuk membuat layar full screen

        //Array background
        final int[] background_footer = {

                R.drawable.footer_1,
                R.drawable.footer_2,
                R.drawable.footer_3

        };
        bg_footer = background_footer;
        final int[] background_intro = {
                R.drawable.bg_login_1,
                R.drawable.bg_login_2,
                R.drawable.bg_login_3
        };
        bg_intro = background_intro;


        //fill list screen
        final List<SliderItem> mList = new ArrayList<>();
        mList.add(new SliderItem("Admin","Mengatur manajemen keuanagn, mengonfirmasi pesanan dan memantau Anjay-anjay yang ada", R.drawable.ic_android_black_24dp));
        mList.add(new SliderItem("Dapur","Memastikan kebutuhan bahan makanan alat dan barang yang ingin di gunakan dan yang di pesan oleh pemesan", R.drawable.ic_android_black_24dp));
        mList.add(new SliderItem("Driver","Mengefisienkan waktu untuk mengantarkan pesanan para pelanggan", R.drawable.ic_android_black_24dp));
        // setup view pager
        final ViewPager screenPager = findViewById(R.id.view_pager);
        sliderAdapter = new SliderAdapter(this, mList);
        screenPager.setAdapter(sliderAdapter);

        tab_indikator.setupWithViewPager(screenPager);

        //tablayout add change listener

        tab_indikator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//
//                if (tab.getPosition() == mList.size() - 1) {
////                    LoadLastScreen();
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        screenPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                if(position< (sliderAdapter.getCount()-1)&& position < (bg_footer.length-1)&& position < (bg_intro.length-1)) {

                    content_login.setBackgroundResource(background_intro[position]);
                    rl_footer.setBackgroundResource(background_footer[position]);


                }else{
                    content_login.setBackgroundResource(background_intro[background_intro.length-1]);
                    rl_footer.setBackgroundResource(background_footer[background_footer.length-1]);

                }

                position++;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
