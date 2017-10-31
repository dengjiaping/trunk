package com.histudent.jwsoft.histudent.body.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.adapter.QualityPersonAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.BlackListBean;
import com.histudent.jwsoft.histudent.body.mine.model.DisabledFriendListBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
* 删除黑名单、删除屏蔽好友选择页面----动态
*
* */
public class QualitySelectActivity extends BaseActivity implements View.OnClickListener {

    private List<Object> list;
    private ListView listView;
    private QualityPersonAdapter qualityPersonAdapter;
    private TextView tv_title;
    //    private ImageView iv_back;
    private String tag;
    private int delete_user_position = -1;
    private String useId;
    private ImageView emptyView;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //黑名单
                case 0:

                    String content = ((String) msg.obj);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        List<BlackListBean> blackListBean = JSON.parseArray(content, BlackListBean.class);

                        if (blackListBean != null && blackListBean.size() > 0) {
                            list.clear();
                            list.addAll(blackListBean);
                        }
                    }
                    qualityPersonAdapter.notifyDataSetChanged();
                    Log.e("BlackList", content);
                    break;


                //屏蔽动态的人
                case 1:

                    content = ((String) msg.obj);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        List<DisabledFriendListBean> bean = JSON.parseArray(content, DisabledFriendListBean.class);
                        if (bean != null && bean.size() > 0) {
                            list.clear();
                            list.addAll(bean);
                        }
                    }
                    qualityPersonAdapter.notifyDataSetChanged();
                    Log.e("DisableUserList", content);
                    break;


                //删除屏蔽用户
                case 3:


                    content = ((String) msg.obj);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        JSONObject j = JSONObject.parseObject(content);
                        if (j != null && j.getIntValue("status") == 1) {

                            list.remove(delete_user_position);
                            delete_user_position = -1;
                        }
                    }
                    qualityPersonAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    public static void start(Context context, String tag) {
        Intent intent = new Intent(context, QualitySelectActivity.class);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        tag = getIntent().getStringExtra("tag");
        list = new ArrayList<>();
        listView = ((ListView) findViewById(R.id.list_view));
        tv_title = ((TextView) findViewById(R.id.title_middle_text));
        emptyView = EmptyViewUtils.EmptyView(R.mipmap.default_general, this);
        listView.setEmptyView(emptyView);


        if (tag.equals("blackList")) {

            tv_title.setText("黑名单");

        }
        if (tag.equals("sheild")) {

            tv_title.setText("屏蔽的人");

        }
        qualityPersonAdapter = new QualityPersonAdapter(list, this);
        listView.setAdapter(qualityPersonAdapter);

//        iv_back= ((ImageView) findViewById(R.id.title_left_image));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Object o = list.get(i);

                if (tag.equals("blackList")) {

                    //删除黑名单用户
                    BlackListBean bean = ((BlackListBean) o);
                    postDeleteUserdata(5, bean.getUserId());

                } else {

                    //删除屏蔽动态用户
                    DisabledFriendListBean bean = ((DisabledFriendListBean) o);
                    postDeleteUserdata(7, bean.getUserId());
                }

                delete_user_position = i;


                Log.e("currentPosition", i + "");
            }
        });

//        iv_back.setOnClickListener(this);
    }


    //获取用户的黑名单或者屏蔽的用户

    private void getData() {

        //黑名单
        if (tag.equals("blackList")) {

            HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.mine_userBlackList_url, null, HiWorldCache.Quarry,false,true);

            //屏蔽用户的集合
        } else {
            HiWorldCache.postHttpData(this, handler, 1, HistudentUrl.mine_disableUserList_url, null, HiWorldCache.Quarry, false,true);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_quality_select;
    }

    //取消屏蔽动态，移除黑名单用户
    private void postDeleteUserdata(int action, String userId) {

        Map<String, Object> map = new HashMap<>();
        map.put("action", action + "");
        map.put("objectId", userId);

        HiWorldCache.postHttpData(this, handler, 3, HistudentUrl.sns_doActio_url, map, HiWorldCache.Quarry,false,true);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                this.finish();
                break;
        }
    }

}
