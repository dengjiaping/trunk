package com.histudent.jwsoft.histudent.view.activity.blog;


import android.os.Handler;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.presenter.blog.BlogDetailPresenter;
import com.histudent.jwsoft.histudent.presenter.blog.contract.BlogDetailContract;

import butterknife.BindView;

/**
 * Created by huyg on 2017/9/11.
 */

public class BlogDetailActivity extends BaseActivity<BlogDetailPresenter> implements BlogDetailContract.View {

    @BindView(R.id.webView)
    WebView mWebView;



    private String actionId;

    private Handler mHandler = new Handler();

    @Override
    public void showContent(String message) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_blog_detail;
    }

    @Override
    protected void init() {
        initIntent();
        initView();

        initData();
    }

    private void initView() {
        WebSettings webSettings =   mWebView .getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
    }

    private void initIntent() {
        actionId =  getIntent().getStringExtra("actionId");
    }

    private void initData() {
        mPresenter.getBlogDetail(actionId);
    }

    @Override
    public void showBlogDetail(String content) {
        if (!TextUtils.isEmpty(content)){

            mWebView.loadData(content,"text/html","utf-8");
        }


    }


}
