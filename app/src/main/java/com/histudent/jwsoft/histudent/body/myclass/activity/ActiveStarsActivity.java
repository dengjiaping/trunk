package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.adapter.ActivityStarsAdapter;
import com.histudent.jwsoft.histudent.body.myclass.bean.ActivityStarsBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/6.
 * 活跃之星
 */

public class ActiveStarsActivity extends BaseActivity {

    private List<ActivityStarsBean> fashionBeans;
    private ListView start_list;
    private TextView title;
    private ActivityStarsAdapter adapter;

    public static void start(Activity activity, ArrayList<ActivityStarsBean> fashionBeans) {
        Intent intent = new Intent(activity, ActiveStarsActivity.class);
        intent.putExtra("fashionBeans", fashionBeans);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_start;
    }

    @Override
    public void initView() {
        fashionBeans = new ArrayList<>();
        fashionBeans.addAll((List<ActivityStarsBean>) getIntent().getSerializableExtra("fashionBeans"));
        start_list = (ListView) findViewById(R.id.start_list);
        title = (TextView) findViewById(R.id.title_middle_text);
        adapter = new ActivityStarsAdapter(this, fashionBeans);
        start_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left://返回
                finish();
                break;
        }
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("活跃之星");
        start_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PersonCenterActivity.start(ActiveStarsActivity.this, fashionBeans.get(i).getUserId());
            }
        });

    }
}
