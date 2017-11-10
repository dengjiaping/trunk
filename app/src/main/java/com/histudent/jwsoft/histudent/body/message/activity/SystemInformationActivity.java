package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.homework.WorkDetailStudentActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkUndoneActivity;
import com.histudent.jwsoft.histudent.body.message.adapter.OfficialChatAdapter;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.CustomAttachmentType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.ChatType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;
import com.histudent.jwsoft.histudent.body.myclass.activity.HuoDongCenterActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.WebViewHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by liuguiyu-pc on 2016/7/4.
 * 系统消息展示
 */
public class SystemInformationActivity extends BaseActivity {
    private PullToRefreshListView listView;
    private TextView title;
    private IconView right_image;
    private OfficialChatAdapter adapter;
    private List<RecentContactsModel> list;
    private View emptyView;
    private int flag = 0;
    private String accountId;

    @BindView(R.id.srl_refresh_layout)
    RefreshLayout mRefresh;

    /**
     * @param activity
     * @param flag
     */
    public static void start(Activity activity, int flag, String accountId) {
        Intent intent = new Intent(activity, SystemInformationActivity.class);
        intent.putExtra("flag", flag);
        intent.putExtra("accountId", accountId);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.chat_activity;
    }

    @Override
    public void initView() {
        flag = getIntent().getIntExtra("flag", 0);
        accountId = getIntent().getStringExtra("accountId");

        list = new ArrayList<>();
        adapter = new OfficialChatAdapter(this, list, flag);

        listView = (PullToRefreshListView) findViewById(R.id.chat_list);
        title = (TextView) findViewById(R.id.title_middle_text);
        right_image = (IconView) findViewById(R.id.title_right_image);
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
        listView.setAdapter(adapter);
        mRefresh.setEnableLoadmore(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getDatas();
            }
        });
        getDatas();
    }

    @Override
    public void doAction() {
        super.doAction();

        switch (flag) {
            case ChatType.SYSTEMINFO:
                title.setText("系统消息");
                emptyView = EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_request, this, R.string.empty_no_message);
                listView.setEmptyView(emptyView);
                break;
            case ChatType.SUBSCIBR:
                emptyView = EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_request, this, R.string.empty_no_order);
                listView.setEmptyView(emptyView);
                title.setText("订阅号");
                break;
            case ChatType.ACTION:
                title.setText("官方活动");
                emptyView = EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_request, this, R.string.empty_no_group);
                listView.setEmptyView(emptyView);
                break;
        }

        right_image.setVisibility(View.VISIBLE);
        right_image.setText(R.string.icon_id);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecentContactsModel msgModel = list.get(i - 1);
                switch (msgModel.getImmsgtype()) {
                    case CustomAttachmentType.HomeWork://家庭作业 跳转学生详情作业
                        //获取作业homeworkId(pushId)
                        solveHomeworkDetail(msgModel);
                        break;
                    case CustomAttachmentType.HUODONG_G://班级活动
                        if (msgModel.getOpenmode() == 1) {
                            MyWebActivity.start(SystemInformationActivity.this, msgModel.getUrl());
                        } else if (msgModel.getOpenmode() == 2) {
                            HuoDongCenterActivity.start(SystemInformationActivity.this, msgModel.getOpenParam(), 1, 0);
                        }
                        break;
                    case CustomAttachmentType.HUODONG_C://社群活动
                        if (msgModel.getOpenmode() == 1) {
                            MyWebActivity.start(SystemInformationActivity.this, msgModel.getUrl());
                        } else if (msgModel.getOpenmode() == 2) {
                            HuoDongCenterActivity.start(SystemInformationActivity.this, msgModel.getOpenParam(), 2, 20);
                        }
                        break;
                    default:
                        MyWebActivity.start(SystemInformationActivity.this, WebViewHelper.checkUrl(msgModel.getUrl()));
                        break;
                }
            }
        });

    }


    /**
     * 根据作业Id跳转相应页面
     *
     * @param msgModel
     */
    private void solveHomeworkDetail(RecentContactsModel msgModel) {
        //如果已完成 跳转学生完成详情页面  如果未完成 则去跳转去完成页面
        final String homeworkId = msgModel.getPushId();
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.HOMEWORK_ID, homeworkId)
                .getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(this, paramsMap, HistudentUrl.HOMEWORK_STATUS, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                final JSONObject jsonObject = JSONObject.parseObject(result);
                final Integer status = jsonObject.getInteger("status");
                //status 1已完成 2未完成 3已删除
                if (status == 3) {
                    ToastTool.showCommonToast("作业已取消");
                    return;
                }
                final Intent intent = new Intent();
                intent.putExtra(TransferKeys.HOMEWORK_ID, homeworkId);
                if (status == 1) {
                    intent.setClass(SystemInformationActivity.this, WorkDetailStudentActivity.class);
                } else if (status == 2) {
                    intent.setClass(SystemInformationActivity.this, WorkUndoneActivity.class);
                }
                CommonAdvanceUtils.startActivity(SystemInformationActivity.this, intent);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    private void getDatas() {

        List<RecentContactsModel> recentContactsModels = HiCache.getInstance().getSystemNotification(flag);
        mRefresh.finishRefresh();
        if (recentContactsModels != null) {
            list.clear();
            list.addAll(recentContactsModels);
            adapter.notifyDataSetChanged();
            scrollMyListViewToBottom();
        }
    }

    private void scrollMyListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.getRefreshableView().setSelection(adapter.getCount() - 1);
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
            case R.id.title_right:

                OfficialCentreActivity.start(this, flag);

                break;
        }
    }

}
