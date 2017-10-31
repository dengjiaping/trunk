package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.ClassNoticeAdapter;
import com.histudent.jwsoft.histudent.body.message.model.NoticeModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/30.
 * 班级公告
 */
public class ClassNoticeActivity extends BaseActivity {

    private PullToRefreshListView listView_notice;
    private ClassNoticeAdapter adapter_notice;
    private List<NoticeModel> list_notices;
    private String classId;
    private List<NoticeModel> models;
    private TextView title;

    public static void start(Activity activity, String classId) {
        Intent intent = new Intent(activity, ClassNoticeActivity.class);
        intent.putExtra("classId", classId);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.class_notice_activity;
    }

    @Override
    public void initView() {
        classId = getIntent().getStringExtra("classId");
        list_notices = new ArrayList<>();
        title = (TextView) findViewById(R.id.title_middle_text);
        listView_notice = (PullToRefreshListView) findViewById(R.id.class_center_notice_list);
    }

    @Override
    public void doAction() {
        super.doAction();
        title.setText("班级公告");
        adapter_notice = new ClassNoticeAdapter(this, list_notices);
        listView_notice.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView_notice.setAdapter(adapter_notice);

        listView_notice.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                list_notices.clear();
                getNotices();
            }
        });

        getNotices();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left://顶部回退

                finish();

                break;

            case R.id.title_right://顶部菜单
                break;
        }
    }

    private void getNotices() {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.getClassNoticeList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                listView_notice.onRefreshComplete();

                models = JSON.parseArray(result, NoticeModel.class);

                if (models != null) {
                    if (models.size() > 0) {
                        list_notices.addAll(models);
                        adapter_notice.notifyDataSetChanged();
                    } else {
                        listView_notice.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_notice, ClassNoticeActivity.this, R.string.have_no_classNotice));
                    }
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

}
