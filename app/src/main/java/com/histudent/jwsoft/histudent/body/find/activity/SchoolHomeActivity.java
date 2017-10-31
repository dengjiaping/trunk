package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.SchoolHomeBean;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.SharePopupWindow;
import com.histudent.jwsoft.histudent.widget.refresh.DefinedWeChatHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SchoolHomeActivity extends BaseActivity {

    private PullToRefreshListView mListView;
    private View view;
    private IconView title_right;
    private TextView school_name, school_code, school_members;
    private LinearLayout class_list;
    private ActionAdapter adapter_shcool;
    private List<ActionListBean> list_shcool;
    private String schoolId;
    private int timeCursor;
    private LinearLayout rank_list;
    private RelativeLayout school_layout;
    private SmartRefreshLayout mRefreshLayout;

    public static void start(Activity context, String schoolId) {
        Intent intent = new Intent(context, SchoolHomeActivity.class);
        intent.putExtra("schoolId", schoolId);
        context.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_school_home;
    }

    @Override
    public void initView() {
        schoolId = getIntent().getStringExtra("schoolId");
        list_shcool = new ArrayList<>();
        mListView = (PullToRefreshListView) findViewById(R.id.school_home_listView);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter_shcool = new ActionAdapter(this, list_shcool, ActionAdapter.SHCOOL);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        mRefreshLayout.setEnableAutoLoadmore(true);
        DefinedWeChatHeader definedWeChatHeader = new DefinedWeChatHeader(this);
        mRefreshLayout.setRefreshHeader(definedWeChatHeader);
        mRefreshLayout.setEnableHeaderTranslationContent(false);//下拉时内容不偏移
        mRefreshLayout.autoRefresh();
        initHeadView();
    }

    private void initHeadView() {
        view = View.inflate(this, R.layout.school_home_head, null);
        title_right = (IconView) findViewById(R.id.title_right_image);
        school_name = view.findViewById(R.id.school_name);
        school_code = view.findViewById(R.id.school_code);
        school_members = view.findViewById(R.id.school_members);
        rank_list = view.findViewById(R.id.rank_list);
        school_layout = view.findViewById(R.id.school_layout);
        class_list = view.findViewById(R.id.class_list);
    }

    @Override
    public void doAction() {
        super.doAction();
        title_right.setText(R.string.icon_forward);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mListView.setAdapter(adapter_shcool);
        mListView.getRefreshableView().addHeaderView(view);
        getSchool(schoolId);

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getSchoolActions(schoolId);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                timeCursor = 0;
                list_shcool.clear();
                getSchool(schoolId);
            }
        });

        //排行榜
        rank_list.setOnClickListener((View view) -> RankListActivity.start(SchoolHomeActivity.this, schoolId));
        //学校班级
        school_layout.setOnClickListener((View view) -> AllClassesInSchoolActivity.start(SchoolHomeActivity.this, schoolId, 111));

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                getShareData(this, schoolId);
                break;
        }
    }

    /**
     * 获取学校信息
     */
    private void getSchool(String schoolId) {
        Map<String, Object> map = new TreeMap<>();
        map.put("schoolId", schoolId);
        map.put("topClassCount", 15);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.home_school, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                SchoolHomeBean beans = JSON.parseObject(result, SchoolHomeBean.class);
                if (beans != null) {
                    school_name.setText(beans.getSchoolName());
                    school_code.setText(beans.getClassCount() + "");
                    school_members.setText(beans.getMemberCount() + "");
                    class_list.removeAllViews();
                    showClassList(beans.getTopClasses());
                }

            }

            @Override
            public void onFailure(String msg) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        }, LoadingType.NONE);
    }

    /**
     * 获取学校动态列表信息
     */
    private void getSchoolActions(String schoolId) {
        Map<String, Object> map = new TreeMap<>();
        map.put("schoolId", schoolId);
        map.put("timeCursor", timeCursor);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.activities_school, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                UserTimeModel model = DataParser.getUserTimeModel(result);
                if (model.getItems().size() > 0) {
                    timeCursor = model.getCursor();
                    list_shcool.addAll(ItemDataExchangeUtils.dataExchange(model.getItems()));
                    adapter_shcool.notifyDataSetChanged();
                } else {
                    if (timeCursor != 0)
                        Toast.makeText(SchoolHomeActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String msg) {

                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }
        }, LoadingType.NONE);
    }

    /**
     * 学校班级
     */
    public void showClassList(final List<SchoolHomeBean.TopClassesBean> fashionBeans) {

        if (fashionBeans == null || fashionBeans.size() == 0) {
            class_list.setVisibility(View.GONE);
        } else {
            class_list.setVisibility(View.VISIBLE);
            for (final SchoolHomeBean.TopClassesBean model : fashionBeans) {

                View layout = LayoutInflater.from(this).inflate(R.layout.fragment_homepage_fashion_item, null);
                ImageView iv = layout.findViewById(R.id.fashion_head);
                TextView className = layout.findViewById(R.id.fashion_name);

                if (!TextUtils.isEmpty(model.getLogo())) {
                    CommonGlideImageLoader.getInstance().displayNetImage(this, model.getLogo(),
                            iv, ContextCompat.getDrawable(this, R.mipmap.avatar_def), SystemUtil.dp2px(60), SystemUtil.dp2px(60));
                }


                className.setText(model.getName());

                layout.setTag(model);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClassCircleActivity.start(SchoolHomeActivity.this, model.getClassesId());
                    }
                });
                class_list.addView(layout);
            }

            getSchoolActions(schoolId);
        }
    }

    /**
     * 获取个人主页分享数据并予以分享
     *
     * @param schoolId
     */
    private void getShareData(final BaseActivity activity, final String schoolId) {
        if (TextUtils.isEmpty(schoolId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("objectId", schoolId);
        map.put("shareObjectType", 12);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.share_url_, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                MyShareBean shareBean = JSON.parseObject(result, MyShareBean.class);
                SharePopupWindow popupWindow = new SharePopupWindow(activity, shareBean);
                popupWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

}
