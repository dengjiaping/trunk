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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.adapter.AllItemAdapter;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ItemType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/12/21.
 */

public class PersionEssayFragment extends BaseFragment {

    private View view;
    private PullToRefreshListView essay_list;
    private AllItemAdapter adapter_essay;
    private List<ActionListBean> list_essays;
    private int pageIndex;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 2:

//                    essay_list.onRefreshComplete();
////                    UserEssayModel model = DataParser.getUserEssayModel(msg.obj.toString());
////                    List<ActionListBean> lists = ItemDataExchangeUtils.dataExchange_Essay(model.getItems());
//                    if (lists.size() > 0) {
//                        pageIndex++;
//                        list_essays.addAll(lists);
//                        adapter_essay.notifyDataSetChanged();
//                    } else {
//                        if (pageIndex != 0)
//                            Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
//                    }

                    break;

                case -2:

                    list_essays.remove(msg.arg1);
                    adapter_essay.notifyDataSetChanged();

                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_persion_essay, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();

        list_essays = new ArrayList<>();
        essay_list = (PullToRefreshListView) view.findViewById(R.id.persion_essay_list);
        essay_list.setMode(PullToRefreshBase.Mode.BOTH);
        adapter_essay = new AllItemAdapter(getActivity(), list_essays, ItemType.PERSON_ESSAY);
        essay_list.setAdapter(adapter_essay);
        essay_list.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_essay, getActivity(), R.string.have_no_persionEssay));
    }

    @Override
    public void initData() {
        super.initData();

        essay_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list_essays.clear();
                pageIndex = 0;
                PersionHelper.getInstance().getPersionEssays((BaseActivity) getActivity(), pageIndex, handler, 2, LoadingType.NONE);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                PersionHelper.getInstance().getPersionEssays((BaseActivity) getActivity(), pageIndex, handler, 2, LoadingType.NONE);
            }
        });

        adapter_essay.setDeletListener(new AllItemAdapter.DeletListener() {
            @Override
            public void delet(final String id, final int position) {

                ReminderHelper.getIntentce().showDialog(getActivity(), "删除随记", "是否删除该项随记？", "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                        PersionHelper.getInstance().deletePersionEssay((BaseActivity) getActivity(), id, handler, -2, position);
                    }
                }, "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                });
            }
        });

        PersionHelper.getInstance().getPersionEssays((BaseActivity) getActivity(), 0, handler, 2, LoadingType.FLOWER);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            list_essays.clear();
            pageIndex = 0;
            PersionHelper.getInstance().getPersionEssays((BaseActivity) getActivity(), pageIndex, handler, 2, LoadingType.NONE);
        } else {
            adapter_essay.notifyDataSetChanged();
        }
    }

}
