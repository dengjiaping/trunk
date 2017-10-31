package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.fragment.DailyTasksFragment;
import com.histudent.jwsoft.histudent.body.myclass.fragment.NoviceTasksFragment;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 赚成长值
 */

public class ClassGetGroupNumActivity extends BaseActivity {
    @BindView(R.id.title_middle_text)
    TextView mTitle;
    @BindView(R.id.title_right_text)
    TextView mRightTitle;
    @BindView(R.id.group_left)
    TextView mLeftGroup;
    @BindView(R.id.group_right)
    TextView mRightGroup;
    @BindView(R.id.framelayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.growth_value)
    TextView mGrowthValue;


    @BindDrawable(R.drawable.grade_green_button_left)
    Drawable mGreenLeft;
    @BindDrawable(R.drawable.grade_green_button_right)
    Drawable mGreenRight;
    @BindDrawable(R.drawable.grade_white_button_right)
    Drawable mWhiteRight;
    @BindDrawable(R.drawable.grade_white_button_left)
    Drawable mWhiteLeft;

    @BindColor(R.color.green_color)
    int green;
    @BindColor(R.color.text_white)
    int white;


    @OnClick({R.id.title_left, R.id.title_right, R.id.group_left, R.id.group_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left://返回
                finish();
                break;

            case R.id.title_right://成长值记录
                Intent intent = new Intent(ClassGetGroupNumActivity.this,PointRecordActivity.class);
                startActivity(intent);
                break;

            case R.id.group_left://日常任务
                mLeftGroup.setBackground(mGreenLeft);
                mLeftGroup.setTextColor(white);
                mRightGroup.setBackground(mWhiteRight);
                mRightGroup.setTextColor(green);
                mManager.beginTransaction().replace(R.id.framelayout,mDailyTasks).commit();
                break;

            case R.id.group_right://新手任务
                mLeftGroup.setBackground(mWhiteLeft);
                mLeftGroup.setTextColor(green);
                mRightGroup.setBackground(mGreenRight);
                mRightGroup.setTextColor(white);
                mManager.beginTransaction().replace(R.id.framelayout,mNoviceTasks).commit();
                break;
        }
    }


    private DailyTasksFragment mDailyTasks;
    private NoviceTasksFragment mNoviceTasks;
    private FragmentManager mManager;
    private int classGrowthValue;





    public static void start(Activity activity,int classGrowthValue) {
        Intent intent = new Intent(activity, ClassGetGroupNumActivity.class);
        intent.putExtra("classGrowthValue",classGrowthValue);
        activity.startActivity(intent);

    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_getgroupnum;
    }

    @Override
    public void initView() {
        initTitle();
        initFragment();

    }

    private void initFragment() {
        mDailyTasks = new DailyTasksFragment();
        mNoviceTasks = new NoviceTasksFragment();
        mManager = getSupportFragmentManager();
        mManager.beginTransaction().replace(R.id.framelayout,mDailyTasks).commit();
    }

    private void initTitle() {
        mTitle.setText("赚成长值");
        mRightTitle.setText("成长值记录");
    }

    @Override
    public void doAction() {
        super.doAction();
        classGrowthValue = getIntent().getIntExtra("classGrowthValue",0);
        mGrowthValue.setText(classGrowthValue+"");
    }

}
