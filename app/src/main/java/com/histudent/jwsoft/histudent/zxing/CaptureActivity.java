
package com.histudent.jwsoft.histudent.zxing;

import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.clock.ManualInputBarCodeActivity;
import com.histudent.jwsoft.histudent.activity.clock.ManualInputBookNameActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.zxing.camera.CameraManager;
import com.histudent.jwsoft.histudent.zxing.decode.DecodeThread;
import com.histudent.jwsoft.histudent.zxing.utils.BeepManager;
import com.histudent.jwsoft.histudent.zxing.utils.CaptureActivityHandler;
import com.histudent.jwsoft.histudent.zxing.utils.InactivityTimer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lizhaotailang on 2017/2/13.
 */

public class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;

    private Rect mCropRect = null;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private boolean isHasSurface = false;

    private int isLocalStartScan = -1;

    @BindView(R.id.thetopview)
    RelativeLayout mRLTopBackground;
    @BindView(R.id.title_right_text)
    TextView mTvTopRightText;
    @BindView(R.id.title_middle_text)
    TextView mTvTopMiddleText;
    @BindView(R.id.title_left_image)
    IconView mBackImage;
    @BindView(R.id.view_bottom_line)
    View mViewTopBottomLine;

    @BindView(R.id.title_right_image)
    IconView mTitleRightImage;

    @BindView(R.id.tv_scan_not_bar_code)
    AppCompatTextView mTvScanNotBarCode;

    @OnClick(R.id.title_left)
    void finishPage() {
        this.finish();
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_scan;
    }

    @Override
    public void initView() {
        final Intent intent = getIntent();
        isLocalStartScan = intent.getIntExtra(TransferKeys.LOCAL_START_SCAN, -1);
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        mRLTopBackground.setBackgroundColor(ContextCompat.getColor(this, R.color._201D1A));
        mTvTopMiddleText.setText(R.string.scan_image_shape_code);
        mTvTopRightText.setText(R.string.manual_add_isbn);
        mTitleRightImage.setTextSize(14);
        mTitleRightImage.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTitleRightImage.setText(R.string.icon_add);
        mTvTopMiddleText.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        mTvTopRightText.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        mBackImage.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        mViewTopBottomLine.setVisibility(View.GONE);

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);

        loadListener();

    }

    private void loadListener() {
        mTvTopRightText.setOnClickListener((View view) -> {
            //跳转手动添加书籍页面
            final Intent intent = new Intent(CaptureActivity.this, ManualInputBookNameActivity.class);
            CommonAdvanceUtils.startActivity(CaptureActivity.this, intent);
        });


        mTvScanNotBarCode.setOnClickListener((View view) -> {
            //无法扫描-跳转至手动输入ISBN码页面
            final Intent intent = new Intent(CaptureActivity.this, ManualInputBarCodeActivity.class);
            intent.putExtra(TransferKeys.LOCAL_START_SCAN, isLocalStartScan);
            CommonAdvanceUtils.startActivity(CaptureActivity.this, intent);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }


    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        cameraManager.getCamera().autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {
                if (b) {
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1连续对焦
                    camera.setParameters(parameters);
                    camera.startPreview();
                    camera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
                }
            }
        });
    }


    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        requestScanBookInformation(rawResult, bundle);
    }

    private void requestScanBookInformation(Result rawResult, Bundle bundle) {
        final String isBn = rawResult.getText();
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ParamKeys.ISBN, isBn);
        HiStudentHttpUtils.postDataByOKHttp(this, hashMap, HistudentUrl.GET_SCAN_BOOK_INFORMATION, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Intent resultIntent = new Intent(CaptureActivity.this, CaptureConfirmActivity.class);
                bundle.putInt("width", mCropRect.width());
                bundle.putInt("height", mCropRect.height());
                bundle.putString(ParamKeys.ISBN, rawResult.getText());
                bundle.putString(TransferKeys.RESULT, result);
                bundle.putInt(TransferKeys.LOCAL_START_SCAN, isLocalStartScan);
                resultIntent.putExtras(bundle);
                Log.i(TAG, "handleDecode: result:--->" + rawResult.getText());
                CommonAdvanceUtils.startActivity(CaptureActivity.this, resultIntent);
                CaptureActivity.this.finish();
            }

            @Override
            public void onFailure(String errorMsg) {
                //扫描失败后未查询到书籍信息 弹窗提示让去添加
                showHintDialog();
            }
        });
    }

    private void showHintDialog() {
        ReminderHelper.getIntentce().showDialog(this, "", getString(R.string.not_query_book_name), getString(R.string.cancel), () -> {
        }, getString(R.string.go_add), () -> {
            final Intent intent = new Intent(CaptureActivity.this, ManualInputBookNameActivity.class);
            CommonAdvanceUtils.startActivity(CaptureActivity.this, intent);
        });
    }


    /**
     * Init the camera.
     *
     * @param surfaceHolder The surface holder.
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            Toast.makeText(CaptureActivity.this, "无法打开相机，请检查后重试", Toast.LENGTH_SHORT).show();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            Toast.makeText(CaptureActivity.this, "无法打开相机，请检查后重试", Toast.LENGTH_SHORT).show();
        }
    }


    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * Init the interception rectangle area
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        // Obtain the location information of the scanning frame in layout
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        // Obtain the height and width of layout container.
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        // Compute the coordinate of the top-left vertex x
        // of the final interception rectangle.
        int x = cropLeft * cameraWidth / containerWidth;
        // Compute the coordinate of the top-left vertex y
        // of the final interception rectangle.
        int y = cropTop * cameraHeight / containerHeight;

        // Compute the width of the final interception rectangle.
        int width = cropWidth * cameraWidth / containerWidth;
        // Compute the height of the final interception rectangle.
        int height = cropHeight * cameraHeight / containerHeight;

        // Generate the final interception rectangle.
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
