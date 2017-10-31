package com.histudent.jwsoft.histudent.body.hiworld.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.bean.AboutMineBean;
import com.histudent.jwsoft.histudent.body.hiworld.bean.AboutMineRedFlagBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.adapter.AllItemAdapter;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ItemType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.listener.ParserCallBack;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/6.
 * 与我相关
 */
public class HiWorldChild_ourFragmnet extends BaseFragment implements AllItemAdapter.AdapterCallBack {

    private PullToRefreshListView pullToRefreshListView;
    private FrameLayout cotainer, frameLayout;
    private AllItemAdapter adapter_;
    private List<ActionListBean> list_all;
    private AboutMineBean model;
    private int timeCursor = 0;
    private int pageSize;
    private View view;
    private BroadcastReceiver receiver;
    private int position01 = -1;
    private int position02 = -1;
    private int flag = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hiworldchild_ourfragmnet, container, false);
        doWorkByResevier();

        return view;
    }

    @Override
    public void initView() {
        super.initView();

        pageSize = Integer.parseInt(getResources().getString(R.string.pageSize));
        list_all = new ArrayList<>();
        adapter_ = new AllItemAdapter(getActivity(), list_all, ItemType.HI_ABOUTMINE, this);
        pullToRefreshListView = ((PullToRefreshListView) view.findViewById(R.id.pull_to_refresh));
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setAdapter(adapter_);

        getShowData(LoadingType.FLOWER);

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                reFreshListData(LoadingType.NONE);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getShowData(LoadingType.NONE);

            }
        });

        cotainer = ((FrameLayout) view.findViewById(R.id.container));
        initEmotionMainFragment();
    }

    /**
     * 获取需要显示的数据源
     */
    private void getShowData(LoadingType flag) {

        final Map<String, Object> map_post = new TreeMap<>();

        map_post.put("timeCursor", timeCursor + "");
        map_post.put("pageSize", pageSize + "");

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map_post, HistudentUrl.aboutMine_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                pullToRefreshListView.onRefreshComplete();
                model = JSON.parseObject(result, AboutMineBean.class);

                if (model != null && model.getItems().size() > 0) {

                    if (timeCursor == 0) {
                        AboutMineRedFlagBean.getIntence().setRtimePoint(model.getStartTime() + "");
                    }

                    sortRecentContactsModel(model.getItems());
                    list_all.addAll(ItemDataExchangeUtils.dataExchange_AboutMine(model.getItems()));
                    ItemDataExchangeUtils.sortRecentContacts(list_all);

                    adapter_.notifyDataSetChanged();
                    timeCursor = model.getCursor();
                    EventBus.getDefault().post(3);

                } else {
                    if (list_all.size() == 0) {
                        pullToRefreshListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_dynamic, getActivity(), R.string.have_no_action));
                    }
                    if (timeCursor != 0)
                        Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(String msg) {
                pullToRefreshListView.onRefreshComplete();
            }
        }, flag);
    }


    public void doWorkByResevier() {

        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(TheReceiverAction.HIDE_FLOOTACTIONBAR);
        myIntentFilter.addAction(TheReceiverAction.DELET_ABOUTMINE_COMMENT_02);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(TheReceiverAction.HIDE_FLOOTACTIONBAR)) {
                    position01 = intent.getIntExtra("position01", -1);
                    flag = intent.getIntExtra("position02", -1);
                    if (flag > -1) {
                        position02 = list_all.get(position01).getItemsBeen().size() - 1 - flag;
                        emotionMainFragment.setEditText("回复 " + list_all.get(position01).getItemsBeen().get(position02).getUser().getName() + ":");
                    } else {
                        emotionMainFragment.setEditText("");
                    }
                } else if (intent.getAction().equals(TheReceiverAction.DELET_ABOUTMINE_COMMENT_02)) {
                    position01 = intent.getIntExtra("position01", -1);
                    position02 = list_all.get(position01).getItemsBeen().size() - 1 - intent.getIntExtra("position02", -1);
                    int comment = list_all.get(position01).getCommentCount() - 1;
                    list_all.get(position01).getItemsBeen().remove(position02);
                    list_all.get(position01).setCommentCount(comment);
                    adapter_.notifyDataSetChanged();
                }
            }
        };
        getActivity().registerReceiver(receiver, myIntentFilter);
    }

    @Override
    public void onDestroy() {
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    /**
     * 发表评论
     *
     * @param content
     * @param position01
     * @param position02
     */
    private void publicCommentData(String content, int position01, int position02) {

        if (TextUtils.isEmpty(content)) return;

        ActionListBean itemsBean = list_all.get(position01);
        Map<String, Object> map = new TreeMap<>();
        map.put("content", content);
        if (flag > -1) {
            map.put("parentId", itemsBean.getItemsBeen().get(position02).getCommentId());
            map.put("toUserId", itemsBean.getItemsBeen().get(position02).getUser().getUserId());
        } else {
            map.put("parentId", "");
            map.put("toUserId", "");
        }
        map.put("ownerId", itemsBean.getOwnerId());
        map.put("relatedMainId", itemsBean.getSourceId());
        int objectType = 0;
        switch (itemsBean.getActivityItemKey()) {
            case "UploadPhoto":
                objectType = 1;
                break;
            case "CreateBlog":
                objectType = 2;
                break;
            case "CreateMicroBlog":
                objectType = 3;
                break;
        }
        map.put("objectType", objectType);
        map.put("objectId", itemsBean.getActivityItemKey().equals("UploadPhoto") ? itemsBean.getActId() : itemsBean.getSourceId());

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.publicComment_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                getActivity().sendBroadcast(new Intent(TheReceiverAction.SHOW_FLOOTACTIONBAR));

                timeCursor = 0;
                list_all.clear();
                getShowData(LoadingType.NONE);

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    /**
     * 初始化表情面板
     */
    private EmotionMainFragment emotionMainFragment;

    public void initEmotionMainFragment() {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false);
        //替换fragment
        //创建修改实例
        emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
        emotionMainFragment.bindToContentView(pullToRefreshListView);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
//        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();

        emotionMainFragment.setCallBack(new ParserCallBack() {
            @Override
            public void parser(String result) {

                publicCommentData(result, position01, position02);

            }
        });
    }

    /**
     * **************************** 排序 ***********************************
     */
    private void sortRecentContactsModel(List<AboutMineBean.ItemsBean> list) {
        if (list.size() == 0) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Collections.sort(list.get(i).getComments(), comp);
        }
    }

    private static Comparator<AboutMineBean.ItemsBean.CommentsBean> comp = new Comparator<AboutMineBean.ItemsBean.CommentsBean>() {

        @Override
        public int compare(AboutMineBean.ItemsBean.CommentsBean o1, AboutMineBean.ItemsBean.CommentsBean o2) {
            long time = TimeUtils.getTimeLong(o2.getCreateTime()) - TimeUtils.getTimeLong(o1.getCreateTime());
            return time == 0 ? 0 : (time > 0 ? -1 : 1);
        }
    };

    /**
     * 刷新数据源
     */
    public void reFreshListData(LoadingType flag) {
        if (list_all != null)
            list_all.clear();
        timeCursor = 0;
        getShowData(flag);
        pullToRefreshListView.getRefreshableView().setSelection(0);
    }

    @Override
    public void refrensh() {
        reFreshListData(LoadingType.FLOWER);
    }
}
