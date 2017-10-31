package com.histudent.jwsoft.histudent.commen.zxing;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.GroupCenterActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.zxing.camera.CameraManager;
import com.histudent.jwsoft.histudent.commen.zxing.decoding.CaptureActivityHandler;
import com.histudent.jwsoft.histudent.commen.zxing.decoding.InactivityTimer;
import com.histudent.jwsoft.histudent.commen.zxing.view.ViewfinderView;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;
import com.histudent.jwsoft.histudent.comment2.utils.ScanfQrFromLocalUitls;
import com.histudent.jwsoft.histudent.info.InfoHelper;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/*
*
* 扫二维码加入班级或者社群
* */

public class CaptureActivity extends BaseActivity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ImageView iv_back;
    private String objectId = "";
    private ImageView iv_qr;
    private Button btn;
    private List<String> list;
    private TextView tv_right;
    public static getCameraPremissionException cameraException;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //二维码扫描失败后间隔一秒重新扫描
                case 1:
                    if (handler!=null)
                    handler.restartPreviewAndDecode();
                    break;

                //扫描本地二维码图片成功
                case -100:
                    String content = ((String) msg.obj);
                    content = ((String) msg.obj);
                    getQRScanfAction(content);
                    Log.e("AAAA", "" + content);
                    break;

                //扫描本地二维码图片失败
                case -200:
                    content = ((String) msg.obj);
                    Toast.makeText(CaptureActivity.this, R.string.qrcode_error_local, Toast.LENGTH_LONG).show();

                    iv_qr.setImageResource(R.drawable.transparent);
                    Log.e("BBBB", "" + content);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ��ʼ�� CameraManager

        //捕获摄像机异常，提示打开相机权限

        cameraException = new getCameraPremissionException() {

            @Override
            public void cacthException() {

                ReminderHelper.getIntentce().showDialog(CaptureActivity.this, "提示", "无法获取摄像头数据，请检查是否已经打开摄像头权限。",
                        "", null, "确定", new DialogButtonListener() {
                            @Override
                            public void setOnDialogButtonListener() {

                                CaptureActivity.this.finish();
                            }
                        });
            }
        };

        CameraManager.init(getApplication());
        ActivityCollector.addActivity(this);
    }


    @Override
    public void initView() {

        list = new ArrayList<>();
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        iv_qr = ((ImageView) findViewById(R.id.qr_code));
        btn = ((Button) findViewById(R.id.btn));
        iv_back = ((ImageView) findViewById(R.id.title_left_image));
        ((TextView) findViewById(R.id.title_middle_text)).setText("二维码扫描");

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CaptureActivity.this.finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.clear();
                ScanfQrFromLocalUitls.startOpenLocalORCodeIntent(CaptureActivity.this, list);
            }
        });
        tv_right = ((TextView) findViewById(R.id.title_right_text));
        tv_right.setText("开灯");


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:

                CaptureActivity.this.finish();
                break;

            case R.id.btn:
                list.clear();
                ScanfQrFromLocalUitls.startOpenLocalORCodeIntent(CaptureActivity.this, list);
                break;

            case R.id.title_right:

                if ("开灯".equals(tv_right.getText().toString())) {
                    CameraManager.get().openLight();
                    tv_right.setText("关灯");
                } else {
                    CameraManager.get().offLight();
                    tv_right.setText("开灯");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case 200:

                if (requestCode == PictureTailorHelper.PHOTO_REQUEST_GALLERYS) {

                    if (data == null || data.getStringArrayListExtra("return") == null)
                        return;

                    list.clear();
                    list.addAll(((ArrayList) data.getStringArrayListExtra("return")));
                    if (list.size() != 0) {
                        String filePath = list.get(0);

                        Log.e("filePath", "" + filePath);
                        ScanfQrFromLocalUitls.getQRCodePath(filePath, iv_qr, mHandler);
                    }
                }


                break;

            case 300:

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    public int setViewLayout() {
        return R.layout.main;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            cameraException.cacthException();//捕获异常摄像头开启异常
            return;
        } catch (RuntimeException e) {
            cameraException.cacthException();//捕获异常摄像头开启异常
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    public void handleDecode(final Result obj) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        if (!StringUtil.isEmpty(obj.getText())) {
            Log.e("QRResult", obj.getText());
            getQRScanfAction(obj.getText().trim());
        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private void DoAction(String QRContent) {

        if (!QRContent.contains("objectId") || !QRContent.contains("objectType") || !QRContent.contains("action")) {

            Toast.makeText(CaptureActivity.this, R.string.qrcode_error, Toast.LENGTH_LONG).show();
            return;
        }


        String str[] = new String[4];
        JSONObject o = JSON.parseObject(QRContent);
        str[0] = o.getString("objectId");
        str[1] = o.getIntValue("objectType") + "";
        str[2] = o.getIntValue("action") + "";
        Log.e("ObjectId", str[0]);
        Log.e("type", str[1]);
        Log.e("action", str[2]);

        objectId = str[0];

        switch (Integer.parseInt(str[1])) {

            //用户
            case 1:

                InfoHelper.gotoPersonHome(this, objectId, true);
                finish();

                break;

            //班级
            case 2:

                InfoHelper.gotoClassHome(this, objectId, true);
                finish();

                break;

            //社群
            case 3:

                GroupCenterActivity.start(CaptureActivity.this, objectId);
                finish();
                break;
        }

    }


    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }


    private void getQRScanfAction(String QRContent) {

        Map<String, Object> map = new HashMap<>();
        map.put("url", QRContent);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.qrScanf_action_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                JSONObject o = JSON.parseObject(result);
                int processType = o.getIntValue("processType");
                switch (processType) {
                    case 1:

//                        GroupDisDetailActiviy.start(CaptureActivity.this, o.getString("url"), 11, "", "", false, false, false);
                        break;

                    case 2:
                        if (!StringUtil.isEmpty(result))
                            DoAction(result);

                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg) {

                //扫描失败后间隔一秒后会重新扫描
                iv_qr.setImageResource(R.drawable.transparent);
                mHandler.sendEmptyMessageDelayed(1, 3000);

            }
        });
    }


    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    public interface getCameraPremissionException {
        public void cacthException();
    }
}