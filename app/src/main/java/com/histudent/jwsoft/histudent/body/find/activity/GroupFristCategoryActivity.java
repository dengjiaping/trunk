package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.CategroyAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.ParentCategoryModel;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 社群一级分类界面
 */
public class GroupFristCategoryActivity extends BaseActivity {

    private List<Object> list;
    private ListView listView;
    private CategroyAdapter adapter;
    private String parentId;
    private String parentType;


    @Override
    public int setViewLayout() {
        return R.layout.activity_group_frist_category;
    }

    /**
     * 社群一级分类的入口
     *
     * @param activity
     * @param requestCode
     */

    public static void start(Activity activity, int requestCode) {

        Intent intent = new Intent(activity, GroupFristCategoryActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {
        ((TextView) findViewById(R.id.title_middle_text)).setText("选择标签");
        list = new ArrayList<>();

        listView = ((ListView) findViewById(R.id.listview));
        adapter = new CategroyAdapter(list, GroupFristCategoryActivity.this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentId = ((ParentCategoryModel) list.get(position)).getId();
                parentType = ((ParentCategoryModel) list.get(position)).getName();

                //进去社群的二级分类界面
                GroupSecondCategroyActivity.start(GroupFristCategoryActivity.this, 222, parentType, parentId, "");
            }
        });
        getParentCategroyInfor();
    }


    @Override
    public void doAction() {
        super.doAction();

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

    //获取一级分类的信息

    private void getParentCategroyInfor() {

        GroupHelper.getParentCategroy(this, 2, 1, "", new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<ParentCategoryModel> tem = JSON.parseArray(result, ParentCategoryModel.class);
                if (tem != null) {
                    list.addAll(tem);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200 && requestCode == 222) {
            if (data != null) {
                data.putExtra("parentId", parentId);//返回一级分类id
                data.putExtra("parentType", parentType);//返回一级分类名
                setResult(200, data);
                finish();
            }
        }
    }
}
