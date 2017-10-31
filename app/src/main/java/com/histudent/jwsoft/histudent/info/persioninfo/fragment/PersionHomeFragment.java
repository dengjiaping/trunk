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
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.adapter.AllItemAdapter;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ItemType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.receiver.RequestCodeModel;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/12/21.
 */

public class PersionHomeFragment extends BaseFragment {

    private View view;
    private PullToRefreshListView time_list;
    private AllItemAdapter adapter_time;
    private List<ActionListBean> list_times;
    private int timeCursor;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 1:

                    UserTimeModel model = DataParser.getUserTimeModel(msg.obj.toString());
                    time_list.onRefreshComplete();
                    if (model.getItems().size() > 0) {
                        timeCursor = model.getCursor();
                        list_times.addAll(ItemDataExchangeUtils.dataExchange(model.getItems()));
                        adapter_time.notifyDataSetChanged();
                    } else {
                        if (timeCursor != 0)
                            Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_persion_home, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();

        list_times = new ArrayList<>();
        time_list = (PullToRefreshListView) view.findViewById(R.id.persion_home_list);
        time_list.setMode(PullToRefreshBase.Mode.BOTH);
        adapter_time = new AllItemAdapter(getActivity(), list_times, ItemType.PERSON_HOME);
        time_list.setAdapter(adapter_time);
        time_list.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_dynamic, getActivity(), R.string.have_no_persionActions));
    }

    @Override
    public void initData() {
        super.initData();

        time_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list_times.clear();
                timeCursor = 0;
                PersionHelper.getInstance().getPersionTimeline((BaseActivity) getActivity(), timeCursor, handler, 1, LoadingType.NONE);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                PersionHelper.getInstance().getPersionTimeline((BaseActivity) getActivity(), timeCursor, handler, 1, LoadingType.NONE);
            }
        });

        PersionHelper.getInstance().getPersionTimeline((BaseActivity) getActivity(), 0, handler, 1, LoadingType.FLOWER);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeModel.getInstence().TIME) {
            adapter_time.notifyDataSetChanged();
        } else if (requestCode == 10 && resultCode == 200) {
            list_times.clear();
            timeCursor = 0;
            PersionHelper.getInstance().getPersionTimeline((BaseActivity) getActivity(), timeCursor, handler, 1, LoadingType.FLOWER);
        }
    }

}
