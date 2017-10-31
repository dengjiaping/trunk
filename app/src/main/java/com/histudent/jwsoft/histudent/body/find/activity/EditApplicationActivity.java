package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.ApplicationAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.HuodongBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.comment2.adapter.DragItemAdapter;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.DragItemCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.GroupOrClassActionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.RecycleCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 编辑应用界面
 */
public class EditApplicationActivity extends BaseActivity implements View.OnClickListener, RecycleCallBack {

    private MyGridView gridView_02;
    private RecyclerView dragGrid;
    private List<Object> list_01, list_02;
    private ApplicationAdapter adapter_02;
    private Object item;
    private DragItemAdapter dragAdapter;
    private boolean isChanged;
    private View emptyView;
    public onItemClick onItemClick;
    private ItemTouchHelper mItemTouchHelper;
    private TextView tv_title_right;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {


                //所有应用
                case 0:

                    String content = ((String) msg.obj);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        List<HuodongBean> beanList = JSON.parseArray(content, HuodongBean.class);
                        if (beanList != null && beanList.size() > 0) {
                            list_02.addAll(beanList);
                        }
                        changeAppState();
                        adapter_02.notifyDataSetChanged();
                    }

                    break;

                // 提交编辑后的应用信息
                case 3:
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        isChanged = true;
                    }
                    break;

            }
        }
    };


    //标记全部应用中，用户已经添加的应用
    private void changeAppState() {
        for (int i = 0; i < list_02.size(); i++) {
            HuodongBean b = ((HuodongBean) list_02.get(i));
            b.setAdd(GroupOrClassActionUtils.isAlreadAdd(list_01, b));
        }
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_edit_application;
    }

    /**
     * @param activity
     * @param list     我的应用集合
     */

    public static void start(Activity activity, List<HuodongBean> list) {
        Intent intent = new Intent(activity, EditApplicationActivity.class);
        intent.putExtra("application", (Serializable) list);
        activity.startActivityForResult(intent, 500);
    }

    @Override
    public void initView() {
        onItemClick = new onItemClick() {
            @Override
            public void onItemClick(HuodongBean huodongBean, boolean isLongClick) {
                if (isLongClick) {

                    tv_title_right.setText("完成");
                    showUserAppEdit(true);
                    showAllAppEdit(true);

                }
            }
        };

        ((TextView) findViewById(R.id.title_middle_text)).setText("应用中心");
        tv_title_right = ((TextView) findViewById(R.id.title_right_text));
        tv_title_right.setText("编辑");
        emptyView = EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_general, this, R.string.default_no_application);

        dragGrid = ((RecyclerView) findViewById(R.id.userGridView));
        gridView_02 = ((MyGridView) findViewById(R.id.grid_view_02));
        list_01 = new ArrayList<>();
        list_02 = new ArrayList<>();

        list_01.addAll(((ArrayList) getIntent().getSerializableExtra("application")));

        dragGrid.setLayoutManager(new GridLayoutManager(this, 4));
        dragAdapter = new DragItemAdapter(this, this, list_01, onItemClick);
        mItemTouchHelper = new ItemTouchHelper(new DragItemCallBack(this));
        mItemTouchHelper.attachToRecyclerView(dragGrid);
        dragGrid.setAdapter(dragAdapter);

        adapter_02 = new ApplicationAdapter(this, list_02, gridView_02, 2);
        gridView_02.setAdapter(adapter_02);
        gridView_02.setEmptyView(emptyView);

        gridView_02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //没显示编辑标记位时跳转到相应的界面
                if (!adapter_02.isShow()) {

                    HuodongBean bean = ((HuodongBean) list_02.get(position));
                    if (!bean.isEmpty() && !StringUtil.isEmpty(bean.getAppUrl())) {
                        MyWebActivity.start(EditApplicationActivity.this, bean.getAppUrl());
                    }
                    //显示编辑标记位时，点击每个条目为添加应用
                } else {
                    item = list_02.get(position);
                    //如果该应用已经被添加，者全部应用中不删除该item
                    if (!GroupOrClassActionUtils.isAlreadAdd(list_01, item)) {
                        ((HuodongBean) list_02.get(position)).setAdd(true);
                        list_01.add(item);
                        dragAdapter.notifyDataSetChanged();
                        adapter_02.notifyDataSetChanged();
                    }
                }


            }
        });


        //所有应用每个条目的常点击事件监听
        gridView_02.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                adapter_02.setShow(true);
                tv_title_right.setText("完成");
                showUserAppEdit(true);

                return true;
            }
        });


        ViewUtils.getGridViewHeight(gridView_02);
        getAllApp();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:
                doBackAction();
                break;

            case R.id.title_right:
                if (!StringUtil.isEmpty(tv_title_right.getText().toString())) {
                    if (tv_title_right.getText().toString().equals("完成")) {
                        tv_title_right.setText("编辑");
                        showUserAppEdit(false);
                        showAllAppEdit(false);
                        postEditInfor();

                    } else {
                        tv_title_right.setText("完成");
                        showUserAppEdit(true);
                        showAllAppEdit(true);
                    }
                }


                break;
        }
    }


    //提交用户编辑后的应用信息
    private void postEditInfor() {

        if (list_01.size() > 0) {
            GroupOrClassActionUtils.removeEmptyItem(list_01);
            Map<String, Object> map = new HashMap();
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < list_01.size(); i++) {

                Object o = list_01.get(i);
                HuodongBean b = ((HuodongBean) o);
                builder.append(b.getAppKey() + ",");
            }
            builder.deleteCharAt(builder.lastIndexOf(","));

            Log.e("postEditInfor", builder.toString());
            map.put("ordervalue", builder.toString());

//            HiWorldCache.postHttpData(this, handler, 3, HistudentUrl.sortUserApp, map, HiWorldCache.Quarry, false, true);
            HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.sortUserApp, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    isChanged = true;
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            }, LoadingType.FLOWER);
        } else {

            ReminderHelper.getIntentce().ToastShow(this, "未选择任何对象！");
        }
    }

    //返回时通知更新
    private void setResult() {

        if (isChanged) {
            setResult(200);
        }
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            doBackAction();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void doBackAction() {
        //返回时先将 编辑状态还原
        tv_title_right.setText("编辑");
        if (adapter_02.isShow() || dragAdapter.isShow()) {

            showUserAppEdit(false);
            showAllAppEdit(false);

            //然后在返回
        } else {
            setResult();
        }
    }

    @Override
    public void itemOnClick(int position, View view) {

        //点击删除
        if (view.getId() == R.id.del) {
            item = list_01.remove(position);
            dragAdapter.setData(list_01);
            dragAdapter.notifyDataSetChanged();
            changeAppState();
            adapter_02.notifyDataSetChanged();
            item = null;
        } else {

            //非移动状态时点击进入应用界面
            if (!dragAdapter.isShow()) {
                HuodongBean bean = ((HuodongBean) list_01.get(position));
                if (!bean.isEmpty() && !StringUtil.isEmpty(bean.getAppUrl())) {
                    MyWebActivity.start(EditApplicationActivity.this, bean.getAppUrl());
                }
            }
        }
    }

    @Override
    public void onMove(int from, int to) {
        synchronized (this) {

            if (from > to) {
                int count = from - to;
                for (int i = 0; i < count; i++) {
                    Collections.swap(list_01, from - i, from - i - 1);
                }
            }
            if (from < to) {
                int count = to - from;
                for (int i = 0; i < count; i++) {
                    Collections.swap(list_01, from + i, from + i + 1);
                }
            }
            dragAdapter.setData(list_01);
            dragAdapter.notifyItemMoved(from, to);
            dragAdapter.show.clear();
            dragAdapter.setShow(true);
            dragAdapter.show.put(to, to);
        }
    }


    //显示或者隐藏用户使用的应用的编辑状态
    private void showUserAppEdit(boolean isShow) {

        if (isShow != dragAdapter.isShow()) {
            dragAdapter.setShow(isShow);
            dragAdapter.notifyDataSetChanged();
        }
    }


    //显示或者隐藏所用应有的编辑状态
    private void showAllAppEdit(boolean isShow) {

        if (isShow != adapter_02.isShow()) {
            adapter_02.setShow(isShow);
            adapter_02.notifyDataSetChanged();
        }
    }


    public interface onItemClick {
        void onItemClick(HuodongBean huodongBean, boolean isLongClick);
    }

    //获取所有的应用
    private void getAllApp() {
        Map<String, Object> map = new HashMap<>();
        AddressInforBean userLocationInfor = HiWorldCache.getUserLocationInfor();
        String city = userLocationInfor.getCity();
        String area = userLocationInfor.getAreaStr();
        if (TextUtils.isEmpty(city) && TextUtils.isEmpty(area)) {
            map.put(ParamKeys.CITY_NAME, city);
            map.put(ParamKeys.AREA_NAME, area);
        } else {
            map.put(ParamKeys.CITY_NAME, "");
            map.put(ParamKeys.AREA_NAME, "");
        }
//        HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.getAllApp, new HashMap<String, Object>(), HiWorldCache.Quarry, true,true);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.getAllApp, new HttpRequestCallBack() {

            @Override
            public void onSuccess(String result) {
                List<HuodongBean> beanList = JSON.parseArray(result, HuodongBean.class);
                if (beanList != null && beanList.size() > 0) {
                    list_02.addAll(beanList);
                }
                changeAppState();
                adapter_02.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.FLOWER);
    }

}
