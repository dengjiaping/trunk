package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.histudent.jwsoft.histudent.R;
public class CameraActivity extends BaseActivity {


    FragmentManager mManager;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_camera;
    }


    public static void start(Activity activiy){
        Intent intent=new Intent(activiy,CameraActivity.class);
        activiy.startActivity(intent);
    }
    @Override
    public void initView() {

        mManager=this.getSupportFragmentManager();
        FragmentTransaction trans= mManager.beginTransaction();
       trans.replace(R.id.fragment, fragment).commit();

    }
}
