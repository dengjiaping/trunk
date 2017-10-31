package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.fragment.ClassRankFragment;
import com.histudent.jwsoft.histudent.body.find.fragment.UserRankFragment;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;


/**
 * Created by liuguiyu-pc on 2016/12/8.
 * 排行榜
 */

public class RankListActivity extends BaseActivity {

    private TextView title;
    private RadioButton rdaiobutton_left, rdaiobutton_right;
    private String schoolId;
    private BaseFragment classRankFragment, userRankFragment;
    private FragmentManager fragmentManager;

    public static void start(Activity activity, String schoolId) {
        Intent intent = new Intent(activity, RankListActivity.class);
        intent.putExtra("schoolId", schoolId);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_action_ranking;
    }

    @Override
    public void initView() {
        schoolId = getIntent().getStringExtra("schoolId");
        title = (TextView) findViewById(R.id.title_middle_text);
        rdaiobutton_left = (RadioButton) findViewById(R.id.rdaiobutton_left);
        rdaiobutton_right = (RadioButton) findViewById(R.id.rdaiobutton_right);
        Bundle bundle = new Bundle();
        bundle.putString("schoolId", schoolId);
        classRankFragment = new ClassRankFragment();
        userRankFragment = new UserRankFragment();
        classRankFragment.setArguments(bundle);
        userRankFragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("排行榜");

        fragmentManager.beginTransaction().add(R.id.rank_fragment, classRankFragment).commit();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.rdaiobutton_left:
                rdaiobutton_left.setTextColor(getResources().getColor(R.color.text_white));
                rdaiobutton_right.setTextColor(getResources().getColor(R.color.green_color));
                switchFragment(fragmentManager, R.id.rank_fragment, userRankFragment, classRankFragment);
                break;
            case R.id.rdaiobutton_right:
                rdaiobutton_left.setTextColor(getResources().getColor(R.color.green_color));
                rdaiobutton_right.setTextColor(getResources().getColor(R.color.text_white));
                switchFragment(fragmentManager, R.id.rank_fragment, classRankFragment, userRankFragment);
                break;
        }
    }

}
