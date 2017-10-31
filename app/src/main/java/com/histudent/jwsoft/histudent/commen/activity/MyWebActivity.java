package com.histudent.jwsoft.histudent.commen.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.gzsll.jsbridge.WVJBWebView;
import com.gzsll.jsbridge.WVJBWebViewClient;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.clock.AddClockActivity;
import com.histudent.jwsoft.histudent.activity.clock.ReadClockInActivity;
import com.histudent.jwsoft.histudent.bean.ClassIdBean;
import com.histudent.jwsoft.histudent.bean.TaskItemBean;
import com.histudent.jwsoft.histudent.body.find.activity.GroupCenterActivity;
import com.histudent.jwsoft.histudent.body.homepage.activity.TopicActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCircleActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.HuoDongCenterActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.JoinSpecClassActivity;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.bean.MyWebBean;
import com.histudent.jwsoft.histudent.commen.bean.WebBlogBean;
import com.histudent.jwsoft.histudent.commen.bean.WebEssayBean;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.WebViewHelper;
import com.histudent.jwsoft.histudent.commen.model.JsModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.SharePopupWindow;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.constant.Const;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.ReadTaskEvent;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.zxing.CaptureActivity;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.histudent.jwsoft.histudent.comment2.utils.ScanfQrFromLocalUitls.getPath;


/**
 * 可以选择文件且可以拍照
 */
@TargetApi(19)
@SuppressLint("NewApi")
public class MyWebActivity extends BaseActivity implements TopMenuPopupWindow.OnCloseListener {

    private TopMenuPopupWindow menuWindow;
    private RelativeLayout mRLTopLayout;
    private Uri mPicUri;
    private boolean flag_onclick;
    private LinearLayout layout, add_layout;
    private JsModel model;
    private ImageView title_left_image, title_right_image;
    private TextView title_right, title, title_left, title_close;
    private RelativeLayout title_left_layout, title_right_layout;
    private WVJBWebView mWebView;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private static final int FILE_CHOOSER_RESULT_CODE = 1;// 表单的结果回调</span>
    private static final int PHOTO = 2;
    private WebViewHelper helper;
    private WebHandler handler;
    private boolean showTitle, showClose;
    private String title_text;
    private static final int CODE_CREATE_BOOK_TASK = 2000;
    private static final int CODE_EDIT_BOOK_TASK = 2001;
    private WVJBWebView.WVJBResponseCallback mWVJBResponseCallback;
    /**
     * 根据code分辨H5调用不同的本地页面从而执行相应的回调过滤
     * 在onEvent中
     * 2000:二维码扫描
     * 2001:学生阅读打卡页
     */
    private int mCode = -1;

    private View.OnClickListener mOnItemClickListener = (View view) -> {
        switch (view.getId()) {
            case R.id.btn_01:
                flag_onclick = true;
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Calendar cal = Calendar.getInstance();
                cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
                File photo = new File(Environment.getExternalStorageDirectory(), sdf.format(cal.getTime()) + ".jpg");
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    mPicUri = Uri.fromFile(photo);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                } else {
                    //7.0以上版本拍照
                    mPicUri = FileProvider.getUriForFile(getApplicationContext(), Const.AUTHORITIES_SIT, photo);
                    Logger.e("fileProvider:-->" + mPicUri);
                    //将拍取的照片保存到指定URI\
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPicUri);
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivityForResult(cameraIntent, PHOTO);
                menuWindow.dismiss();
                break;
            case R.id.btn_02:
                flag_onclick = true;
                Logger.e("相册选择页面");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                MyWebActivity.this.startActivityForResult(intent, FILE_CHOOSER_RESULT_CODE);
                menuWindow.dismiss();
                break;
            default:
                menuWindow.dismiss();
                break;
        }
    };

    public static void start(Context context, String url) {
        if (TextUtils.isEmpty(url)) return;
        Intent intent = new Intent(context, MyWebActivity.class);
        intent.putExtra(TransferKeys.URL, WebViewHelper.checkUrl(url));
        context.startActivity(intent);
    }

    public static void start(Activity activity, String url, String title) {
        if (TextUtils.isEmpty(url)) return;
        Intent intent = new Intent(activity, MyWebActivity.class);
        intent.putExtra(TransferKeys.URL, WebViewHelper.checkUrl(url));
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }

    public static void startNoTitle(Context context, String url) {
        if (TextUtils.isEmpty(url)) return;
        Intent intent = new Intent(context, MyWebActivity.class);
        intent.putExtra(TransferKeys.URL, WebViewHelper.checkUrl(url));
        intent.putExtra("showTitle", false);
        context.startActivity(intent);
    }

    public static void startNoClose(Activity activity, String url) {
        if (TextUtils.isEmpty(url)) return;
        Intent intent = new Intent(activity, MyWebActivity.class);
        intent.putExtra(TransferKeys.URL, WebViewHelper.checkUrl(url));
        intent.putExtra("showClose", false);
        activity.startActivity(intent);
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        showTitle = getIntent().getBooleanExtra("showTitle", true);
        showClose = getIntent().getBooleanExtra("showClose", true);
        title_text = getIntent().getStringExtra("title");

        layout = (LinearLayout) findViewById(R.id.layout);
        mWebView = (WVJBWebView) findViewById(R.id.deal_web);
        title_left_layout = (RelativeLayout) findViewById(R.id.title_left);
        title_right_layout = (RelativeLayout) findViewById(R.id.title_right);
        title_right = (TextView) findViewById(R.id.title_right_text);
        title_left = (TextView) findViewById(R.id.title_left_text);
        title_close = (TextView) findViewById(R.id.title_close);
        title_left_image = (ImageView) findViewById(R.id.title_left_image);
        title_right_image = (ImageView) findViewById(R.id.title_right_image);
        title = (TextView) findViewById(R.id.title_middle_text);
        mRLTopLayout = (RelativeLayout) findViewById(R.id.thetopview);
        add_layout = (LinearLayout) findViewById(R.id.add_layout);

        WebViewHelper.setWebView(mWebView);
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        mWebView.loadUrl(getIntent().getStringExtra(TransferKeys.URL));
        doSomethings(mWebView);

    }


    @Override
    public int setViewLayout() {
        return R.layout.web_activity;
    }

    @Override
    public void initView() {

        handler = new WebHandler();
    }

    @Override
    public void onDismiss() {
        if (!flag_onclick) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            } else if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:

                if (showClose) {
                    title_close.setVisibility(View.VISIBLE);
                    title_close.setClickable(true);
                }

                if (model == null) {
                    helper.exit(MyWebActivity.this, layout, mWebView);
                } else {
                    if (model.getLeft().isControl()) {
                        mWebView.callHandler("onNavbarClick", model.getLeft().getName());
                    } else {
                        if (mWebView.canGoBack()) {
                            mWebView.goBack();
                        } else {
                            layout.removeView(mWebView);
                            mWebView.destroy();
                            finish();

                        }
                    }
                }
                break;
            case R.id.title_right:
                if (model != null)
                    mWebView.callHandler("onNavbarClick", model.getRight().getName());
                break;

            case R.id.title_close:
                helper.exit(MyWebActivity.this, layout, mWebView);
                break;
        }
    }

    private static final String TAG = "MyWebActivity";


    private class MyWebChromeClient extends WebChromeClient {


        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            getPicture();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        //<3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            getPicture();
        }

        //>3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            getPicture();
        }

        //>4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            getPicture();
        }
    }

    public class MyWebViewClient extends WVJBWebViewClient {

        public MyWebViewClient(WVJBWebView webView) {
            super(webView);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoadingImage(MyWebActivity.this, LoadingType.FLOWER);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            closeLoadingImage();
        }


        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e("onActivityResult: ---->requestCode:" + requestCode + "resultCode:" + requestCode);
        if (resultCode == 0) {
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            return;
        }
        switch (requestCode) {
            case FILE_CHOOSER_RESULT_CODE:
                if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (mUploadCallbackAboveL != null) {
                    onActivityResultAboveL(requestCode, resultCode, data);
                } else if (mUploadMessage != null) {
                    if (result != null) {
                        String path = getPath(getApplicationContext(), result);
                        Uri uri = Uri.fromFile(new File(path));
                        mUploadMessage.onReceiveValue(uri);
                    } else {
                        mUploadMessage.onReceiveValue(null);
                    }
                    mUploadMessage = null;
                }
                break;
            case PHOTO:
                if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
                if (mUploadCallbackAboveL != null) {
                    Uri[] results = new Uri[]{mPicUri};
                    mUploadCallbackAboveL.onReceiveValue(results);
                    mUploadCallbackAboveL = null;
                } else if (mUploadMessage != null) {
                    if (resultCode != 0) {
                        mUploadMessage.onReceiveValue(mPicUri);
                    } else {
                        mUploadMessage.onReceiveValue(null);
                    }
                }
                break;
            case CODE_CREATE_BOOK_TASK:
                int createStatus = data.getIntExtra("status", 1);
                this.mWVJBResponseCallback.callback(createStatus);
                break;
            case CODE_EDIT_BOOK_TASK:
                int editStatus = data.getIntExtra("status", 1);
                this.mWVJBResponseCallback.callback(editStatus);
                break;
        }
    }

    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.BASE)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {

        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (mUploadCallbackAboveL == null) return;
            Uri[] results = null;

            if (resultCode == Activity.RESULT_OK) {

                if (data == null) {

                    results = new Uri[]{null};
                } else {
                    String dataString = data.getDataString();
                    ClipData clipData = data.getClipData();

                    if (clipData != null) {
                        results = new Uri[clipData.getItemCount()];
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            results[i] = item.getUri();
                        }
                    }

                    if (dataString != null)
                        results = new Uri[]{Uri.parse(dataString)};
                }
            }
            if (results != null) {
                mUploadCallbackAboveL.onReceiveValue(results);
                mUploadCallbackAboveL = null;
            } else {
                results = new Uri[]{null};
                mUploadCallbackAboveL.onReceiveValue(results);
                mUploadCallbackAboveL = null;
            }
        } else {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 获取照片
     */
    private void getPicture() {

        checkTakePhotoPermission(() -> {
            List<String> list_name = new ArrayList<>();
            list_name.add("拍照");
            list_name.add("从相册中选择");

            List<Integer> list_color = new ArrayList<>();
            list_color.add(Color.rgb(51, 51, 51));
            list_color.add(Color.rgb(51, 51, 51));

            menuWindow = new TopMenuPopupWindow(MyWebActivity.this, mOnItemClickListener, list_name, list_color, false);
            menuWindow.showAtLocation(MyWebActivity.this.findViewById(R.id.layout), Gravity.CENTER, 0, 0);

            menuWindow.setDismissListener(MyWebActivity.this);

            flag_onclick = false;
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            if (mWebView.canGoBack()) {
//                mWebView.goBack();
//            } else {
//                helper.exit(MyWebActivity.this, layout, mWebView);
//            }
//            return true;
            if (model != null && model.getLeft().isControl()) {
                mWebView.callHandler("onNavbarClick", model.getLeft().getName());
            } else {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    layout.removeView(mWebView);
                    mWebView.destroy();
                    finish();
                }
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
//            mWebView.setVisibility(View.GONE);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private class WebHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 100:

                    //标题
                    if (!TextUtils.isEmpty(model.getTitle())) {
                        title.setText(helper.convert(model.getTitle()));
                    } else {
                        if (!TextUtils.isEmpty(title_text))
                            title.setText(title_text);
                    }


                    //背景
                    if (!TextUtils.isEmpty(model.getBgColor()))
                        mRLTopLayout.setBackgroundColor(android.graphics.Color.parseColor(model.getBgColor()));


                    //左边布局
                    if (model.getLeft() != null) {
                        if (model.getLeft().isShow()) {
                            title_left_layout.setVisibility(View.VISIBLE);
                        } else {
                            title_left_layout.setVisibility(View.GONE);
                        }
                        //左边文字
                        if (!TextUtils.isEmpty(model.getLeft().getText())) {
                            title_left.setText(helper.convert(model.getLeft().getText()));
                        } else {
                            title_left.setText("");
                        }
                        //左边图标
                        if (!TextUtils.isEmpty(model.getLeft().getIcon())) {
                            title_left_image.setImageResource(getResources().getIdentifier(model.getLeft().getIcon() + "_web", "mipmap", getBaseContext().getPackageName()));
                        } else {
                            title_left_image.setImageResource(R.mipmap.goback);
                        }
                    }


                    //右边布局
                    if (model.getRight() != null) {
                        if (model.getRight().isShow()) {
                            title_right_layout.setVisibility(View.VISIBLE);
                        } else {
                            title_right_layout.setVisibility(View.GONE);
                        }
                        //右边文字
                        if (!TextUtils.isEmpty(model.getRight().getText())) {
                            title_right.setText(helper.convert(model.getRight().getText()));
                        } else {
                            title_right.setText("");
                        }
                        //右边图标
                        if (!TextUtils.isEmpty(helper.convert(model.getRight().getIcon()))) {
                            title_right_image.setVisibility(View.VISIBLE);
                            title_right_image.setImageResource(getResources().getIdentifier(model.getRight().getIcon() + "_web", "mipmap", getBaseContext().getPackageName()));
                        } else {
                            title_right_image.setVisibility(View.GONE);
                        }
                    }

                    add_layout.removeAllViews();
                    List<JsModel.ItemsBean> itemsBeens = model.getItems();
                    if (itemsBeens != null) {
                        for (int i = 0; i < itemsBeens.size(); i++) {
                            final JsModel.ItemsBean itemsBean = itemsBeens.get(i);

                            if (itemsBean.isShow()) {
                                //图标
                                ImageView imageView = new ImageView(MyWebActivity.this);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 60);
                                layoutParams.width = SystemUtil.dip2px(getApplicationContext(), 20);
                                layoutParams.height = SystemUtil.dip2px(getApplicationContext(), 20);
                                layoutParams.setMargins(0, 0, SystemUtil.dip2px(getApplicationContext(), 16), 0);
                                imageView.setLayoutParams(layoutParams);
                                if (!TextUtils.isEmpty(helper.convert(itemsBean.getIcon()))) {
                                    imageView.setImageResource(getResources().getIdentifier(itemsBean.getIcon() + "_web", "mipmap", getBaseContext().getPackageName()));
                                }

                                //文字
                                TextView textView = new TextView(MyWebActivity.this);
                                if (!TextUtils.isEmpty(itemsBean.getText())) {
                                    textView.setText(helper.convert(model.getRight().getText()));
                                }

                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (itemsBean.isControl())
                                            mWebView.callHandler("onNavbarClick", itemsBean.getName());
                                    }
                                });

                                add_layout.addView(imageView);
                                add_layout.addView(textView);
                            }

                        }
                    }

                    break;

                case 200:

                    String info = msg.obj.toString();
                    if (!TextUtils.isEmpty(info))
                        mWebView.callHandler("onVoiceRecordEnd", info);

                    break;
                case 300:

                    String id = msg.obj.toString();
                    if (!TextUtils.isEmpty(id))
                        mWebView.callHandler("onVoicePlayEnd", id);

                    break;
            }

        }
    }

    /**
     * 协议接口
     *
     * @param mWebView
     */
    private void doSomethings(final WVJBWebView mWebView) {

        if (!TextUtils.isEmpty(title_text))
            title.setText(title_text);

        helper = WebViewHelper.getIntence();
        helper.init(MyWebActivity.this, handler);

        /**
         * 标题、按钮显示参数
         */
        mWebView.registerHandler("configNavbar", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            model = JSONObject.parseObject(data.toString(), JsModel.class);
            if (model != null) handler.sendEmptyMessage(100);
        });


        /**
         * 关闭
         */
        mWebView.registerHandler("close", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                helper.exit(MyWebActivity.this, layout, mWebView));

        /**
         * 显示、隐藏title栏
         */
        mWebView.registerHandler("navbarVisible", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                mRLTopLayout.setVisibility((boolean) data ? View.VISIBLE : View.GONE));

        /**
         * 开始录音
         */
        mWebView.registerHandler("startRecord", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                checkRecordPermission(() -> helper.startRecord()));

        /**
         * 停止录音
         */
        mWebView.registerHandler("stopRecord", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                helper.stopRecord(callback));

        /**
         * 开始播放语音
         */
        mWebView.registerHandler("playVoice", (final Object data, final WVJBWebView.WVJBResponseCallback callback) -> {
            if (data != null) {
                checkPlayPermission(() -> helper.playVoice(MyWebActivity.this, data.toString(), callback));
            } else {
                callback.callback(helper.getPlayResultObject(0, ""));//播放失败
            }
        });

        /**
         * 暂停播放语音
         */
        mWebView.registerHandler("pauseVoice", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                helper.pauseVoice());

        /**
         * 停止播放语音
         */
        mWebView.registerHandler("stopVoice", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                helper.stopVoice());

        /**
         * 上传语音文件
         */
        mWebView.registerHandler("uploadVoice", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                helper.uploadVoice(MyWebActivity.this, data.toString(), callback));

        /**
         * 下载语音文件
         */
        mWebView.registerHandler("downloadVoice", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                helper.downloadVoice(MyWebActivity.this, data.toString(), callback));

        /**
         * 发布读书轨迹
         */
        mWebView.registerHandler("showUploadMyRead", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                TrackActivity.start(MyWebActivity.this, callback));

        /**
         * 发布读后感
         */
        mWebView.registerHandler("showBookReview", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                ReadBookActivity.start(MyWebActivity.this, callback));

        /**
         * 发布日志
         */
        mWebView.registerHandler("postBlog", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            if (data == null) return;
            WebBlogBean bean = JSON.parseObject(data.toString(), WebBlogBean.class);
            WebLogActivity.start(MyWebActivity.this, callback, bean);
        });

        /**
         * 发布随记
         */
        mWebView.registerHandler("postMicroblog", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            if (data == null) return;
            WebEssayBean bean = JSON.parseObject(data.toString(), WebEssayBean.class);
            WebEssayActivity.start(MyWebActivity.this, callback, bean);
        });

        /**
         * 调用拍照对话框
         */
        mWebView.registerHandler("uploadAttach", (Object data, WVJBWebView.WVJBResponseCallback callback) -> getPicture());

        /**
         * 跳转主页
         */
        mWebView.registerHandler("showUserHome", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            if (!TextUtils.isEmpty(data.toString())) {
                if (data.toString().contains("objId")) {
                    MyWebBean bean = JSON.parseObject(data.toString(), MyWebBean.class);
                    PersonCenterActivity.start(MyWebActivity.this, bean.getObjId());
                } else {
                    PersonCenterActivity.start(MyWebActivity.this, data.toString());
                }
            }
        });

        /**
         * 跳转话题详情页面
         */
        mWebView.registerHandler("showTopicHome", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            if (!TextUtils.isEmpty(data.toString())) {
                MyWebBean bean = JSON.parseObject(data.toString(), MyWebBean.class);
                TopicActivity.start(MyWebActivity.this, bean.getObjId());
            }
        });

        /**
         * 跳转班级主页
         */
        mWebView.registerHandler("showClassHome", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            if (!TextUtils.isEmpty(data.toString())) {
                MyWebBean bean = JSON.parseObject(data.toString(), MyWebBean.class);
                ClassCircleActivity.start(MyWebActivity.this, bean.getObjId());
            }
        });

        /**
         * 跳转社群主页
         */
        mWebView.registerHandler("showGroupHome", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            if (!TextUtils.isEmpty(data.toString())) {
                MyWebBean bean = JSON.parseObject(data.toString(), MyWebBean.class);
                GroupCenterActivity.start(MyWebActivity.this, bean.getObjId());
            }
        });

        /**
         * 跳转活动主页
         */
        mWebView.registerHandler("showHuodongHome", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            if (!TextUtils.isEmpty(data.toString())) {
                MyWebBean bean = JSON.parseObject(data.toString(), MyWebBean.class);
                HuoDongCenterActivity.start(MyWebActivity.this, bean.getObjId(), bean.isIsClass() ? 1 : 2, 0);
            }
        });

        /**
         * 分享
         */
        mWebView.registerHandler("share", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            try {
                MyShareBean bean = new MyShareBean();
                org.json.JSONObject object = new org.json.JSONObject(data.toString());
                bean.setShareTitle(helper.convert(object.optString("title")));
                bean.setShareUrl(object.optString("url"));
                bean.setSharePic(object.optString("icon"));
                bean.setShareText(helper.convert(object.optString("summary")));
                bean.setShareObjectId(object.optString("objectId"));
                SharePopupWindow popupWindow = new SharePopupWindow(MyWebActivity.this, bean);
                popupWindow.showAtLocation(findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


        /**
         * 加入班级
         */
        mWebView.registerHandler("joinClass", (Object data, WVJBWebView.WVJBResponseCallback callback) ->
                JoinSpecClassActivity.start(this, callback)
        );

        /**
         * 学生阅读打卡页面
         */
        mWebView.registerHandler("readCardRecord", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            this.mWVJBResponseCallback = callback;
            this.mCode = TransferKeys.ConstantNum.NUM_2001;
            final JSONObject jsonObject = JSON.parseObject(data.toString());
            final String bookTaskId = jsonObject.getString(TransferKeys.BOOK_TASK_ID);
            final String type = jsonObject.getString(TransferKeys.TYPE);
            final String isbn = jsonObject.getString(TransferKeys.ISBN);
            final String bookName = jsonObject.getString(TransferKeys.BOOK_NAME);
            final Intent intent = new Intent(MyWebActivity.this, ReadClockInActivity.class);
            intent.putExtra(TransferKeys.BOOK_TASK_ID, bookTaskId);
            intent.putExtra(TransferKeys.TYPE, type);
            intent.putExtra(TransferKeys.ISBN, isbn);
            intent.putExtra(TransferKeys.BOOK_NAME, bookName);
            intent.putExtra(TransferKeys.RESULT,TransferKeys.ConstantNum.NUM_1001);
            CommonAdvanceUtils.startActivity(MyWebActivity.this, intent);
        });

        /**
         * 老师创建读书任务
         */
        mWebView.registerHandler("createBookTask", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            this.mWVJBResponseCallback = callback;
            ClassIdBean classIdBean = new Gson().fromJson(data.toString(), ClassIdBean.class);
            if (classIdBean != null) {
                Intent intent = new Intent(this, AddClockActivity.class);
                intent.putExtra("classId", classIdBean.getClassId());
                intent.putExtra("isEdit",false);
                startActivityForResult(intent, CODE_CREATE_BOOK_TASK);
            }

        });

        /**
         * 二维码扫描
         */
        mWebView.registerHandler("scanBarcode", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            this.mWVJBResponseCallback = callback;
            this.mCode = TransferKeys.ConstantNum.NUM_2000;
            final Intent intent = new Intent(MyWebActivity.this, CaptureActivity.class);
            intent.putExtra(TransferKeys.LOCAL_START_SCAN, 1);
            CommonAdvanceUtils.startActivity(MyWebActivity.this, intent);
        });

        /**
         * 修改任务
         */
        mWebView.registerHandler("editReadingCard", (Object data, WVJBWebView.WVJBResponseCallback callback) -> {
            this.mWVJBResponseCallback = callback;
            TaskItemBean taskItemBean = new Gson().fromJson(data.toString(), TaskItemBean.class);
            if (taskItemBean != null) {
                Intent intent = new Intent(MyWebActivity.this,AddClockActivity.class);
                intent.putExtra("task",taskItemBean.items);
                intent.putExtra("isEdit",true);
                startActivityForResult(intent,CODE_EDIT_BOOK_TASK);
            }
        });
    }

    @Subscribe
    public void onEvent(ReadTaskEvent readTaskEvent) {
        if (readTaskEvent != null) {
            final int localStartScan = readTaskEvent.getLocalStartScan();
            final String result = readTaskEvent.getResult();
            final boolean performPublish = readTaskEvent.isPerformPublish();
            final String bookBn = readTaskEvent.getIsBn();
            if (mCode == TransferKeys.ConstantNum.NUM_2001 && localStartScan == 0 && performPublish) {
                //H5直接调用学生阅读打卡页面
                mWVJBResponseCallback.callback(result);
                return;
            }

            if (mCode == TransferKeys.ConstantNum.NUM_2000 && localStartScan == 1) {
                //H5直接开启扫描页面
                mWVJBResponseCallback.callback(bookBn);
                return;
            }
        }

    }


}

