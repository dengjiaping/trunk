package com.histudent.jwsoft.histudent.activity.clock;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.TaskItemBean;
import com.histudent.jwsoft.histudent.commen.utils.TimeUtils;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.histudent.jwsoft.histudent.presenter.clock.AddClockPresenter;
import com.histudent.jwsoft.histudent.presenter.clock.contract.AddClockContract;
import com.netease.nim.uikit.common.util.sys.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/8/18.
 */

public class AddClockActivity extends BaseActivity<AddClockPresenter> implements AddClockContract.View {
    @BindView(R.id.add_task_title)
    TextView mTaskName;
    @BindView(R.id.add_task_time_begin)
    TextView mBeginTime;
    @BindView(R.id.add_task_time_end)
    TextView mEndTime;
    @BindView(R.id.add_task_is_everyday)
    CheckBox mCheckBox;
    @BindView(R.id.add_task_day)
    EditText mTaskDay;
    @BindView(R.id.add_task_content)
    EditText mTaskContent;
    @BindView(R.id.title_right_text)
    TextView mSubmit;
    @BindView(R.id.title_middle_text)
    TextView mTitle;
    @BindView(R.id.ll_task_day)
    LinearLayout mTaskDayLayout;

    @OnClick({R.id.title_right_text, R.id.title_left, R.id.add_task_title,
            R.id.add_task_time_begin, R.id.add_task_time_end})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right_text:
                taskName = mTaskName.getText().toString().trim();
                String memo = mTaskContent.getText().toString().trim();
                standardDays = mTaskDay.getText().toString();
                String beginTimeStr = mBeginTime.getText().toString();
                String endTimeStr = mEndTime.getText().toString();
                if (TextUtils.isEmpty(taskName)) {
                    Toast.makeText(AddClockActivity.this, "请填写任务标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(memo)) {
                    Toast.makeText(AddClockActivity.this, "请填写任务内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(beginTimeStr)) {
                    Toast.makeText(AddClockActivity.this, "请填写开始时间", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(endTimeStr)) {
                    Toast.makeText(AddClockActivity.this, "请填写结束时间", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mCheckBox.isChecked() && TextUtils.isEmpty(standardDays)) {
                    Toast.makeText(AddClockActivity.this, "请填写打卡天数", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TimeUtils.parseYYMMDD(beginTimeStr) > TimeUtils.parseYYMMDD(endTimeStr)) {
                    Toast.makeText(AddClockActivity.this, "结束时间应大于开始时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mCheckBox.isChecked() && ((TimeUtils.parseYYMMDD(endTimeStr) - TimeUtils.parseYYMMDD(beginTimeStr)) / (1000 * 60 * 60 * 24) + 1) < Integer.parseInt(standardDays)) {
                    Toast.makeText(AddClockActivity.this, "打卡天数不能超过间隔时间", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (((TimeUtils.parseYYMMDD(endTimeStr) - TimeUtils.parseYYMMDD(beginTimeStr)) / (1000 * 60 * 60 * 24)) < 1) {
                    Toast.makeText(AddClockActivity.this, "开始时间和结束时间不能设置为同一天", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isEdit) {
                    mPresenter.editClockTask(mTask.getId(), taskName, memo, endTimeStr, mCheckBox.isChecked(), standardDays);
                } else {
                    mPresenter.createClockTask(classId, taskName, memo, beginTimeStr, endTimeStr, mCheckBox.isChecked(), standardDays);
                }
                break;
            case R.id.title_left:
                finish();
                break;
            case R.id.add_task_title:
                intent.putExtra("code", CODE_TITLE);
                intent.putExtra("name", mTaskName.getText().toString());
                startActivityForResult(intent, CODE_TITLE);
                break;
            case R.id.add_task_time_begin:
                ViewUtils.hideSoftKeyBoard(this);
                if (!isEdit) {
                    initTimePicker(startDate, Calendar.getInstance());
                    pvTime.show(view);
                } else {
                    Toast.makeText(AddClockActivity.this, "开始时间不可修改", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.add_task_time_end:
                if (isEdit) {
                    if ((Calendar.getInstance().getTimeInMillis() - TimeUtils.parseYYMMDD(mTask.getStartTime()) >= 1000 * 60 * 60 * 24)) {
                        initTimePicker(Calendar.getInstance(), Calendar.getInstance());
                    } else {
                        initTimePicker(TimeUtils.parseTimeNext(mTask.getStartTime()), TimeUtils.parseTime(mTask.getEndTime()));
                    }

                } else {
                    if (TextUtils.isEmpty(mBeginTime.getText())) {
                        endDate.add(Calendar.DAY_OF_MONTH, 1);
                        initTimePicker(endDate, Calendar.getInstance());
                    } else {
                        Calendar startTime = TimeUtils.parseTime(mBeginTime.getText().toString());
                        startTime.add(Calendar.DAY_OF_MONTH, 1);
                        initTimePicker(startTime, Calendar.getInstance());
                    }

                }
                ViewUtils.hideSoftKeyBoard(this);
                pvTime.show(view);
                break;

        }
    }

    private String classId;
    private Intent intent;
    private static final int CODE_TITLE = 1000;
    private static final int CODE_DAY = 1001;
    private String taskName;
    private String standardDays;
    private TimePickerView pvTime;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private boolean isEdit = false;
    private TaskItemBean.Item mTask;

    @Override
    public void showContent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_task_add;
    }

    @Override
    protected void init() {
        initIntent();
        initView();
        initOthers();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            isEdit = intent.getBooleanExtra("isEdit", false);
            classId = getIntent().getStringExtra("classId");
            mTask = (TaskItemBean.Item) getIntent().getSerializableExtra("task");
        }
    }

    private void initOthers() {

        intent = new Intent();
        intent.setClass(this, TextInputActivity.class);
    }

    private void initView() {
        if (isEdit) {
            mTitle.setText("修改任务");
            mSubmit.setText("完成");
            if (mTask != null) {
                mTaskName.setText(mTask.getName());
                mBeginTime.setText(TimeUtils.formatYYMMDD(mTask.getStartTime()));
                mEndTime.setText(TimeUtils.formatYYMMDD(mTask.getEndTime()));
                mCheckBox.setChecked(mTask.isEveryDay());
                mTaskContent.setText(mTask.getMemo());
                mTaskContent.setSelection(mTask.getMemo().length());
            }

        } else {
            mTitle.setText("发布任务");
            mCheckBox.setChecked(true);
            mSubmit.setText("发布");
        }
        if (mCheckBox.isChecked()) {
            mTaskDayLayout.setVisibility(View.GONE);
        } else {
            mTaskDayLayout.setVisibility(View.VISIBLE);
            mTaskDay.setText(String.valueOf(mTask.getStandardDays()));
            mTaskDay.setSelection(mTaskDay.getText().length());
        }

        mSubmit.setTextColor(getResources().getColor(R.color.green_color));


        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mTaskDayLayout.setVisibility(View.GONE);
                } else {
                    mTaskDayLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case CODE_DAY:
                mTaskDay.setText(data.getStringExtra("input"));
                break;
            case CODE_TITLE:
                mTaskName.setText(data.getStringExtra("input"));
                break;
        }
    }


    private void initTimePicker(Calendar startDate, Calendar selectedDate) {
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                TextView textView = (TextView) v;
                textView.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }


    //返回选择的时间，用于时间的回显
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void createTaskSuccess() {
        showContent("发布任务成功");
        Intent intent = new Intent();
        intent.putExtra("status", 1);
        setResult(2000, intent);
        finish();
    }

    @Override
    public void createTaskFailure() {
        showContent("发布任务失败");
        Intent intent = new Intent();
        intent.putExtra("status", 2);
        setResult(2000, intent);
        finish();
    }

    @Override
    public void editTaskSuccess() {
        showContent("修改任务成功");
        Intent intent = new Intent();
        intent.putExtra("status", 1);
        setResult(2001, intent);
        finish();
    }

    @Override
    public void editTaskFailure() {
        showContent("修改任务失败");
        Intent intent = new Intent();
        intent.putExtra("status", 2);
        setResult(2001, intent);
        finish();
    }
}
