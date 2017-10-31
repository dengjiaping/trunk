package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.CheckBoxView;

import java.util.ArrayList;
import java.util.List;

//选择上传照片质量页面
public class PhotoQualitySelect extends BaseActivity implements View.OnClickListener {

    private CheckBoxView iv_normal, iv_high, iv_original;
    private List<CheckBoxView> images;
    private Intent intent;
    private String quality;
    private RelativeLayout relative_normal, relative_high, relative_original;

    @Override
    public int setViewLayout() {
        return R.layout.activity_photo_quality_select;
    }

    public static void start(Activity activity,String quality,int requetCode){

        Intent intent=new Intent(activity,PhotoQualitySelect.class);
        intent.putExtra("quality",quality);
        activity.startActivityForResult(intent,requetCode);
    }

    @Override
    public void initView() {
        intent = getIntent();
        quality = intent.getStringExtra("quality");
        iv_normal = ((CheckBoxView) findViewById(R.id.iv_normal));
        iv_high = ((CheckBoxView) findViewById(R.id.iv_high));
        iv_original = ((CheckBoxView) findViewById(R.id.iv_original));
        ((TextView) findViewById(R.id.title_middle_text)).setText("画质");
        ((TextView) findViewById(R.id.title_right_text)).setText("确定");
        findViewById(R.id.title_left_image).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.title_left_text)).setText("取消");
        ((TextView) findViewById(R.id.title_right_text)).setTextColor(getResources().getColor(R.color.green_color));

        relative_normal = ((RelativeLayout) findViewById(R.id.relative_normal));
        relative_high = ((RelativeLayout) findViewById(R.id.relative_high));
        relative_original = ((RelativeLayout) findViewById(R.id.relative_original));

        initImages();
    }

    private void initImages() {

        //初始化Image的背景
        iv_normal.setSelected(false);
        iv_original.setSelected(false);
        iv_high.setSelected(false);

        //根据跳转前接受的照片质量，来初始化图片质量的初始被选中状态
        if ("正常".equals(quality)) {
            iv_normal.setChecked(true);
        } else if ("高清".equals(quality)) {
            iv_high.setChecked(true);
        } else {
            iv_original.setChecked(true);
        }

        //设置监听事件
        relative_normal.setOnClickListener(this);
        relative_high.setOnClickListener(this);
        relative_original.setOnClickListener(this);

        //初始化Images集合
        images = new ArrayList<>();
        images.add(iv_normal);
        images.add(iv_high);
        images.add(iv_original);

    }

    //根据选择改变Image的背景，实现选中状态的切换
    private void changeImageBackGround(int position) {
        for (int i = 0; i < images.size(); i++) {
            if (position == i) {
                images.get(i).setChecked(true);
            } else {
                images.get(i).setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //返回
            case R.id.title_left:
                this.finish();
                break;

            //正常图片上传
            case R.id.relative_normal:
                quality = "正常";
                changeImageBackGround(0);
                break;

            //高清图片上传
            case R.id.relative_high:
                quality = "高清";
                changeImageBackGround(1);
                break;

            //原图上传
            case R.id.relative_original:
                quality = "原图";
                changeImageBackGround(2);
                break;

            case R.id.title_right:

                //返回权限
                intent.putExtra("quality", quality);
                setResult(1000, intent);
                this.finish();
                break;
        }
    }

}
