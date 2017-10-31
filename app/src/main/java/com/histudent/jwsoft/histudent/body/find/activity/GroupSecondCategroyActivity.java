package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.GroupSecondCategoryAdapter;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.comment2.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 社群的二级分类
 */

public class GroupSecondCategroyActivity extends BaseActivity implements View.OnClickListener {

    private List<Object> list;
    private GroupSecondCategoryAdapter adapter;
    private GridView gridView;
    private Intent intent;
    private String tagId, parentId, tagName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_activity_type;
    }

    /**
     * @param context
     * @param requstCode
     * @param parentId   一级分类的id
     * @param tagId      二级分类id 可传空,目前没用到
     */

    public static void start(Activity context, int requstCode, String tagName, String parentId, String tagId) {
        Intent intent = new Intent(context, GroupSecondCategroyActivity.class);
        intent.putExtra("parentId", parentId);
        intent.putExtra("parentType", tagName);
        intent.putExtra("tagId", tagId);
        context.startActivityForResult(intent, requstCode);
    }

    @Override
    public void initView() {
        intent = getIntent();
        parentId = intent.getStringExtra("parentId");
        tagId = getIntent().getStringExtra("tagId");
        tagName = getIntent().getStringExtra("parentType");


        //获取网路活动类型的数据
        getGroupTypeHttpData();

        gridView = ((GridView) findViewById(R.id.activity_type_grid_view));
        ((TextView) findViewById(R.id.title_middle_text)).setText("选择标签");
        ((TextView) findViewById(R.id.tv_name)).setText(tagName);

        list = new ArrayList<>();

        adapter = new GroupSecondCategoryAdapter(list, this, tagId);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击每个类型后的处理

                TypeBean dataBean = ((TypeBean) list.get(position));
                intent = new Intent();
                intent.putExtra("childType", dataBean.getName());//返回二级分类名
                intent.putExtra("childId", dataBean.getId());//返回二级分类id
                GroupSecondCategroyActivity.this.setResult(200, intent);
                GroupSecondCategroyActivity.this.finish();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回键后退回到上一级
            case R.id.title_left:
                this.finish();
                break;
        }
    }

    //获取社群分类的二级目录
    private void getGroupTypeHttpData() {

        GroupHelper.getParentCategroy(this, 2, 2, parentId, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<TypeBean> list1 = JSON.parseArray(result, TypeBean.class);
                if (list1 != null) {
                    list.addAll(list1);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }
}
