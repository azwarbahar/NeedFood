package com.technest.needfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    Context mContext;
    List<SliderItem> mListScreen;

    LayoutInflater layoutInflater;

    public SliderAdapter(Context mContext, List<SliderItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.intro,null);

        ImageView slideImageView = (ImageView) layoutScreen.findViewById(R.id.img_intro);
        TextView title_intro = (TextView) layoutScreen.findViewById(R.id.title_intro);
        TextView deskripsi_intro = (TextView) layoutScreen.findViewById(R.id.deskripsi_intro);

        slideImageView.setImageResource(mListScreen.get(position).getScreenImg());
        title_intro.setText(mListScreen.get(position).getTitle());
        deskripsi_intro.setText(mListScreen.get(position).getDesk());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
