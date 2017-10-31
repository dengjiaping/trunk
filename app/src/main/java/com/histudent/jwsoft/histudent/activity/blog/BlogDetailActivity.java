package com.histudent.jwsoft.histudent.activity.blog;


import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.presenter.blog.BlogDetailPresenter;
import com.histudent.jwsoft.histudent.presenter.blog.contract.BlogDetailContract;

import org.xml.sax.XMLReader;

import java.net.URL;

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
