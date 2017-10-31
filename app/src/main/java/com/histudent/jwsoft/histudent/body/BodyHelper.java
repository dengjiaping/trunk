package com.histudent.jwsoft.histudent.body;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.activity.LoginActivity;
import com.histudent.jwsoft.histudent.body.message.activity.SeeFriendActivity;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.zxing.CaptureActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by liuguiyu-pc on 2017/1/4.
 */

public class BodyHelper {

    final int TYPE_MESSAGE = 0;
    final int TYPE_HIWORLD = 1;
    final int TYPE_FIND = 2;
    final int TYPE_MY = 3;
    BaseActivity activity;
    boolean isInHiworld;
    int[] array_gray, array_blue;

    public BodyHelper(BaseActivity activity, List<IconView> iamgeView_foot) {
        this.activity = activity;
        array_gray = new int[]{R.string.icon_homenone2, R.string.icon_classnone, R.string.icon_findnone, R.string.icon_minenone};
        array_blue = new int[]{R.string.icon_home2, R.string.icon_class, R.string.icon_find, R.string.icon_mine};
    }

    /**
     * 切换Fragment
     */
    protected void switchFragment(FragmentManager fragmentManager, Fragment from, Fragment to) {
        if (from == null || to == null)
            return;
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中
            fragmentManager.beginTransaction().hide(from).add(R.id.body_fragment, to).commit();
        } else {
            // 隐藏当前的fragment，显示下一个
            fragmentManager.beginTransaction().hide(from).show(to).commit();
        }
        // 让menu回去
    }

    /**
     * 更新头部和底部状态
     */
    protected void showTitleAndFoot(int type, int flag, List<IconView> iamgeView_foot, List<TextView> textView_foot) {

        switch (type) {
            case TYPE_MESSAGE:
                isInHiworld = false;
                updataFoot(type, flag, array_blue[0], iamgeView_foot, textView_foot);

                break;

            case TYPE_HIWORLD:
                isInHiworld = true;
                updataFoot(type, flag, array_blue[1], iamgeView_foot, textView_foot);

                break;

            case TYPE_FIND:
                isInHiworld = false;
                updataFoot(type, flag, array_blue[2], iamgeView_foot, textView_foot);

                break;

            case TYPE_MY:
                isInHiworld = false;
                updataFoot(type, flag, array_blue[3], iamgeView_foot, textView_foot);

                break;
        }
    }

    /**
     * 更新底部图标和文字颜色
     */
    private void updataFoot(int type, int flag, int image_blue, List<IconView> iamgeView_foot, List<TextView> textView_foot) {
        iamgeView_foot.get(type).setText(image_blue);
        iamgeView_foot.get(type).setTextColor(activity.getResources().getColor(R.color.green_color));
        textView_foot.get(type).setTextColor(activity.getResources().getColor(R.color.green_color));
        if (flag > -1 && type != flag) {
            iamgeView_foot.get(flag).setText(array_gray[flag]);
            iamgeView_foot.get(flag).setTextColor(activity.getResources().getColor(R.color.text_gray_h2));
            textView_foot.get(flag).setTextColor(activity.getResources().getColor(R.color.text_gray_h2));
        }

    }

    /**
     * 主动退出登录
     */
    protected void exit() {
        HiStudentHttpUtils.postDataByOKHttp(activity, null, HistudentUrl.logout_delet, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                HiCache.getInstance().exchangeLoginStatue(2);
                //清除本次保存的密码。
                HiCache.getInstance().clearAccountInDB();
                HiCache.getInstance().setUserDetailInfo(null);
                HiCache.getInstance().saveLoginUserInfo(null);
                //登出
                NIMClient.getService(AuthService.class).logout();
                activity.finish();
                activity.startActivity(new Intent(activity, LoginActivity.class));
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 强制退出登录
     */
    public void exitAction() {
        HiCache.getInstance().exchangeLoginStatue(2);
        HiCache.getInstance().clearAccountInDB();
        HiCache.getInstance().setUserDetailInfo(null);
        HiCache.getInstance().saveLoginUserInfo(null);
        NIMClient.getService(AuthService.class).logout();
        activity.finish();
    }

    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    protected void copy(String content) {
        ClipboardManager cmb = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 显示顶部弹窗
     *
     * @param view
     */
    protected void showTopPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(activity).inflate(
                R.layout.message_popwindow, null);
        // 设置按钮的点击事件
        LinearLayout radioButton_01 = (LinearLayout) contentView.findViewById(R.id.radio_group_01);
        LinearLayout radioButton_02 = (LinearLayout) contentView.findViewById(R.id.radio_group_02);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        radioButton_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeeFriendActivity.start(activity);
                popupWindow.dismiss();
            }
        });

        radioButton_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();

                activity.checkTakePhotoPermission(new IPermissionCallBackListener() {
                    @Override
                    public void doAction() {
                        Intent intent = new Intent(activity, CaptureActivity.class);
                        activity.startActivity(intent);
                    }
                });
            }
        });

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(
                R.drawable.transparent));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }


    /**
     * 显示发布内容弹窗
     *
     * @param layout
     * @param layout_
     */
    protected void showBottomPopupWindow(Activity activity,FrameLayout layout, FrameLayout layout_, final int requestCode, final int resultCode) {

//        final PromotedActionsLibrary promotedActionsLibrary = new PromotedActionsLibrary();
//
//        promotedActionsLibrary.setup(activity, layout, layout_);
//
//        //发表随记
//        promotedActionsLibrary.addItem(R.string.icon_topic7, R.drawable.green_button_bg_circle, "发随记", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                EssayActivity.start(activity, requestCode, resultCode);
//                promotedActionsLibrary.CloseRotaion();
//
//            }
//        });
//
//        //上传照片
//        promotedActionsLibrary.addItem(R.string.icon_img2, R.drawable.yellow_button_bg_circle, "传照片", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                UploadPhotoActivity.start(activity, ActionTypeEnum.CLASSANDOWNER,
//                        HiCache.getIntence().getLoginUserInfo().getUserId(), true, requestCode);
//                promotedActionsLibrary.CloseRotaion();
//            }
//        });
//
//        //发表日志
//        promotedActionsLibrary.addItem(R.string.icon_content, R.drawable.red_button_bg_circle, "写日志", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, LogActivity.class);
//                activity.startActivityForResult(intent, requestCode);
//                promotedActionsLibrary.CloseRotaion();
//            }
//        });
//
//        promotedActionsLibrary.addMainItem(R.mipmap.public_blue);
    }


    /**
     * 删除评论
     *
     * @param commentId
     */
    protected void deletCommentData(String commentId, final int position01, final int position02) {

        Map<String, Object> map = new TreeMap<>();
        map.put("commentId", commentId);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.deletComment_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Intent mIntent = new Intent(TheReceiverAction.DELET_ABOUTMINE_COMMENT_02);
                mIntent.putExtra("position01", position01);
                mIntent.putExtra("position02", position02);
                activity.sendBroadcast(mIntent);

            }

            @Override
            public void onFailure(String msg) {
            }
        });

    }

    /**
     * 同步分享
     *
     * @param data
     */
    protected void synShare(BaseActivity activity, Intent data) {
//        if (data == null)
//            return;
//        int flag_share = data.getIntExtra("flag_share", 0);
//        String share_content = data.getStringExtra("share_content");
//        if (flag_share == 1) {
//            ShareUtils.getShareData_(activity, share_content, ShareUtils.WEIXIN_CIRCLE);
//        } else if (flag_share == 2) {
//            ShareUtils.getShareData_(activity, share_content, ShareUtils.QQ);
//        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyword(View view) {

        if (view.getHeight() < SystemUtil.getScreenHeight_() * 2 / 3) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 实现文件的定时更新
     */
    protected void deletFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                File file = new File(FileUtil.getPathFactory(FileUtil.CacheType.IMAGE, true));
                if (file.exists()) {
                    File[] files = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        try {
                            if (FileUtil.getFileSize(files[i]) == 0) {
                                File file1 = files[i];
                                file1.delete();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();

    }

}
