package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.view.StarBar;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 班级等级
 */

public class OtherClassGradeActivity extends BaseActivity {


    @BindView(R.id.title_middle_text)
    TextView mTitle;
    @BindView(R.id.title_right_text)
    TextView mRightText;
    @BindView(R.id.class_group_num)
    TextView mGroupNum;
    @BindView(R.id.school_grade)
    TextView mSchoolGrade;
    @BindView(R.id.terrace_grade)
    TextView mTerraceGrade;
    @BindView(R.id.starBar)
    StarBar mStarBar;



    @OnClick(R.id.title_left)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left://返回
                finish();
                break;

        }
    }

    private ClassModel mClassModel;
    private String classId;
    private int classGrowthValue=0;


    public static void start(Activity activity, String classId) {
        Intent intent = new Intent(activity, OtherClassGradeActivity.class);
        intent.putExtra("classId", classId);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_other_class_grade;
    }

    @Override
    public void initView() {
        initTitle();
    }


    private void initTitle() {
        mTitle.setText("班级等级");
    }


    @Override
    public void doAction() {
        super.doAction();
        classId = getIntent().getStringExtra("classId");
        getClassInfo();
    }

    //获取班级详情数据
    private void getClassInfo() {
        ClassHelper.getClassInfo(this, classId, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mClassModel = JSON.parseObject(result, ClassModel.class);
                if (mClassModel != null) {
                    showClassInfo(mClassModel);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });
    }

    private void showClassInfo(ClassModel model) {
        if (model == null) return;
        classGrowthValue=model.getClassGrothValue();
        mGroupNum.setText(classGrowthValue+ "");

        setMask(model.getPlatformRanking());
        mSchoolGrade.setText("" + model.getSchoolRanking());
        mTerraceGrade.setText("" + model.getPlatformRanking());
    }

    private void setMask(int rank) {
        if (rank > 0 && rank <= 1000) {
            mStarBar.setStarMark(5);
        } else if (1001 < rank && rank <= 3000) {
            mStarBar.setStarMark(4);
        } else if (3001 < rank && rank <= 5000) {
            mStarBar.setStarMark(3);
        } else if (5001 < rank && rank <= 10000) {
            mStarBar.setStarMark(2);
        } else if (rank==0){
            mStarBar.setStarMark(0);
        } else{
            mStarBar.setStarMark(1);
        }
    }
}
