package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.activity.DealActivity;
import com.histudent.jwsoft.histudent.body.mine.adapter.GridViewAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.body.mine.model.SpecialBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by liuguiyu-pc on 2016/11/17.
 * 成长等级界面
 */

public class LevelActivity extends BaseActivity {

    private TextView level_name, level_progress_lack, title, level_growGrade, grade_experience, grade_integral, title_right;
    private TextView press_start, press_end, press_green, press_gray;
    private HiStudentHeadImageView headImageView;
    private CurrentUserDetailInfoModel model;
    private MyGridView gridView;
    private GridViewAdapter adapter;
    private List<SpecialBean> beens;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, LevelActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_level;
    }

    @Override
    public void initView() {

        model = HiCache.getInstance().getUserDetailInfo();
        beens = new ArrayList<>();

        adapter = new GridViewAdapter(this, beens);

        title = (TextView) findViewById(R.id.title_middle_text);
        title_right = (TextView) findViewById(R.id.title_right_text);
        headImageView = (HiStudentHeadImageView) findViewById(R.id.themy_fragment_portrait);
        level_name = (TextView) findViewById(R.id.level_name);
        level_progress_lack = (TextView) findViewById(R.id.level_progress_lack);
        level_growGrade = (TextView) findViewById(R.id.level_growGrade);
        gridView = (MyGridView) findViewById(R.id.gridView);

        press_start = (TextView) findViewById(R.id.press_start);
        press_end = (TextView) findViewById(R.id.press_end);
        press_green = (TextView) findViewById(R.id.press_green);
        press_gray = (TextView) findViewById(R.id.press_gray);

        grade_experience = (TextView) findViewById(R.id.grade_experience);
        grade_integral = (TextView) findViewById(R.id.grade_integral);

        title.setText("用户等级");
        title_right.setText("积分兑换");
        title_right.setVisibility(View.GONE);

        grade_experience.setText("" + model.getExperiencePoints());
        grade_integral.setText("" + model.getRequtationPoints());

        level_name.setText("经验&积分细则");
        level_growGrade.setText("LV." + model.getLevel());

        headImageView.loadBuddyAvatar(model.getAvatar());

        int level = model.getLevel();
        if (level < 50) {
            press_start.setText("LV." + level);
            press_end.setText("LV." + (level + 1));
        } else {
            press_start.setText("LV." + 49);
            press_end.setText("LV." + 50);
        }

        level_progress_lack.setText(model.getPointLower() - model.getExperiencePoints() + "");

        LinearLayout.LayoutParams params_gree = (LinearLayout.LayoutParams) press_green.getLayoutParams();
        params_gree.weight = model.getExperiencePoints();
        press_green.setLayoutParams(params_gree);

        LinearLayout.LayoutParams params_gray = (LinearLayout.LayoutParams) press_gray.getLayoutParams();
        params_gray.weight = model.getPointLower() - model.getExperiencePoints();
        press_gray.setLayoutParams(params_gray);

    }

    @Override
    public void doAction() {
        super.doAction();

        gridView.setAdapter(adapter);

        specialRightList();

    }

    private void specialRightList() {
        Map<String, Object> map = new TreeMap<>();
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.specialRightList, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                beens.addAll(JSON.parseArray(result, SpecialBean.class));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:

                finish();

                break;
            case R.id.level_layout_00:

                DealActivity.start(this, HistudentUrl.userleveldetail, "了解等级特权");

                break;
            case R.id.level_layout_01:

                break;

        }

    }

}
