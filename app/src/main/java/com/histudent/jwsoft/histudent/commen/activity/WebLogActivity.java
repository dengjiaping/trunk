package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gzsll.jsbridge.WVJBWebView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.activity.DraftActivity;
import com.histudent.jwsoft.histudent.body.hiworld.activity.LogNextActivity;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.bean.WebBlogBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.YdType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.keyword.emotionkeyboardview.EmotionKeyboard;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ListenerUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.LogParameterModel;
import com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.histudent.jwsoft.histudent.R.id.iv_pic;


/**
 * Created by liuguiyu-pc on 2016/11/14.
 * 发布日志界面(发布读后感)
 */
public class WebLogActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private IconView title_image_left;
    private TextView title_text_right;
    private EditText edit_title, et_content;
    private LinearLayout log_content_body;
    private ArrayList<String> file_url, all_path;//压缩前的图片保存集合
    private List<String> file_url_compress;//压缩后的图片保存集合.
    private RelativeLayout log_bottom;
    public ArrayList<UserClassListModel> classListModels;
    private CheckBox cb_isAnonymous;
    private PictureTailorHelper clippHelper;
    private LinearLayout addpicture_layout;
    private Handler handler;
    private ArrayList<SimpleUserModel> atList;//at的用户集合
    private int cursorPosition;
    private String logId;//日志id
    private LogParameterModel parameterModel;//用于保存发送日志所需参数
    private AddressInforBean addressModel;
    private List<SimpleUserModel> allAtList;
    private EmotionMainFragment emotionMainFragment;
    private LinearLayout layout;
    private static WVJBWebView.WVJBResponseCallback callback;
    private WebBlogBean blogBean;
    private IconView tv_emtion, tv_at;

    public static void start(Activity activity, WVJBWebView.WVJBResponseCallback callback, WebBlogBean blogBean) {
        Intent intent = new Intent(activity, WebLogActivity.class);
        WebLogActivity.callback = callback;
        intent.putExtra("blogBean", blogBean);
        activity.startActivityForResult(intent, 500);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_log;
    }

    @Override
    public void initView() {

        blogBean = (WebBlogBean) getIntent().getSerializableExtra("blogBean");

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                showUserShadeWindow(WebLogActivity.this, log_content_body, YdType.LOG_SHARE, null);
            }
        };

        setInput();

        clippHelper = PictureTailorHelper.getInstance();
        file_url = new ArrayList<>();
        all_path = new ArrayList<>();
        atList = new ArrayList<>();
        allAtList = new ArrayList<>();
        file_url_compress = new ArrayList<>();
        classListModels = new ArrayList<>();
        title_image_left = (IconView) findViewById(R.id.title_left_image);
        layout = (LinearLayout) findViewById(R.id.layout);
        title_text_right = (TextView) findViewById(R.id.title_right_text);
        edit_title = (EditText) findViewById(R.id.log_title_edit);
        log_content_body = (LinearLayout) findViewById(R.id.log_content_body);
        addpicture_layout = (LinearLayout) findViewById(R.id.log_picture_select);
        log_bottom = (RelativeLayout) findViewById(R.id.log_bottom_);
        et_content = (EditText) findViewById(R.id.log_content_edit);

        tv_emtion = (IconView) findViewById(R.id.tv_emtion);
        tv_at = (IconView) findViewById(R.id.tv_at);

        if (blogBean != null) {
            ((TextView) findViewById(R.id.title_middle_text)).setText(("add".equals(blogBean.getAction()) ? "发布" : "修改") + blogBean.getName());
            edit_title.setHint(blogBean.getName() + "标题");
            et_content.setHint(blogBean.getName() + "内容");
        }

        log_bottom.setVisibility(View.VISIBLE);
        tv_emtion.setVisibility(View.GONE);
        tv_at.setVisibility(View.GONE);

        title_image_left.setVisibility(View.VISIBLE);
        title_image_left.setText(R.string.icon_close);
        title_image_left.setTextSize(15);
        findViewById(R.id.title_right).setEnabled(false);
        title_text_right.setText((blogBean.isHideUIPrivacy() && blogBean.isHideUISyncClass()) ? "发布" : "下一步");
        title_text_right.setTextColor(getResources().getColor(R.color.text_gray_h2));
        parameterModel = new LogParameterModel();
        addpicture_layout.setVisibility(View.VISIBLE);
        getAddressInfor();


        initEmotionMainFragment();
        ListenerUtils.setOnDeleteUserListener(et_content, allAtList);

        addFocusChangeListener();
        edit_title.addTextChangedListener(this);
        et_content.addTextChangedListener(this);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int X = (int) ev.getX();
        int Y = (int) ev.getY();

        if (isInChangeImageZone(log_content_body, X, Y)) {
            for (int i = 0; i < log_content_body.getChildCount(); i++) {
                View view1 = log_content_body.getChildAt(i);
                if (view1 instanceof EditText && isInChangeImageZone(view1, X, Y))
                    emotionMainFragment.bindToEditText((EditText) view1);
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    //屏蔽标题弹出表情输入框
    private void addFocusChangeListener() {

        edit_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    log_bottom.setVisibility(View.GONE);
                } else {
                    log_bottom.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    //获取地址信息
    private void getAddressInfor() {
        addressModel = HiWorldCache.getUserLocationInfor();
        if (addressModel == null) {
            addressModel = new AddressInforBean();
        }
        parameterModel.setLatitude(addressModel.getLatitude());
        parameterModel.setLongitude(addressModel.getLongitude());
        parameterModel.setLocation(addressModel.getCity() + addressModel.getAreaStr());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            //返回
            case R.id.title_left:

                showBackNotice();
                break;

            //下一步，发帖子
            case R.id.title_right:
                Next();
                break;


            //添加图片
            case iv_pic:

                checkTakePhotoPermission(new IPermissionCallBackListener() {
                    @Override
                    public void doAction() {
                        clippHelper.selectPictures(WebLogActivity.this, all_path, 9, null);
                    }
                });
                break;

            //@
            case R.id.tv_at:

                cursorPosition = et_content.getSelectionStart();

                //不传递已经选择的用户id
                SelecteClassmatesActiviy.start(this, new ArrayList<SimpleUserModel>(), 222, SeletClassMateEnum.AT);

                break;


            //草稿箱
            case R.id.draft:
                DraftActivity.start(this, 777);
                break;

            case R.id.log_picture_select:
//                addpicture_layout.setVisibility(View.GONE);
//                emotionMainFragment.isInterceptBackPress();
                break;
        }
    }

    //日志下一步
    private void Next() {

        if (StringUtil.isEmpty(getTitle01())) {
            Toast.makeText(this, "请输入日志标题！", Toast.LENGTH_LONG).show();
        } else {
            if (StringUtil.isEmpty(getContent())) {
                Toast.makeText(this, "请输入日志内容", Toast.LENGTH_LONG).show();
            } else {

                parameterModel.setContent(getContent());
                parameterModel.setTitle(getTitle01());
                parameterModel.setFileList(all_path);
                parameterModel.setAtUserList(DataUtils.deleteUser(getContent(), allAtList));

                if (blogBean.isHideUIPrivacy() && blogBean.isHideUISyncClass()) {
                    createBlog(parameterModel);
                } else {
                    LogNextActivity.start(this, parameterModel, blogBean, callback, 661);
                }

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO && resultCode == -1) {
            //判断上一个childView,如果它是空edit则删除它
            int childViewNums = log_content_body.getChildCount();
            View view = log_content_body.getChildAt(childViewNums - 1);
            if (view instanceof EditText) {
                if (TextUtils.isEmpty(((EditText) view).getText().toString())) {
                    log_content_body.removeViewAt(childViewNums - 1);
                }
            }

            //添加图片
            addPicture(clippHelper.photo_path.getAbsolutePath());

            //添加编辑控件并置与其焦点
            et_content = new EditText(this);
            et_content.setMinHeight(0);
            et_content.setBackgroundResource(R.color.transparent);
            et_content.setTextColor(getResources().getColor(R.color.text_gray_h2));
            et_content.setTextSize(15);
            log_content_body.addView(et_content);

            et_content.setFocusable(true);
            et_content.setFocusableInTouchMode(true);
            et_content.requestFocus();
            ListenerUtils.setOnDeleteUserListener(et_content, allAtList);

            emotionMainFragment.bindToEditText(et_content);

        } else if (requestCode == PictureTailorHelper.PHOTO_REQUEST_GALLERYS && resultCode == 200) {

            int childViewNums = log_content_body.getChildCount();
            View view = log_content_body.getChildAt(childViewNums - 1);
            if (view instanceof EditText) {
                if (TextUtils.isEmpty(((EditText) view).getText().toString())) {
                    log_content_body.removeViewAt(childViewNums - 1);
                }
            }

            List<String> urls = data.getStringArrayListExtra("return");
            if (urls != null) {
                urls.removeAll(all_path);
            }
            for (int i = 0; i < urls.size(); i++) {
                String path = urls.get(i);
                addPicture(path);
            }

            EditText editText = new EditText(this);
            editText.setBackgroundResource(R.color.transparent);
            log_content_body.addView(editText);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.setTextColor(getResources().getColor(R.color.text_gray_h2));
            editText.setTextSize(15);
            editText.requestFocus();
            et_content = editText;
            ListenerUtils.setOnDeleteUserListener(et_content, allAtList);
            emotionMainFragment.bindToEditText(editText);
        } else if (resultCode == 200 && requestCode == 100) {
            if (data != null && data.getSerializableExtra("classList") != null) {
                classListModels.clear();
                classListModels.addAll(((ArrayList<UserClassListModel>) data.getSerializableExtra("classList")));

                Log.e("Size", classListModels.size() + "");
            }

            //at返回
        } else if (resultCode == 200 && requestCode == 222) {
            if (data != null) {
                atList.clear();
                atList.addAll((ArrayList<SimpleUserModel>) data.getSerializableExtra("userModel"));
                allAtList.addAll(atList);
                setText();

                Log.e("atList", atList.toArray().toString());
            }
            //日志设置同步班级和权限后返回
        } else if (resultCode == 200 && requestCode == 666) {

            if (data != null) {
                int privacyStatus = data.getIntExtra("privacyStatus", -1);
                if (privacyStatus != -1) {
                    parameterModel.setPrivacyStatus(privacyStatus);

                    //根据动态权限隐藏或者显示@功能
                    if (privacyStatus == 2) {
                        findViewById(R.id.tv_at).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.tv_at).setVisibility(View.VISIBLE);
                    }
                }

                //设置同步班级
                if (data.getSerializableExtra("classIds") != null) {
                    classListModels.clear();
                    classListModels.addAll(((ArrayList) data.getSerializableExtra("classIds")));
                    parameterModel.setClassIds(((ArrayList) data.getSerializableExtra("classIds")));
                }
            }
            //选择草稿返回
        } else if (resultCode == 400 && requestCode == 666) {//日志发布成功,界面销毁
            setResult(0);
            finish();
        } else if (resultCode == 101 && requestCode == 666) {
            finish();
        }
    }

    private void setText() {
        DataUtils.setAtText(et_content, cursorPosition, DataUtils.getAtUserNameStr(atList));
    }

    /**
     * 动态添加图片
     */
    private void addPicture(final String path) {
        final RelativeLayout layout = (RelativeLayout) LayoutInflater.from(WebLogActivity.this).inflate(R.layout.log_edit_image_view, null);
        ImageView imageView = (ImageView) layout.findViewById(R.id.log_edit_picture);
        IconView imageView_delete = (IconView) layout.findViewById(R.id.log_edit_delet);
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
        all_path.add(path);

        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log_content_body.removeView(layout);
                all_path.remove(path);
            }
        });
    }

    //获取标题
    private String getTitle01() {
        final String title = edit_title.getText().toString();
        return title;
    }

    //获取内容
    private String getContent() {

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

        return content.toString();
    }

    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment() {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, false);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, true);
        //替换fragment
        //创建修改实例
        emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
        emotionMainFragment.bindToContentView(layout);
        emotionMainFragment.bindToEditText(et_content);
        emotionMainFragment.setExchangeButton(tv_emtion);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
        //ransaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    public void setInput() {
//        final View rootView = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        final View decorView = getWindow().getDecorView();
        //该Activity的最外层Layout
        final View activityRootView = findViewById(R.id.activityRoot);
        //给该layout设置监听，监听其布局发生变化事件
        activityRootView.getViewTreeObserver().
                addOnGlobalLayoutListener(
                        new ViewTreeObserver
                                .OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                Rect rect = new Rect();
                                decorView.getWindowVisibleDisplayFrame(rect);
                                int heightDifferent = SystemUtil.getScreenHeight_() - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                                EventBus.getDefault().post(new EmotionKeyboard.KeyWord(heightDifferent));
                            }
                        }
                );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showKeyWordItem(EmotionKeyboard.KeyWord word) {

        int height = word.height;

        if (edit_title.hasFocus()) {

            if (EmotionKeyboard.isShow)
                emotionMainFragment.hitEmotion();

            if (log_bottom.getVisibility() == View.VISIBLE)
                log_bottom.setVisibility(View.GONE);

            return;
        } else {
//            emotionMainFragment.setEmotionIsVisiable(true);
        }

        if (EmotionKeyboard.onClikeExchangeButton) {
            EmotionKeyboard.onClikeExchangeButton = false;
            return;
        }

        if (EmotionKeyboard.isShow) return;

        if (height > 100) {
//            log_bottom.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) log_bottom.getLayoutParams();
            lp.setMargins(0, 0, 0, height);
            log_bottom.setLayoutParams(lp);
        } else {
//            log_bottom.setVisibility(View.GONE);
        }
    }

    /**
     * 判断是否拦截返回键操作
     */
    @Override
    public void onBackPressed() {
        boolean flag = emotionMainFragment.isInterceptBackPress();
        if (!flag) {
            super.onBackPressed();
        }
    }

    private void changTextColor() {

        ViewUtils.changeTitleRightClickable(this, !StringUtil.isEmpty(getContent()) && !StringUtil.isEmpty(edit_title.getText().toString()) ? true : false);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            showBackNotice();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showBackNotice() {

        if (!StringUtil.isEmpty(getContent()) || !StringUtil.isEmpty(edit_title.getText().toString())) {
            ReminderHelper.getIntentce().showDialog(WebLogActivity.this, "提示", "日志还没发布，确定不发布吗？", "确定", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {
                    finish();
                }
            }, "取消", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {

                }
            });
        } else {
            finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        changTextColor();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private Rect mChangeImageBackgroundRect = null;

    private boolean isInChangeImageZone(View view, int x, int y) {
        if (null == mChangeImageBackgroundRect) {
            mChangeImageBackgroundRect = new Rect();
        }
        view.getDrawingRect(mChangeImageBackgroundRect);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mChangeImageBackgroundRect.left = location[0];
        mChangeImageBackgroundRect.top = location[1];
        mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right + location[0];
        mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom + location[1];
        return mChangeImageBackgroundRect.contains(x, y);
    }

    /**
     * 发布日志
     */
    private void createBlog(LogParameterModel parameterModel) {

        showLoadingImage(this, LoadingType.FLOWER);
        file_url_compress.addAll(ImageUtils.comPressBitmaps(WebLogActivity.this, all_path, 80));

        Map<String, Object> map = new TreeMap<>();

        map.put("title", getTitle01());
        map.put("content", getContent());

        AddressInforBean inforBean = HiWorldCache.getUserLocationInfor();
        map.put("longitude", inforBean.getLongitude());
        map.put("latitude", inforBean.getLatitude());
        map.put("location", inforBean.getCity() + inforBean.getAreaStr());

        map.put("privacyStatus", 0);

        map.put("ownerId", HiCache.getInstance().getLoginUserInfo().getUserId());
        map.put("ownerType", "1");
        map.put("appExtType", blogBean.getAppType());

        if (parameterModel.getAtUserList() != null && parameterModel.getAtUserList().size() > 0) {
            map.put("atUser", DataUtils.getAtUserString(parameterModel.getAtUserList()));
        } else {
            map.put("atUser", "");
        }

        map.put("isDraft", false + "");

        HiStudentHttpUtils.postImageByOKHttp(WebLogActivity.this, file_url_compress, map, HistudentUrl.createCommonBlog, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                MyShareBean shareBean = JSON.parseObject(result, MyShareBean.class);
                ComShareAcitivity.start(WebLogActivity.this, shareBean, callback);
                finish();

            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });

    }

}
