package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;

/**
 * 活动轨迹
 */

public class HuoDongTrack extends BaseActivity {

    private String classhuodongId;
    private int timeCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huo_dong_track);
    }


    public static void start(Activity activity, String classhuodongId) {


        Intent intent = new Intent(activity, HuoDongTrack.class);
        intent.putExtra("classhuodongId", classhuodongId);
        activity.startActivity(intent);
    }


    @Override
    public int setViewLayout() {
        return R.layout.activity_huo_dong_track;
    }

    @Override
    public void initView() {

        classhuodongId=getIntent().getStringExtra("classhuodongId");

        getHuoDongTrack();

    }

    public void getHuoDongTrack() {

        ClassHelper.getClassHuoDongTimeLine(this,classhuodongId,timeCursor, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Log.e("---",result);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.FLOWER);

    }
}
