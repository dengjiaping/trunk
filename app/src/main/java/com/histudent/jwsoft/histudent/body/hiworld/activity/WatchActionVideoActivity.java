package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.aliyun.vodplayer.media.AliyunMediaInfo;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVidSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.google.gson.Gson;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.VideoAuthBean;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ScreenStatusController;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by liuguiyu-pc on 2017/4/14.
 * 播放发布随记时的预览视频
 */

public class WatchActionVideoActivity extends BaseActivity {

    private String id;
    private AliyunPlayAuth mPlayAuth = null;
    private ScreenStatusController mScreenStatusController = null;
    private Button rateBtn;
    private SeekBar mSmallSeekBar;
    private SeekBar mFullSeekBar;

    @BindView(R.id.video_view)
    AliyunVodPlayerView mPlayerView;

    public static void start(Activity activity, String id) {
        Intent intent = new Intent(activity, WatchActionVideoActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return R.layout.activity_watch_action_movie;
    }

    @Override
    public void initView() {
        id = getIntent().getStringExtra("id");
        ImageView mMode = (ImageView) findViewById(R.id.alivc_screen_mode);
        TextView mTitle = (TextView) findViewById(R.id.alivc_title_title);
        rateBtn = (Button) findViewById(R.id.alivc_info_large_rate_btn);
        mSmallSeekBar = (SeekBar) findViewById(R.id.alivc_info_small_seekbar);
        mFullSeekBar = (SeekBar) findViewById(R.id.alivc_info_large_seekbar);
        rateBtn = (Button) findViewById(R.id.alivc_info_large_rate_btn);
        mMode.setVisibility(View.GONE);
        mTitle.setVisibility(View.GONE);
        rateBtn.setVisibility(View.GONE);
    }


    @Override
    public void doAction() {
        super.doAction();
        getAuthInfo();
    }

    private void getAuthInfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("videoId", id);
        HiStudentHttpUtils.getDataByOKHttp(this, params, HistudentUrl.getVodPlayAuth, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                VideoAuthBean videoAuthBean = new Gson().fromJson(result, VideoAuthBean.class);
                initVidSource(videoAuthBean.getPlayAuth());
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(WatchActionVideoActivity.this, "获取视频失败", Toast.LENGTH_SHORT).show();
            }
        }, LoadingType.FLOWER);
    }

    private void initVidSource(String playAuth) {
        mScreenStatusController = new ScreenStatusController(this);
        mScreenStatusController.setScreenStatusListener(new ScreenStatusController.ScreenStatusListener() {
            @Override
            public void onScreenOn() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onScreenOff() {

            }
        });

        mScreenStatusController.startListen();

        AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
        aliyunPlayAuthBuilder.setPlayAuth(playAuth);
        aliyunPlayAuthBuilder.setVid(id);
        aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL);
        mPlayAuth = aliyunPlayAuthBuilder.build();
        mPlayerView.setAuthInfo(mPlayAuth);
        mPlayerView.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {

                if (NetworkUtil.isWifi(WatchActionVideoActivity.this)) {
                    if (!isFinishing()){
                        mPlayerView.start();
                    }

                }
            }
        });


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePlayerViewMode();
        if (mPlayerView != null) {
            mPlayerView.resume();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayerView != null) {
            mPlayerView.stop();
        }
    }


    private void updatePlayerViewMode() {
        if (mPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
                //显示状态栏
                if (Build.DEVICE.equalsIgnoreCase("mx5")
                        || Build.DEVICE.equalsIgnoreCase("Redmi Note2")
                        || Build.DEVICE.equalsIgnoreCase("Z00A_1")) {
                    getSupportActionBar().show();
                }
                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                ViewGroup.LayoutParams aliVcVideoViewLayoutParams = mPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

                //设置为小屏状态
                mPlayerView.changeScreenMode(AliyunScreenMode.Small);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
                //隐藏状态栏
                if (Build.DEVICE.equalsIgnoreCase("mx5")
                        || Build.DEVICE.equalsIgnoreCase("Redmi Note2")
                        || Build.DEVICE.equalsIgnoreCase("Z00A_1")) {
                    getSupportActionBar().hide();
                } else if (!(Build.DEVICE.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"))) {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }

                //设置view的布局，宽高
                ViewGroup.LayoutParams aliVcVideoViewLayoutParams = mPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

                //设置为全屏状态
                mPlayerView.changeScreenMode(AliyunScreenMode.Full);
                rateBtn.setVisibility(View.GONE);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayerView != null) {
            mPlayerView.destroy();
            mPlayerView = null;
        }
        if (mScreenStatusController != null) {
            mScreenStatusController.stopListen();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView != null) {
            boolean handler = mPlayerView.onKeyDown(keyCode, event);
            if (!handler) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
