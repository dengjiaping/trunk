package com.histudent.jwsoft.histudent.body.find.activity;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.SchoolClassesAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.SchoolClassesModel;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 某个学校下的所有班级
 */

public class AllClassesInSchoolActivity extends BaseActivity {

    private PullToRefreshListView mRefreshListView;
    private List<Object> classList;
    private String schoolId;
    private int pageIndex;
    private SchoolClassesAdapter adapter;
    private SchoolClassesModel model;
    private SchoolClassesModel.ItemsBean classModel;

    /**
     * @param activity
     * @param schoolId   学校的id
     * @param requstCode
     */

    public static void start(Activity activity, String schoolId, int requstCode) {

        Intent intent = new Intent(activity, AllClassesInSchoolActivity.class);
        intent.putExtra("schoolId", schoolId);
        activity.startActivityForResult(intent, requstCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_all_classes_in_school;
    }

    @Override
    public void initView() {

        ((TextView) findViewById(R.id.title_middle_text)).setText("学校班级");
        mRefreshListView = ((PullToRefreshListView) findViewById(R.id.listview));
        schoolId = getIntent().getStringExtra("schoolId");
        classList = new ArrayList<>();
        adapter = new SchoolClassesAdapter(this, classList);
        mRefreshListView.setAdapter(adapter);

        getClasses(false);
        initRefreshListView();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                finish();
        }
    }

    private void initRefreshListView() {

        PullToRefreshUtils.initPullToRefreshListView2(mRefreshListView);
        mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getClasses(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getClasses(true);
            }
        });

        mRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classModel = ((SchoolClassesModel.ItemsBean) classList.get(position - 1));

                //进入班级圈
                ClassCircleActivity.start(AllClassesInSchoolActivity.this, classModel.getClassId());

            }
        });

    }

    /**
     * 获取班级
     *
     * @param isMore 是否是加载更多，false表示刷新，true表示加载更多
     */
    public void getClasses(boolean isMore) {

        if (isMore) {
            pageIndex++;
        } else {
            pageIndex = 0;
            classList.clear();
        }

        GroupHelper.getSchoolClasses(this, schoolId, pageIndex, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                mRefreshListView.onRefreshComplete();
                model = JSON.parseObject(result, SchoolClassesModel.class);
                if (model != null && model.getItems() != null) {

                    List<SchoolClassesModel.ItemsBean> temList = model.getItems();

                    dealData(temList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }


    //将获取的班级按照年级顺序进行分组
    private void dealData(List<SchoolClassesModel.ItemsBean> temList) {
        String lastgradeName = "";

        if (classList.size() > 0) {
            if ((classList.get(classList.size() - 1) instanceof SchoolClassesModel.ItemsBean)) {
                lastgradeName = ((SchoolClassesModel.ItemsBean) classList.get(classList.size() - 1)).getGradeName();
            } else {
                lastgradeName = classList.get(classList.size() - 1).toString();
            }

        }
        if (temList != null && temList.size() > 0) {
            for (SchoolClassesModel.ItemsBean classModel : temList) {

                if (StringUtil.isEmpty(lastgradeName)) {
                    lastgradeName = classModel.getGradeName();
                    classList.add(classModel.getGradeName());
                    classList.add(classModel);
                } else {
                    if (classModel.getGradeName().equals(lastgradeName)) {
                        classList.add(classModel);
                    } else {
                        lastgradeName = classModel.getGradeName();
                        classList.add(classModel.getGradeName());
                        classList.add(classModel);
                    }
                }
            }
        }
    }
}
