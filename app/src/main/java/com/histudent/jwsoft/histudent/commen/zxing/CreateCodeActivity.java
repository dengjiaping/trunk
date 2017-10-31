package com.histudent.jwsoft.histudent.commen.zxing;
//package application.jinwen.com.demozxing.ZXing;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.BitmapFactory;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import java.io.File;
//
//import application.jinwen.com.demozxing.R;
//import application.jinwen.com.demozxing.ZXing.view.QRCodeUtil;
//
//public myclass CreateCodeActivity extends Activity {
//
//    private EditText et;
//    private ImageView iv;
//    private Button btn;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_code);
//
//        iv= ((ImageView) findViewById(R.id.iv));
//        btn= ((Button) findViewById(R.id.btn));
//        et= ((EditText) findViewById(R.id.et_));
//
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final String filePath = getFileRoot(CreateCodeActivity.this) + File.separator
//                        + "qr_" + System.currentTimeMillis() + ".jpg";
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                            boolean success = QRCodeUtil.createQRImage(et.getText().toString().trim(), 800, 800,
//                                    BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher),
//                                    filePath);
//
//                            if (success) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        iv.setImageBitmap(BitmapFactory.decodeFile(filePath));
//                                    }
//                                });
//                            }
//
//                        }
//                }).start();
//            }
//        });
//    }
//    //文件存储根目录
//    @TargetApi(Build.VERSION_CODES.FROYO)
//    private String getFileRoot(Context context) {
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            File external = context.getExternalFilesDir(null);
//            if (external != null) {
//                return external.getAbsolutePath();
//            }
//        }
//
//        return context.getFilesDir().getAbsolutePath();
//    }
//}
