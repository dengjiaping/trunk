package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.adapter.LevelRuleAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.RankListModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/11/29.
 * 等级规则
 */

public class LevelRuleActivity extends BaseActivity {

    private TextView title;
    private ListView levelrule_list;
    private List<RankListModel> models;
    private LevelRuleAdapter adapter;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, LevelRuleActivity.class));

    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_levelrule;
    }

    @Override
    public void initView() {

        models = new ArrayList<>();

        adapter = new LevelRuleAdapter(this, models);

        title = (TextView) findViewById(R.id.title_middle_text);
        levelrule_list = (ListView) findViewById(R.id.levelrule_list);

        title.setText("等级规则");
        levelrule_list.setAdapter(adapter);

        getRankList();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        finish();
    }

    /**
     * 获取等级规则列表
     */
    private void getRankList() {

        HiStudentHttpUtils.postDataByOKHttp(this, null, HistudentUrl.rankList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<RankListModel> models_ = JSONArray.parseArray(result, RankListModel.class);

                if (models_ != null && models_.size() > 0) {
                    models.addAll(models_);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

}
