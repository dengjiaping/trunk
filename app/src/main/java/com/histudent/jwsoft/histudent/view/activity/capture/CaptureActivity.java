package com.histudent.jwsoft.histudent.view.activity.capture;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.widget.RoundProgressBar;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.util.file.AttachmentStore;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.sys.TimeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 视频录制界面
 * <p/>
 * Created by huangjun on 2015/4/11.
 */
public class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PictureCallback {

    private static final String TAG = "video";

    private static final String EXTRA_DATA_FILE_NAME = "EXTRA_DATA_FILE_NAME";

    private static final int VIDEO_TIMES = 30;//最长录制时间

    private static final int VIDEO_WIDTH = 320;

    private static final int VIDEO_HEIGHT = 240;

    private Camera.Parameters parameters = null;

    private long recordTotalTime;

    //类型
    private int action;//(1:拍照,2:摄像)

    // media

    private MediaRecorder mediaRecorder;// 录制视频的类

    private Camera camera;

    private int button_stute;//按钮状态
    private int PRESS = 100;
    private int UP = -100;
    private int action_stute;//操作类型
    private int MOVIE = 99;
    private int PHOTO = -99;

    // view

    private SurfaceView surfaceview;

    private SurfaceHolder surfaceHolder;

    //    private ButtonCircleProgressBar recordBtn;
    private RelativeLayout recordBtn;

    private IconView cancle, complete;

    private ImageView recordingState;

    private TextView recordingTimeTextView, text_tip;

    private IconView switchCamera, exit; // 切换摄像头

    private LinearLayout time_layout;

    // state

    private int cameraId = 0;

    private String filename, path;

    private boolean previewing = false;

    private boolean multiCamera = false;

    private boolean recording = false;

    private long start, end; // 录制时间控制

    private long duration = 0;

    private boolean destroyed = false;

    private int mAngle = 0;

    private Camera.Parameters mParams;

    private int flag;//1:拍照，2:摄像，3:拍照与摄像

    private LinkedList<Point> backCameraSize = new LinkedList<>();

    private LinkedList<Point> frontCameraSize = new LinkedList<>();

    public static void start(Activity activity, String videoFilePath, int captureCode, int flag) {
        Intent intent = new Intent();
        intent.setClass(activity, CaptureActivity.class);
        intent.putExtra(EXTRA_DATA_FILE_NAME, videoFilePath);
        intent.putExtra("flag", flag);
        activity.startActivityForResult(intent, captureCode);
    }

    // context
    private int MSG_PROGRESS_UPDATE = -100;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    if (button_stute == PRESS) {
                        action_stute = MOVIE;
                        startRecorder();
                    }

                    break;
                case 1:
                    if (action_stute == MOVIE) {
                        stopRecorder();
                        updateButton(true);
                    } else {
                        tackePhoto();
                        updateButton(true);
                    }
                    break;
            }
        }
    };

    private int progress;
    private long temp;

    // 录制时间计数
    private Runnable runnable = new Runnable() {

        public void run() {
            end = new Date().getTime();
            temp = (end - start);
            duration = recordTotalTime - end;
            int invs = (int) (duration / 1000);

            recordingTimeTextView.setText(TimeUtil.secToTime(invs));
            String time = TimeUtil.secToTime((int) (temp / 1000));
            time = time.substring(time.length() - 2, time.length());
            progress = Integer.parseInt(time);
            if (mOnTimeChangeListener != null) {
                mOnTimeChangeListener.onChange(progress);
            }

            // 录制过程中红点闪烁效果
            if (invs % 2 == 0) {
                recordingState.setBackgroundResource(R.drawable.nim_record_start);
            } else {
                recordingState.setBackgroundResource(R.drawable.nim_record_video);
            }
            if (invs == 0) {
                stopRecorder();
                sendVideo();
            } else {
                handler.postDelayed(this, 1000);
            }
        }
    };

    public interface OnTimeChangeListener {
        void onChange(int progress);
    }

    public void setOnTimeChangedListener(OnTimeChangeListener onTimeChangeListener) {
        this.mOnTimeChangeListener = onTimeChangeListener;
    }

    public OnTimeChangeListener mOnTimeChangeListener;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_capture);
        setTitle(R.string.video_record);

        parseIntent();
        findViews();
        initActionBar();

        setViewsListener();
        updateRecordUI();

        getVideoPreviewSize();

        surfaceview = (SurfaceView) this.findViewById(R.id.videoView);

        SurfaceHolder holder = surfaceview.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);

        resizeSurfaceView();
    }

    private void parseIntent() {
        flag = getIntent().getExtras().getInt("flag");
        path = getIntent().getExtras().getString(EXTRA_DATA_FILE_NAME);
    }

    private RoundProgressBar mRoundProgressBar;

    private void findViews() {
        cancle = (IconView) findViewById(R.id.cancel);
        complete = (IconView) findViewById(R.id.complete);
        recordingTimeTextView = (TextView) findViewById(R.id.record_times);
        recordingState = (ImageView) findViewById(R.id.recording_id);
        recordBtn = (RelativeLayout) findViewById(R.id.record_btn);
        switchCamera = (IconView) findViewById(R.id.switch_cameras);
        time_layout = (LinearLayout) findViewById(R.id.time_layout);
        exit = (IconView) findViewById(R.id.exit);
        text_tip = (TextView) findViewById(R.id.text_tip);
        mRoundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar);
        text_tip.setText(flag == 1 ? "轻触拍照" : flag == 3 ? "轻触拍照，按住摄像" : "");

    }

    private void initActionBar() {
        checkMultiCamera();
    }


    private void setViewsListener() {

        //重拍
        cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                action = 0;
                updateButton(false);
                cancelRecord();
            }
        });

        exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //完成
        complete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("duration", temp);
                intent.putExtra("file", filename);
                setResult(action == 1 ? -2 : -1, intent);
                finish();
            }
        });

        //监听长按操作用于判断，是拍照还是摄像
        if (flag == 3) {
            //录像
            recordBtn.setOnTouchListener((View view, MotionEvent motionEvent) -> {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button_stute = PRESS;
                        action_stute = PHOTO;
                        handler.sendEmptyMessageDelayed(0, 500);
                        setOnTimeChangedListener((int progress) -> {
                            Log.e(TAG, "progress-->" + progress);
                            mRoundProgressBar.setProgress(progress * 35);
                        });
                        break;
                    case MotionEvent.ACTION_UP:
                        button_stute = UP;
                        handler.sendEmptyMessage(1);
                        mRoundProgressBar.setProgress(0);
                        break;
                }
                return true;
            });
        }

        if (flag == 1) {
            //拍照
            recordBtn.setOnClickListener((View view) -> {
                tackePhoto();
                updateButton(true);
            });
        }

        //切换摄像头
        switchCamera.setOnClickListener((View v) -> switchCamera());

    }


    @TargetApi(9)
    private void switchCamera() {
        if (Build.VERSION.SDK_INT >= 9) {
            cameraId = (cameraId + 1) % Camera.getNumberOfCameras();
        }
        resizeSurfaceView();
        shutdownCamera();
        initCamera();
        startPreview();
    }

    public void onResume() {
        super.onResume();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void onPause() {
        super.onPause();

        getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (recording) {
            stopRecorder();
            sendVideo();
        } else {
//            shutdownCamera();
        }

    }

    public void onDestroy() {
        super.onDestroy();

        shutdownCamera();

        destroyed = true;
    }

    @Override
    public void onBackPressed() {
        if (recording) {
            stopRecorder();
        }

        shutdownCamera();

        setResult(RESULT_CANCELED);
        finish();
    }


    protected boolean isCompatible(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    @SuppressLint("NewApi")
    private void getVideoPreviewSize(boolean isFront) {
        CamcorderProfile profile;
        int cameraId = 0;

        if (isCompatible(9)) {
            if (isFront) {
                cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            } else {
                cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            }
        }

        if (isCompatible(11)) {
            if (CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_480P)) {
                profile = CamcorderProfile.get(cameraId, CamcorderProfile.QUALITY_480P);
                if (profile != null) {
                    Point point = new Point();
                    point.x = profile.videoFrameWidth;
                    point.y = profile.videoFrameHeight;
                    if (isFront) {
                        frontCameraSize.addLast(point);
                    } else {
                        backCameraSize.addLast(point);
                    }
                }
            } else {
                LogUtil.e(TAG, (isFront ? "Back Camera" : "Front Camera") + " no QUALITY_480P");
            }

            if (CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_CIF)) {
                profile = CamcorderProfile.get(cameraId, CamcorderProfile.QUALITY_CIF);
                if (profile != null) {
                    Point point = new Point();
                    point.x = profile.videoFrameWidth;
                    point.y = profile.videoFrameHeight;
                    if (isFront) {
                        frontCameraSize.addLast(point);
                    } else {
                        backCameraSize.addLast(point);
                    }
                }
            } else {
                LogUtil.e(TAG, (isFront ? "Back Camera" : "Front Camera") + " no QUALITY_CIF");
            }

            if (isCompatible(15)) {
                if (CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_QVGA)) {
                    profile = CamcorderProfile.get(cameraId, CamcorderProfile.QUALITY_QVGA);
                    if (profile != null) {
                        Point point = new Point();
                        point.x = profile.videoFrameWidth;
                        point.y = profile.videoFrameHeight;
                        if (isFront) {
                            frontCameraSize.addLast(point);
                        } else {
                            backCameraSize.addLast(point);
                        }
                    }
                } else {
                    LogUtil.e(TAG, (isFront ? "Back Camera" : "Front Camera") + " no QUALITY_QVGA");
                }
            }
        }

        if (isCompatible(9)) {
            profile = CamcorderProfile.get(cameraId, CamcorderProfile.QUALITY_LOW);
            if (profile == null) {
                Point point = new Point();
                point.x = 320;
                point.y = 240;
                if (isFront) {
                    frontCameraSize.addLast(point);
                } else {
                    backCameraSize.addLast(point);
                }
                LogUtil.e(TAG, (isFront ? "Back Camera" : "Front Camera") + " no QUALITY_LOW");
            } else {
                Point point = new Point();
                point.x = profile.videoFrameWidth;
                point.y = profile.videoFrameHeight;
                if (isFront) {
                    frontCameraSize.addLast(point);
                } else {
                    backCameraSize.addLast(point);
                }
            }
        } else {
            if (!isFront) {
                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
                if (profile == null) {
                    Point point = new Point();
                    point.x = 320;
                    point.y = 240;
                    backCameraSize.addLast(point);
                } else {
                    Point point = new Point();
                    point.x = profile.videoFrameWidth;
                    point.y = profile.videoFrameHeight;
                    backCameraSize.addLast(point);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private void getVideoPreviewSize() {
        backCameraSize.clear();
        frontCameraSize.clear();
        getVideoPreviewSize(false);
        if (Build.VERSION.SDK_INT >= 9) {
            if (Camera.getNumberOfCameras() >= 2) {
                getVideoPreviewSize(true);
            }
        }
    }

    private Point currentUsePoint = null;

    private void resizeSurfaceView() {
        Point point;
        if (cameraId == 0) {
            point = backCameraSize.getFirst();
        } else {
            point = frontCameraSize.getFirst();
        }
        if (currentUsePoint != null && point.equals(currentUsePoint)) {
            return;
        } else {
            currentUsePoint = point;
            int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
            int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
            int surfaceHeight = screenWidth * point.x / point.y;
            if (surfaceview != null) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) surfaceview.getLayoutParams();
                lp.width = screenWidth;
                lp.height = screenHeight;
                lp.addRule(13);
                surfaceview.setLayoutParams(lp);
            }
        }
    }

    @SuppressLint("NewApi")
    private void setCamcorderProfile() {
        CamcorderProfile profile;
        if (isCompatible(11)) {
            if (CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_CIF)) {
                profile = CamcorderProfile.get(cameraId, CamcorderProfile.QUALITY_CIF);
            } else {
                profile = CamcorderProfile.get(cameraId, CamcorderProfile.QUALITY_LOW);
            }
        } else {
            if (isCompatible(9)) {
                profile = CamcorderProfile.get(cameraId, CamcorderProfile.QUALITY_LOW);
            } else {
                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
            }
        }

        if (profile != null) {
            if (currentUsePoint != null) {
                profile.videoFrameWidth = currentUsePoint.x;
                profile.videoFrameHeight = currentUsePoint.y;
            }

            profile.fileFormat = MediaRecorder.OutputFormat.MPEG_4;

            if (Build.MODEL.equalsIgnoreCase("MB525") || Build.MODEL.equalsIgnoreCase("C8812") || Build.MODEL.equalsIgnoreCase("C8650")) {
                profile.videoCodec = MediaRecorder.VideoEncoder.H263;
            } else {
                profile.videoCodec = MediaRecorder.VideoEncoder.H264;
            }

            if (Build.VERSION.SDK_INT < 11) {
                profile.videoCodec = MediaRecorder.VideoEncoder.H263;
            }

            if (Build.VERSION.SDK_INT >= 14) {
                profile.audioCodec = MediaRecorder.AudioEncoder.AAC;
            } else {
                if (Build.DISPLAY != null && Build.DISPLAY.indexOf("MIUI") >= 0) {
                    profile.audioCodec = MediaRecorder.AudioEncoder.AAC;
                } else {
                    profile.audioCodec = MediaRecorder.AudioEncoder.AMR_NB;
                }
            }
            mediaRecorder.setProfile(profile);
        } else {
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setVideoSize(VIDEO_WIDTH, VIDEO_HEIGHT);
        }
    }

    @SuppressLint("NewApi")
    private void setVideoOrientation() {
        if (Build.VERSION.SDK_INT >= 9) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, info);
            mediaRecorder.setOrientationHint(info.orientation);
        }
    }

    public void updateRecordUI() {
//        if (recording) {
//            recordBtn.setBackgroundResource(R.drawable.nim_video_capture_stop_btn);
//        } else {
//            recordBtn.setBackgroundResource(R.drawable.nim_video_capture_start_btn);
//        }
    }

    private boolean startRecorderInternal() throws Exception {
        shutdownCamera();
        if (!initCamera())
            return false;

        switchCamera.setVisibility(View.GONE);

        mediaRecorder = new MediaRecorder();

        camera.unlock();
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        setCamcorderProfile();

        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setMaxDuration(1000 * VIDEO_TIMES);
        mediaRecorder.setOutputFile(filename);
        setVideoOrientation();

        mediaRecorder.prepare();
        mediaRecorder.start();
        return true;
    }

    /**
     * 开始摄像
     */
    private void startRecorder() {

//        //去除视频录制的声音
//        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
//        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
//        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
//        audioManager.setStreamVolume(AudioManager.STREAM_DTMF, 0, 0);
//        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
//        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);

        action = 2;
        try {
            if (TextUtils.isEmpty(filename)) {
                File file = new File(path);
                if (!file.exists()) file.mkdirs();
                filename = path + "/" + System.currentTimeMillis() + ".mp4";
                File file_ = new File(filename);
                if (!file_.exists()) file_.createNewFile();
            }
            startRecorderInternal();
        } catch (Exception e) {
            LogUtil.e(TAG, "start MediaRecord failed: " + e);
            Toast.makeText(this, R.string.start_camera_to_record_failed, Toast.LENGTH_SHORT).show();
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }
            if (camera != null) {
                camera.release();
                camera = null;
            }
            return;

        }
        recording = true;
        start = new Date().getTime();
        handler.postDelayed(runnable, 1000);
        recordTotalTime = start + VIDEO_TIMES * 1000;

//        recordingTimeTextView.setText("00:00");
        recordingTimeTextView.setText("00:30");

        updateRecordUI();
    }

    /**
     * 停止摄像
     */
    private void stopRecorder() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (Exception e) {
                LogUtil.w(TAG, getString(R.string.stop_fail_maybe_stopped));
            }
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if (camera != null) {
            camera.release();
            camera = null;
        }

        handler.removeCallbacks(runnable);
        recordingState.setBackgroundResource(R.drawable.nim_record_start);
        recording = false;
        updateRecordUI();
    }

    /**
     * 开始拍照
     */
    private void tackePhoto() {

        action = 1;
        try {
            if (TextUtils.isEmpty(filename)) {
                File file = new File(path);
                if (!file.exists())
                    file.mkdirs();
                String filPath = path + "/" + System.currentTimeMillis() + ".jpg";
                File file_ = new File(filPath);
                if (!file_.exists())
                    file_.createNewFile();
                filename = file_.getPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Intent intent = new Intent(
//                MediaStore.ACTION_IMAGE_CAPTURE);
//        //下面这句指定调用相机拍照后的照片存储的路径
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//                .fromFile(TakePicUtils.getTepFile()));
//        activity.startActivityForResult(intent, resultCode);
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPictureFormat(PixelFormat.JPEG);
            parameters.set("orientation", "portrait");
            parameters.setRotation(90);
            camera.setParameters(parameters);
            camera.takePicture(null, null, CaptureActivity.this);
        }
    }

    private void updateButton(boolean flag) {

        cancle.setVisibility(flag ? View.VISIBLE : View.GONE);
        complete.setVisibility(flag ? View.VISIBLE : View.GONE);
        time_layout.setVisibility(flag ? View.GONE : View.VISIBLE);
        exit.setVisibility(flag ? View.GONE : View.VISIBLE);
        switchCamera.setVisibility(flag ? View.GONE : View.VISIBLE);

        recordBtn.setVisibility(flag ? View.GONE : View.VISIBLE);
        text_tip.setVisibility(flag ? View.GONE : View.VISIBLE);

    }

    private void sendVideo() {
        updateButton(true);
//        File convertedFile = new File(filename);
//        String message = "";
//        if (convertedFile.exists()) {
//            int b = (int) convertedFile.length();
//            int kb = b / 1024;
//            float mb = kb / 1024f;
//            message += mb > 1 ? getString(R.string.capture_video_size_in_mb, mb) : getString(
//                    R.string.capture_video_size_in_kb, kb);
//            message += getString(R.string.is_send_video);
//            if (mb <= 1 && kb < 10) {
//                tooShortAlert();
//                return;
//            }
//        }
//        EasyAlertDialogHelper.OnDialogActionListener listener = new EasyAlertDialogHelper.OnDialogActionListener() {
//
//            @Override
//            public void doCancelAction() {
//                cancelRecord();
//            }
//
//            @Override
//            public void doOkAction() {
//                Intent intent = new Intent();
//                intent.putExtra("duration", duration);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        };
//
//        final EasyAlertDialog dialog = EasyAlertDialogHelper.createOkCancelDiolag(this, null, message, true, listener);
//
//        if (!isFinishing() && !destroyed) {
//            dialog.show();
//        }
    }

    /**
     * 视频录制太短
     */
    private void tooShortAlert() {
        EasyAlertDialogHelper.showOneButtonDiolag(this, null, getString(R.string.video_record_short), getString(R.string.iknow), true, new OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelRecord();
            }
        });
    }

    /**
     * 取消重录
     */
    private void cancelRecord() {
        AttachmentStore.delete(filename);
//        recordingTimeTextView.setText("00:00");
        recordingTimeTextView.setText("00:30");
        shutdownCamera();
        initCamera();
        startPreview();
        checkMultiCamera();
    }

    /**
     * *************************************************** Camera Start ***************************************************
     */
    @SuppressLint("NewApi")
    public void checkMultiCamera() {
        if (Build.VERSION.SDK_INT >= 9) {
            if (Camera.getNumberOfCameras() > 1) {
                multiCamera = true;
                switchCamera.setVisibility(View.VISIBLE);
            } else {
                switchCamera.setVisibility(View.GONE);
            }
        } else {
            switchCamera.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NewApi")
    private boolean initCamera() {
        try {
            if (multiCamera) {
                camera = Camera.open(cameraId);
            } else {
                camera = Camera.open();
            }
        } catch (RuntimeException e) {
            LogUtil.e(TAG, "init camera failed: " + e);
            Toast.makeText(this, R.string.connect_vedio_device_fail, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (camera != null) {
            setCameraParameters();
        }

        return camera != null;
    }

    @SuppressLint("NewApi")
    private void setCameraParameters() {
        Camera.Parameters params = camera.getParameters();

        if (Build.VERSION.SDK_INT >= 15) {
            if (params.isVideoStabilizationSupported()) {
                params.setVideoStabilization(true);
            }
        }

        List<String> focusMode = params.getSupportedFocusModes();
        if (focusMode.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }

        if (params != null) {
            mAngle = setCameraDisplayOrientation(this, cameraId, camera);
            Log.i(TAG, "camera angle = " + mAngle);
        }

        params.setPreviewSize(currentUsePoint.x, currentUsePoint.y);

        try {
            camera.setParameters(params);
        } catch (RuntimeException e) {
            LogUtil.e(TAG, "setParameters failed", e);

        }
    }

    private void shutdownCamera() {
        if (camera != null) {
            if (previewing) {
                camera.stopPreview();
            }
            camera.release();
            camera = null;
            previewing = false;
        }
    }

    /**
     * **************************** SurfaceHolder.Callback Start *******************************
     */

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surfaceHolder = holder;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;

        shutdownCamera();
        if (!initCamera())
            return;
        startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceHolder = null;
        mediaRecorder = null;
    }

    /**
     * ************************ SurfaceHolder.Callback Start ********************************
     */

    private void startPreview() {
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            previewing = true;
        } catch (Exception e) {
            Toast.makeText(this, R.string.connect_vedio_device_fail, Toast.LENGTH_SHORT).show();
            shutdownCamera();
            e.printStackTrace();
        }
    }

    /**
     * ********************************* camera util ************************************
     */
    @SuppressLint("NewApi")
    public int setCameraDisplayOrientation(Context context, int cameraId, Camera camera) {
        int orientation = 90;
        boolean front = (cameraId == 1);
        if (Build.VERSION.SDK_INT >= 9) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, info);
            orientation = info.orientation;
            front = (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT);
        }

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int rotation = manager.getDefaultDisplay().getRotation();
        int activityOrientation = roundRotation(rotation);
        int result;
        if (front) {
            result = (orientation + activityOrientation) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (orientation - activityOrientation + 360) % 360;
            //遇到过一个小米1s后置摄像头旋转180°，但是不确定是不是所有小米1s都是这样的. 先做一个适配,以后有问题再说.
            if ("Xiaomi_MI-ONE Plus".equalsIgnoreCase(Build.MANUFACTURER + "_" + Build.MODEL)) {
                result = 90;
            }
        }
        camera.setDisplayOrientation(result);
        return result;
    }

    private int roundRotation(int rotation) {
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        //在这里我们可以存储图片，很显然可以采用MediaStore
        //注意保存图片后，再次调用startPreview()回到预览
        try {
            File file = new File(filename);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
            shutdownCamera();
            updateButton(true);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}