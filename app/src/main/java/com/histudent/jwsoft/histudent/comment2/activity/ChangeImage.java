package com.histudent.jwsoft.histudent.comment2.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;

import java.io.File;

public class ChangeImage extends BaseActivity implements View.OnClickListener {

    private ImageView iv = null;
    private String tp = null;
    private Intent intent;
    private String imagName;
    private PopupWindow popupWindow;
    private Button btn_cammer, btn_file, btn_cancel;

    @Override
    public int setViewLayout() {
        return R.layout.activity_change_image;
    }

    /**
     * 初始化方法实现
     */
    @Override
    public void initView() {
       // initPopWindow();
        iv = (ImageView) findViewById(R.id.imageView1);
        iv.setOnClickListener(this);

    }

    /**
     * 控件点击事件实现
     * <p/>
     * 因为有朋友问不同控件的背景图裁剪怎么实现，
     * 我就在这个地方用了三个控件，只为了自己记录学习
     * 大家觉得没用的可以跳过啦
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView1:
                initPopWindow();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                startPhotoZoom(data.getData());
                break;
            // 如果是调用相机拍照时
            case 2:
                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/xiaoma.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            // 取得裁剪后的图片
            case 3:
                /**
                 * 非空判断大家一定要验证，如果不验证的话，
                 * 在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 * 当前功能时，会报NullException，小马只
                 * 在这个地方加下，大家可以根据不同情况在合适的
                 * 地方做判断处理类似情况
                 *
                 */
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        /*
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
         * yourself_sdk_path/docs/reference/android/content/Intent.html
         * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
         * 是直接调本地库的，小马不懂C C++  这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么
         * 制做的了...吼吼
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);

            /**
             * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上
             * 传到服务器，QQ头像上传采用的方法跟这个类似
             */

            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] b = stream.toByteArray();
            // 将图片流以字符串形式存储下来

            tp = new String(Base64Coder.encodeLines(b));
            这个地方大家可以写下给服务器上传图片的实现，直接把tp直接上传就可以了，
            服务器处理的方法是服务器那边的事了，吼吼

            如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换
            为我们可以用的图片类型就OK啦...吼吼
            Bitmap dBitmap = BitmapFactory.decodeFile(tp);
            Drawable drawable = new BitmapDrawable(dBitmap);
            */
            //     ib.setBackgroundDrawable(drawable);
            iv.setBackgroundDrawable(drawable);
        }
    }

    //更多的PopWindow
    private void initPopWindow() {

        View popView = View.inflate(this, R.layout.buttom_pop_window, null);
        btn_cammer = ((Button) popView.findViewById(R.id.activity_details_pop_window_btn_share));
        btn_file = ((Button) popView.findViewById(R.id.activity_details_pop_window_btn_report));
        btn_cancel = ((Button) popView.findViewById(R.id.activity_details_pop_window_btn_cancel));
        btn_cammer.setText("拍照");
        btn_file.setText("从相册中选择");
        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);

        //点击取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //启动系统相机
        btn_cammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
                 * 文档，you_sdk_path/docs/guide/topics/media/camera.html
                 * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
                 * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
                 */
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                //下面这句指定调用相机拍照后的照片存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                        .fromFile(new File(Environment
                                .getExternalStorageDirectory(),
                                "xiaoma.jpg")));
                startActivityForResult(intent, 2);
                popupWindow.dismiss();

            }
        });

        //点击从相册中选择照片
        btn_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
                 * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
                 */
                Intent intent = new Intent(Intent.ACTION_PICK, null);

                /**
                 * 下面这句话，与其它方式写是一样的效果，如果：
                 * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 * intent.setType(""image/*");设置数据类型
                 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                 * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
                 */
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 1);
                popupWindow.dismiss();

            }
        });
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
    }

}

