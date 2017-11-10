package com.histudent.jwsoft.histudent.account.login.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.fragment.AccountFragmnet;
import com.histudent.jwsoft.histudent.account.login.fragment.PhoneFragmnet;
import com.histudent.jwsoft.histudent.account.login.model.LoginData;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.model.SaveAccountModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/5/26.
 * 登录页面
 */
public class LoginActivity extends BaseLoginActivity {

    private FragmentManager fragmentManager;
    public LinearLayout layout_;
    public HiStudentHeadImageView headImage;
    public RelativeLayout fragment_layout;
    private TextView link_clik, link_clik_withLine, link_clik_, link_clik_withLine_;
    private RelativeLayout link_layout;
    private Button login_button;
    private IconView login_phone_image;
    private LoginData loginData;
    private Fragment fragment;

    /**
     * 正常跳转
     *
     * @param activity
     */
    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.login_activity;
    }

    @Override
    public void initView() {
        fragment = new AccountFragmnet();
        headImage = (HiStudentHeadImageView) findViewById(R.id.hendImage);
        fragment_layout = (RelativeLayout) findViewById(R.id.login_fragment);
        layout_ = (LinearLayout) findViewById(R.id.layout_);
        link_clik = (TextView) findViewById(R.id.link_clik);
        link_clik_withLine = (TextView) findViewById(R.id.link_clik_withLine);
        link_clik_ = (TextView) findViewById(R.id.link_clik_);
        link_clik_withLine_ = (TextView) findViewById(R.id.link_clik_withLine_);
        link_layout = (RelativeLayout) findViewById(R.id.link_layout);
        login_button = (Button) findViewById(R.id.login);
        login_phone_image = (IconView) findViewById(R.id.login_phone_image);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.login_fragment, fragment).commit();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    public void doAction() {
        super.doAction();

        link_clik_.setText("收不到短信？");
        link_clik_withLine_.setText(R.string.verification_getCode_voice);

        link_clik.setText("还没有账号？");
        link_clik_withLine.setText("注册账号");

        link_layout.setVisibility(View.GONE);

        if (!HTApplication.isOnLine) {
            //调试用
            final List<Long> times = new ArrayList<>();
            headImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long time = System.currentTimeMillis();
                    if (times.size() == 0) {
                        times.add(time);
                    } else {
                        if (time - times.get(times.size() - 1) < 1000) {
                            times.add(time);
                            if (times.size() > 5) {
                                showTest();
                            }
                        } else {
                            times.clear();
                        }
                    }
                }
            });

        }
    }

    /**
     * 验证码倒计时
     *
     * @param num
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSubscribe(String num) {
        if ("0".equals(num)) {
            link_clik_withLine_.setClickable(true);
            link_clik_withLine_.setText(R.string.verification_getCode_voice);
        } else {
            link_clik_withLine_.setClickable(false);
            link_clik_withLine_.setText(num + getResources().getString(R.string.verification_num));
        }
    }

    /**
     * 获取登录信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoginInfo(LoginData data) {
        if (data == null) return;
        loginData = data;

        //刷新头像
        if (loginData.isAccountExchange()) {
            SaveAccountModel model = HiCache.getInstance().getAccountDataInDB(loginData.getAccount());
            if (model != null && !TextUtils.isEmpty(model.getAvatar())) {
                headImage.setVisibility(View.VISIBLE);
                MyImageLoader.getIntent().displayNetImage(this, model.getAvatar(), headImage);
            } else {
                headImage.setVisibility(View.GONE);
            }
        }

        //改变登录按钮是否可点击
        ReminderHelper.getIntentce().exchangeButtonBG(login_button, (!TextUtils.isEmpty(loginData.getAccount())) && (!TextUtils.isEmpty(loginData.getPwd())));
    }

    /**
     * 登录
     */
    private void login() {
        if (loginData == null) return;
        if (loginData.isAccountLogin()) {
            loginAction(this, loginData.getAccount(), loginData.getPwd(), BaseLoginActivity.NOMARL);
        } else {
            loginActionByPhone(this, loginData.getAccount(), loginData.getPwd());
        }
    }

    /**
     * 测试专用
     */
    private void showTest() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_show_test, null);

        final Dialog dialog_test = new Dialog(this, R.style.TipDialog);
        dialog_test.setContentView(view);
        dialog_test.setCancelable(false);

        final EditText editText = (EditText) view.findViewById(R.id.dialog_test_edit);
        Button cancle = (Button) view.findViewById(R.id.dialog_test_cancel);
        Button save = (Button) view.findViewById(R.id.dialog_test_save);

        editText.setText(TextUtils.isEmpty(HiCache.getInstance().getUrl()) ? HistudentUrl.getBaseUrl() : HiCache.getInstance().getUrl());

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_test.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editText.getText().toString();
                if (TextUtils.isEmpty(info)) {
                    Toast.makeText(LoginActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                } else {
                    HiCache.getInstance().saveUrl(info);
                    dialog_test.dismiss();
                }
            }
        });

        dialog_test.show();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.login://登录
                login();
                break;

            case R.id.login_phone_image://手机登录
                if (fragment instanceof AccountFragmnet) {
                    fragment = new PhoneFragmnet();
                    fragmentManager.beginTransaction().replace(R.id.login_fragment, fragment).commit();
                    login_phone_image.setText(R.string.icon_member2);
                    link_layout.setVisibility(View.VISIBLE);
                } else {
                    fragment = new AccountFragmnet();
                    fragmentManager.beginTransaction().replace(R.id.login_fragment, fragment).commit();
                    link_layout.setVisibility(View.GONE);
                    login_phone_image.setText(R.string.icon_telphone);
                }
                break;

            case R.id.login_weixin://微信登录

                LoginPresenter.getIntentce().loginByWweXin(this);
                break;

            case R.id.login_QQ://QQ登录

                LoginPresenter.getIntentce().loginByQQ(this);
                break;

            case R.id.foegetPassword://忘记密码

                LoginPresenter.getIntentce().forgetPsw(this);
                break;

            case R.id.link_clik_withLine://注册新用户

                LoginPresenter.getIntentce().userRegister(this);
                break;

            case R.id.link_clik_withLine_://获取语音验证码

                EventBus.getDefault().post(true);
                break;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == 200) {
//            fragment.exchangeAccount(data.getStringExtra("account"));
            EventBus.getDefault().post(new SaveAccountModel());
        } else {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
    }

}
