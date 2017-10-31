package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.DraftAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.LogModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.histudent.jwsoft.histudent.comment2.utils.SuccessCallBack;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 草稿箱
 */
public class DraftActivity extends BaseActivity {

    private List<LogModel.ItemsBean> logList;
    private DraftAdapter draftAdapter;
    private PullToRefreshListView draftListView;
    private int pageIndex;

    private onItemClick onItemClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static void start(Activity activity, int requstCode) {
        Intent intent = new Intent(activity, DraftActivity.class);
        activity.startActivityForResult(intent, requstCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_draft;
    }

    @Override
    public void initView() {

        onItemClick = new onItemClick() {
            @Override
            public void onItemClick(String id) {
                if (!StringUtil.isEmpty(id)) {


                    Log.e("onItemClick--", id);
//                    setResult(id);
                }
            }
        };
        ((TextView) findViewById(R.id.title_middle_text)).setText("草稿箱");

        draftListView = ((PullToRefreshListView) findViewById(R.id.listview));
        logList = new ArrayList<>();
        draftAdapter = new DraftAdapter(this, logList, onItemClick);
        draftListView.setAdapter(draftAdapter);

        initRefreshListView();

        getDraftList(false);
    }

    private void initRefreshListView() {
        PullToRefreshUtils.initPullToRefreshListView2(draftListView);

        draftListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getDraftList(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getDraftList(true);
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.title_left:
                setResult(-2);
                finish();
                break;

        }
    }


    //选择草稿，传递草稿内容
    private void setResult(String id) {

        Intent intent = new Intent();
        intent.putExtra("blogId", id);//返回选择的草稿日志id
        setResult(200, intent);
        finish();
    }


    private void getDraftList(boolean isMore) {
        if (isMore) {
            pageIndex++;
        } else {
            pageIndex = 0;
            logList.clear();
        }
        DataUtils.getDraftList(this, logList, pageIndex, new SuccessCallBack() {
            @Override
            public void onSuccess() {
                if (draftListView.isRefreshing()) {
                    draftListView.onRefreshComplete();
                }
                draftAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed() {

            }
        });

    }

    public interface onItemClick {
        public void onItemClick(String id);
    }

}
