package com.histudent.jwsoft.histudent.body.message.activity;

import android.widget.Toast;

import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.body.BodyActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.Dialog_confirm;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/9/17.
 */
public class ClassUtils {

    public static void showExchangeClassDialog(final BaseActivity activity, final ClassModel classModel) {
        final Dialog_confirm confirmDialog = new Dialog_confirm(activity, "取消", "确定");
        confirmDialog.show();
        confirmDialog.setTitle("请输入转让用户的手机号");
        confirmDialog.setClicklistener(new Dialog_confirm.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                // TODO Auto-generated method stub
                confirmDialog.dismiss();

            }

            @Override
            public void doCancel(String info) {
                // TODO Auto-generated method stub
                confirmDialog.dismiss();
                exchangeClassOwner(activity, classModel, info);
            }
        });
    }

    /**
     * 转让班级
     *
     * @param acceptUserMobile
     */
    private static void exchangeClassOwner(final BaseActivity activity, ClassModel classModel, String acceptUserMobile) {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classModel.getClassId());
        map.put("acceptUserMobile", acceptUserMobile);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.exchangeClassOwner, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(activity, "操作成功！", Toast.LENGTH_SHORT).show();

                BodyActivity.start(activity);

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }
}
