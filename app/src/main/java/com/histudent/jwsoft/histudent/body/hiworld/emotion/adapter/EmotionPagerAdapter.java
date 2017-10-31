package com.histudent.jwsoft.histudent.body.hiworld.emotion.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZhangYT on 2016/7/11.
 */
public class EmotionPagerAdapter extends PagerAdapter {

    private List<View>list;
    public EmotionPagerAdapter(List<View>list){
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=list.get(position);
        container.addView(view);
        return view;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(list.get(position));
    }
}
