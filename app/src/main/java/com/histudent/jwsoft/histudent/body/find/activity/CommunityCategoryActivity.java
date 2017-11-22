package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.MyCommunityListAdapter;
import com.histudent.jwsoft.histudent.body.find.adapter.MyFragmentPagerAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.TagBean;
import com.histudent.jwsoft.histudent.body.find.fragment.PageFragment;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.FragmentFactory;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.model.constant.ConstantUtil;
import com.histudent.jwsoft.histudent.model.constant.TransferKeys;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 兴趣社群-社群分类
 */
public class CommunityCategoryActivity extends BaseActivity {

    private TextView mTextView;
    private String mCommunityId;
    private LinearLayout mLinearLayout;
    private EditText mSearchContent;
    private List<TagBean> mFavorGroupList;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private TextView mSearchView;
    private List<PageFragment> mPageFragments;
    private List<String> mCommunityIds;

    public static void start(Activity activity, String id) {
        Intent intent = new Intent(activity, CommunityCategoryActivity.class);
        intent.putExtra(TransferKeys.COMMUNITY_ID, id);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_community_category;
    }

    @Override
    public void initView() {
        mLinearLayout = (LinearLayout) findViewById(R.id.title_left);
        mTextView = (TextView) findViewById(R.id.title_middle_text);
        mSearchContent = (EditText) findViewById(R.id.community_edit);
        mSearchView = (TextView) findViewById(R.id.tv_search);
        mTextView.setText(R.string.community_type);
        mFavorGroupList = new ArrayList<>();
        mTabLayout = (TabLayout) findViewById(R.id.community_tabLayout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void doAction() {
        super.doAction();
        initData();
        initListener();
    }

    private void initData() {
        mCommunityId = getIntent().getStringExtra(TransferKeys.COMMUNITY_ID);
        loadCommunityTitles();
    }

    /**
     * 加载社群分类标题
     */
    private void loadCommunityTitles() {
        GroupHelper.getCommunityTag(this, ConstantUtil.Group, ConstantUtil.Second, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mFavorGroupList.clear();
                mFavorGroupList.addAll(JSON.parseArray(result, TagBean.class));
                initTabLayout(mFavorGroupList);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.FLOWER);
    }

    private void initListener() {
        mLinearLayout.setOnClickListener((View v) -> CommunityCategoryActivity.this.finish());
        mSearchView.setOnClickListener((View v) -> {
            if (!TextUtils.isEmpty(mSearchContent.getText().toString().trim())) {
                if (mOnSearchChangedListener != null)
                    doSearchAction();
            } else {
                finish();
            }
        });
        mSearchContent.addTextChangedListener(mSearchWatcher);
        mSearchContent.clearFocus();
        mSearchContent.setOnEditorActionListener(mOnEditorActionListener);
    }


    private void initTabLayout(List<TagBean> favorGroupList) {
        mCommunityIds = new ArrayList<>();
        mPageFragments = new ArrayList<>();
        mViewPager = (ViewPager) findViewById(R.id.community_viewPager);
        for (TagBean tagBean : favorGroupList) {
            mPageFragments.add(FragmentFactory.getSingleFactoryInstance().getCommunityFragment(tagBean.getId()));
            mCommunityIds.add(tagBean.getId());
        }
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), favorGroupList, mPageFragments);
        mViewPager.setAdapter(mMyFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (mCommunityIds.contains(mCommunityId)) {
            mTabLayout.getTabAt(mCommunityIds.indexOf(mCommunityId)).select();
        } else {
            mTabLayout.getTabAt(0).select();
        }
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = (TextView v, int actionId, KeyEvent event) -> {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (TextUtils.isEmpty(v.getText().toString().trim())) {
                ToastTool.showCommonToast(getApplicationContext(), "请输入关键词");
                return true;
            }
            mSearchView.setText("取消");
            doSearchAction();
            return true;
        }
        return false;
    };

    private TextWatcher mSearchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s.toString().trim())) {
                mSearchView.setText("取消");
                if (mOnSearchChangedListener != null)
                    doSearchAction();
            } else {
                mSearchView.setText("搜索");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public String getSearchContent() {
        return mSearchContent.getText().toString().trim();
    }


    /**
     * 1.执行搜索
     * 2.清空内容 执行搜索
     */
    private void doSearchAction() {
        int position = mTabLayout.getSelectedTabPosition();
        PageFragment fragment = mPageFragments.get(position);
        MyCommunityListAdapter adapter = fragment.getAdapter();
        PullToRefreshListView pullToRefreshListView = fragment.getPullToRefreshListView();
        String tagId = fragment.getTagId();
        mOnSearchChangedListener.onSearchChange(getSearchContent(), adapter, tagId, pullToRefreshListView);
    }

    public OnSearchChangedListener mOnSearchChangedListener;

    public void setOnSearchChangedListener(OnSearchChangedListener onSearchChangedListener) {
        this.mOnSearchChangedListener = onSearchChangedListener;
    }

    public interface OnSearchChangedListener {
        void onSearchChange(String inputContent, MyCommunityListAdapter mCommunityListAdapter, String childTagId, PullToRefreshListView pullToRefreshListView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOnSearchChangedListener != null)
            mOnSearchChangedListener = null;
    }
}
