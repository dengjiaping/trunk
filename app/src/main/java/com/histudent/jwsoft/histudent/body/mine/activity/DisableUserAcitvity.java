package com.histudent.jwsoft.histudent.body.mine.activity;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.adapter.QualityPersonAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.DisabledFriendListBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//屏蔽访问个人主页的用户列表
public class DisableUserAcitvity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back, iv_add;
    private PullToRefreshListView pullToRefreshListView;
    private QualityPersonAdapter adapter;
    private List<Object> list;
    private List<String> userIds;
    private int delete_user_position = -1;
    private ImageView emptyView;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //删除屏蔽用户
                case 0:

                    String content = ((String) msg.obj);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        JSONObject j = JSONObject.parseObject(content);
                        if (j != null && j.getIntValue("status") == 1) {

                            list.remove(delete_user_position);
                            delete_user_position = -1;

                        }
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public int setViewLayout() {
        return R.layout.activity_disable_user_acitvity;
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        userIds = new ArrayList<>();
        pullToRefreshListView = ((PullToRefreshListView) findViewById(R.id.pull_to_refresh));
        iv_add = ((ImageView) findViewById(R.id.title_right_image));
        iv_back = ((ImageView) findViewById(R.id.title_left_image));
        emptyView = EmptyViewUtils.EmptyView(R.mipmap.default_general, this);
        pullToRefreshListView.setEmptyView(emptyView);

        iv_add.setImageResource(R.mipmap.add_writer_big);
        ((TextView) findViewById(R.id.title_middle_text)).setText("隐私权限");

        adapter = new QualityPersonAdapter(list, this);
        pullToRefreshListView.setAdapter(adapter);

        getDisableUserData();

        iv_back.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Object o = list.get(i - 1);
                DisabledFriendListBean bean = ((DisabledFriendListBean) o);

                Log.e("position", i - 1 + "");
                postDeleteUserdata(bean.getUserId());
                delete_user_position = i - 1;
            }
        });
    }

    //获取屏蔽人用户的数据
    private void getDisableUserData() {
        RequestParams requestParams = new RequestParams();
        requestParams.addHeader("Authorization", "Bearer " + HiCache.getInstance().getLoginUserInfo().getToken());
        requestParams.addHeader("Content-Type", "application/json");

        HttpUtils httpUtils = new HttpUtils();

        httpUtils.send(HttpRequest.HttpMethod.GET, HistudentUrl.mine_disableuser_of_myhome_url, requestParams, new RequestCallBack<String>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.e("DisableUserAcitvity", "onStart");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {


                List<DisabledFriendListBean> bean = JSON.parseArray(responseInfo.result, DisabledFriendListBean.class);
                if (bean != null && bean.size() > 0) {
                    list.clear();
                    list.addAll(bean);
                    adapter.notifyDataSetChanged();
                }

                Log.e("DisableUserAcitvity", responseInfo.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("DisableUserAcitvity", msg);
            }
        });
    }

    //删除屏蔽访问个人主页的用户
    private void postDeleteUserdata(String userId) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.mine_delete_disable_user_url, map, HiWorldCache.Quarry,false,true);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left_image:

                this.finish();

            case R.id.title_right_image:

                getIntent().putStringArrayListExtra("list", (ArrayList<String>) userIds);
        }
    }

}
