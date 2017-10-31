package com.histudent.jwsoft.histudent.body.hiworld.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.bean.AboutMineRedFlagBean;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.adapter.AllItemAdapter;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ItemType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.receiver.RequestCodeModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.changeBackUtils;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/6.
 * 好友动态
 */
public class HiWorldChild_friendFragmnet extends BaseFragment implements AllItemAdapter.AdapterCallBack {

    private View view;
    private PullToRefreshListView pullToRefreshListView;
    private Intent intent;
    private FrameLayout cotainer, frameLayout;
    private AllItemAdapter adapter_;
    private List<ActionListBean> list_all;
    private UserTimeModel model;
    private int timeCursor = 0;
    private int pageSize;
    private int flag_share;
    private String share_content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hiworldchild_friendfragmnet, container, false);
        return view;

    }

    @Override
    public void initView() {
        super.initView();

        pageSize = Integer.parseInt(getResources().getString(R.string.pageSize));
        list_all = new ArrayList<>();
        adapter_ = new AllItemAdapter(getActivity(), list_all, ItemType.HI_FRIEND_ACTION, this);
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
        frameLayout = ((FrameLayout) view.findViewById(R.id.frame));
        changeBackUtils.setFrame(frameLayout, 0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeModel.getInstence().PUBLIC && resultCode == 200) {
            if (data != null) {
                flag_share = data.getIntExtra("flag_share", 0);
                share_content = data.getStringExtra("share_content");
            }
            reFreshListData(LoadingType.FLOWER);
        } else if (requestCode == RequestCodeModel.getInstence().GOODFRIENDA_ACTION) {
            adapter_.notifyDataSetChanged();
        }else if(requestCode == 500 && resultCode == 200){
            reFreshListData(LoadingType.FLOWER);
        }
    }

    /**
     * 获取需要显示的数据源
     */
    private void getShowData(LoadingType flag) {

        final Map<String, Object> map_post = new TreeMap<>();

        map_post.put("timeCursor", timeCursor + "");
        map_post.put("pageSize", pageSize + "");

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map_post, HistudentUrl.goodFriendAction_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                pullToRefreshListView.onRefreshComplete();
                model = DataParser.getUserTimeModel(result);
                if (model != null && model.getItems().size() > 0) {

                    if (timeCursor == 0) {
                        AboutMineRedFlagBean.getIntence().setFtimePoint(model.getStartTime() + "");
                        if ("0".endsWith(AboutMineRedFlagBean.getIntence().getCtimePoint()) && "0".endsWith(AboutMineRedFlagBean.getIntence().getRtimePoint())) {
                            AboutMineRedFlagBean.getIntence().setCtimePoint(model.getStartTime() + "");
                            AboutMineRedFlagBean.getIntence().setRtimePoint(model.getStartTime() + "");
                        }
                    }

                    list_all.addAll(ItemDataExchangeUtils.dataExchange(model.getItems()));
                    ItemDataExchangeUtils.sortRecentContacts(list_all);

                    adapter_.notifyDataSetChanged();
                    timeCursor = model.getCursor();
                    EventBus.getDefault().post(1);

                } else {

                    if (list_all.size() == 0) {
                        pullToRefreshListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.mipmap.default_dynamic, getActivity(), R.string.have_no_action));
                    }
                    if (timeCursor != 0)
                        Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
//                if (flag_share == 1) {
//                    ShareUtils.getShareData_(getActivity(), share_content, ShareUtils.WEIXIN_CIRCLE);
//                } else if (flag_share == 2) {
//                    ShareUtils.getShareData_(getActivity(), share_content, ShareUtils.QQ);
//                }
                flag_share = 0;
            }

            @Override
            public void onFailure(String msg) {
                pullToRefreshListView.onRefreshComplete();
            }
        }, flag);
    }

    /**
     * 刷新数据源
     */
    public void reFreshListData(LoadingType flag) {
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
