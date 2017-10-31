package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SelecteClassApdater;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置默认同步班级界面
 */
public class SetingDefaultSyncClassActivity extends BaseActivity {


    private TextView title_text_left, title_text_right;
    private MyListView2 listView;
    private SelecteClassApdater apdater;
    private boolean isChanged, isRefresh;
    private ArrayList<UserClassListModel> classListModels_select, mClassList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            changTextColor();
            sendEmptyMessageDelayed(1, 1000);
        }
    };

    /**
     * 设置默认同步班级
     *
     * @param activity
     * @param requestCode
     */
    public static void start(Activity activity, int requestCode) {

        Intent intent = new Intent(activity, SetingDefaultSyncClassActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_log_selecteclass;
    }

    @Override
    public void initView() {
        mClassList = new ArrayList<>();
        classListModels_select = new ArrayList<>();
        findViewById(R.id.title_left_image).setVisibility(View.GONE);
        title_text_left = (TextView) findViewById(R.id.title_left_text);
        title_text_right = (TextView) findViewById(R.id.title_right_text);
        listView = (MyListView2) findViewById(R.id.select_class_list);
        findViewById(R.id.line).setVisibility(View.VISIBLE);
        listView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_general, this,
                R.string.haveno_sycnclass_emptyview));

        apdater = new SelecteClassApdater(this, mClassList, 1);
        title_text_left.setText("取消");
        title_text_right.setText("确定");
        ((TextView) findViewById(R.id.title_middle_text)).setText("设置默认班级");
        listView.setAdapter(apdater);

        getMyClasses();
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            //操作完成，返回值
            case R.id.title_left:
                setResult();

                break;

            //设置默认同步班级
            case R.id.title_right:
                setDefaultSyncClasses();
                break;
        }
    }


    private void setResult() {
        if (isChanged) {
            setResult(200);
        } else {
            setResult(-2);
        }
        finish();
    }


    //获取班级列表并且初始化默认被选中班级
    private void getMyClasses() {
        DataUtils.GetUserSyncClassList(this, false, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                List<UserClassListModel> list = JSON.parseArray(result, UserClassListModel.class);
                if (list != null) {
                    mClassList.addAll(list);
                    initData(mClassList);
                    apdater.notifyDataSetChanged();
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
                model.setTag(model.getIsDefaultClass());
            }
        }
    }

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

    private void setDefaultSyncClasses() {

        getSelectedClassMates();
        if (classListModels_select != null) {
            DataUtils.SetDefaultSyncClasses(this, classListModels_select, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    //saveDefaultSyncClassToLocal();
                    isChanged = true;
                    setResult();
                    ReminderHelper.getIntentce().ToastShow_with_image(SetingDefaultSyncClassActivity.this,
                            "设置成功", R.string.icon_check);
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        }
    }


    //将设置的同步班级数据更新到本地
    private void saveDefaultSyncClassToLocal() {

        DataUtils.GetUserSyncClassList(this, true, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                //更新本地默认同步班级信息
                HiWorldCache.saveDataToDB(HiCache.getInstance().getLoginUserInfo().getUserId(), result, HistudentUrl.GetUserSyncClassList);
            }

            @Override
            public void onFailure(String errorMsg) {

                //获取失败将本地数据置空
                HiWorldCache.saveDataToDB(HiCache.getInstance().getLoginUserInfo().getUserId(), "", HistudentUrl.GetUserSyncClassList);
            }
        }, LoadingType.FLOWER);
    }

    //改变发布的点击状态
    private void changTextColor() {

        getSelectedClassMates();
        ViewUtils.changeTitleRightClickable(this, classListModels_select.size() > 0);
    }

}
