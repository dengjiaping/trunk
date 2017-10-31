package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.activity.ActionBaseActivity;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/6/19.
 * 屏蔽的动态
 */

public class ClassShieldActivity extends ActionBaseActivity {

    private TextView title;
    private PullToRefreshListView class_action_list;
    private UserTimeModel model;
    private List<ActionListBean> list_datas;
    private ActionAdapter actionAdapter;
    private String classId;
    private int timeCursor;

    public static void start(Activity activity, String classId) {
        if (TextUtils.isEmpty(classId)) return;
        Intent intent = new Intent(activity, ClassShieldActivity.class);
        intent.putExtra("classId", classId);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_shield;
    }

    @Override
    public void initView() {

        classId = getIntent().getStringExtra("classId");

        title = (TextView) findViewById(R.id.title_middle_text);
        class_action_list = (PullToRefreshListView) findViewById(R.id.class_action_list);

        list_datas = new ArrayList<>();
        actionAdapter = new ActionAdapter(this, list_datas, ActionAdapter.CLASS_CIRCLE);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("屏蔽的动态");
        class_action_list.setMode(PullToRefreshBase.Mode.BOTH);
        class_action_list.setAdapter(actionAdapter);

        getClassAction();


        class_action_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                timeCursor = 0;
                list_datas.clear();
                getClassAction();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getClassAction();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(ActionAdapter.Refresh refresh) {
        timeCursor = 0;
        list_datas.clear();
        getClassAction();
    }

    /**
     * 获取班级动态(屏蔽)
     */
    private void getClassAction() {
        final Map<String, Object> map_post = new TreeMap<>();
        map_post.put("timeCursor", timeCursor);
        map_post.put("pageSize", 8);
        map_post.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp(this, map_post, HistudentUrl.disableActivities_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                class_action_list.onRefreshComplete();
                model = DataParser.getUserTimeModel(result);
                if (model != null) {
                    List<ActionListBean> beens = ItemDataExchangeUtils.dataExchange(model.getItems());
                    if (beens.size() > 0) {
                        timeCursor = model.getCursor();
                    } else {
                        if (timeCursor == 0) {
                            class_action_list.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_state,
                                    ClassShieldActivity.this, R.string.empty_no_state_));
                        } else {
                            Toast.makeText(ClassShieldActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                        }
                    }
                    list_datas.addAll(beens);
                    actionAdapter.setisShield(true);
                    actionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                class_action_list.onRefreshComplete();
            }
        });
    }

    @Override
    protected void reFreshActionList(int position, ActionListBean bean) {
        list_datas.set(position, bean);
        actionAdapter.notifyDataSetChanged();
    }

    @Override
    protected void reMoveAction(int position) {
        list_datas.remove(position);
        actionAdapter.notifyDataSetChanged();
    }
}
