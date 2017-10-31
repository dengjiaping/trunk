package com.histudent.jwsoft.histudent.body.find.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ZhangYT on 2016/12/21.
 */

public class GroupCenterFragmentViewPager extends FragmentPagerAdapter {
    private List<Fragment> list;

    public GroupCenterFragmentViewPager(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
