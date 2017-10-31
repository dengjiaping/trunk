package com.histudent.jwsoft.histudent.manage;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.histudent.jwsoft.histudent.R;

/**
 * Created by lichaojie on 2017/9/13.
 * desc:
 * 管理状态栏 标题栏
 */

public class ImmersionBarManager {


    private ImmersionBarManager() {
    }

    private static final class ImmersionBarManagerHolder {
        private static final ImmersionBarManager INSTANCE = new ImmersionBarManager();
    }

    public static ImmersionBarManager getInstance() {
        return ImmersionBarManagerHolder.INSTANCE;
    }

    /**
     * 状态栏为透明
     */
    public final void initStatusBarWithTransParent(Activity activity) {
        ImmersionBar.with(activity).init();
    }

    /**
     * 状态栏为透明
     */
    public final void initStatusBarWithTransParent(Fragment fragment) {
        ImmersionBar.with(fragment).init();
    }

    /**
     * 状态栏为透明
     */
    public final void initStatusBarWithAssignColor(Activity activity,int color) {
        ImmersionBar.with(activity)
                .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
                .init();
    }

}
