package com.histudent.jwsoft.histudent.commen.activity;

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
import com.histudent.jwsoft.histudent.commen.adapter.ParserAdapter;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.model.CommenModel;
import com.histudent.jwsoft.histudent.commen.model.ParserModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/8/30.
 * 点赞详情界面
 */
public class ParserActivity extends BaseActivity {

    private TextView title;
    private PullToRefreshListView listView;
    private ParserAdapter adapter;
    private List<ParserModel.ItemsBean> beens;
    private String objectId;
    private int objectType;
    private ParserModel model;
    private int pageIndex;

    public static void start(Activity activity, String objectId, int objectType) {
        Intent intent = new Intent(activity, ParserActivity.class);
        intent.putExtra("objectId", objectId);
        intent.putExtra("objectType", objectType);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.parser_activity;
    }

    @Override
    public void initView() {
        objectId = getIntent().getStringExtra("objectId");
        objectType = getIntent().getIntExtra("objectType", 100);

        beens = new ArrayList<>();
        adapter = new ParserAdapter(this, beens);
        title = (TextView) findViewById(R.id.title_middle_text);
        listView = (PullToRefreshListView) findViewById(R.id.parser_list);
        listView.setAdapter(adapter);

        getData(false);
    }

    @Override
    public void doAction() {
        super.doAction();


        PullToRefreshUtils.initPullToRefreshListView2(listView);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getData(true);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PersonCenterActivity.start(ParserActivity.this, beens.get(i - 1).getUserId());
            }
        });

    }

    /**
     * 获取点赞详情的数据
     */
    private void getData(boolean isMore) {

        if (isMore) {
            pageIndex++;
        } else {
            pageIndex = 0;
            beens.clear();
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("objectId", objectId);
        map.put("objectType", objectType + "");
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 10);
        HiStudentHttpUtils.getDataByOKHttp(this, map, HistudentUrl.getPraiseDatil_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                listView.onRefreshComplete();
                model = JSON.parseObject(result, ParserModel.class);
                beens.addAll(model.getItems());
                adapter.notifyDataSetChanged();
                if (model.getItemCount() > 0)
                    title.setText("点赞人(" + model.getItemCount() + ")");
            }

            @Override
            public void onFailure(String msg) {
                listView.onRefreshComplete();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommenModel.GOTO_PERSON_HOME && resultCode == CommenModel.GOBACK_SUCCEED) {
            getData(false);
        }
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

}
