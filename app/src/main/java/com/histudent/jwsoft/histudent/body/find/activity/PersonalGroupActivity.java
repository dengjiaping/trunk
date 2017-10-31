package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.UserGroupListModel;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.mine.adapter.MyClassActivityAdapter;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.GroupNewsModel;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.GroupOrClassActionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import static com.histudent.jwsoft.histudent.CodeNum.GROUP_DISSOLVE_RESULT;
import static com.histudent.jwsoft.histudent.CodeNum.GROUP_EXIT_RESULT;
import static com.histudent.jwsoft.histudent.CodeNum.GROUP_MEMBER_RESULT;

/**
 * 我的社群
 */
public class PersonalGroupActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_right, tv_notice;
    private PullToRefreshListView listView;
    private MyClassActivityAdapter mClassActivityAdapter;
    private List<Object> list;
    private IconView iv_back;
    private GroupNewsModel groupNewsModel;
    private GroupOrClassActionUtils actionUtils;
    private UserGroupListModel groupListModel;
    private SmartRefreshLayout mRefreshLayout;
    private GroupOrClassActionUtils.isSuccessCallBack mSuccessCallBack = (int type, boolean isSuccess)->{
        //操作成功时刷新数据源
        if (isSuccess) {
            getMyGroupList();
        }
    };

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, PersonalGroupActivity.class);
        activity.startActivityForResult(intent, 20);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public int setViewLayout() {
        return R.layout.our_activity_myclass;
    }

    @Override
    public void initView() {

        actionUtils = new GroupOrClassActionUtils(this, mSuccessCallBack);
        ((TextView) findViewById(R.id.title_middle_text)).setText("我的社群");
        title_right = (TextView) findViewById(R.id.title_right_text);
        tv_notice = ((TextView) findViewById(R.id.tv_notice));
        listView = (PullToRefreshListView) findViewById(R.id.our_activity_myclass_list);
        iv_back = ((IconView) findViewById(R.id.iv_back));

        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setEnableLoadmore(false);

        list = new ArrayList<>();
        mClassActivityAdapter =  MyClassActivityAdapter.create(PersonalGroupActivity.this, list);
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
        listView.setAdapter(mClassActivityAdapter);
        title_right.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        getMyGroupList();
        getMyGroupNews();

    }

    @Override
    public void doAction() {
        super.doAction();

        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> getMyGroupList());

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id)->{
            //跳转到社群主页
            String groupId = "";
            if (list.get(position - 1) instanceof UserGroupListModel.MyJoinGroupsBean) {
                UserGroupListModel.MyJoinGroupsBean InforBean = ((UserGroupListModel.MyJoinGroupsBean) list.get(position - 1));
                groupId = InforBean.getGroupId();
            } else {

                UserGroupListModel.MyCreateGroupsBean InforBean = ((UserGroupListModel.MyCreateGroupsBean) list.get(position - 1));
                groupId = InforBean.getGroupId();
            }

            GroupCenterActivity.start(PersonalGroupActivity.this, groupId);
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            //跳转到处理加入请求的界面
            case R.id.title_right_text:

                PersonGroupNewsActivity.start(this, groupNewsModel);

                break;
        }
    }

    /**
     * 获取社群/班级消息
     */
    private void getMyGroupList() {

        list.clear();
        GroupHelper.getMyGroupList(this, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();
                groupListModel = JSON.parseObject(result, UserGroupListModel.class);
                if (groupListModel != null) {

                    list.clear();
                    if (groupListModel.getMyCreateGroupNum() > 0) {
                        list.add("我创建的(" + groupListModel.getMyCreateGroupNum() + ")");
                        list.addAll(groupListModel.getMyCreateGroups());
                    }

                    if (groupListModel.getMyJoinGroupNum() > 0) {
                        list.add("我加入的(" + groupListModel.getMyJoinGroupNum() + ")");
                        list.addAll(groupListModel.getMyJoinGroups());
                    }
                    //空白页面显示处理
                    if (groupListModel.getMyCreateGroupNum() == 0 && groupListModel.getMyJoinGroupNum() == 0) {
                        listView.setEmptyView(EmptyViewUtils.NewEmptyViewTextAndImage(PersonalGroupActivity.this,
                                R.drawable.no_find_group, R.string.empty_no_person_group, R.string.empty_create_group, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        CreateCommunityActivity.start(PersonalGroupActivity.this);
                                    }
                                }));
                    }
                    mClassActivityAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishRefresh();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200 || resultCode == GROUP_EXIT_RESULT
                || resultCode == GROUP_DISSOLVE_RESULT || resultCode == CodeNum.GROUP_INFO_RESULT
                || resultCode == GROUP_MEMBER_RESULT) {
            getMyGroupList();
            getMyGroupNews();
            setResult(200);
        }
    }


    //获取社群消息的列表

    private void getMyGroupNews() {

        GroupHelper.getGroupNews(this, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                groupNewsModel = JSON.parseObject(result, GroupNewsModel.class);
                if (groupNewsModel != null) {
                    setNewsCount(groupNewsModel.getApplyList().size() + groupNewsModel.getInvitationList().size());
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }


    //设置我的社群消息
    private void setNewsCount(int count) {

        if (count != 0) {

            tv_notice.setVisibility(View.VISIBLE);

            if (count > 99) {
                tv_notice.setText(99 + "+");
            } else {
                tv_notice.setText(count + "");
            }

        } else {
            tv_notice.setVisibility(View.GONE);
        }
    }
}
