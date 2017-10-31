package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.hiworld.bean.TopicModel;
import com.histudent.jwsoft.histudent.body.mine.adapter.MyClassActivityAdapter;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 话题
 */
public class TopicSearchActiviy extends BaseActivity {

    private PullToRefreshListView mListView;
    private MyClassActivityAdapter topicAdapter, searchAdapter;
    private List<Object> topicList, searchList;
    private EditText_clear editText_clear;
    private ListView searchListView;
    private String keyWord;
    private int pageIdex, searcIndex;
    private TextView tv_search;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_topic_activiy;
    }

    /**
     * @param activity
     * @param id         社群话题社群id ,其他话题传空
     * @param requstCode
     */

    public static void start(Activity activity, String id, int requstCode) {
        Intent intent = new Intent(activity, TopicSearchActiviy.class);
        intent.putExtra("id", StringUtil.isEmpty(id) ? "" : id);
        activity.startActivityForResult(intent, requstCode);
    }

    @Override
    public void initView() {

        id = getIntent().getStringExtra("id");
        mListView = (PullToRefreshListView) findViewById(R.id.listview);
        editText_clear = (EditText_clear) findViewById(R.id.filter_edit);
        searchListView = (ListView) findViewById(R.id.search_list);
        tv_search = (TextView) findViewById(R.id.tv_search);
        topicList = new ArrayList<>();
        searchList = new ArrayList<>();
        topicAdapter = MyClassActivityAdapter.create(this, topicList);
        searchAdapter = MyClassActivityAdapter.create(this, searchList);
        mListView.setAdapter(topicAdapter);
        searchListView.setAdapter(searchAdapter);

        getTopicList(false);
        initRefreshListView();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Object o = topicList.get(position - 1);
                if (o instanceof TopicModel) {
                    TopicModel topicModel = ((TopicModel) o);
                    if (!StringUtil.isEmpty(topicModel.getTagName())) {
                        setResult(topicModel);
                    }
                }
            }
        });
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (searchList.get(position) instanceof TopicModel) {
                    TopicModel topicModel = ((TopicModel) searchList.get(position));
                    if (!StringUtil.isEmpty(topicModel.getTagName())) {
                        setResult(topicModel);
                    }
                }

            }
        });

        editText_clear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                if (StringUtil.isEmpty(s + "")) {
//                    searchListView.setVisibility(View.GONE);
//                    mListView.setVisibility(View.VISIBLE);
//                } else {
//                    SearchTopic(editText_clear.getText().toString());
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (StringUtil.isEmpty(s.toString())) {
                    tv_search.setText("取消");
                } else {
                    tv_search.setText("搜索");
                }

            }
        });

    }

    private void initRefreshListView() {

        PullToRefreshUtils.initPullToRefreshListView2(mListView);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getTopicList(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getTopicList(true);
            }
        });


    }

    private void getTopicList(boolean isMore) {

        if (!isMore) {
            pageIdex = 0;
            topicList.clear();
            topicList.add("新话题");
            TopicModel model = new TopicModel();
            model.setTagName("新话题");
            topicList.add(model);
            topicList.add("热门话题");
        } else {
            pageIdex++;
        }

        if (StringUtil.isEmpty(id)) {
            DataUtils.getTopicList(this, keyWord, pageIdex, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    mListView.onRefreshComplete();

                    List<TopicModel> list = JSON.parseArray(JSON.parseObject(result).get("items").toString(), TopicModel.class);

                    if (list != null) {
                        topicList.addAll(list);
                    }
                    topicAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String errorMsg) {
                    mListView.onRefreshComplete();
                    topicAdapter.notifyDataSetChanged();
                }
            });
        } else {

            GroupHelper.getGroupHotTopicList(this, id, "", pageIdex, 10, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    mListView.onRefreshComplete();
                    List<TopicModel> list = JSON.parseArray(JSON.parseObject(result).get("items").toString(), TopicModel.class);
                    if (list != null) {
                        topicList.addAll(list);
                    }
                    topicAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String errorMsg) {
                    mListView.onRefreshComplete();
                }
            });

        }


        //社群话题

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_search:
                if ("搜索".equals(tv_search.getText().toString())) {
                    tv_search.setText("取消");
                    SearchTopic(editText_clear.getText().toString());
                } else {
                    finish();
                }
                break;
        }
    }


    //搜索话题
    private void SearchTopic(String keyWord) {

        if (StringUtil.isEmpty(id)) {
            DataUtils.SearchTopicByKeyWord(this, keyWord, searcIndex, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {

                    searchListView.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    List<TopicModel> list = JSON.parseArray(JSON.parseObject(result).get("items").toString(), TopicModel.class);
                    searchList.clear();
                    if (list != null && list.size() > 0) {
                        searchList.addAll(list);
                    } else {
                        searchList.add("    没找到任何结果！");
                    }
                    searchAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        } else {

            GroupHelper.getGroupHotTopicList(this, id, keyWord, searcIndex, 10, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {

                    searchListView.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    List<TopicModel> list = JSON.parseArray(JSON.parseObject(result).get("items").toString(), TopicModel.class);
                    searchList.clear();
                    if (list != null && list.size() > 0) {
                        searchList.addAll(list);
                    } else {
                        searchList.add("    没找到任何结果！");
                    }
                    searchAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        }
    }

    private void setResult(TopicModel topicModel) {

        Intent intent = new Intent();
        intent.putExtra("topicModel", topicModel);//返回话题
        setResult(200, intent);
        finish();

    }
}
