package com.histudent.jwsoft.histudent.body.find.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.ClassRankingAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.ClassRankBean;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.histudent.jwsoft.histudent.R.id.index;

/**
 * Created by liuguiyu-pc on 2017/5/16.
 */

public class ClassRankFragment extends BaseFragment {
    private View view;
    private PullToRefreshListView class_rank_list;
    private ClassRankingAdapter classRankingAdapter;
    private List<ClassRankBean.ItemsBean> itemsBeens;
    private int pageIndex;
    private String schoolId;
    private TextView name_text, logNum, pictureNum, index_num;
    private ImageView index_image;
    private HiStudentHeadImageView headImageView;
    private View mHeadViewLayout;

    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_rank_page, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this, view);
        schoolId = getArguments().getString("schoolId");

        mHeadViewLayout = LayoutInflater.from(getContext()).inflate(R.layout.fragment_class_rank_head, null);
        index_image = mHeadViewLayout.findViewById(index);
        name_text = mHeadViewLayout.findViewById(R.id.name);
        index_num = mHeadViewLayout.findViewById(R.id.index_num);
        logNum = mHeadViewLayout.findViewById(R.id.logNum);
        pictureNum = mHeadViewLayout.findViewById(R.id.pictureNum);
        headImageView = mHeadViewLayout.findViewById(R.id.headImage);

        class_rank_list = view.findViewById(R.id.class_rank_list);
        class_rank_list.setMode(PullToRefreshBase.Mode.DISABLED);
        itemsBeens = new ArrayList<>();
        classRankingAdapter = new ClassRankingAdapter(getActivity(), itemsBeens);
        mRefreshLayout.setEnableAutoLoadmore(true);

        class_rank_list.getRefreshableView().addHeaderView(mHeadViewLayout);
    }

    @Override
    public void initData() {
        super.initData();
        class_rank_list.setAdapter(classRankingAdapter);
        getMyClassRank(schoolId);

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getClassRank(schoolId, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 0;
                itemsBeens.clear();
                getClassRank(schoolId, false);
            }
        });

        class_rank_list.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) ->
                ClassCircleActivity.start(getActivity(), itemsBeens.get(i - 1).getClassesId()));

    }

    /**
     * 获取班级排名
     */
    private void getClassRank(final String schoolId, boolean isNeedLoading) {
        if (TextUtils.isEmpty(schoolId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("schoolId", schoolId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.classRank, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                ClassRankBean classRankBean = JSON.parseObject(result, ClassRankBean.class);
                List<ClassRankBean.ItemsBean> items = classRankBean.getItems();
                if (items != null && items.size() > 0) {
                    pageIndex++;
                    itemsBeens.addAll(items);
                    classRankingAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
            }
        }, isNeedLoading ? LoadingType.FLOWER : LoadingType.NONE);

    }

    /**
     * 获取我的班级排名
     */
    private void getMyClassRank(final String schoolId) {
        if (TextUtils.isEmpty(schoolId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("schoolId", schoolId);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.myClassRank, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                final ClassRankBean.ItemsBean bean = JSON.parseObject(result, ClassRankBean.ItemsBean.class);

                int index = bean.getRankNum();
                index_num.setVisibility(View.GONE);
                index_image.setVisibility(View.GONE);
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
                name_text.setText(bean.getName());
                logNum.setText(bean.getMemberNum() + "");
                pictureNum.setText(bean.getActivitiesCount() + "");
                MyImageLoader.getIntent().displayNetImage(getActivity(), bean.getLogo(), headImageView);
                getClassRank(schoolId, true);
                mHeadViewLayout.setOnClickListener((View view) -> ClassCircleActivity.start(getActivity(), bean.getClassesId()));
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });

    }

}
