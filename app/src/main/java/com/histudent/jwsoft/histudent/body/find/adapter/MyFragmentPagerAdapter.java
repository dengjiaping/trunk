package com.histudent.jwsoft.histudent.body.find.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.histudent.jwsoft.histudent.body.find.bean.TagBean;
import com.histudent.jwsoft.histudent.body.find.fragment.PageFragment;

import java.util.List;

/**
 * Created by android03_pc on 2017/5/16.
 */

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<PageFragment> mPageFragments;
    private List<TagBean> list;

    public MyFragmentPagerAdapter(FragmentManager fm, List<TagBean> list,List<PageFragment> pageFragments) {
        super(fm);
        this.list = list;
        this.mPageFragments = pageFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mPageFragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}


