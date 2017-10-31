package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.fragment.AccountSafeType;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;

/**
 * Created by liuguiyu-pc on 2016/11/3.
 */
public class AccountSafeActivity extends BaseActivity {

    private boolean hasPhoneNum = true;
    private boolean hasEmailAddress = true;
    private TextView title, text_phone, text_email, user_phone_reset, user_email_reset;
    private String phone, email;
    public static CurrentUserDetailInfoModel model;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AccountSafeActivity.class);
        activity.startActivityForResult(intent, 100);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_account_safe;
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.title_middle_text);
        text_phone = (TextView) findViewById(R.id.user_phone);
        text_email = (TextView) findViewById(R.id.user_email);
        user_phone_reset = (TextView) findViewById(R.id.user_phone_reset);
        user_email_reset = (TextView) findViewById(R.id.user_email_reset);
        UpDataUI();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.title_left:

                finish();

                break;

            case R.id.exchange_psw:

                AccountSafeItemActivity.start(this, getResources().getString(R.string.checkOldPwd), AccountSafeType.PSWSET);

                break;

            case R.id.user_phone_reset:

                AccountSafeItemActivity.start(this, getResources().getString(hasPhoneNum ? R.string.safeCheck : R.string.setPhoneCode), hasPhoneNum ? AccountSafeType.PHONE : AccountSafeType.PHONE_NULL);

                break;

            case R.id.user_email_reset:

                AccountSafeItemActivity.start(this, getResources().getString(hasEmailAddress ? R.string.safeCheck : R.string.setEmailCode), hasEmailAddress ? AccountSafeType.EMAIL : AccountSafeType.EMAIL_NULL);

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 200) {
            UpDataUI();
        } else if (resultCode == 100) {
            setResult(100);
            finish();
        }
    }

    public void UpDataUI() {

        title.setText(R.string.accountSafe);

        PersionHelper.getInstance().getUserInfo(this, HiCache.getInstance().getLoginUserInfo().getUserId(), new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                model = HiCache.getInstance().getUserDetailInfo();

                phone = model.getMobile();
                email = model.getEmail();

                hasPhoneNum = model.isIsMobileVerified();
                hasEmailAddress = model.isIsEmailVerified();

                user_phone_reset.setText(hasPhoneNum ? "更换手机" : "设置手机");
                user_email_reset.setText(hasEmailAddress ? "更换邮箱" : "设置邮箱");

                if (hasPhoneNum) {
                    String bb = phone.substring(3, 7);
                    String cc = phone.replace(bb, "****");
                    text_phone.setText(cc);
                } else {
                    text_phone.setText("");
                }
                text_email.setText(hasEmailAddress ? email : "");
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }
}
