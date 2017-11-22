package com.histudent.jwsoft.histudent.account.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.commen.activity.AndroidBug5497Workaround;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ItemType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.helper.WebViewHelper;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/31.
 * 用户协议界面
 */
public class DealActivity extends BaseActivity {

    private WebView mWebView;
    private TextView title_view;
    private IconView title_image_right;
    private LinearLayout layout;
    private WebSettings webSettings;

    private String url, title;
    private TopMenuPopupWindow menuWindow;
    private ClassModel classModel;
    private Handler handler;

    private static ActionListBean bean;
    private static ItemType type;

    public static void start(Activity activity, String url, String title) {
//        Intent intent = new Intent(activity, DealActivity.class);
//        intent.putExtra("url", url);
//        intent.putExtra("title", title);
//        activity.startActivity(intent);
        HTWebActivity.start(activity, url);
    }

    public static void start(Activity activity, String url, ActionListBean bean, ItemType type) {
//        Intent intent = new Intent(activity, DealActivity.class);
//        intent.putExtra("url", url);
//        DealActivity.bean = bean;
//        DealActivity.type = type;
//        activity.startActivityForResult(intent, 10);
    }

    @Override
    public int setViewLayout() {
        return R.layout.deal_activity;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                exit();
                finish();
                break;
            case R.id.title_right:
                if (title_image_right.getVisibility() == View.VISIBLE)
                    showClassMenue();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {

        AndroidBug5497Workaround.assistActivity(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                setResult(200);
                finish();
            }
        };

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");

        mWebView = (WebView) findViewById(R.id.deal_web);
        title_view = (TextView) findViewById(R.id.title_middle_text);
        title_image_right = (IconView) findViewById(R.id.title_right_image);
        layout = (LinearLayout) findViewById(R.id.layout);

        WebViewHelper.setWebView(mWebView);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new myWebViewClient());
        mWebView.setWebChromeClient(new myWebChromeClient());

        if (!TextUtils.isEmpty(title))
            title_view.setText(title);

        if (DealActivity.type != null) {
            switch (DealActivity.type) {
                case PERSON_HOME:
                case HI_CLASS_ACTION:
                case HI_FRIEND_ACTION:
                case CLASS_HOME:
                    if (SystemUtil.isOneselfIn(bean.getUserId())) {
                        title_image_right.setVisibility(View.GONE);
                    } else {
                        title_image_right.setText(R.string.icon_more3);
                    }
                    break;
                case PERSON_LOG:
                    title_image_right.setText(R.string.icon_more3);
                    break;
                case CLASS_LOG:
                    classModel = HiCache.getInstance().getClassDetailInfo();
                    if (classModel != null && !classModel.isIsAdmin() && SystemUtil.isOneselfIn(bean.getOwnerId())) {
                        title_image_right.setVisibility(View.GONE);
                    } else {
                        title_image_right.setText(R.string.icon_more3);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    public class myWebChromeClient extends WebChromeClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            view.loadUrl(url);
            return true;
        }
    }

    public class myWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            DealActivity.this.showLoadingImage(DealActivity.this, LoadingType.NONE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            DealActivity.this.closeLoadingImage();

        }
    }

    private void exit() {
        layout.removeView(mWebView);
        mWebView.removeAllViews();
        mWebView.destroy();
        finish();
    }

    public void showClassMenue() {

        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();

        switch (DealActivity.type) {
            case PERSON_HOME:
            case HI_CLASS_ACTION:
            case HI_FRIEND_ACTION:
            case CLASS_HOME:
                list_name.add("举报");
                list_color.add(Color.rgb(51, 51, 51));
                break;
            case PERSON_LOG:
                list_name.add("删除");
                list_color.add(Color.rgb(255, 88, 88));
                break;
            case CLASS_LOG:
                if (classModel.isIsAdmin() && SystemUtil.isOneselfIn(bean.getUserId())) {
                    list_name.add("删除");
                    list_color.add(Color.rgb(255, 88, 88));
                } else if (classModel.isIsAdmin() && !SystemUtil.isOneselfIn(bean.getUserId())) {
                    list_name.add("举报");
                    list_name.add("删除");
                    list_color.add(Color.rgb(51, 51, 51));
                    list_color.add(Color.rgb(255, 88, 88));
                } else if (!classModel.isIsAdmin() && !SystemUtil.isOneselfIn(bean.getUserId())) {
                    list_name.add("举报");
                    list_color.add(Color.rgb(51, 51, 51));
                }
                break;
        }

        menuWindow = new TopMenuPopupWindow(this, new View.OnClickListener() {

            public void onClick(View v) {
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_01:

                        switch (DealActivity.type) {
                            case PERSON_HOME:
                            case HI_CLASS_ACTION:
                            case HI_FRIEND_ACTION:
                            case CLASS_HOME:
                                ReportActivity.start(DealActivity.this, bean.getSourceId(), ReportType.LOG);
                                break;
                            case PERSON_LOG:
                                PersionHelper.getInstance().deletePersionBlog(DealActivity.this, bean.getSourceId(), handler, 1, 0);
                                break;
                            case CLASS_LOG:
                                if (classModel.isIsAdmin() && !SystemUtil.isOneselfIn(bean.getUserId())) {
                                    ReportActivity.start(DealActivity.this, bean.getSourceId(), ReportType.LOG);
                                } else if (!classModel.isIsAdmin() && !SystemUtil.isOneselfIn(bean.getUserId())) {
                                    ReportActivity.start(DealActivity.this, bean.getSourceId(), ReportType.LOG);
                                } else {
                                    PersionHelper.getInstance().deletePersionBlog(DealActivity.this, bean.getSourceId(), handler, 1, 0);
                                }
                                break;
                        }

                        break;
                    case R.id.btn_02:

                        PersionHelper.getInstance().deletePersionBlog(DealActivity.this, bean.getSourceId(), handler, 1, 0);

                        break;
                    default:
                        break;
                }
            }
        }, list_name, list_color,false);
        menuWindow.showAtLocation(layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            exit();
        }
        return super.onKeyDown(keyCode, event);
    }
}
