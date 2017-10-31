package com.histudent.jwsoft.histudent.account.login.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> views;

    public ViewPagerAdapter(List<View> views) {
        // TODO 自动生成的构造函数存根
        this.views = views;
    }

    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO 自动生成的方法存根
        return view == object;
    }

	/*@Override
    //该方法已弃用
	public Object instantiateItem(View container, int position) {
		// TODO 自动生成的方法存根
		ViewGroup parent = (ViewGroup) views.get(position).getParent();
		if (parent != null)
			parent.removeAllViews();

		((ViewPager) container).addView(views.get(position));
		return views.get(position);
	}*/

	/*@Override
	 //该方法已弃用
	public void destroyItem(View container, int position, Object object) {
		// TODO 自动生成的方法存根
		((ViewPager) container).removeView(views.get(position));
	}*/

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO 自动生成的方法存根
        ViewGroup parent = (ViewGroup) views.get(position).getParent();
        if (parent != null)
            parent.removeAllViews();

        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO 自动生成的方法存根
        container.removeView(views.get(position));
    }

}
