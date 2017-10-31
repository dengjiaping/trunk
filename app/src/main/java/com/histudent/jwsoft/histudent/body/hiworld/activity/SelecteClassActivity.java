package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SelecteClassApdater;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择同步班级界面
 */
public class SelecteClassActivity extends BaseActivity {

    private TextView title_text_right;
    private MyListView2 listView;
    private SelecteClassApdater apdater;
    private boolean isResult;
    private String syncClassId;

    private ArrayList<UserClassListModel> classListModels_select, mClassList;

    /**
     * @param activity
     * @param classListModels 已经选择的班级集合
     * @param syncClassId     已经选择的同步班级id ,该班级在这个界面中将不会显示，是不可更改的
     * @param requestCode
     */

    public static void start(Activity activity, ArrayList<UserClassListModel> classListModels, String syncClassId, int requestCode) {

        Intent intent = new Intent(activity, SelecteClassActivity.class);
        if (classListModels == null) {
            intent.putExtra("classList", new ArrayList<UserClassListModel>());
        } else {
            intent.putExtra("classList", classListModels);
        }

        intent.putExtra("syncClassId", syncClassId);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_log_selecteclass;
    }

    @Override
    public void initView() {
        classListModels_select = new ArrayList<>();
        mClassList = new ArrayList<>();


        if (getIntent() != null && getIntent().getSerializableExtra("classList") != null)
            classListModels_select.addAll(((ArrayList<UserClassListModel>) getIntent().getSerializableExtra("classList")));
        syncClassId = getIntent().getStringExtra("syncClassId");

        Log.e("-----", classListModels_select.toString() + classListModels_select.size());

        title_text_right = (TextView) findViewById(R.id.title_right_text);
        listView = (MyListView2) findViewById(R.id.select_class_list);
        listView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_general, this,
                R.string.haveno_class_emptyview));
        ((TextView) findViewById(R.id.title_middle_text)).setText("选择班级");
        findViewById(R.id.instr).setVisibility(View.VISIBLE);
        findViewById(R.id.sure).setVisibility(View.VISIBLE);
        apdater = new SelecteClassApdater(this, mClassList, 2);
        title_text_right.setText("设置默认");
        listView.setAdapter(apdater);

        getMyClasses();


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            //操作完成，返回值
            case R.id.title_left:
                finish();
                break;


            //设置默认同步班级
            case R.id.title_right:

                getSelectedClassMates();
                SetingDefaultSyncClassActivity.start(this, 200);

                break;

            case R.id.sure:

                getSelectedClassMates();
                Intent intent = new Intent();
                intent.putExtra("classList", ((Serializable) classListModels_select));
                setResult(200, intent);
                finish();
                break;
        }
    }


    //返回选择的班级
    public void getSelectedClassMates() {

        classListModels_select.clear();
        if (mClassList.size() > 0) {
            for (UserClassListModel model : mClassList) {
                //如果该班级被选择，则返回该班级的数据
                if (model.isTag()) {
                    classListModels_select.add(model);
                }
            }
        }
    }

    //获取班级列表并且初始化默认被选中班级
    private void getMyClasses() {

        DataUtils.GetUserSyncClassList(this, false, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                List<UserClassListModel> friendsBean = JSON.parseArray(result, UserClassListModel.class);
                if (friendsBean != null && friendsBean.size() > 0) {
                    if (mClassList != null) {
                        mClassList.clear();
                        mClassList.addAll(friendsBean);
                        initData(mClassList);
                        apdater.notifyDataSetChanged();
                        if (isResult) {
                            isResult = false;
                        }

                        if (mClassList.size() > 0) {
                            findViewById(R.id.sure).setVisibility(View.VISIBLE);
                        } else {
                            findViewById(R.id.sure).setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.FLOWER);

    }

    //用于初始化被选择的数据
    public void initData(ArrayList<UserClassListModel> list) {


        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                UserClassListModel model = list.get(i);

                for (int j = 0; j < classListModels_select.size(); j++) {
                    if (model.getClassId().equals(classListModels_select.get(j).getClassId())) {
                        //将该数据设置为已选择的状态
                        model.setTag(true);
                        break;
                    }
                }

                if (isResult) {
                    model.setTag(model.getIsDefaultClass());
                }
            }
        }


        for (int i = 0; i < list.size(); i++) {
            UserClassListModel model = list.get(i);

            if (!StringUtil.isEmpty(syncClassId) && model.getClassId().equals(syncClassId)) {

                list.remove(model);//删除已经选择在该界面中不可编辑的班级
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == 200) {
            isResult = true;
            getMyClasses();
        }
    }

}
