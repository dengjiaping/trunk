package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/17.
 * "我的"-"设置"-"账号绑定"
 */

public class MyAccountBind extends BaseActivity {

    private TextView title;
    private ImageView checkBox_weixin, checkBox_QQ;
    private CurrentUserDetailInfoModel model;
    private final int QQ = 1;
    private final int WEIXIN = 2;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, MyAccountBind.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_bind_myaccount;
    }

    @Override
    public void initView() {
        model = HiCache.getInstance().getUserDetailInfo();
        checkBox_weixin = (ImageView) findViewById(R.id.checkBox_weixin);
        checkBox_QQ = (ImageView) findViewById(R.id.checkBox_QQ);
        title = (TextView) findViewById(R.id.title_middle_text);

    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("绑定账号");

        exchangeButton();

        checkBox_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!model.isIsBindingWeChat()) {
                    UMShareAPI.get(MyAccountBind.this).getPlatformInfo(MyAccountBind.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                    Config.dialogSwitch = false;
                } else {
                    ReminderHelper.getIntentce().showDialog(MyAccountBind.this, null, "确定要解除绑定吗？", "取消", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                        }
                    }, "确定", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {
                            unBindThrideAccount(WEIXIN);
                        }
                    });
                }
            }
        });

        checkBox_QQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!model.isIsBindingQQ()) {
                    UMShareAPI.get(MyAccountBind.this).getPlatformInfo(MyAccountBind.this, SHARE_MEDIA.QQ, umAuthListener);
                } else {
                    ReminderHelper.getIntentce().showDialog(MyAccountBind.this, null, "确定要解除绑定吗？", "取消", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                        }
                    }, "确定", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {
                            unBindThrideAccount(QQ);
                        }
                    });
                }
            }
        });

    }

    private void exchangeButton() {
        checkBox_weixin.setImageResource(model.isIsBindingWeChat() ? R.mipmap.checkbox_true : R.mipmap.checkbox_false);
        checkBox_QQ.setImageResource(model.isIsBindingQQ() ? R.mipmap.checkbox_true : R.mipmap.checkbox_false);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:

                finish();
                break;

        }

    }

    /**
     * 绑定第三方账号
     */
    private void bindThrideAccount(final int type, String openId) {

        final Map<String, Object> map = new TreeMap<>();
        map.put("openId", openId);
        map.put("bindingType", type);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.bindingThirdparty_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                if (type == WEIXIN) {
                    model.setIsBindingWeChat(true);
                } else {
                    model.setIsBindingQQ(true);
                }
                ReminderHelper.getIntentce().ToastShow(MyAccountBind.this, "绑定成功");
                exchangeButton();
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    /**
     * 解除绑定第三方账号
     */
    private void unBindThrideAccount(final int type) {

        final Map<String, Object> map = new TreeMap<>();
        map.put("bindingType", type);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.relieveThirdparty_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                if (type == WEIXIN) {
                    model.setIsBindingWeChat(false);
                } else {
                    model.setIsBindingQQ(false);
                }
                ReminderHelper.getIntentce().ToastShow(MyAccountBind.this, "解绑成功");
                exchangeButton();
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    /**
     * 第三方登录接口回调
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            final String openId = data.get("openid");
            final int type = platform == SHARE_MEDIA.QQ ? QQ : WEIXIN;
            bindThrideAccount(type, openId);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            Toast.makeText(MyAccountBind.this, "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            Toast.makeText(MyAccountBind.this, "取消授权", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
