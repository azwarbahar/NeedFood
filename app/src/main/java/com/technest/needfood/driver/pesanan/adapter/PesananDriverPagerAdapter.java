package com.technest.needfood.driver.pesanan.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PesananDriverPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listfragment = new ArrayList<>();
    private final List<String> listtitle = new ArrayList<>();

    public PesananDriverPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position);
    }

    @Override
    public int getCount() {
        return listtitle.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listtitle.get(position);
    }

    public void AddFragment(Fragment fragment, String title){
        listfragment.add(fragment);
        listtitle.add(title);
    }

}
