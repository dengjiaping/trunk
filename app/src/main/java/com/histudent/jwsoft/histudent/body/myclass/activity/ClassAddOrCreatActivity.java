package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.fragment.ClassAddOrCreateFragment;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.model.constant.TransferKeys;

/**
 * Created by liuguiyu-pc on 2017/4/27.
 * 学生加入班级，老师加入或创建班级
 */

public class ClassAddOrCreatActivity extends BaseActivity {

    public static void start(Activity activity, int code,boolean isGone) {
        Intent intent = new Intent(activity, ClassAddOrCreatActivity.class);
        intent.putExtra(TransferKeys.IS_GONE,isGone);
        activity.startActivityForResult(intent, code);
    }

    @Override
    public int setViewLayout() {
        return R.layout.framentlayout;
    }

    @Override
    public void initView() {
        boolean isGone = getIntent().getBooleanExtra(TransferKeys.IS_GONE, false);
        ClassAddOrCreateFragment fragment = ClassAddOrCreateFragment.getInstance(isGone);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_id,fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getSupportFragmentManager().findFragmentById(R.id.fragment_id).onActivityResult(requestCode, resultCode, data);
        setResult(resultCode);
        finish();
    }
}
