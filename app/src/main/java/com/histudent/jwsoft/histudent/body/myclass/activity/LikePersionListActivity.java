package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.adapter.LikePersionAdapter;
import com.histudent.jwsoft.histudent.body.myclass.bean.LikeBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/5/3.
 * 点赞的人
 */

public class LikePersionListActivity extends BaseActivity {

    private TextView title;
    private PullToRefreshListView listView;
    private int pageIndex;
    private String objectId;
    private boolean isAlbum;
    private List<Object> itemsBeens;
    private LikePersionAdapter adapter;

    public static void start(Activity activity, String objectId, boolean isAlbum) {
        Intent intent = new Intent(activity, LikePersionListActivity.class);
        intent.putExtra("objectId", objectId);
        intent.putExtra("isAlbum", isAlbum);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_likes;
    }

    @Override
    public void initView() {
        itemsBeens = new ArrayList<>();
        objectId = getIntent().getStringExtra("objectId");
        isAlbum = getIntent().getBooleanExtra("isAlbum", false);
        title = (TextView) findViewById(R.id.title_middle_text);
        listView = (PullToRefreshListView) findViewById(R.id.like_list);
        adapter = new LikePersionAdapter(this, itemsBeens);
    }

    @Override
    public void doAction() {
        super.doAction();
        title.setText("点赞人(0)");
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setAdapter(adapter);
        getLikesData();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex = 0;
                itemsBeens.clear();
                getLikesData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getLikesData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PersonCenterActivity.start(LikePersionListActivity.this, ((LikeBean.ItemsBean) itemsBeens.get(i - 1)).getUserId());
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }

    }

    /**
     * 获取点赞人数据
     */
    private void getLikesData() {

        Map<String, Object> map = new TreeMap<>();
        map.put("objectId", objectId);
        map.put("isAlbum", isAlbum);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);
        map.put("objectType", isAlbum ? 4 : 1);

        HiStudentHttpUtils.getDataByOKHttp(this, map, HistudentUrl.getPraiseUsers_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                listView.onRefreshComplete();
                LikeBean bean = JSON.parseObject(result, LikeBean.class);
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    itemsBeens.addAll(bean.getItems());
                    title.setText("点赞人（" + itemsBeens.size() + ")");
                    adapter.notifyDataSetChanged();
                    pageIndex++;
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                listView.onRefreshComplete();
            }
        });

    }

}
