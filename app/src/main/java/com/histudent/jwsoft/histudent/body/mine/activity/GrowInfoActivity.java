package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.adapter.GrowInfoAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.GrowInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.dateSelect.TheDatePickerDialog;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/11/29.
 * 我的成长信息
 */

public class GrowInfoActivity extends BaseActivity {

    private TextView title;
    private IconView title_right_image;
    private ListView growInfo_list;
    private List<GrowInfoModel> models;
    private GrowInfoAdapter adapter;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, GrowInfoActivity.class));

    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_growinfo;
    }

    @Override
    public void initView() {
        models = new ArrayList<>();

        adapter = new GrowInfoAdapter(this, models);
        title_right_image = (IconView) findViewById(R.id.title_right_image);
        title = (TextView) findViewById(R.id.title_middle_text);
        growInfo_list = (ListView) findViewById(R.id.growInfo_list);

        title_right_image.setVisibility(View.VISIBLE);

        title.setText("成长信息");
        growInfo_list.setAdapter(adapter);

        getGrowInfoList(null);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                setBirthDay();
                break;
        }
    }

    /**
     * 获取等级规则列表
     */
    private void getGrowInfoList(String someday) {

        Map<String, Object> map = new TreeMap<>();
        map.put("someday", someday);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.pointRecords_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                if (!"{ret:1}".equals(result)) {
                    List<GrowInfoModel> models_ = JSONArray.parseArray(result, GrowInfoModel.class);
                    if (models_ != null && models_.size() > 0) {
                        models.addAll(models_);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    /**
     * 选择日期
     */
    private void setBirthDay() {

        String date = TimeUtils.getCurrentTime().split(" ")[0];

        String[] data_str = date.split("-");
        int[] date_time = new int[3];

        date_time[0] = Integer.parseInt(data_str[0]);
        date_time[1] = Integer.parseInt(data_str[1]);
        date_time[2] = Integer.parseInt(data_str[2]);

        String[] date_ = {"2010-1-1", "2020-1-1"};

        TheDatePickerDialog datePickerDialog = new TheDatePickerDialog(GrowInfoActivity.this, date_time, date_, 3, new TheDatePickerDialog.Callback() {
            @Override
            public void showInfo(String string) {

                if (judgeTime(string)) {
                    Toast.makeText(GrowInfoActivity.this, "请选择正确的日期", Toast.LENGTH_SHORT).show();
                } else {
                    models.clear();
                    getGrowInfoList(string);
                }
            }
        });
        datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        datePickerDialog.show();
    }

    private boolean judgeTime(String time) {
        String[] times1 = SystemUtil.getCurrentData().split("-");
        String[] times2 = time.split("-");
        String date_ = times1[0] + times1[1] + times1[2];
        String time_ = times2[0] + times2[1] + times2[2];
        return (Integer.parseInt(time_) - Integer.parseInt(date_)) > 0;
    }

}
