package com.technest.needfood.admin.stok.item_stok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ItemStokPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listfragmentitemstok = new ArrayList<>();
    private final List<String>listtitleitemstok = new ArrayList<>();

    public ItemStokPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listtitleitemstok.get(position);
    }

public void AddFragmentItemStok(Fragment fragment, String title){
        listfragmentitemstok.add(fragment);
        listtitleitemstok.add(title);
}

}
