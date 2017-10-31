package com.histudent.jwsoft.histudent.info.persioninfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.histudent.jwsoft.histudent.commen.bean.AlbumAuthorityModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.fragment.AlbumFragment;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.info.persioninfo.fragment.PersionEssayFragment;
import com.histudent.jwsoft.histudent.info.persioninfo.fragment.PersionHomeFragment;
import com.histudent.jwsoft.histudent.info.persioninfo.fragment.PersionLogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/12/21.
 */

public class PersionFragmentPageAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;

    public PersionFragmentPageAdapter(FragmentManager fm) {
        super(fm);

        fragments = new ArrayList<>();
        String userId = HiCache.getInstance().getUserDetailInfo().getUserId();

        PersionHomeFragment fragment_home = new PersionHomeFragment();
        PersionEssayFragment fragment_essay = new PersionEssayFragment();
        PersionLogFragment fragment_log = new PersionLogFragment();
        AlbumFragment fragment_album = AlbumFragment.getAlbumFragment(ActionTypeEnum.OWNER,new AlbumAuthorityModel(SystemUtil.isOneselfIn(userId),userId));

        fragments.add(fragment_home);
        fragments.add(fragment_essay);
        fragments.add(fragment_log);
        fragments.add(fragment_album);

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    // 初始化每个页卡选项
    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        // TODO Auto-generated method stub
        return super.instantiateItem(arg0, arg1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
