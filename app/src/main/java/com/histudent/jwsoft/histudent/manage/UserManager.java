package com.histudent.jwsoft.histudent.manage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CheckUpdataBean;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.IntenetUtil;
import com.histudent.jwsoft.histudent.commen.utils.MyUpdateManager;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lichaojie on 2017/7/3.
 */

public class UserManager {


    /**
     * 用户当前班级id
     */
    private String currentClassId;

    private UserManager() {
    }

    private static final class UserManageHolder {
        private static UserManager instance = new UserManager();
    }

    public static UserManager getInstance() {
        return UserManageHolder.instance;
    }

    public String getCurrentClassId() {
        return currentClassId;
    }

    public void setCurrentClassId(String currentClassId) {
        this.currentClassId = currentClassId;
    }



    /**
     * 检查版本更新
     *
     * @param context
     */
    public void checkVersionUpdate(Context context) {
        if (IntenetUtil.getNetworkState() > 0) {
            //请求版本信息
            Map<String, Object> map_sign = new HashMap<>();
            map_sign.put("devicetype", "1");
            map_sign.put("version", SystemUtil.getVersion());

            HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) context), map_sign, HistudentUrl.update_apk_url, new HttpRequestCallBack() {

                @Override
                public void onSuccess(String result) {
                    resolveVersionInformation(result, context);
                }

                @Override
                public void onFailure(String errorMsg) {
                    ToastTool.showCommonToast(context, "连接超时,请稍后再试！");
                }

            });

        } else {
            ((BaseActivity) context).closeLoadingImage();
            ReminderHelper.getIntentce().showDialog(((BaseActivity) context), "提示", "当前网络不可用，请连接网络后重试", "退出", () -> ((BaseActivity) context).finish(), "设置网络", () -> {
                Intent intent;
                //跳转到系统设置网络的界面
                // 先判断当前系统版本
                if (Build.VERSION.SDK_INT > 10) {
                    // 3.0以上
                    intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                }
            });
        }

    }


    /**
     * 处理从服务端获取来的版本信息
     *
     * @param result
     */
    private void resolveVersionInformation(String result, Context context) {
        if (StringUtil.isEmpty(result))
            return;

        CheckUpdataBean checkUpdataBean = JSON.parseObject(result, CheckUpdataBean.class);
        if (checkUpdataBean == null)
            return;
        if (checkUpdataBean.getStatus() == 0) {
            //不更新 已经是最新的版本 不需要更新
            return;
        }
        if (!checkUpdataBean.getVersion().equals(SystemUtil.getVersion()))
            updateNewVersion(context, checkUpdataBean);

    }


    /**
     * 提示升级对话框及相关更新
     *
     * @param context
     * @param checkUpdataBean
     */
    private void updateNewVersion(Context context, CheckUpdataBean checkUpdataBean) {
        if (checkUpdataBean == null) return;

        View view = LayoutInflater.from(context).inflate(R.layout.version_upadate_layout_login, null);

        Dialog dialog_select_login = new Dialog(context, R.style.TipDialog);
        dialog_select_login.setContentView(view);
        dialog_select_login.setCancelable(false);

        TextView updateConfirm = (TextView) view.findViewById(R.id.upldate_btn);
        TextView updateCancel = (TextView) view.findViewById(R.id.iv_cancel);
        TextView line = (TextView) view.findViewById(R.id.line);

        if (checkUpdataBean.getStatus() == 2) {
            //强制更新
            updateCancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }

        TextView tv = ((TextView) view.findViewById(R.id.contnet));
        if (!StringUtil.isEmpty(checkUpdataBean.getUpdatedesc())) {
            tv.setText(checkUpdataBean.getUpdatedesc());
        }

        updateConfirm.setOnClickListener((View v) -> {
            dialog_select_login.dismiss();
            MyUpdateManager myUpdateManager = new MyUpdateManager(context);
            myUpdateManager.LoadingApk(checkUpdataBean.getStatus(), checkUpdataBean.getUpdateurl());
        });

        updateCancel.setOnClickListener((View v) -> dialog_select_login.dismiss());
        dialog_select_login.show();
    }
}
