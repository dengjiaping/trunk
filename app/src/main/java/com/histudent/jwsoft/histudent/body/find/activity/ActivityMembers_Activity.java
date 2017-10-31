package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.MemberOfActvitiyAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.ActivityMembersBean;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
* 活动成员
* */
public class ActivityMembers_Activity extends BaseActivity implements View.OnClickListener {

    private List<ActivityMembersBean> list;
    private MemberOfActvitiyAdapter adapter;
    private PullToRefreshListView listView;
    private TextView tv_title, title_middle;
    private String groupId;
    ActivityMembersBean bean;
    private boolean isMember;
    private int pageIndex, type;

    public static void start(Activity activity, String id, int type) {
        Intent intent = new Intent(activity, ActivityMembers_Activity.class);
        intent.putExtra("type", type);
        intent.putExtra("groupId", id);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_member_of_activity_;
    }

    @Override
    public void initView() {

        ButterKnife.bind(this);

        groupId = getIntent().getStringExtra("groupId");
        type = getIntent().getIntExtra("type", 0);
        getActivityMemberDetails();

        listView = ((PullToRefreshListView) findViewById(R.id.member_of_activiy_lv));
        tv_title = ((TextView) findViewById(R.id.title_middle_text));

        list = new ArrayList<>();

        adapter = new MemberOfActvitiyAdapter(list, this);
        listView.setAdapter(adapter);
        initRefresListView();

    }

    private void initRefresListView() {
        PullToRefreshUtils.initPullToRefreshListView2(listView);
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.title_left:
                this.finish();
                break;
        }
    }

    //获取参加活动的人员信息
    private void getActivityMemberDetails() {


        switch (type) {


            case 1:

                ClassHelper.getClassHuoDongMembers(this, groupId, pageIndex, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        List<ActivityMembersBean> list1 =
                                JSON.parseArray(JSON.parseObject(result).getJSONArray("items").toString(), ActivityMembersBean.class);
                        if (list1 != null) {
                            list.addAll(list1);
                            tv_title.setText("活动成员(" + list.size() + ")");
                            sortMember(list);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                    }
                });
                break;


            case 2:

                GroupHelper.getGroupHuoDongMembers(this, groupId, pageIndex, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        List<ActivityMembersBean> list1 =
                                JSON.parseArray(JSON.parseObject(result).getJSONArray("items").toString(), ActivityMembersBean.class);
                        if (list1 != null) {
                            list.addAll(list1);
                            tv_title.setText("活动成员(" + list.size() + ")");
                            sortMember(list);
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(String errorMsg) {
                    }
                });
                break;
        }


    }


    //成员排序
    private void sortMember(List<ActivityMembersBean> list) {

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ActivityMembersBean b = list.get(i);
                if (b.isIsCreateUser()) {
                    ActivityMembersBean b2 = list.get(0);
                    list.set(0, b);
                    list.set(i, b2);
                    break;
                }
            }

        }
    }

}
