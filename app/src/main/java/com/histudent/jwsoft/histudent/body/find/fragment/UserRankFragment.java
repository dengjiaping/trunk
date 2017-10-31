package com.histudent.jwsoft.histudent.body.find.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.UserRankingAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.UserRankBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.histudent.jwsoft.histudent.R.id.class_rank_list;
import static com.histudent.jwsoft.histudent.R.id.index;

/**
 * Created by liuguiyu-pc on 2017/5/16.
 */

public class UserRankFragment extends BaseFragment {

    private View view;
    private PullToRefreshListView user_rank_list;
    private UserRankingAdapter userRankingAdapter;
    private List<UserRankBean.ItemsBean> itemsBeens;
    private int pageIndex;
    private String schoolId;
    private TextView name_text, actionNum, lev_text, index_num;
    private ImageView index_image;
    private HiStudentHeadImageView headImageView;
    private RelativeLayout item_layout;
    private SmartRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_rank_page, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        schoolId = getArguments().getString("schoolId");
        final View headView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_user_rank_head, null);
        index_image = headView.findViewById(index);
        name_text = headView.findViewById(R.id.name);
        index_num = headView.findViewById(R.id.index_num);
        actionNum = headView.findViewById(R.id.actionNum);
        lev_text = headView.findViewById(R.id.lev_text);
        headImageView = headView.findViewById(R.id.headImage);
        item_layout = headView.findViewById(R.id.item_layout);

        user_rank_list = view.findViewById(class_rank_list);

        user_rank_list.setMode(PullToRefreshBase.Mode.BOTH);
        itemsBeens = new ArrayList<>();
        userRankingAdapter = new UserRankingAdapter(getActivity(), itemsBeens);
        mRefreshLayout = view.findViewById(R.id.srl_refresh_layout);
        mRefreshLayout.setEnableAutoLoadmore(true);

        user_rank_list.getRefreshableView().addHeaderView(headView);
    }

    @Override
    public void initData() {
        super.initData();
        user_rank_list.setAdapter(userRankingAdapter);
        getMyUserRank(schoolId);
        user_rank_list.setMode(PullToRefreshBase.Mode.DISABLED);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getUserRank(schoolId);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 0;
                itemsBeens.clear();
                getUserRank(schoolId);
            }
        });

        user_rank_list.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) ->
                PersonCenterActivity.start(getActivity(), itemsBeens.get(i - 1).getUserId()));

    }

    /**
     * 获取班级排名
     */
    private void getUserRank(final String schoolId) {
        if (TextUtils.isEmpty(schoolId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("schoolId", schoolId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.userRank, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                user_rank_list.onRefreshComplete();
                UserRankBean userRankBean = JSON.parseObject(result, UserRankBean.class);
                List<UserRankBean.ItemsBean> items = userRankBean.getItems();
                if (items != null && items.size() > 0) {
                    pageIndex++;
                    itemsBeens.addAll(items);
                    userRankingAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                user_rank_list.onRefreshComplete();
            }
        }, LoadingType.NONE);

    }

    /**
     * 获取我的班级排名
     */
    private void getMyUserRank(final String schoolId) {
        if (TextUtils.isEmpty(schoolId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("schoolId", schoolId);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.myUserRank, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                final UserRankBean.ItemsBean bean = JSON.parseObject(result, UserRankBean.ItemsBean.class);
                int index = bean.getRankNum();
                index_image.setVisibility(View.GONE);
                index_num.setVisibility(View.GONE);
                switch (index) {
                    case 1:
                        index_image.setVisibility(View.VISIBLE);
                        index_image.setImageResource(R.mipmap.award_01);
                        break;
                    case 2:
                        index_image.setVisibility(View.VISIBLE);
                        index_image.setImageResource(R.mipmap.award_02);
                        break;
                    case 3:
                        index_image.setVisibility(View.VISIBLE);
                        index_image.setImageResource(R.mipmap.award_03);
                        break;
                    default:
                        index_num.setVisibility(View.VISIBLE);
                        index_num.setText(index + "");
                        break;
                }
                name_text.setText(bean.getRealName());
                actionNum.setText(bean.getActivitiesCount() + "");
                lev_text.setText("LV." + bean.getLevel());
                MyImageLoader.getIntent().displayNetImage(getActivity(), bean.getAvatar(), headImageView);
                getUserRank(schoolId);
                item_layout.setOnClickListener((View view) -> PersonCenterActivity.start(getActivity(), bean.getUserId()));
            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
            }
        });

    }

}
