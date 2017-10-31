package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CheckUpdataBean;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.HttpResponseModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.MyUpdateManager;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.comment2.utils.UpDateDialogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.netease.nim.uikit.common.util.string.StringUtil;

/**
 * Created by liuguiyu-pc on 2016/9/14.
 */
public class AboutHiActivity extends BaseActivity implements View.OnClickListener {

    private TextView version, title;
    private RelativeLayout layout_version;
    private TextView tv_update;
    private boolean isLast;

    public static void start(Activity activity) {

        activity.startActivity(new Intent(activity, AboutHiActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_abouthi;
    }

    @Override
    public void initView() {
        version = (TextView) findViewById(R.id.version);
        title = (TextView) findViewById(R.id.title_middle_text);
        layout_version = (RelativeLayout) findViewById(R.id.layout_version);
        tv_update = ((TextView) findViewById(R.id.tv_update));
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("关于");

        version.setText("version : " + SystemUtil.getVersion());

        checkUpdata();

        layout_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLast)
                    checkUpdata();
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.agreement:
                MyWebActivity.start(AboutHiActivity.this, HistudentUrl.agreement_url, "用户协议");
                break;
            case R.id.phone:
                ClassHelper.callPhone(AboutHiActivity.this, getResources().getString(R.string.offical_phone));
                break;
            case R.id.offical:
                MyWebActivity.start(AboutHiActivity.this, getResources().getString(R.string.offical_url));
                break;
        }

    }

    private void checkUpdata() {

        final RequestParams requestParams = new RequestParams();
        requestParams.addHeader("Content-Type", "application/json");
        requestParams.addQueryStringParameter("devicetype", "1");
        requestParams.addQueryStringParameter("version", SystemUtil.getVersion());
        Log.e("getVersion", SystemUtil.getVersion());

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, HistudentUrl.update_apk_url, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                HttpResponseModel model = HiStudentHttpUtils.dataIsCorrect(responseInfo.result);
                if (model != null && !StringUtil.isEmpty(model.getData())) {

                    final CheckUpdataBean checkUpdataBean = JSON.parseObject(model.getData(), CheckUpdataBean.class);

                    String category = HistudentUrl.update_apk_url.replace(HistudentUrl.getBaseUrl(), "");
                    String appInfor = HiWorldCache.getDBData(HiCache.getInstance().getAcount(), category);

//                    if (!StringUtil.isEmpty(appInfor) && !appInfor.equals(model.getData())) {
//                        HiWorldCache.saveDataToDB("", model.getData(), category);
//                    }

                    if (checkUpdataBean != null) {
                        if (checkUpdataBean.getStatus() == 0) {
                            tv_update.setText("已是最新版本");
                            isLast = true;
                        } else {
                            //不是最新版本时显示版本更新界面，同时版本信息显示为红色
                            tv_update.setTextColor(Color.RED);
                            tv_update.setText("更新到最新版本");
                            UpDateDialogUtils.showUpdateView(new DialogButtonListener() {
                                @Override
                                public void setOnDialogButtonListener() {
                                    //版本更新的操作
                                    MyUpdateManager myUpdateManager = new MyUpdateManager(AboutHiActivity.this);
                                    myUpdateManager.LoadingApk(checkUpdataBean.getStatus(), checkUpdataBean.getUpdateurl());
                                }
                            }, checkUpdataBean.getUpdatedesc(), AboutHiActivity.this);
                        }
                    }

                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });

    }

}
