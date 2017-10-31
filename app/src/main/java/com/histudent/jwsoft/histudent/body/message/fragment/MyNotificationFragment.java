package com.histudent.jwsoft.histudent.body.message.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.MyNotificationAdapter;
import com.histudent.jwsoft.histudent.body.message.model.NoticeBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuguiyu-pc on 2017/4/16.
 */

public class MyNotificationFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView comment, about_me, add_see;
    private final int COMMENT = 0;
    private final int ABOUT = 1;
    private final int ADDSEE = 2;
    private int timeCursor = 0;
    private MyNotificationAdapter myAdapter;
    private List<NoticeBean.ItemsBean> models;
    private PullToRefreshListView listView;
    private int type = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        models = new ArrayList<>();
        myAdapter = new MyNotificationAdapter(getActivity(), models);
        listView = (PullToRefreshListView) view.findViewById(R.id.nofication_list);
        comment = (TextView) view.findViewById(R.id.comment);
        about_me = (TextView) view.findViewById(R.id.about_me);
        add_see = (TextView) view.findViewById(R.id.add_see);
    }

    @Override
    public void initData() {
        super.initData();

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setAdapter(myAdapter);
        comment.setOnClickListener(this);
        about_me.setOnClickListener(this);
        add_see.setOnClickListener(this);

        myAdapter.setType(type);
        getNoticeList(type);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                reFresh(type);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getNoticeList(type);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NoticeBean.ItemsBean itemsBean = models.get(i - 1);
                if (itemsBean != null) {
                    if (itemsBean.getInfoStatus() == 1) {
                        ReminderHelper.getIntentce().ToastShow(getActivity(), "该动态已被删除");
                    } else if (itemsBean.getInfoStatus() == 2) {

                    } else {
                        CommentActivity.start(getActivity(), itemsBean.getActId(), i - 1);
                    }
                }

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.comment:
                exchangeButton(COMMENT);
                break;
            case R.id.about_me:
                exchangeButton(ABOUT);
                break;
            case R.id.add_see:
                exchangeButton(ADDSEE);
                break;
        }

    }

    private void exchangeButton(int index) {
        switch (index) {
            case COMMENT:
                comment.setBackgroundResource(R.drawable.green_left_round_bg);
                comment.setTextColor(getResources().getColor(R.color.text_white));
                about_me.setBackground(null);
                about_me.setTextColor(getResources().getColor(R.color.green_color));
                add_see.setBackground(null);
                add_see.setTextColor(getResources().getColor(R.color.green_color));
                type = 5;
                reFresh(5);
                break;
            case ABOUT:
                comment.setBackground(null);
                comment.setTextColor(getResources().getColor(R.color.green_color));
                about_me.setBackgroundResource(R.color.green_color);
                about_me.setTextColor(getResources().getColor(R.color.text_white));
                add_see.setBackground(null);
                add_see.setTextColor(getResources().getColor(R.color.green_color));
                comment.setTextColor(getResources().getColor(R.color.green_color));
                type = 6;
                reFresh(6);
                break;
            case ADDSEE:
                comment.setBackground(null);
                comment.setTextColor(getResources().getColor(R.color.green_color));
                about_me.setBackground(null);
                about_me.setTextColor(getResources().getColor(R.color.green_color));
                add_see.setBackgroundResource(R.drawable.green_right_round_bg);
                add_see.setTextColor(getResources().getColor(R.color.text_white));
                type = 7;
                reFresh(7);
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (type == 7)
            reFresh(type);
    }

    /**
     * 获取通知列表
     */
    private void getNoticeList(int type) {
        Map<String, Object> map = new HashMap();
        map.put("pmType", type);
        map.put("timeCursor", timeCursor);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.noticeList, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                listView.onRefreshComplete();
                NoticeBean bean = JSON.parseObject(result, NoticeBean.class);
                if (bean.getItems().size() == 0) {
                    listView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_comment_zan, getContext(), R.string.empty_no_message));
                } else {
                    if (bean != null && bean.getItems().size() >= 0) {
                        timeCursor = bean.getCursor();
                        models.addAll(bean.getItems());
                        myAdapter.notifyDataSetChanged();
                    } else {
                        ReminderHelper.getIntentce().ToastShow(getContext(), getActivity().getResources().getString(R.string.no_more_information));
                    }
                }
                EventBus.getDefault().post(true);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    /**
     * 刷新
     */
    private void reFresh(int type) {
        models.clear();
        timeCursor = 0;
        myAdapter.setType(type);
        getNoticeList(type);
    }

}
