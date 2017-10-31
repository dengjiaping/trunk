package com.histudent.jwsoft.histudent.body.hiworld.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SelectFileAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.FileListBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 班级相册
 * Created by ZhangYT on 2016/6/29.
 */

//班级相册页面
public class PersonAlbumList extends BaseFragment {
    private PullToRefreshListView refreshListView;
    private SelectFileAdapter adapter;
    private List<FileListBean.ItemsBean> list;
    private Intent intent;
    private int nextPageIndex;
    private String content;
    private ImageView emptyView;
    private String classId;
    private int currentIndex;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //请求上拉加载的数据处理
                case 2:
                    content = (String) msg.obj;
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        FileListBean fileListBean = JSON.parseObject(content, FileListBean.class);

                        if (fileListBean != null && fileListBean.getItemCount() > 0) {

                            list.addAll(list.size(), fileListBean.getItems());
                        }
                    }

                    Log.e("OhterAlbumList", content);
                    break;

                //请求下拉刷新的数据处理(请求最新的数据)
            }

            adapter.notifyDataSetChanged();
            if (refreshListView.isRefreshing()) {
                refreshListView.onRefreshComplete();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        refreshListView = new PullToRefreshListView(getActivity());
        refreshListView.getRefreshableView().setDividerHeight(0);
        return refreshListView;
    }

    @Override
    public void initView() {
        super.initView();

        list = new ArrayList<>();
        adapter = new SelectFileAdapter(list, getActivity(), 1);
        refreshListView.setAdapter(adapter);
        emptyView = EmptyViewUtils.EmptyView(R.drawable.no_photo_video, getActivity());
        refreshListView.setEmptyView(emptyView);

        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                intent = getActivity().getIntent();
                //将选择的相册名返回
                intent.putExtra("fileName", list.get(position - 1).getAlbumName());
                intent.putExtra("albumId", list.get(position - 1).getAlbumId());
                intent.putExtra("ownId", list.get(position - 1).getOwnerId());
                intent.putExtra("privacyStatus",list.get(position - 1).getPrivacyStatus());
                getActivity().setResult(400, intent);
                getActivity().finish();
            }
        });

        initPullToRefreshListView();

    }


    //设置上啦刷新下拉加载

    private void initPullToRefreshListView() {

        PullToRefreshUtils.initPullToRefreshListView(refreshListView);

        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //下来刷新的最新数据
                getHttpData(false);

            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                //上拉加载更多数据
                getHttpData(true);

            }
        });

    }

    //网络请求数据
    private void getHttpData(boolean isMore) {


        if (!isMore) {
            currentIndex = 0;
            list.clear();
        } else {
            currentIndex++;
        }


        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("pageSize", 8 + "");
        map1.put("pageIndex", currentIndex + "");
        map1.put("ownerId", HiCache.getInstance().getLoginUserInfo().getUserId());
        map1.put("ownerType", 1 + "");
        map1.put("categoryid",  "");

        HiWorldCache.postHttpData((BaseActivity) getActivity(), handler, 2, HistudentUrl.photofilelist_url, map1, HiWorldCache.Body, false,true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getHttpData(false);
    }
}
