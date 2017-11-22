package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.netease.nim.uikit.common.util.string.StringUtil;

/**
 * 通讯录
 */
public class ContactActivity extends BaseActivity {

    private SimpleUserModel userModel;
    private HiStudentHeadImageView iv_avtar;
    TextView tv_name, tv_tell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void start(Activity activity, int requstCode, SimpleUserModel simpleUserModel) {

        Intent intent = new Intent(activity, ContactActivity.class);
        intent.putExtra("userModel", simpleUserModel);
        activity.startActivityForResult(intent, requstCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_contact;
    }

    @Override
    public void initView() {

        userModel = ((SimpleUserModel) getIntent().getSerializableExtra("userModel"));
        iv_avtar = ((HiStudentHeadImageView) findViewById(R.id.iv));
        tv_name = ((TextView) findViewById(R.id.tv_name));
        tv_tell = ((TextView) findViewById(R.id.tv_tell));
        ((TextView) findViewById(R.id.title_middle_text)).setText("通讯录");
        iv_avtar.setOnClickListener((View view) -> PersonCenterActivity.start(ContactActivity.this, userModel.getUserId()));
        HiStudentLog.e("---", userModel.toString());

        if (userModel != null) {
            PersionHelper.getInstance().getUserInfo(this, userModel.getUserId(), new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    updateUi();
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        }

    }

    private void updateUi() {
        CommonGlideImageLoader.getInstance().displayNetImage(this, userModel.getAvatar(),
                iv_avtar, ContextCompat.getDrawable(this, R.mipmap.avatar_def));

        tv_name.setText(userModel.getName());
        if (!StringUtil.isEmpty(userModel.getUserMobile()) && userModel.getUserMobile().trim().length() == 11) {
            tv_tell.setText(userModel.getUserMobile().substring(0, 3)
                    + "-" + userModel.getUserMobile().substring(3, 7) + "-" + userModel.getUserMobile().substring(7, 11));
        } else {
            if (!StringUtil.isEmpty(userModel.getUserMobile())) {
                tv_tell.setText(userModel.getUserMobile());
            } else {
                tv_tell.setText("该用户没绑定手机号");
            }
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.send_msg:

                PersionHelper.getInstance().sendMsg(this);
                break;

            case R.id.call_phone:

                takeCall(userModel.getUserMobile().trim());
                break;
            case R.id.go_home:
                PersonCenterActivity.start(this, userModel.getUserId());
                break;

            case R.id.title_left:

                finish();
                break;
        }
    }

    //打电话的操作
    private void takeCall(final String tell) {
        if (!StringUtil.isEmpty(tell)) {
            checkCallPermission(() -> {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + tell));
                ContactActivity.this.startActivity(intent);
            });
        }
    }
}
