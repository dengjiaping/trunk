package com.histudent.jwsoft.histudent.body.find.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.DiscoverGroupAdapter;
import com.histudent.jwsoft.histudent.body.find.adapter.SortGroupAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.DiscoverGroupListBean;
import com.histudent.jwsoft.histudent.body.find.bean.SortGroupListBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.LocationCallBack;
import com.histudent.jwsoft.histudent.commen.utils.LocationUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.comment2.activity.SelectAddressActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 兴趣社群页面
 */

public class FindGroupAvtivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivBack, ivMore;
    private TextView tv_sort;

    private PullToRefreshListView pListView;
    private SortGroupAdapter sortGroupAdapter;
    private DiscoverGroupAdapter discoverGroupAdapter;
    private EditText_clear searchView;
    private PopupWindow popupWindow;
    int width;
    private String tagId, groupname;
    private List<DiscoverGroupListBean.ItemsBean> discoverGroupLists;
    private int text_len, currentTextLength;
    private List<Object> sortGroupLists;
    private boolean isShow;
    private View emptyView;
    private int pageIndex;
    private LocationCallBack callBack;
    private RelativeLayout frame;
    private TextView tv_address;
    private String city;
    private int itemCount;
    private boolean isSortItem;
    private boolean isFirstCome;
    private TextView tv_count;
    private String type;
    private int height;


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //分类社群网路数据返回
                case 0:

                    String content = (String) msg.obj;
                    Log.e("getSortGroup", content);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        List<SortGroupListBean> bean = JSON.parseArray(content, SortGroupListBean.class);

                        if (bean != null && bean.size() >= 0) {
                            sortGroupLists.clear();
                            sortGroupLists.addAll(sortGroupLists.size(), bean);
                            SortGroupListBean bean1 = new SortGroupListBean();
                            bean1.setName("全部");
                            bean1.setTagId("");
                            sortGroupLists.add(0, bean1);
                            sortGroupAdapter.notifyDataSetChanged();
                        }
                    }

                    break;


                //发现社群网路数据返回
                case 1:


                    content = (String) msg.obj;
                    Log.e("getDisconverGroup", content);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        DiscoverGroupListBean bean1 = JSON.parseObject(content, DiscoverGroupListBean.class);
                        itemCount = bean1.getItemCount();
                        if (!StringUtil.isEmpty(searchView.getText().toString())) {
                            tv_count.setText("共发现" + itemCount + "条数据");
                        } else {

                            if (isSortItem) {
                                tv_count.setText("共发现(" + type + ")类型数据" + itemCount + "条数据");
                            } else {
                                tv_count.setText("");
                            }

                        }

                        isSortItem = false;

                        discoverGroupLists.addAll(bean1.getItems());
                        if (discoverGroupLists.size() == 0) {
                            pListView.setEmptyView(emptyView);
                        }
                    }

                    if (pListView.isRefreshing()) {
                        pListView.onRefreshComplete();
                    }

                    if (msg.arg1 == HiWorldCache.FAIL) {
                        pListView.setEmptyView(emptyView);
                    }

                    discoverGroupAdapter.notifyDataSetChanged();
                    break;


            }
        }
    };

    @Override
    public int setViewLayout() {
        return R.layout.activity_find_group_avtivity;
    }

    @Override
    public void initView() {

        showLoadingImage(this, LoadingType.NONE);
        tagId = groupname = "";
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height=metrics.heightPixels;

        ivBack = ((ImageView) findViewById(R.id.favor_group_iv));
        ivMore = ((ImageView) findViewById(R.id.favor_group_iv_sort));
        tv_address = ((TextView) findViewById(R.id.favor_group_tv_create_group));
        tv_sort = ((TextView) findViewById(R.id.favor_group_tv_sort));
        pListView = ((PullToRefreshListView) findViewById(R.id.favor_group_pull_to_refresh));
        tv_count = ((TextView) findViewById(R.id.tv_count));
        frame = ((RelativeLayout) findViewById(R.id.frame));

        emptyView = EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_or_take_group, this, R.string.group_emptyview);


        searchView = ((EditText_clear) findViewById(R.id.favor_group_search_view));
        searchView.clearFocus();

        discoverGroupLists = new ArrayList<>();
        discoverGroupAdapter = new DiscoverGroupAdapter(discoverGroupLists, this);
        pListView.setAdapter(discoverGroupAdapter);
        pListView.getRefreshableView().setDividerHeight(0);

        sortGroupLists = new ArrayList<>();
        sortGroupAdapter = new SortGroupAdapter(sortGroupLists, this);

        tv_address.setText("定位中...");
        ivBack.setOnClickListener(this);
        tv_sort.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        pListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //在传递的时候，点击的位置等于position-2
                Log.e("position----", position + "");
                DiscoverGroupListBean.ItemsBean dataBean = discoverGroupLists.get(position - 1);
                String groupId = dataBean.getGroupId();
                GroupCenterActivity.start(FindGroupAvtivity.this, groupId);

                Log.e("AdminId", dataBean.getGroupUserId());
                Log.e("getUserId", HiCache.getInstance().getLoginUserInfo().getUserId());


            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {

                        }
                        onQueryTextChange(editable.toString());
                    }
                }).start();


            }
        });

        initPullToRefresh();

        callBack = new LocationCallBack() {
            @Override
            public void getLocationInfor(double lat, double lon, String address, String city, String dist, boolean isSuccess) {


                AddressInforBean bean = HiWorldCache.getUserLocationInfor();
                FindGroupAvtivity.this.city = bean.getCity();
                tv_address.setText(bean.getCity());
                if (!isFirstCome) {
                    getDisconverGroup(false);
                    getSortGroup();
                    isFirstCome = true;
                }

            }
        };


        LocationUtils.getLocationUtils().getLocationInfor(this, callBack);

    }

    private void initPullToRefresh() {
        PullToRefreshUtils.initPullToRefreshListView(pListView);




        pListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getDisconverGroup(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getDisconverGroup(true);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favor_group_iv:
                this.finish();
                break;
            case R.id.favor_group_tv_sort:
            case R.id.favor_group_iv_sort:

                if (sortGroupLists!=null&&sortGroupLists.size()==0){
                    Toast.makeText(FindGroupAvtivity.this,"未发现任何分类，请稍后再试",Toast.LENGTH_LONG).show();
                    getSortGroup();
                    break;
                }
                if (!isShow) {
                    ivMore.setImageResource(R.mipmap.all_see_up);
                    initPopWindow();
                    isShow = true;
                }
                break;

            case R.id.favor_group_tv_create_group:

                SelectAddressActivity.start(this, city, "", 1);
                break;
        }
    }

    public void onQueryTextChange(String newText) {

        currentTextLength = newText.length();

        if (newText.trim().length() != 0) {
//            tagId = "";
        }
        //如果当前出入输入文字状态
        if (currentTextLength != text_len) {

            groupname = newText.toString();
            getDisconverGroup(false);

            //如果当前搜索框为空白时
        } else if (StringUtil.isEmpty(newText)) {
            groupname = "";
            getDisconverGroup(false);
        }

        //记录当前搜索框内文字的长度
        text_len = currentTextLength;
    }

    private void initPopWindow() {
        frame.setVisibility(View.VISIBLE);
        View layout = View.inflate(this, R.layout.same_city_popwindow, null);
        final ListView listView = ((ListView) layout.findViewById(R.id.list_view));
        listView.setAdapter(sortGroupAdapter);
        popupWindow = new PopupWindow(layout, width,height- SystemUtil.dp2px(70));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.bg_color));

        int xoff = width / 2 - 120;
        popupWindow.showAsDropDown(findViewById(R.id.relative), xoff, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                frame.setVisibility(View.GONE);
                isShow = false;
                ivMore.setImageResource(R.mipmap.all_see_down);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击条目的事件
                TextView tv = ((TextView) view.findViewById(R.id.tv_name));
                tv.setTextColor(Color.rgb(32, 126, 189));

                SortGroupListBean bean = ((SortGroupListBean) sortGroupLists.get(position));
                tagId = bean.getTagId();

                if (!StringUtil.isEmpty(tagId)) {
                    isSortItem = true;
                    if (StringUtil.isEmpty(searchView.getText().toString())) {
                        type = bean.getName();
                    }
                }

                getDisconverGroup(false);
                popupWindow.dismiss();
            }
        });
    }

    //分类社群的数据
    private void getSortGroup() {

        Map<String, Object> map = new HashMap<>();
        map.put("areaName", city);

        HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.group_tag_list_url, map, HiWorldCache.Quarry, true,true);
    }

    //发现社群的数据
    private void getDisconverGroup(boolean isMore) {

        Map<String, Object> map = new HashMap<>();
        if (!isMore) {
            pageIndex = 0;
            discoverGroupLists.clear();
        }

        map.put("areaName", city);
        map.put("groupNameKey", groupname);
        map.put("tagId", tagId);
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", 8 + "");


        HiWorldCache.postHttpData(this, handler, 1, HistudentUrl.group_get_discover_group_url, map, HiWorldCache.Quarry, true,true);

        pageIndex++;
    }


    //获取社群消息的列表

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {

            case 0:
            case 200:

                if (requestCode == 300 && data != null) {
                    city = data.getStringExtra("city");
                    tv_address.setText(city);
                }
                getDisconverGroup(false);
                getSortGroup();
                break;
        }
    }

}
