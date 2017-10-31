package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.CheckBoxView;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 隐私权限
 */
public class CommenPrivacySetting extends BaseActivity {
    private List<CheckBoxView> checkBoxList;
    private String quality;
    private ActionTypeEnum type;
    private TextView tv_member_instr, tv_instr;
    private RelativeLayout myselfPublic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * @param activity
     * @param quality    公开 ，成员可见，私密
     * @param type       OWNER 个人，GROUP 社群 ，CLASS 班级
     * @param requstCode
     */

    public static void start(Activity activity, String quality, ActionTypeEnum type, int requstCode) {

        Intent intent = new Intent(activity, CommenPrivacySetting.class);
        intent.putExtra("quality", quality);
        intent.putExtra("typeEnum", type);
        activity.startActivityForResult(intent, requstCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_commen_privacy_setting;
    }

    @Override
    public void initView() {

        ((TextView) findViewById(R.id.title_middle_text)).setText("隐私设置");
        findViewById(R.id.title_left_image).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.title_left_image)).setText(R.string.icon_arrowleft);
        findViewById(R.id.title_left_image).setVisibility(View.VISIBLE);
        tv_member_instr = (TextView) findViewById(R.id.tv_member_instr);
        tv_instr = ((TextView) findViewById(R.id.instr));
        myselfPublic = (RelativeLayout) findViewById(R.id.relative_myself);
        quality = getIntent().getStringExtra("quality");
        type = ((ActionTypeEnum) getIntent().getSerializableExtra("typeEnum"));

        initViews();
        initCheckBoxs();

        if (type == ActionTypeEnum.CLASS)
            myselfPublic.setVisibility(View.GONE);

    }

    //根据相册类型初始化不同相册权限界面
    private void initViews() {

        switch (type) {
            case OWNER:
                break;
            case CLASS:
                myselfPublic.setVisibility(View.GONE);
                break;

            //社群相册权限
            case GROUP:
                myselfPublic.setVisibility(View.GONE);
                tv_member_instr.setText("社群成员可见");
                tv_instr.setText("仅对社群成员可见");
                break;

            //社群权限
            case GROUP_:
                ((TextView) findViewById(R.id.title_middle_text)).setText("访问权限");
                myselfPublic.setVisibility(View.GONE);
                tv_member_instr.setText("非公开");
                tv_instr.setText("仅对社群成员可见");
                break;
        }
    }

    //初始化checkBoxs
    private void initCheckBoxs() {
        checkBoxList = new ArrayList<>();
        checkBoxList.add(((CheckBoxView) findViewById(R.id.iv_all_public)));
        checkBoxList.add(((CheckBoxView) findViewById(R.id.iv_member_public)));
        if (type == ActionTypeEnum.OWNER) {
            checkBoxList.add(((CheckBoxView) findViewById(R.id.iv_myself_public)));
        }

        changeCheckBoxBackgroud(DataUtils.changeQualityString2Number(quality));
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //返回,返回时，如果用户做出了修改，提示用户是否放弃修改
//            case R.id.title_left:
//
////                setResult();
//                break;

            //所有人
            case R.id.relative_all:
                changeCheckBoxBackgroud(0);
                break;

            //校友同学
            case R.id.relative_classmate:
                changeCheckBoxBackgroud(1);
                break;

            //仅自己
            case R.id.relative_myself:
                changeCheckBoxBackgroud(2);
                break;

            case R.id.title_right:
                setResult();
                break;
        }
        setResult();
    }


    private void setResult() {
        for (int i = 0; i < checkBoxList.size(); i++) {
            if (checkBoxList.get(i).isChecked()) {

                Intent intent = new Intent();
                intent.putExtra("authority", DataUtils.changeNumber2QualityString(i));
                setResult(200, intent);
                break;
            }
        }
        finish();
    }

    //根据选择改变checkbox的背景
    private void changeCheckBoxBackgroud(int position) {

        for (int i = 0; i < checkBoxList.size(); i++) {

            if (i == position) {
                checkBoxList.get(i).setChecked(true);
            } else {
                checkBoxList.get(i).setChecked(false);
            }
        }

    }

}
