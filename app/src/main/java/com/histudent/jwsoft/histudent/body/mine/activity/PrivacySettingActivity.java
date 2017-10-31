package com.histudent.jwsoft.histudent.body.mine.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.activity.CreateGroupActivity;
import com.histudent.jwsoft.histudent.body.mine.model.UserQualitySettingInforBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/*
        隐私设置页面
 */
public class PrivacySettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relative_all, relative_classmate, relative_myself, relative_defined, relative_disableUser;
    //    private ImageView iv_back;
    private List<ImageView> checkBoxList;
    private Intent intent;
    private String question, answer, ori_question, ori_answer;
    private int type, ori_type;
    private UserQualitySettingInforBean.DataBean userQualitySettingInforBean;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //提交用户设置的权限信息
                case 0:

                    String content = ((String) msg.obj);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        JSONObject object = JSON.parseObject(content);
                        if (object != null && object.getIntValue("ret") == 1) {

                            Toast.makeText(PrivacySettingActivity.this, "设置成功", Toast.LENGTH_LONG).show();
                        }
                    }
                    PrivacySettingActivity.this.finish();

                    Log.e("DefinedQuqality", content);
                    break;

            }
        }
    };

    @Override
    public int setViewLayout() {
        return R.layout.activity_privacy_setting;
    }

    @Override
    public void initView() {
        question = answer = "";
        relative_all = ((RelativeLayout) findViewById(R.id.relative_all));
        relative_classmate = ((RelativeLayout) findViewById(R.id.relative_classmate));
        relative_myself = ((RelativeLayout) findViewById(R.id.relative_myself));
        relative_defined = ((RelativeLayout) findViewById(R.id.relative_defined));
        relative_disableUser = ((RelativeLayout) findViewById(R.id.relative_disableduser));
        ((TextView) findViewById(R.id.title_middle_text)).setText("隐私设置");
        ((TextView) findViewById(R.id.title_right_text)).setText("完成");


        initCheckBoxs();

        getUserQualitySetting();
        relative_all.setOnClickListener(this);
        relative_classmate.setOnClickListener(this);
        relative_myself.setOnClickListener(this);
        relative_defined.setOnClickListener(this);
        relative_disableUser.setOnClickListener(this);
    }

    //初始化checkBoxs
    private void initCheckBoxs() {
        checkBoxList = new ArrayList<>();
        checkBoxList.add(((ImageView) findViewById(R.id.cb_all)));
        checkBoxList.add(((ImageView) findViewById(R.id.cb_classmate)));
        checkBoxList.add(((ImageView) findViewById(R.id.cb_myself)));
        checkBoxList.add(((ImageView) findViewById(R.id.cb_defined)));
        checkBoxList.get(0).setSelected(true);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //返回,返回时，如果用户做出了修改，提示用户是否放弃修改
            case R.id.title_left:

                showIsGiveUpChangeNoticeDialog();

                break;

            //返回时，提交用户的隐私设置
            case R.id.title_right:

                if (type != 3) {
                    postDefindeQuality(question, answer, type);
                } else {
                    if (StringUtil.isEmpty(answer) || StringUtil.isEmpty(question)) {
                        Toast.makeText(this, "请填写问题和答案", Toast.LENGTH_LONG).show();
                    } else {
                        postDefindeQuality(question, answer, type);
                    }
                }

                break;


            //所有人
            case R.id.relative_all:
                changeCheckBoxBackgroud(0);
                break;


            //校友同学
            case R.id.relative_classmate:
                changeCheckBoxBackgroud(1);
                break;


            //仅自己
            case R.id.relative_myself:
                changeCheckBoxBackgroud(2);
                break;

            //自定义
            case R.id.relative_defined:

                changeCheckBoxBackgroud(3);
                intent = new Intent(this, PrivacySettingDefinedActivity.class);
                intent.putExtra("answer", answer);
                intent.putExtra("question", question);
                startActivityForResult(intent, 100);
                break;


            //屏蔽动态
            case R.id.relative_disableduser:

                CreateGroupActivity.start(this, "", 2);
                break;
        }
    }

    //根据选择改变checkbox的背景
    private void changeCheckBoxBackgroud(int position) {

        type = position;
        for (int i = 0; i < checkBoxList.size(); i++) {

            if (i == position) {
                checkBoxList.get(i).setSelected(true);
            } else {
                checkBoxList.get(i).setSelected(false);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            switch (resultCode) {

                //自定义权限返回值
                case 100:
                    type = 3;
                    question = data.getStringExtra("question");
                    answer = data.getStringExtra("answer");
                    break;

            }
        }
    }

    //获取用户设置的权限
    private void getUserQualitySetting() {

        Map<String, Object> map = new TreeMap<>();
        map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        HiStudentHttpUtils.getDataByOKHttp(this, map, HistudentUrl.mine_get_user_quality_setting_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                userQualitySettingInforBean = JSON.parseObject(result, UserQualitySettingInforBean.DataBean.class);

                if (userQualitySettingInforBean != null && userQualitySettingInforBean != null) {
                    ori_type = userQualitySettingInforBean.getPrivacyType();
                    changeCheckBoxBackgroud(userQualitySettingInforBean.getPrivacyType());

                    //仅当用户设置隐私为自定义时，才去取自定义问题和答案
                    if (userQualitySettingInforBean.getPrivacyType() == 3) {
                        ori_answer = userQualitySettingInforBean.getPrivacyAnswer();
                        ori_question = userQualitySettingInforBean.getPrivacyQuestion();
                        answer = ori_answer;
                        question = ori_question;

                    }
                }

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });


    }

    //提交用户隐私设置
    private void postDefindeQuality(String question, String answer, int type) {

        Map<String, Object> map = new HashMap<>();
        map.put("privacyType", type + "");
        map.put("question", question);
        map.put("answer", answer);

        HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.mine_submitprivacy_url, map, HiWorldCache.Quarry, false,true);

    }

    private void showIsGiveUpChangeNoticeDialog() {

        if (ori_type == type) {
            if (ori_type != 3) {
                PrivacySettingActivity.this.finish();
                return;
            } else {
                if (question.equals(ori_question) && question.equals(ori_answer)) {
                    PrivacySettingActivity.this.finish();
                    return;
                }
            }

        }
        ReminderHelper.getIntentce().showDialog(PrivacySettingActivity.this, "提示", "是否取消修改？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                PrivacySettingActivity.this.finish();
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });
    }
}
