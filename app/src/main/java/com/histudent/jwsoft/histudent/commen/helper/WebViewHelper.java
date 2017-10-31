package com.histudent.jwsoft.histudent.commen.helper;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gzsll.jsbridge.WVJBWebView;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.activity.NoticePublishActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ShareUtils;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.entity.AudioPlayEvent;
import com.histudent.jwsoft.histudent.entity.AudioPlayStatusEvent;
import com.histudent.jwsoft.histudent.manage.UserManager;
import com.netease.nimlib.sdk.media.player.AudioPlayer;
import com.netease.nimlib.sdk.media.player.OnPlayListener;
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by liuguiyu-pc on 2017/1/3.
 */

public class WebViewHelper {

    private String startTime;
    private File file_r;
    private AudioRecorder recorder;
    private Handler handler;
    private WVJBWebView.WVJBResponseCallback callback_onRecordstop;
    private AudioPlayer player;
    private int curPosition;
    private PopupWindow mPopupWindow;
    private String voicId;

    public static WebViewHelper getIntence() {
        return new WebViewHelper();
    }

    public void init(Activity activity, Handler handler) {
        recorder = new AudioRecorder(activity, RecordType.AAC, 60 * 5, callback);
        this.handler = handler;
    }

    /**
     * 配置web的属性
     *
     * @param mWebView
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setWebView(WebView mWebView) {

        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setLoadWithOverviewMode(true);
        setting.setUserAgentString(setting.getUserAgentString() + " Jwsoft.Hitongx.App");
        setting.setSupportMultipleWindows(true);
        setting.setUseWideViewPort(true);// 可任意比例缩放
        setting.setSavePassword(true);
        setting.setSaveFormData(true);// 保存表单数据

        setting.setPluginState(WebSettings.PluginState.ON);

        setting.setDatabaseEnabled(true);
        setting.setGeolocationEnabled(true);
        String dir = FileUtil.getPathFactory(FileUtil.CacheType.BATEBASE, true).toString() + "/";
        setting.setDatabasePath(dir);
        setting.setGeolocationDatabasePath(dir);

        setting.setAppCacheEnabled(true);
        String cacheDir = FileUtil.getPathFactory(FileUtil.CacheType.WEBVIEW, true).toString() + "/";
        setting.setAppCachePath(cacheDir);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
        setting.setAppCacheMaxSize(1024 * 1024 * 10);
        setting.setAllowFileAccess(true);

        setting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        setting.setBlockNetworkImage(false);

        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(false);

    }

    /**
     * 录制音频监听
     */
    IAudioRecordCallback callback = new IAudioRecordCallback() {
        @Override
        public void onRecordReady() {

        }

        @Override
        public void onRecordStart(File file, RecordType recordType) {

            HiStudentLog.e("开始录音" + file.toString());
            file_r = file;
            startTime = System.currentTimeMillis() + "";

        }

        @Override
        public void onRecordSuccess(File file, long l, RecordType recordType) {

            HiStudentLog.e("结束录音" + file.toString() + "===" + l);
            String info = saveInfo(startTime, (int) l / 1000, file.getAbsolutePath());

            if (callback_onRecordstop != null) {
                callback_onRecordstop.callback(info);
            } else {
                EventBus.getDefault().post(new NoticePublishActivity.VoiceInfo(startTime, (int) l / 1000, file.getAbsolutePath()));
            }
        }

        @Override
        public void onRecordFail() {
            HiStudentLog.e("录音出错");
        }

        @Override
        public void onRecordCancel() {
            HiStudentLog.e("取消录音");
        }

        @Override
        public void onRecordReachedMaxTime(int i) {
            HiStudentLog.e("到达指定的最长录音时间");
            String info = saveInfo(startTime, i / 1000, file_r.getAbsolutePath());
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = info;
                message.what = 200;
                handler.sendMessage(message);
            } else {
                EventBus.getDefault().post(new NoticePublishActivity.VoiceInfo(startTime, i / 1000, file_r.getAbsolutePath()));
            }
        }
    };

    /**
     * 播放音频监听
     */
    OnPlayListener listener = new OnPlayListener() {

        // 音频转码解码完成，会马上开始播放了
        public void onPrepared() {
        }

        // 播放结束
        public void onCompletion() {
            curPosition = 0;
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = voicId;
                message.what = 300;
                handler.sendMessage(message);
            }
            EventBus.getDefault().post(new AudioPlayStatusEvent(0));
        }

        // 播放被中断了
        public void onInterrupt() {
//            EventBus.getDefault().post(new AudioPlayStatusEvent(-1));
        }

        // 播放过程中出错。参数为出错原因描述
        public void onError(String error) {
            EventBus.getDefault().post(new AudioPlayStatusEvent(-1));
        }

        // 播放进度报告，每隔 500ms 会回调一次，告诉当前进度。 参数为当前进度，单位为毫秒，可用于更新 UI
        public void onPlaying(long curPosition_) {
            EventBus.getDefault().post(new AudioPlayEvent(curPosition_));
        }
    };

    /**
     * 修改地址
     *
     * @param url
     * @return
     */
    public static String checkUrl(String url) {

        if (url.contains("{token}"))
            url = url.replace("{token}", HiCache.getInstance().getLoginUserInfo().getToken());

        if (url.contains("{userId}"))
            url = url.replace("{userId}", HiCache.getInstance().getLoginUserInfo().getUserId());

        if (url.contains("{classId}"))
            url = url.replace("{classId}", UserManager.getInstance().getCurrentClassId());

        return url;
    }

    /**
     * 开始录音
     */
    public void startRecord() {
        recorder.startRecord();
    }

    /**
     * 开始录音
     */
    public void startRecord_o() {

        if (recorder != null) {
            if (recorder.isRecording()) {
                HiStudentLog.e("结束录音...");
                recorder.completeRecord(false);
            } else {
                HiStudentLog.e("开始录音...");
                recorder.startRecord();
            }
        }
    }

    public boolean isRecording() {
        if (recorder != null) {
            return recorder.isRecording();
        }
        return false;
    }

    /**
     * 关闭页面
     *
     * @param activity
     * @param layout
     * @param mWebView
     */
    public void exit(BaseActivity activity, LinearLayout layout, WVJBWebView mWebView) {
        stopRecord(null);
        stopVoice();
        layout.removeView(mWebView);
        mWebView.removeAllViews();
        mWebView.destroy();
        activity.finish();
    }

    /**
     * 停止录音
     *
     * @param callback_stop
     */
    public void stopRecord(WVJBWebView.WVJBResponseCallback callback_stop) {

        if (recorder != null) {
            this.callback_onRecordstop = callback_stop;
            // 结束录音, 正常结束，或者取消录音
            recorder.completeRecord(false);
        }
    }

    /**
     * 开始播放语音
     *
     * @param activity
     * @param id       AudioManager.STREAM_VOICE_CALL 表示使用听筒模式
     *                 AudioManager.STREAM_MUSIC 表示使用扬声器模式
     */
    public void playVoice(Activity activity, String id, WVJBWebView.WVJBResponseCallback callback) {
        if (voicId != null && voicId.equals(id)) {
            Log.d(getClass().getSimpleName(), "seekTo--->" + curPosition);
            player.start(AudioManager.STREAM_MUSIC);
            player.seekTo(curPosition);
            callback.callback(getPlayResultObject(1, id));//播放成功
        } else {
            try {
                player = new AudioPlayer(activity, HiCache.getInstance().getVoiceInfo(id).getFile(), listener);
                player.start(AudioManager.STREAM_MUSIC);
                voicId = id;
                callback.callback(getPlayResultObject(1, id));//播放成功
            } catch (Exception e) {
                callback.callback(getPlayResultObject(0, id));//播放失败
            }
        }
    }

    /**
     * 开始播放语音
     *
     * @param activity
     * @param id       AudioManager.STREAM_VOICE_CALL 表示使用听筒模式
     *                 AudioManager.STREAM_MUSIC 表示使用扬声器模式
     */
    public void playVoice_o(Activity activity, String id) {

        if (voicId != null && voicId.equals(id)) {
            player.start(AudioManager.STREAM_MUSIC);
            player.seekTo(curPosition);
        } else {
            try {
                player = new AudioPlayer(activity, HiCache.getInstance().getVoiceInfo(id).getFile(), listener);
                player.start(AudioManager.STREAM_MUSIC);
                voicId = id;
            } catch (Exception e) {
            }
        }
    }

    public boolean isPlaying() {
        if (player != null) {
            return player.isPlaying();
        }
        return false;
    }

    public String getPlayResultObject(int result, String id) {

        JSONObject object = new JSONObject();
        try {
            object.put("result", result);
            object.put("localId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    /**
     * 暂停播放语音
     */
    public void pauseVoice() {
        if (player != null && player.isPlaying()) {
            curPosition = (int) player.getCurrentPosition();
            Log.d(getClass().getSimpleName(), "curPosition--->" + curPosition);
            player.stop();
        }
    }

    /**
     * 停止播放语音
     */
    public void stopVoice() {
        if (player != null && player.isPlaying()) {
            curPosition = 0;
            player.stop();
        }
    }

    /**
     * 上传语音文件
     *
     * @param activity
     * @param data
     * @param callback
     */
    public void uploadVoice(final Activity activity, String data, final WVJBWebView.WVJBResponseCallback callback) {
        String voiceId = null;
        try {
            org.json.JSONObject object = new org.json.JSONObject(data);
            voiceId = object.optString("localId");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(voiceId))
            return;

        List<String> files = new ArrayList<>();
        files.add(HiCache.getInstance().getVoiceInfo(voiceId).getFile());

        Map<String, Object> map = new TreeMap<>();
        map.put("mediaType", 11);
        map.put("ext", HiCache.getInstance().getVoiceInfo(voiceId).getTime());

        HiStudentHttpUtils.postFileByOKHttp((BaseActivity) activity, files, map, HistudentUrl.uploadfile_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    org.json.JSONObject object_r = new org.json.JSONObject(result);
                    org.json.JSONObject object_s = new org.json.JSONObject();
                    object_s.put("serverId", object_r.optString("fileId"));
                    object_s.put("voiceUrl", object_r.optString("filePath"));
                    callback.callback(object_s.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * 下载语音文件
     *
     * @param activity
     * @param data
     */
    public void downloadVoice(final BaseActivity activity, String data, final WVJBWebView.WVJBResponseCallback callback) {

        String voiceId = null;
        try {
            org.json.JSONObject object = new org.json.JSONObject(data);
            voiceId = object.optString("serverId");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(voiceId))
            return;

        if (HiCache.getInstance().getVoiceInfo(voiceId) != null) {
            callback.callback(voiceId);
            return;
        }

        Map<String, Object> map = new TreeMap<>();
        map.put("fileId", voiceId);
        HiStudentHttpUtils.downFileByOKHttp(activity, map, HistudentUrl.downloadfile_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                String[] re = result.split("=");
                HiCache.getInstance().saveVoiceInfo((String) map.get("fileId"), 0, re[0]);
                callback.callback((String) map.get("fileId"));
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });

    }

    /**
     * 下载语音文件
     *
     * @param activity
     * @param path
     */
    public void downloadVoice_o(final BaseActivity activity, String path) {

        if (TextUtils.isEmpty(path))
            return;

        Map<String, Object> map = new TreeMap<>();
        HiStudentHttpUtils.getFileByOKHttp(activity, map, path, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                String[] re = result.split("=");
                HiCache.getInstance().saveVoiceInfo(re[1], 0, re[0]);
                EventBus.getDefault().post(re[1]);
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });

    }

    /**
     * 第三方分享
     *
     * @param activity
     * @param bean
     * @param callback
     */
    public void share(final BaseActivity activity, final MyShareBean bean, final WVJBWebView.WVJBResponseCallback callback) {

        if (bean == null)
            return;

        View popupWindow = LayoutInflater.from(activity).inflate(R.layout.webview_share_layout, null);
        mPopupWindow = new PopupWindow(popupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView wx = (ImageView) popupWindow.findViewById(R.id.share_weixin);
        ImageView py = (ImageView) popupWindow.findViewById(R.id.share_weixin_circle);
        ImageView qq = (ImageView) popupWindow.findViewById(R.id.qq_circle);
        RelativeLayout layout = (RelativeLayout) popupWindow.findViewById(R.id.layout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPopupWindow.dismiss();
                ShareUtils.getShareData_w(activity, bean, 0, callback);

            }
        });

        py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPopupWindow.dismiss();
                ShareUtils.getShareData_w(activity, bean, 1, callback);

            }
        });

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPopupWindow.dismiss();
                ShareUtils.getShareData_w(activity, bean, 2, callback);

            }
        });

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        mPopupWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    /**
     * 保存录音信息
     *
     * @param id
     * @param time
     * @param path
     * @return
     */
    public String saveInfo(String id, int time, String path) {
        HiCache.getInstance().saveVoiceInfo(id, time, path);
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("localId", id);
            object.put("length", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    /**
     * 乱码转换
     *
     * @param unicodeStr
     * @return
     */
    public String convert(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if ((i < maxLoop - 4) && ((unicodeStr.charAt(i) == 'u') || (unicodeStr.charAt(i) == 'U')))
                try {
                    retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 1, i + 5), 16));
                    i += 4;
                } catch (NumberFormatException localNumberFormatException) {
                    retBuf.append(unicodeStr.charAt(i));
                }
            else
                retBuf.append(unicodeStr.charAt(i));
        }
        return retBuf.toString();
    }

    /**
     * 上传单张图片
     *
     * @param activity
     * @param path
     */
    public void uploadAttach(BaseActivity activity, String path, final WVJBWebView.WVJBResponseCallback callback) {
        List<String> stringList = new ArrayList<>();
        stringList.add(path);
        HiStudentHttpUtils.postImageByOKHttp(activity, stringList, null, HistudentUrl.uploadAttach, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                callback.callback(result);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

}
