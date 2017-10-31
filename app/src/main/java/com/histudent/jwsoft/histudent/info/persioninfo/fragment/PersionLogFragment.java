package com.histudent.jwsoft.histudent.info.persioninfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.UserBlogItemModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.adapter.AllItemAdapter;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ItemType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/12/21.
 */

public class PersionLogFragment extends BaseFragment {

    private View view;
    private PullToRefreshListView log_list;
    private AllItemAdapter adapter_log;
    private List<ActionListBean> list_logs;
    private int pageIndex;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 3:
                    log_list.onRefreshComplete();
                    UserBlogItemModel model = DataParser.getBlogParser(msg.obj.toString());
                    List<ActionListBean> lists = ItemDataExchangeUtils.dataExchange_Blog(model.getItems());
                    if (lists.size() > 0) {
                        pageIndex++;
                        list_logs.addAll(lists);
                        adapter_log.notifyDataSetChanged();
                    } else {
                        if (pageIndex != 0)
                            Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
                    }
                    break;

                case -3:
                    list_logs.remove(msg.arg1);
                    adapter_log.notifyDataSetChanged();

                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_persion_log, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();

        list_logs = new ArrayList<>();
        log_list = (PullToRefreshListView) view.findViewById(R.id.persion_log_list);
        log_list.setMode(PullToRefreshBase.Mode.BOTH);
        adapter_log = new AllItemAdapter(getActivity(), list_logs, ItemType.PERSON_LOG);
        log_list.setAdapter(adapter_log);
        log_list.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_rizhi, getActivity(), R.string.have_no_persionLogs));
    }

    @Override
    public void initData() {
        super.initData();

        log_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list_logs.clear();
                pageIndex = 0;
                PersionHelper.getInstance().getPersionBlogs((BaseActivity) getActivity(), pageIndex, handler, 3, false,false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                PersionHelper.getInstance().getPersionBlogs((BaseActivity) getActivity(), pageIndex, handler, 3, false,false);
            }
        });

        adapter_log.setDeletListener(new AllItemAdapter.DeletListener() {
            @Override
            public void delet(final String id, final int position) {
                ReminderHelper.getIntentce().showDialog(getActivity(), "删除日志", "是否删除该项日志？", "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {
                        PersionHelper.getInstance().deletePersionBlog((BaseActivity) getActivity(), id, handler, -3, position);
                    }
                }, "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                });
            }
        });

        PersionHelper.getInstance().getPersionBlogs((BaseActivity) getActivity(), 0, handler, 3, true,true);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 200) {
            list_logs.clear();
            pageIndex = 0;
            PersionHelper.getInstance().getPersionBlogs((BaseActivity) getActivity(), pageIndex, handler, 3, true,true);
        } else {
            adapter_log.notifyDataSetChanged();
        }

    }

}
