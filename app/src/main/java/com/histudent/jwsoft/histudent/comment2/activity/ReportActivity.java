package com.histudent.jwsoft.histudent.comment2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.ReportReasonSelectActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.HashMap;
import java.util.Map;


/*
*
* 举报界面
* */

public class ReportActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private TextView btn_report;
    private LinearLayout mReason;
    private EditText et_content;
    private String reportReason;
    private Intent intent;
    private TextView tv_reason;
    private String otherReason, objectId;
    private ReportType type;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    String content = (String) msg.obj;
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        JSONObject object = JSONObject.parseObject(content);
                        if (object != null && object.getIntValue("ret") == 1) {
                            ReportActivity.this.finish();
                            ReminderHelper.getIntentce().ToastShow_with_image(ReportActivity.this,
                                    "我们会审核内容，做出相应的处理", R.string.icon_check);
                        }
                    }
                    Log.e("ReportActivity", content);
                    break;
            }
        }
    };


    @Override
    public int setViewLayout() {
        return R.layout.activity_report;
    }

    /**
     * @param activity
     * @param objectId 举报主题的id
     * @param type     举报的类型
     */
    public static void start(Activity activity, String objectId, ReportType type) {

        Intent intent = new Intent(activity, ReportActivity.class);
        intent.putExtra("objectId", objectId);
        intent.putExtra("objectType", type);
        activity.startActivity(intent);
    }

    @Override
    public void initView() {
        objectId = getIntent().getStringExtra("objectId");
        type = ((ReportType) getIntent().getSerializableExtra("objectType"));
        btn_report = ((TextView) findViewById(R.id.btn_report));
        mReason = ((LinearLayout) findViewById(R.id.ll_reason));
        et_content = ((EditText) findViewById(R.id.et_content));
        tv_reason = ((TextView) findViewById(R.id.tv_reason));
        ((TextView) findViewById(R.id.title_middle_text)).setText("举报");
        btn_report.setOnClickListener(this);
        mReason.setOnClickListener(this);
        et_content.addTextChangedListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //点击发送
            case R.id.btn_report:

                otherReason = et_content.getText().toString().trim();
                if (StringUtil.isEmpty(reportReason)) {
                    Toast.makeText(this, "请选择举报原因！", Toast.LENGTH_LONG).show();
                } else {
                    if (!StringUtil.isEmpty(otherReason) && !otherReason.equals("选择举报原因")) {
                        postReportReason();
                    } else {
                        Toast.makeText(this, "请填写举报原因的描述！", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            //选择举报原因
            case R.id.ll_reason:

                ReportReasonSelectActivity.start(ReportActivity.this, tv_reason.getText().toString().trim().equals("选择举报原因") ? ""
                        : tv_reason.getText().toString().trim(), 100);
                break;

            //返回
            case R.id.title_left:

                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {

            //举报原因的返回值
            switch (resultCode) {
                case 100:

                    if (data != null && data.getStringExtra("reason") != null) {
                        reportReason = data.getStringExtra("reason");
                        tv_reason.setText(reportReason);
                    }
                    break;
            }
        }
    }

    //提交举报信息
    private void postReportReason() {
        Map<String, Object> map = new HashMap<>();
        map.put("objectId", objectId);//举报对象的id
        map.put("objecType", getType() + "");//举报的类型
        map.put("reason", reportReason);//选择举报的原因
        map.put("otherReason", otherReason);//填写的举报的原因
        map.put("createBy", HiCache.getInstance().getLoginUserInfo().getUserId());//举报者的id
        map.put("createTime", TimeUtils.getCurrentTime());//举报的时间
        HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.report_url, map, HiWorldCache.Body, false,true);

    }

    public int getType() {

        int type1 = 0;
        switch (type) {

            //其他
            case OTHER:
                type1 = 0;
                break;

            //动态
            case DYNAMIC:
                type1 = 1;
                break;

            //日志
            case LOG:
                type1 = 2;
                break;

            //随记
            case ESSAY:
                type1 = 3;
                break;

            //相册
            case ALBUM:
                type1 = 4;
                break;

            //图片
            case IMAGE:
                type1 = 5;
                break;

            //hiba主题
            case HIBA_THEME:
                type1 = 6;
                break;

            //hiba回复
            case HIBA_REPLY:
                type1 = 7;
                break;

            //用户
            case USER:
                type1 = 10;
                break;

        }
        return type1;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length()>0){
            btn_report.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button_bg));
            btn_report.setClickable(true);
        }else{
            btn_report.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button_bg_));
            btn_report.setClickable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
