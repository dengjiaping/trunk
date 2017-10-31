package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.SubscriptionAdapter;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/3.
 * 订阅号
 */
public class SubscriptionActivity extends BaseActivity {
    private TextView title_middle_text;
    private ImageView title_left_image;
    private ListView listView;
    private EditText_clear editText;
    private List<Map<String, String>> users, users_save;
    private SubscriptionAdapter adapter;
    private ImageView emptyView;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, SubscriptionActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.subscription_activity;
    }

    @Override
    public void initView() {

        users = new ArrayList<>();
        users_save = new ArrayList<>();

        adapter = new SubscriptionAdapter(this, users);

        //头部控件初始化
        title_middle_text = (TextView) findViewById(R.id.title_middle_text);
        title_left_image = (ImageView) findViewById(R.id.title_left_image);

        editText = (EditText_clear) findViewById(R.id.subscription_edit);

        listView = (ListView) findViewById(R.id.subscription_list);
        emptyView = EmptyViewUtils.EmptyView(R.mipmap.default_general, this);
        listView.setEmptyView(emptyView);


    }

    @Override
    public void doAction() {
        super.doAction();

        title_middle_text.setText("订阅号");

        listView.setAdapter(adapter);

        getDatas();

        //根据输入框输入值的改变来过滤搜索
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
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

        for (int i = 0; i < 3; i++) {
            Map<String, String> map = new TreeMap<>();
            map.put("class_name", "丹霞路小学2013级" + i + "班");
            map.put("class_introduce", "为了丰富同学们的课外生活，培养同学们的阅读...");
            users.add(map);
        }

        users_save.addAll(users);
        adapter.notifyDataSetChanged();
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<Map<String, String>> filterDateList = new ArrayList<Map<String, String>>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList.addAll(users_save);
        } else {
            filterDateList.clear();
            for (Map<String, String> user : users_save) {
                String name = user.get("class_name");
                if (name.indexOf(filterStr.toString()) != -1) {
                    filterDateList.add(user);
                }
            }
        }
        users.clear();
        users.addAll(filterDateList);
        adapter.notifyDataSetChanged();
    }

}
