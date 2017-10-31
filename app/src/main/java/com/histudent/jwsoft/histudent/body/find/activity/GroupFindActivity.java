package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.GroupFindAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.GroupFindBean;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.GroupNewsModel;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by liuguiyu-pc on 2017/5/18.
 * 发现社群
 */

public class GroupFindActivity extends BaseActivity {

    private TextView title, title_right;
    private List<GroupFindBean> findBeens;
    private ListView main_list;
    private GroupFindAdapter adapter;
    private GroupNewsModel groupNewsModel;
    private IconView search_icon;
    private EditText_clear mSearchContent;
    private TextView mSearchView;

    public static void start(Activity context) {
        Intent intent = new Intent(context, GroupFindActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_group_find;
    }

    @Override
    public void initView() {
        findBeens = new ArrayList<>();
        title = (TextView) findViewById(R.id.title_middle_text);
        title_right = (TextView) findViewById(R.id.title_right_text);
        main_list = (ListView) findViewById(R.id.main_list);
        mSearchContent = (EditText_clear) findViewById(R.id.community_edit);
        mSearchView = (TextView) findViewById(R.id.tv_search);
        search_icon = (IconView) findViewById(R.id.search_icon);
        adapter = new GroupFindAdapter(findBeens, this);
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("发现社群");
        title_right.setTextColor(getResources().getColor(R.color.green_color));
        title_right.setText("创建");
        initListener();
        main_list.setAdapter(adapter);

        getGroupList(new RequestBean("", 5, ""));
        getMyGroupNews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;

            case R.id.mygroup_layout://我的社群
                PersonalGroupActivity.start(this);
                break;

            case R.id.myhot_invitation://热门帖子
                HotInvitationActivity.start(this);
                break;

            case R.id.title_right://创建社群
                CreateCommunityActivity.start(this);
                break;
        }
    }

    //获取我的社群的消息列表，控制红点的隐藏和显示
    private void getMyGroupNews() {

        GroupHelper.getGroupNews(this, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                groupNewsModel = JSON.parseObject(result, GroupNewsModel.class);
                if (groupNewsModel != null) {

                    if ((groupNewsModel.getApplyList().size() + groupNewsModel.getInvitationList().size()) > 0) {
                        findViewById(R.id.red_point).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.red_point).setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * 获取社群列表
     */
    private void getGroupList(RequestBean bean) {
        Map<String, Object> map = new TreeMap<>();
        map.put("strKey", bean.strKey);
        map.put("showSize", bean.showSize);
        map.put("childTagId", bean.childTagId);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.findGroupsList, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                List<GroupFindBean> beans = JSON.parseArray(result, GroupFindBean.class);
                findBeens.addAll(beans);
                if (findBeens.size() == 0) {
                    main_list.setEmptyView(EmptyViewUtils.NewEmptyViewTextAndImage(GroupFindActivity.this,
                            R.drawable.no_find_group, R.string.empty_no_find_group, R.string.empty_create_group, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CreateCommunityActivity.start(GroupFindActivity.this);
                                }
                            }));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private class RequestBean {
        String strKey;
        int showSize;
        String childTagId;

        public RequestBean(String strKey, int showSize, String childTagId) {
            this.strKey = strKey;
            this.showSize = showSize;
            this.childTagId = childTagId;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200 && requestCode == 20) {
            getMyGroupNews();
        }
    }

    private void initListener() {
        //社群搜索
        mSearchView.setOnClickListener((View v) -> {
            // TODO: 2017/6/15
            if (!TextUtils.isEmpty(mSearchContent.getText().toString().trim())){
                findBeens.clear();
                getGroupList(new RequestBean(mSearchContent.getText().toString().trim(), 5, ""));
            }else{
                finish();
            }
        });
        mSearchContent.addTextChangedListener(mSearchWatcher);
    }


    private TextWatcher mSearchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s.toString().trim())) {
                mSearchView.setText("取消");
                findBeens.clear();
                getGroupList(new RequestBean("", 5, ""));
            } else {
                mSearchView.setText("搜索");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
