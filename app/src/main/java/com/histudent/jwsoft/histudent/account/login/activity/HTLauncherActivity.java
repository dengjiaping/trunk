package com.histudent.jwsoft.histudent.account.login.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.adapter.ViewPagerAdapter;
import com.histudent.jwsoft.histudent.account.login.model.CheckUpdateBean;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.body.HTMainActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.IntenetUtil;
import com.histudent.jwsoft.histudent.commen.utils.LocationUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HiStudent App Entrance
 * describe:
 * 1.guide
 * 2.do yunxin automatic login
 */
public class HTLauncherActivity extends BaseActivity {

    private ViewPager mGuideViewPager;
    private Button btnEntry;
    private LinearLayout lltPageIndicator;
    private RelativeLayout layout;
    private ImageView gg_image;
    private RelativeLayout gg_layout;
    private Button mSkipCountTimer;
    private int num;

    /**
     * 登录状态
     * 0：未登录
     * other:已登录
     */
    private int mLoginType;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    sendEmptyMessageDelayed(1, 1000);
                    break;
                case 1:
                    if (num > 0) {
                        gg_layout.setVisibility(View.VISIBLE);
                        mSkipCountTimer.setText("跳过 " + num);
                        sendEmptyMessageDelayed(0, 1000);
                        num--;
                    } else {
                        doLoginAction();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 时间计数器 若在欢迎页面停留超过3s 直接跳转主页面
     */
    private int mCountDownSec = 1;
    private boolean isPageFinish;
    private boolean mEnterSetNetWork;
    private boolean mHasAllPermission;

    private static final String TAG = "HTLauncherActivity";

    private CountDownTimer mCountDownTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mCountDownSec++;
            if (mCountDownSec >= 3) {
                //登录超时
                if (!mEnterSetNetWork && mHasAllPermission)
                    doLoginAction();
            }
        }

        @Override
        public void onFinish() {

        }
    };

    @Override
    public int setViewLayout() {
        return R.layout.welcome_activity;
    }

    @Override
    public void initView() {
        gg_image = (ImageView) findViewById(R.id.gg_image);
        mSkipCountTimer = (Button) findViewById(R.id.gg_button);
        layout = (RelativeLayout) findViewById(R.id.view_image);
        gg_layout = (RelativeLayout) findViewById(R.id.gg_layout);
    }

    @Override
    public void doAction() {
        super.doAction();
        mLoginType = HiCache.getInstance().getLoginStatue();
        checkAllPermission(() -> {
            mHasAllPermission = true;
            if (mLoginType == 0) {
                //Enter Guide Page
                loadGuidePage();
            } else {
                //Enter Splash Page
                loadSplashPage();
            }
        });
    }

    private void loadGuidePage() {
        layout.setVisibility(View.VISIBLE);
        mGuideViewPager = (ViewPager) findViewById(R.id.vp_guide);
        mGuideViewPager.setAdapter(new ViewPagerAdapter(getAllGuidePages()));
        mGuideViewPager.addOnPageChangeListener(mOnPageChangeListener);

        btnEntry = (Button) findViewById(R.id.btn_entry);
        btnEntry.setOnClickListener((View v) -> {
            HiCache.getInstance().exchangeLoginStatue(2);
            doLoginAction();
        });
        lltPageIndicator = (LinearLayout) findViewById(R.id.llt_page_indicator);
        lltPageIndicator.getChildAt(0).setEnabled(false);
    }

    private void loadSplashPage() {
        mCountDownTimer.start();
        mSkipCountTimer.setOnClickListener((View view) -> {
            doLoginAction();
            mHandler.removeMessages(1);
        });
        loadSplashData();
    }

    private List<View> getAllGuidePages() {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            views.add(getGuidePage(i));
        }
        return views;
    }

    private View getGuidePage(int i) {
        View v = View.inflate(this, R.layout.include_guide_page, null);
        ImageView ivGuidePage = v.findViewById(R.id.iv_guide_page);
        switch (i) {
            case 0:
                ivGuidePage.setImageResource(R.mipmap.welcome_01);
                break;
            case 1:
                ivGuidePage.setImageResource(R.mipmap.welcome_02);
                break;
            case 2:
                ivGuidePage.setImageResource(R.mipmap.welcome_03);
                break;
            case 3:
                ivGuidePage.setImageResource(R.mipmap.welcome_04);
                break;
            case 4:
                ivGuidePage.setImageResource(R.mipmap.welcome_05);
                break;
        }

        return ivGuidePage;
    }

    private void clearIndicatorFocusedState() {
        int childCount = lltPageIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            lltPageIndicator.getChildAt(i).setBackgroundResource(R.drawable.page_indicator_bg_fase);
        }
    }


    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            clearIndicatorFocusedState();
            lltPageIndicator.getChildAt(position).setBackgroundResource(R.drawable.page_indicator_bg_true);

            if (position == 4)
                btnEntry.setVisibility(View.VISIBLE);
            else
                btnEntry.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 加载闪屏页面所需要数据
     * 根据返回的数据信息去控制广告的显示概况
     */
    private void loadSplashData() {
        if (IntenetUtil.getNetworkState() > 0) {

            LocationUtils.getLocationUtils().getLocationInfor(this, null);
            Map<String, Object> map_sign = new HashMap<>();
            map_sign.put("devicetype", "1");
            map_sign.put("version", SystemUtil.getVersion());
            HiStudentHttpUtils.postDataByOKHttp(HTLauncherActivity.this, map_sign, HistudentUrl.update_apk_url, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    if (isPageFinish) return;
                    initAdvertisementPage(result);
                    doYunXinAutomaticLogin();
                }

                @Override
                public void onFailure(String errorMsg) {
                    if (isPageFinish) return;
                    doYunXinAutomaticLogin();
                }

            }, LoadingType.NONE);

        } else {
            solveNetworkStatus();
        }
    }


    /**
     * 初始化广告页面
     */
    private void initAdvertisementPage(String result) {
        if (StringUtil.isEmpty(result)) {
            ToastTool.showCommonToast(this, "数据异常,请稍候再试！");
            return;
        }
        CheckUpdateBean checkUpdateBean = JSON.parseObject(result, CheckUpdateBean.class);
        if (checkUpdateBean == null)
            return;
        if (checkUpdateBean.getLaunchpage() != null && checkUpdateBean.getPageTime() > 0) {
            String path = checkUpdateBean.getLaunchpage().get(0).getImgUrl();
            num = checkUpdateBean.getPageTime();
            gg_layout.setVisibility(View.VISIBLE);
            MyImageLoader.getIntent().displayNetImage(this, path, gg_image);
            mHandler.sendEmptyMessage(1);
            mCountDownTimer.cancel();
        }
    }


    /**
     * 云信自动登录
     */
    private void doYunXinAutomaticLogin() {
        HiStudentLog.e("Yun Xin Start Login");
        CurrentUserInfoModel model = HiCache.getInstance().getLoginUserInfo();
        if (model == null) {
            if (isPageFinish) return;
            LoginActivity.start(HTLauncherActivity.this);
            finish();
        } else {
            if (TextUtils.isEmpty(model.getToken())) {
                if (isPageFinish) return;
                LoginActivity.start(HTLauncherActivity.this);
                finish();
                return;
            }

            HiStudentLog.e("YUNXIN--->token:" + model.getToken());

            LoginInfo info = new LoginInfo(model.getUserId().replace("-", ""), model.getImToken()); // config...
            NIMClient.getService(AuthService.class).login(info).setCallback(new RequestCallbackWrapper() {
                @Override
                public void onResult(int i, Object o, Throwable throwable) {
                    if (isPageFinish) return;
                    HiStudentLog.e("Yun Xin Start Login------>back:i===" + i);
                    if (i == ResponseCode.RES_SUCCESS) {
                        HiCache.getInstance().exchangeLoginStatue(1);
                        mHandler.sendEmptyMessage(1);
                    } else {
                        //登录失败 跳转登录页面
                        LoginActivity.start(HTLauncherActivity.this);
                        finish();
                    }
                    mCountDownTimer.cancel();
                }
            });
        }
    }

    /**
     * 登录跳转
     */
    private void doLoginAction() {
        if (isPageFinish)
            return;
        if (IntenetUtil.getNetworkState() > 0) {
            if (mLoginType == 1 && !TextUtils.isEmpty(HiCache.getInstance().getUserLoginToken())) {
                HTMainActivity.start(HTLauncherActivity.this);
            } else {
                LoginActivity.start(HTLauncherActivity.this);
            }
            isPageFinish = true;
            finish();
        } else {
            //没有网络连接
            solveNetworkStatus();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mEnterSetNetWork = false;
        if (requestCode == 1000) {
            //设置网络返回
            mCountDownSec = 1;
            loadSplashPage();
        }
    }

    /**
     * 处理无网络状态
     */
    private void solveNetworkStatus() {
        mEnterSetNetWork = true;
        ReminderHelper.getIntentce().showDialog(this, "提示", "当前网络不可用，请连接网络后重试", "退出", () -> HTLauncherActivity.this.finish(), "设置网络", () -> {
            Intent intent;
            if (Build.VERSION.SDK_INT > 10) {
                // 3.0以上
                intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
            } else {
                intent = new Intent();
                ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                intent.setComponent(component);
                intent.setAction("android.intent.action.VIEW");
            }
            HTLauncherActivity.this.startActivityForResult(intent, 1000);
            mCountDownTimer.cancel();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mCountDownTimer.cancel();
    }


}
