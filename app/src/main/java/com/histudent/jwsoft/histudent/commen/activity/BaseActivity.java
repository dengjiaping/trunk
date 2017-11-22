package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.view.activity.BasePermissionActivity;
import com.histudent.jwsoft.histudent.model.listener.IGuideCommonClickListener;
import com.histudent.jwsoft.histudent.commen.bean.ExitBean;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.YdType;
import com.histudent.jwsoft.histudent.commen.listener.MyDialogListener;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.Dialog_getPicture;
import com.histudent.jwsoft.histudent.commen.view.LoadingDialog;
import com.histudent.jwsoft.histudent.commen.view.PointDialog;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends BasePermissionActivity {

    private LoadingDialog mLoadingDialog;
    private PointDialog mPointDialog;
    private PopupWindow mPopupWindow;
    private ImageView mIvTipYd, mIvTipKnow, mIvTitleRight, mIvTopYd, mIvTitleLeft;
    private TextView mTvTitleRight;
    private Unbinder mUnbinder;
    private Handler mHandler = new Handler();


    protected View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView=getLayoutInflater().inflate(setViewLayout(),null);
        setContentView(rootView);
        mUnbinder = ButterKnife.bind(this);
        //友盟统计
        PushAgent.getInstance(this).onAppStart();
        registerObservers(true);
        doWorkByResevier();
        init();
        initView();
        doAction();
    }

    /**
     * 解决透明状态栏下，布局无法自动拉起的问题
     * 手动设置View的高度
     */
    public void setInput() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final View rootView = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            final View decorView = getWindow().getDecorView();
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    Rect rect = new Rect();
                    decorView.getWindowVisibleDisplayFrame(rect);
                    int heightDifferent = SystemUtil.getScreenHeight_() - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
                    lp.setMargins(0, 0, 0, heightDifferent);//设置ScrollView的marginBottom的值为软键盘占有的高度即可
                    rootView.requestLayout();
                    decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public abstract int setViewLayout();

    public abstract void initView();

    public void doAction() {
    }
    protected void init(){

    }

    /**
     * 切换Fragment
     */
    public void switchFragment(FragmentManager fragmentManager, int layoutId, Fragment from, Fragment to) {
        if (from == null || to == null)
            return;
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中
            fragmentManager.beginTransaction().hide(from).add(layoutId, to).commitAllowingStateLoss();
        } else {
            // 隐藏当前的fragment，显示下一个
            fragmentManager.beginTransaction().hide(from).show(to).commitAllowingStateLoss();
        }
        // 让menu回去
    }

    /**
     * 控件监听
     *
     * @param view
     */
    public void onClick(View view) {
    }

    /**
     * 通过接受广播来实现fragment与本activity的交互
     */
    public void doWorkByResevier() {
    }

    /**
     * 显示列表选择对话框
     */
    public void showListDialog(String title, String[] items, MyDialogListener listener) {

        new Dialog_getPicture(this, title, items, listener).show();

    }

    /**
     * 显示加载时的等待层
     */
    public void showLoadingImage(final Activity activity, final LoadingType type) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case FLOWER:
                        if (mLoadingDialog == null)
                            mLoadingDialog = new LoadingDialog(activity);
                        if (!activity.isFinishing()) {
                            mLoadingDialog.show();
                            mLoadingDialog.setCanceledOnTouchOutside(false);
                        }
                        break;

                    case PENCIL:
                        if (mPointDialog == null)
                            mPointDialog = new PointDialog(activity);
                        if (!activity.isFinishing()) {
                            mPointDialog.show();
                            mPointDialog.setCanceledOnTouchOutside(false);
                        }

                }
            }
        });

    }

    /**
     * 关闭加载时的等待层
     */
    public void closeLoadingImage() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                if (mPointDialog != null && mPointDialog.isShowing()) {
                    mPointDialog.dismiss();
                }
            }
        });

    }

    /**
     * 发送消息
     */
    public void sendMessage(Handler handler, int what, int arg1, Object obj) {
        Message message = handler.obtainMessage();
        message.what = what;
        message.arg1 = arg1;
        message.obj = obj;
        handler.sendMessage(message);
    }

    /**
     * 单点登录相关
     *
     * @param register
     */
    private void registerObservers(boolean register) {
        final AuthServiceObserver authServiceObserver = NIMClient.getService(AuthServiceObserver.class);
        authServiceObserver.observeOnlineStatus(new Observer<StatusCode>(){
            @Override
            public void onEvent(StatusCode statusCode) {
                // 判断在线状态，如果为被其他端踢掉，做登出操作
                if (statusCode == StatusCode.KICKOUT) {
                    String offLineMsg = "你的账号已经在另一台手机上登录，如非本人操作，则密码可能已泄露，建议前往登录页面修改密码。";
                    EventBus.getDefault().post(new ExitBean(offLineMsg));
                }
            }
        }, register);
    }


    /**
     * 新手引导蒙板
     */
    public void showUserShadeWindow(Activity activity, View view, YdType type, final IGuideCommonClickListener onClikListener) {

        if (true) {
            if (onClikListener != null)
                onClikListener.onClick();
            return;
        }

        View popupWindow = null;
        switch (type) {
            case CREATE_GROUP:
            case CREATE_ASS:
            case CREATE_ALBUM:
            case EDIT_ALBUM:
            case PERSON_TWO:
            case EXCHANGR_COVER:
            case ADD_GOODFRIEND:
            case ALBUM_YD:
                popupWindow = LayoutInflater.from(activity).inflate(R.layout.popup_right_top_yd, null);
                break;
            case FIND_GOODFRIEND:
                popupWindow = LayoutInflater.from(activity).inflate(R.layout.popup_left_top_yd, null);
                break;
            case LOG_SHARE:
                popupWindow = LayoutInflater.from(activity).inflate(R.layout.popup_log_share_yd, null);
                break;
            case ESSAY_SHARE:
                popupWindow = LayoutInflater.from(activity).inflate(R.layout.popup_essay_share_yd, null);
                break;
            case ESSAY_QX:
                popupWindow = LayoutInflater.from(activity).inflate(R.layout.popup_essay_qx_yd, null);
                break;
            case CONTEXT_PUBLIC:
                popupWindow = LayoutInflater.from(activity).inflate(R.layout.popup_public_log_yd, null);
                break;
            case SLIDE_YD:
                popupWindow = LayoutInflater.from(activity).inflate(R.layout.popup_slide_yd, null);
                break;
        }

        mIvTopYd = popupWindow.findViewById(R.id.the_top_yd);
        mIvTipYd = popupWindow.findViewById(R.id.tip_image_yd);
        mIvTipKnow = popupWindow.findViewById(R.id.tip_image_know);
        mIvTitleRight = popupWindow.findViewById(R.id.title_right_image);
        mIvTitleLeft = popupWindow.findViewById(R.id.title_left_image);
        mTvTitleRight = popupWindow.findViewById(R.id.title_right_text);
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) mIvTitleRight.getLayoutParams();
        switch (type) {

            case CREATE_GROUP:
                mTvTitleRight.setText("创建分组");
                mIvTipYd.setImageResource(R.mipmap.create_group_yd);
                break;
            case CREATE_ASS:
                linearParams.height = SystemUtil.dp2px(18);
                linearParams.width = SystemUtil.dp2px(15);
                mIvTitleRight.setLayoutParams(linearParams);
                mTvTitleRight.setText(HiWorldCache.getUserLocationInfor().getCity());
                mIvTitleRight.setImageResource(R.mipmap.address_white);
                mIvTipYd.setImageResource(R.mipmap.create_ass_yd);
                break;
            case CREATE_ALBUM:
//                linearParams.height = SystemUtil.dp2px(20);
//                linearParams.width = SystemUtil.dp2px(20);
//                mIvTitleRight.setLayoutParams(linearParams);
                mIvTitleRight.setImageResource(R.mipmap.add_writer);
                mIvTipYd.setImageResource(R.mipmap.create_albun_yd);
                break;
            case EDIT_ALBUM:
                linearParams.height = SystemUtil.dp2px(25);
                linearParams.width = SystemUtil.dp2px(30);
                mIvTitleRight.setLayoutParams(linearParams);
                mIvTitleRight.setImageResource(R.mipmap.mune);
                mIvTipYd.setImageResource(R.mipmap.edit_album_yd);
                break;
            case FIND_GOODFRIEND:
                mIvTipYd.setImageResource(R.mipmap.find_goodfriend_yd);
                mIvTitleLeft.setImageResource(R.mipmap.msg_top);
                break;
            case LOG_SHARE:
                mIvTipYd.setImageResource(R.mipmap.log_share_yd);
                break;
            case ESSAY_QX:
                mIvTipYd.setImageResource(R.mipmap.essay_qx_yd);
                break;
            case PERSON_TWO:
                linearParams.height = SystemUtil.dp2px(25);
                linearParams.width = SystemUtil.dp2px(30);
                mIvTitleRight.setLayoutParams(linearParams);
                mIvTitleRight.setImageResource(R.mipmap.mune);
                mIvTipYd.setImageResource(R.mipmap.share_code_yd);
                break;
            case ALBUM_YD:
                linearParams.height = SystemUtil.dp2px(25);
                linearParams.width = SystemUtil.dp2px(30);
                mIvTitleRight.setLayoutParams(linearParams);
                mIvTitleRight.setImageResource(R.mipmap.mune);
                mIvTipYd.setImageResource(R.mipmap.album_yd);
                break;
            case EXCHANGR_COVER:
                linearParams.height = SystemUtil.dp2px(25);
                linearParams.width = SystemUtil.dp2px(30);
                mIvTitleRight.setLayoutParams(linearParams);
                mIvTitleRight.setImageResource(R.mipmap.mune);
                mIvTipYd.setImageResource(R.mipmap.share_code_yd);
                break;
            case ADD_GOODFRIEND:
//                linearParams.height = SystemUtil.dp2px(20);
//                linearParams.width = SystemUtil.dp2px(20);
//                mIvTitleRight.setLayoutParams(linearParams);
                mIvTitleRight.setImageResource(R.mipmap.add_writer);
                mIvTipYd.setImageResource(R.mipmap.add_friend_yd);
                break;
        }

        mPopupWindow = new PopupWindow(popupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);

        mIvTipKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
                if (onClikListener != null)
                    onClikListener.onClick();
            }
        });

        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObservers(false);
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();

        if (mUnbinder != null)
            mUnbinder.unbind();
    }
}