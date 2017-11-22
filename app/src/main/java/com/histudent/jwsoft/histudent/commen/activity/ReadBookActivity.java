package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gzsll.jsbridge.WVJBWebView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.YdType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.listener.MyDialogListener;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.photo_selecter.imageloader.SelectPhotoActivity;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.Dialog_getPicture;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by liuguiyu-pc on 2016/11/14.
 * 发布读后感界面
 */
public class ReadBookActivity extends BaseActivity {
    private ImageView title_image_right;
    private TextView title_text_left, title_text_right, title;
    private EditText edit_title;
    private LinearLayout log_content_body;
    private ArrayList<String> url_list;//存放选中图片的路径
    private File file_takePhoto;//拍照后照片存储的路径
    private List<String> file_url;//压缩前的图片保存集合
    private List<String> file_url_compress;//压缩后的图片保存集合.
    private RelativeLayout log_bottom, log_isAnonymous;
    public static List<UserClassListModel> classListModels;
    private String blockId;
    private CheckBox cb_isAnonymous;
    private boolean isGroup;
    static WVJBWebView.WVJBResponseCallback callback;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showUserShadeWindow(ReadBookActivity.this, log_content_body, YdType.LOG_SHARE, null);
        }
    };

    public static void start(Activity activity, WVJBWebView.WVJBResponseCallback callback) {
        ReadBookActivity.callback = callback;
        Intent intent = new Intent(activity, ReadBookActivity.class);
        activity.startActivityForResult(intent, 500);

    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_readbook;
    }

    @Override
    public void initView() {
        blockId = getIntent().getStringExtra("blockId");
        isGroup = getIntent().getBooleanExtra("isGroup", false);
        url_list = new ArrayList<>();
        file_url = new ArrayList<>();
        file_url_compress = new ArrayList<>();
        classListModels = new ArrayList<>();
        title_image_right = (ImageView) findViewById(R.id.title_right_image);
        title_text_left = (TextView) findViewById(R.id.title_left_text);
        title_text_right = (TextView) findViewById(R.id.title_right_text);
        title = (TextView) findViewById(R.id.title_middle_text);
        edit_title = (EditText) findViewById(R.id.log_title_edit);
        log_content_body = (LinearLayout) findViewById(R.id.log_content_body);
        log_bottom = (RelativeLayout) findViewById(R.id.log_bottom_);

        title_image_right.setVisibility(View.VISIBLE);
        title_image_right.setImageResource(R.mipmap.ok_web);
        title_text_left.setText("返回");
        title.setText("发布读后感");
        title_text_right.setText("发布");

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.title_left:

                ReminderHelper.getIntentce().showDialog(ReadBookActivity.this, "提示", "是否确定取消？", "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {
                        setResult(0);
                        finish();
                        callback.callback("2");
                    }
                }, "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                });

                break;
            case R.id.title_right:
                compressImage(file_url);
                break;
//            case log_class_select:

//                SelecteClassActivity.start(ReadBookActivity.this, 100);
//                break;

            case R.id.button_take:

                checkTakePhotoPermission(new IPermissionCallBackListener() {
                    @Override
                    public void doAction() {
                        initPopWindow();
                    }
                });

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {

        } else if (requestCode == 2 && resultCode == -1) {

            //判断上一个childView,如果它是空edit则删除它
            int childViewNums = log_content_body.getChildCount();
            View view = log_content_body.getChildAt(childViewNums - 1);
            if (view instanceof EditText) {
                if (TextUtils.isEmpty(((EditText) view).getText().toString())) {
                    log_content_body.removeViewAt(childViewNums - 1);
                }
            }

            //添加图片
            String path = file_takePhoto.getPath();
            addPicture(path);

            //添加编辑控件并置与其焦点
            EditText editText = new EditText(this);
            editText.setMinHeight(0);
            editText.setBackgroundResource(R.color.transparent);
            log_content_body.addView(editText);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();

        } else if (requestCode == 1001 && resultCode == 200) {

            int childViewNums = log_content_body.getChildCount();
            View view = log_content_body.getChildAt(childViewNums - 1);
            if (view instanceof EditText) {
                if (TextUtils.isEmpty(((EditText) view).getText().toString())) {
                    log_content_body.removeViewAt(childViewNums - 1);
                }
            }

            List<String> urls = data.getStringArrayListExtra("return");
            for (int i = 0; i < urls.size(); i++) {
                String path = urls.get(i);
                addPicture(path);
            }

            EditText editText = new EditText(this);
            editText.setBackgroundResource(R.color.transparent);
            log_content_body.addView(editText);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        }
    }

    /**
     * 动态添加图片
     */
    private void addPicture(final String path) {
        final RelativeLayout layout = (RelativeLayout) LayoutInflater.from(ReadBookActivity.this).inflate(R.layout.log_edit_image_view, null);
        ImageView imageView = (ImageView) layout.findViewById(R.id.log_edit_picture);
        ImageView imageView_delete = (ImageView) layout.findViewById(R.id.log_edit_delet);
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams(); //取控件textView当前的布局参数

        //根据图片尺寸重设imageView大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int h = options.outHeight;
        int w = options.outWidth;
        linearParams.width = SystemUtil.getScreenWind() - SystemUtil.dp2px(30);
        linearParams.height = linearParams.width * h / w;
        imageView.setLayoutParams(linearParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        MyImageLoader.getIntent().displayLocalImage(this, new File(path), imageView);

        log_content_body.addView(layout);
        file_url.add(path);

        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log_content_body.removeView(layout);
                file_url.remove(path);
            }
        });
    }

    /**
     * 发布读后感
     */
    private void createBlog(final List<String> paths, String title, final String content) {

        AddressInforBean inforBean = HiWorldCache.getUserLocationInfor();
        Map<String, Object> map = new TreeMap<>();
        map.put("title", title);
        if (inforBean == null) {
            map.put("longitude", 0);
            map.put("latitude", 0);
            map.put("location", "");
        } else {
            map.put("longitude", inforBean.getLongitude());
            map.put("latitude", inforBean.getLatitude());
            map.put("location", inforBean.getCity() + inforBean.getAreaStr());
        }
        map.put("content", content.toString());

        HiStudentHttpUtils.postImageByOKHttp(this, paths, map, HistudentUrl.createbookreview, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(ReadBookActivity.this, "读后感发布成功", Toast.LENGTH_SHORT).show();

                deletCompressFile(paths);
                setResult(200);
                finish();
                callback.callback("1");

            }

            @Override
            public void onFailure(String errorMsg) {
                deletCompressFile(paths);
            }
        });

    }

    //发表帖子
    private void MakePost(final List<String> paths, String title, StringBuffer content) {

        Map<String, Object> map = new TreeMap<>();
        map.put("blockId", blockId);
        map.put("topicTitle", title);
        map.put("topicBody", content.toString());
        if (isGroup) {
            map.put("isAnonymous", false + "");
        } else {
            map.put("isAnonymous", cb_isAnonymous.isChecked() + "");
        }
        map.put("topicId", "");
        map.put("isGroup", "" + isGroup);

        HiStudentHttpUtils.postImageByOKHttp(this, paths, map, HistudentUrl.hibai_make_post_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(ReadBookActivity.this, "帖子发布成功！", Toast.LENGTH_SHORT).show();

                deletCompressFile(paths);

                setResult(200);
                finish();

            }

            @Override
            public void onFailure(String errorMsg) {
                deletCompressFile(paths);
            }
        });


    }

    /**
     * 删除压缩后的图片
     *
     * @param paths
     */
    private void deletCompressFile(List<String> paths) {
        for (String path : paths) {
            File file = new File(path);
            file.delete();
        }
    }

    /**
     * 照片选择对话菜单
     */
    private void initPopWindow() {

        Dialog_getPicture dialog_head = new Dialog_getPicture(this, "获取照片", getResources().getStringArray(R.array.getPicture), new MyDialogListener() {
            @Override
            public void callBack(final int index) {

                switch (index) {
                    case 0:
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        file_takePhoto = new File(FileUtil.getPathFactory(FileUtil.CacheType.IMAGE, false));
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file_takePhoto));
                        startActivityForResult(intent, 2);
                        break;

                    case 1:
                        SelectPhotoActivity.start(ReadBookActivity.this, url_list, 0,null);
                        break;
                }
            }
        });
        dialog_head.show();
    }


//    /**
//     * 键盘弹出来的时候向上偏移高度
//     */
//    public void setInput() {
//
//        final View rootView = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
//        final View decorView = getWindow().getDecorView();
//
//        //该Activity的最外层Layout
//        final View activityRootView = findViewById(R.id.activityRoot);
//
//        //给该layout设置监听，监听其布局发生变化事件
//        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect rect = new Rect();
//                decorView.getWindowVisibleDisplayFrame(rect);
//                int heightDifferent = SystemUtil.getScreenHeight_() - rect.bottom;
//                if (heightDifferent > 200) {
//                    if (!edit_title.hasFocus()) {
//                        log_bottom.setVisibility(View.VISIBLE);
//                        addpicture_layout.setVisibility(View.VISIBLE);
//                        if (StringUtil.isEmpty(blockId)) {
//                            log_class_select.setVisibility(View.GONE);
//                        } else {
//                            log_isAnonymous.setVisibility(View.GONE);
//                        }
//
//                    } else {
//                        log_bottom.setVisibility(View.GONE);
//                    }
//                } else {
//                    log_bottom.setVisibility(View.VISIBLE);
//                    addpicture_layout.setVisibility(View.GONE);
//                    if (StringUtil.isEmpty(blockId)) {
//                        log_class_select.setVisibility(View.VISIBLE);
//                    } else {
//                        if (isGroup) {
//                            log_isAnonymous.setVisibility(View.GONE);
//                        } else {
//                            log_isAnonymous.setVisibility(View.VISIBLE);
//                        }
//
//                    }
//
//                }
//
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
//                lp.setMargins(0, 0, 0, heightDifferent);
//                rootView.requestLayout();
//            }
//        });
//    }

    /**
     * 压缩图片
     *
     * @param path
     */
    private void compressImage(final List<String> path) {
        final String title = edit_title.getText().toString();
        final StringBuffer content = new StringBuffer();

        for (int i = 0, j = 0; i < log_content_body.getChildCount(); i++) {
            View view = log_content_body.getChildAt(i);
            if (view instanceof EditText) {
                String info = ((EditText) view).getText().toString().replace("{", "&#123;").replace("}", "&#125;");
                content.append(info);
            } else {
                content.append("{" + j + "}");
                j++;
            }
        }

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, StringUtil.isEmpty(blockId) == true ?
                    "请先输入读后感标题！" : "请输入帖子标题！", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, StringUtil.isEmpty(blockId) == true ?
                    "请先输入读后感内容！" : "请输入帖子内容！", Toast.LENGTH_SHORT).show();
            return;
        } else {
            showLoadingImage(this, LoadingType.FLOWER);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    file_url_compress.addAll(ImageUtils.comPressBitmaps(ReadBookActivity.this, path, 80));
                    if (StringUtil.isEmpty(blockId)) {
                        createBlog(file_url_compress, title, content.toString());
                    } else {
                        MakePost(file_url_compress, title, content);
                    }

                }
            }).start();
        }
    }

    /**
     * 获取班级列表
     */
    private void getClassList() {
        Map<String, Object> map_class = new TreeMap<>();
        map_class.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        HiStudentHttpUtils.postDataByOKHttp(this, map_class, HistudentUrl.myClassList, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                classListModels.addAll(DataParser.getUserClassList(result));

                handler.sendEmptyMessage(1);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private String getClassIds(List<UserClassListModel> classListModels) {
        StringBuffer buffer = new StringBuffer();
        if (classListModels == null || classListModels.size() == 0) {
            return "";
        } else {
            for (int i = 0; i < classListModels.size(); i++) {
                UserClassListModel model = classListModels.get(i);
                if (model.isSelect()) {
                    buffer.append(model.getClassId() + ",");
                }
            }
        }
        String info = buffer.toString();
        return info.substring(0, info.length() - 1);
    }

}
