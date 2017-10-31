package com.histudent.jwsoft.histudent.body.mine.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/23.
 * 加入班级
 */
public class AddInClassActivity extends BaseActivity {

    private TextView title_middle;
    private EditText editText;

    @Override
    public int setViewLayout() {
        return R.layout.our_addinclass_activity;
    }

    @Override
    public void initView() {
        title_middle = (TextView) findViewById(R.id.title_middle_text);
        editText = (EditText) findViewById(R.id.our_addinclass_activity_edit);

    }

    @Override
    public void doAction() {
        super.doAction();
        title_middle.setText("加入班级");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.our_addinclass_activity_button:

                String phone = editText.getText().toString();
                if (!TextUtils.isEmpty(phone)) {

                    Map<String, Object> map = new TreeMap<>();
                    map.put("classMasterMobile", phone);
                    HiStudentHttpUtils.postDataByOKHttp(this, map,  HistudentUrl.addInClass, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject object = new JSONObject(result);
                                if (object.optInt("status") == 1) {
                                    Toast.makeText(AddInClassActivity.this, "申请加入成功", Toast.LENGTH_LONG).show();
                                    setResult(200);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(String msg) {

//                            Toast.makeText(AddInClassActivity.this, "请求发送失败", Toast.LENGTH_LONG).show();

                        }
                    });

                } else {
                    Toast.makeText(AddInClassActivity.this, "班主任手机号不为空", Toast.LENGTH_LONG).show();
                }

                break;

        }
    }

}
