package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.MoveGroupAdapter;
import com.histudent.jwsoft.histudent.body.message.model.CategoriesModel;
import com.histudent.jwsoft.histudent.body.message.parser.ParserInMessage;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/8/29.
 * 移动分组界面
 */
public class MoveGroupActivity extends BaseActivity {

    private TextView title, title_right;
    private ListView listView;
    private List<CategoriesModel> models_all;
    private List<String> ids_old, ids_new;
    private MoveGroupAdapter adapter;
    private int index = -1;
    private String followId;
    private String userId;
    private String name;
//    private CategoriesModel model_current;

    public static void start(Activity activity, String userId, String followId, String name) {
        Intent intent = new Intent(activity, MoveGroupActivity.class);
        intent.putExtra("followId", followId);
        intent.putExtra("userId", userId);
        intent.putExtra("name", name);
        activity.startActivityForResult(intent, 100);
    }

    @Override
    public int setViewLayout() {
        return R.layout.movegroup_activity;
    }

    @Override
    public void initView() {
        models_all = new ArrayList<>();
        ids_old = new ArrayList<>();
        ids_new = new ArrayList<>();
        followId = getIntent().getStringExtra("followId");
        userId = getIntent().getStringExtra("userId");
        name = getIntent().getStringExtra("name");
        adapter = new MoveGroupAdapter(this, models_all, ids_new);
        title = (TextView) findViewById(R.id.title_middle_text);
        title_right = (TextView) findViewById(R.id.title_right_text);
        listView = (ListView) findViewById(R.id.moveGroup_list);
    }

    @Override
    public void doAction() {
        super.doAction();

        listView.setAdapter(adapter);
        title.setText("移动分组");
        title_right.setText("确定");

        getAllGroupeData();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.title_left:

                finish();

                break;

            case R.id.title_right:
//                if (index == -1) {
//                    Toast.makeText(this, "请先选择移动后的分组", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                final CategoriesModel model = models_all.get(index);
//                Map<String, Object> map = new TreeMap<>();
//                map.put("followId", followId);
//                map.put("defaultReMove", model_current.isIsDefault());
//                map.put("oldCategoryId", model_current.isIsDefault() ? null : model_current.getCategoryId());
//                map.put("newCateGoryId", model.isIsDefault() ? null : model.getCategoryId());
//
//                HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.editUsersCategory_url, new HttpRequestCallBack() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Intent intent = new Intent();
//                        intent.putExtra("name", model.getName());
//                        setResult(200, intent);
//                        finish();
//
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//
//                    }
//                });
                moveGroupeData();

                break;
        }
    }

    /**
     * 获取所有可以移入的分组
     */
    private void getAllGroupeData() {

        HiStudentHttpUtils.postDataByOKHttp(this, null, HistudentUrl.getFriendsList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                models_all.addAll(exchangeData(ParserInMessage.getCategoriesInfoParser(result)));
                getOurGroupeData();

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 获取所有已经移入的分组
     */
    private void getOurGroupeData() {
        Map<String, Object> map_all = new TreeMap<>();
        map_all.put("followedUserId", userId);
        map_all.put("focusUserId", HiCache.getInstance().getLoginUserInfo().getUserId());

        HiStudentHttpUtils.postDataByOKHttp(this, map_all, HistudentUrl.userFollowGroups, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<CategoriesModel> categoriesModels = exchangeData(ParserInMessage.getCategoriesInfoParser(result));
                for (CategoriesModel categoriesModel : categoriesModels) {
                    ids_old.add(categoriesModel.getId());
                    ids_new.add(categoriesModel.getId());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 移动分组
     */
    private void moveGroupeData() {

        StringBuffer addCategoryIds = new StringBuffer();
        StringBuffer delCategoryIds = new StringBuffer();

        for (String s : ids_old) {
            if (!ids_new.contains(s)) {
                delCategoryIds.append(s + ",");
            }
        }

        for (String s : ids_new) {
            if (!ids_old.contains(s)) {
                addCategoryIds.append(s + ",");
            }
        }

        if (TextUtils.isEmpty(addCategoryIds) && TextUtils.isEmpty(delCategoryIds)) return;

        Map<String, Object> map_all = new TreeMap<>();
        map_all.put("followId", followId);
        map_all.put("addCategoryIds", TextUtils.isEmpty(addCategoryIds) ? "" : addCategoryIds.toString().substring(0, addCategoryIds.length() - 1));
        map_all.put("delCategoryIds", TextUtils.isEmpty(delCategoryIds) ? "" : delCategoryIds.toString().substring(0, delCategoryIds.length() - 1));

        HiStudentHttpUtils.postDataByOKHttp(this, map_all, HistudentUrl.editUserFollowCatrgoryNew, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Intent intent = new Intent();
//                intent.putExtra("name", model.getName());
                setResult(200, intent);
                finish();
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 过滤获得可移动分组
     *
     * @param models
     * @return
     */
    private List<CategoriesModel> exchangeData(List<CategoriesModel> models) {
        List<CategoriesModel> categoriesModels = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            CategoriesModel model = models.get(i);
//            if (model.getName().equals(name)) {
//                model_current = model;
//            } else
                if (!model.getName().equals("特别关注") && !model.isIsDefault()) {
                categoriesModels.add(models.get(i));
            }
        }
        return categoriesModels;
    }

}
