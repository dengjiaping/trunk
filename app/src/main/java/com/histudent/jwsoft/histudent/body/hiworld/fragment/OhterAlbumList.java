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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.activity.UploadPhotoActivity;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SelectFileAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.FileListBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangYT on 2016/6/29.
 */
//我的相册的页面


public class OhterAlbumList extends BaseFragment {

    private List<FileListBean.ItemsBean> list, list_temp;
    private SelectFileAdapter adapter;
    private Intent intent;
    private String content;
    private PullToRefreshListView refreshListView;
    private ImageView emptyView;
    private int currentPageIndex;
    private String id, Key;
    private ActionTypeEnum typeEnum;
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

                        if (fileListBean != null && fileListBean.getItemCount() != 0) {
                            list_temp = fileListBean.getItems();


                            if (list_temp != null) {
                                list.addAll(list_temp);

                            } else {
                                Toast.makeText(getActivity(), R.string.no_more_information, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    if (refreshListView.isRefreshing()) {
                        refreshListView.onRefreshComplete();
                    }
                    adapter.notifyDataSetChanged();

                    Log.e("OhterAlbumList", content);
                    break;
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


    public static OhterAlbumList OhterAlbumList(ActionTypeEnum typeEnum, String id) {

        OhterAlbumList file = new OhterAlbumList();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putSerializable("typeEnum", typeEnum);
        file.setArguments(bundle);
        return file;
    }

    @Override
    public void initView() {
        super.initView();


        if (getArguments() != null) {
            id = getArguments().getString("id");
            typeEnum = ((ActionTypeEnum) getArguments().getSerializable("typeEnum"));
            intKey();
        } else {
            Key = "ownerId";
            id =HiCache.getInstance().getLoginUserInfo().getUserId();
        }

        //获取到保存到数据库中的个人相册中的数据信息

        list = new ArrayList<>();
        emptyView = EmptyViewUtils.EmptyView(R.drawable.no_photo_video, getActivity());
        adapter = new SelectFileAdapter(list, getActivity(),1);
        refreshListView.setEmptyView(emptyView);
        refreshListView.setAdapter(adapter);

        initPullToRefreshListView();


    }

    private void intKey() {

        Key = typeEnum == ActionTypeEnum.CLASS ? "classId" : typeEnum == ActionTypeEnum.GROUP ? "groupId" : "ownerId";
    }

    //网络请求数据
    private void getHttpData(boolean isMore) {

        if (!isMore) {
            currentPageIndex = 0;
            list.clear();
        }

        int type= typeEnum == ActionTypeEnum.CLASS ? 2 : typeEnum == ActionTypeEnum.GROUP ? 3 :1;
        Map<String, Object> map1 = new HashMap<String, Object>();

        map1.put("pageSize", 8 + "");
        map1.put("pageIndex", currentPageIndex + "");
        map1.put("ownerId", id);
        map1.put("ownerType", type + "");
        map1.put("categoryid",  "");

        HiWorldCache.postHttpData((BaseActivity) getActivity(), handler, 2, HistudentUrl.photofilelist_url, map1, HiWorldCache.Body, false,true);

    }


    //设置上啦刷新下拉加载

    private void initPullToRefreshListView() {

        PullToRefreshUtils.initPullToRefreshListView(refreshListView);

        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), UploadPhotoActivity.class);

                String fileName, albumId, ownId;
                int privacyStatus;


                FileListBean.ItemsBean itemsBean = list.get(position - 1);
                fileName = itemsBean.getAlbumName();
                albumId = itemsBean.getAlbumId();
                ownId = itemsBean.getOwnerId();
                privacyStatus=itemsBean.getPrivacyStatus();

                if (StringUtil.isEmpty(fileName) && StringUtil.isEmpty(albumId) && StringUtil.isEmpty(ownId)) {
                    Log.e("OhterAlbumList", "参数为空");
                    Toast.makeText(getActivity(), "请重新选择相册", Toast.LENGTH_LONG).show();
                } else {


                    intent = getActivity().getIntent();
                    //将选择的相册名返回
                    intent.putExtra("fileName", list.get(position - 1).getAlbumName());

                    Log.e("---postion", position + "");
                    intent.putExtra("albumId", albumId);
                    intent.putExtra("ownId", ownId);
                    intent.putExtra("privacyStatus",privacyStatus);
                    getActivity().setResult(400, intent);
                    getActivity().finish();

                }

            }
        });


        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {


                //下来刷新的最新数据
                getHttpData(false);
                Log.e("onPullUpToRefresh", "下来刷新的最新数据");

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                currentPageIndex++;
                //上拉加载更多数据
                getHttpData(true);

                Log.e("onPullDownToRefresh", "上拉加载更多数据");

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!StringUtil.isEmpty(id))
        getHttpData(false);
    }
}
