package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.SeeFriendAdapter;
import com.histudent.jwsoft.histudent.body.message.model.RecommondUserModel;
import com.histudent.jwsoft.histudent.body.message.parser.ParserInMessage;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/3.
 * 添加好友好友
 */
public class SeeFriendActivity extends BaseActivity {
    private TextView title_middle_text;
    private ImageView title_left_image;
    private ListView listView;
    private EditText_clear editText;
    private List<Object> users;
    private SeeFriendAdapter adapter;
    private ImageView emptyView;
    private long textExchangeTime;//内容改变的时间

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    if (!(System.currentTimeMillis() - textExchangeTime < 2000)) {
                        filterData(msg.obj.toString());
                    }
                    break;
            }
        }
    };

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, SeeFriendActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.seefriend_activity;
    }

    @Override
    public void initView() {

        users = new ArrayList<>();

        adapter = new SeeFriendAdapter(this, users);

        //头部控件初始化
        title_middle_text = (TextView) findViewById(R.id.title_middle_text);
        title_left_image = (ImageView) findViewById(R.id.title_left_image);

        editText = (EditText_clear) findViewById(R.id.seefriend_edit);

        listView = (ListView) findViewById(R.id.see_friend_list);
        emptyView = EmptyViewUtils.EmptyView(R.mipmap.default_general, this);
        listView.setEmptyView(emptyView);

        title_left_image.setImageResource(R.drawable.goback);
    }

    @Override
    public void doAction() {
        super.doAction();

        title_middle_text.setText("添加好友");

        listView.setAdapter(adapter);

        getDatas();

        //根据输入框输入值的改变来过滤搜索
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textExchangeTime = System.currentTimeMillis();
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = s;
                handler.sendMessageDelayed(message, 2500);

            }
        });


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:

                finish();

                break;

        }
    }

    private void getDatas() {

        HiStudentHttpUtils.postDataByOKHttp(this, null, HistudentUrl.getRecommondFriendsInList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                users.clear();
                List<RecommondUserModel> models = ParserInMessage.getRecommondUserInfoParser(result);
                users.addAll(models);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    /**
     * 根据输入框中的值作为关键字来查找用户
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {

        Map<String, Object> map = new TreeMap<>();
        map.put("keyword", filterStr);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.findUsers_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                users.clear();
                List<RecommondUserModel> models = ParserInMessage.getRecommondUserInfoParser(result);
                users.addAll(models);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

}
