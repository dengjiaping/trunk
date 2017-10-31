package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/27.
 * 编辑框界面
 */
public class InputActivity extends BaseActivity {

    private EditText editText;
    private TextView title, button;
    private String info;
    private String key;
    private int limit;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 100:
                    Intent data = new Intent();
                    data.putExtra("info", info);
                    setResult(20, data);
                    finish();
                    break;
            }
        }
    };

    @Override
    public int setViewLayout() {
        return R.layout.input_activity;
    }

    public static void startOnResult(Activity activity, String name, String key, String content, int requestCode) {
        Intent intent = new Intent(activity, InputActivity.class);
        intent.putExtra("title", name);
        intent.putExtra("content", content);
        intent.putExtra("key", key);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {
        editText = (EditText) findViewById(R.id.input_editext);
        title = (TextView) findViewById(R.id.title_middle_text);
        button = (TextView) findViewById(R.id.title_right_text);

        key = getIntent().getStringExtra("key");
        limit = getIntent().getIntExtra("limit", 0);

    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText(getIntent().getStringExtra("title"));
        button.setText("保存");

        String content = getIntent().getStringExtra("content");
        if (!this.getString(R.string.no_set).equals(content) && !TextUtils.isEmpty(content)) {
            editText.setText(content);
            editText.setSelection(content.length());
        }

        if (key.equals("address")) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        } else if (key.equals("presentation")) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        } else if (key.equals("classAlias")) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        } else if (key.equals("classInfo")) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
        } else {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:

                Intent data_ = new Intent();
                data_.putExtra("info", "-1");
                setResult(10, data_);
                finish();

                break;
            case R.id.title_right:
                String info = editText.getText().toString().trim();
                if (key.equals("classAlias") || key.equals("classInfo")||key.equals("groupName")) {

                    Intent data = new Intent();
                    data.putExtra("info", info);
                    setResult(20, data);
                    finish();

                } else {
                    if (TextUtils.isEmpty(info)) {
                        ReminderHelper.getIntentce().ToastShow(InputActivity.this, "内容不能为空");
                        return;
                    }
                    Map<String, Object> map = new TreeMap<>();
                    map.put(key, info);
                    HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.editUserInfo, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {

                            handler.sendEmptyMessage(100);

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
                }

        }


    }

}
