package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.comment2.adapter.ReportResonSelectAdapter;
import com.histudent.jwsoft.histudent.comment2.bean.ReportReasonModel;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 举报原因的选择界面
 */
public class ReportReasonSelectActivity extends BaseActivity implements View.OnClickListener {

    private Intent intent;
    private TextView tv_done;
    private String reason;
    private List<ReportReasonModel> reasonList;
    private ListView mListView;
    private int currentPosition;
    private ReportResonSelectAdapter adapter;

    @Override
    public int setViewLayout() {
        return R.layout.activity_report_reason_select;
    }


    public static void start(Activity activity, String reason, int requstCode) {
        Intent intent = new Intent(activity, ReportReasonSelectActivity.class);
        intent.putExtra("reason", reason);
        activity.startActivityForResult(intent, requstCode);

    }

    @Override
    public void initView() {

        intent = getIntent();
        reason = getIntent().getStringExtra("reason");
        initReasons();

        tv_done = ((TextView) findViewById(R.id.title_right_text));
        ((TextView) findViewById(R.id.title_middle_text)).setText("举报原因");
        mListView = ((ListView) findViewById(R.id.listview));
        adapter = new ReportResonSelectAdapter(reasonList, this);
        mListView.setAdapter(adapter);
        tv_done.setVisibility(View.GONE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("reason", reasonList.get(position).getReason());//返回举报的原因
                ReportReasonSelectActivity.this.setResult(100, intent);
                ReportReasonSelectActivity.this.finish();
            }
        });
    }

    private void initReasons() {
        reasonList = new ArrayList<>();
        reasonList.add(new ReportReasonModel("色情暴力", false));
        reasonList.add(new ReportReasonModel("骚扰谩骂", false));
        reasonList.add(new ReportReasonModel("广告欺诈", false));
        reasonList.add(new ReportReasonModel("病毒木马", false));
        reasonList.add(new ReportReasonModel("政治敏感", false));
        reasonList.add(new ReportReasonModel("其他", false));

        if (!StringUtil.isEmpty(reason)) {
            for (int i = 0; i < reasonList.size(); i++) {

                if (reason.equals(reasonList.get(i).getReason())) {
                    currentPosition = i;
                    reasonList.get(i).setSeleted(true);
                }
            }
        }

        if (currentPosition == 0) {
            reasonList.get(0).setSeleted(true);
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            //返回
            case R.id.title_left:
                this.finish();
                break;

        }
    }

}
