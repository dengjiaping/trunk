package com.histudent.jwsoft.histudent.comment2.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.comment2.utils.EditGroupOrClassTypeEnum;
import com.netease.nim.uikit.common.util.string.StringUtil;


/**
 * 通用修改信息界面
 */
public class CommenActivity extends BaseActivity {

    private EditGroupOrClassTypeEnum typeEnum;
    private String content, content_after;
    private String instr;
    private EditText et_content;
    private Intent intent;
    private int limitCount;
    private TextView tv_limit;

    @Override
    public int setViewLayout() {
        return R.layout.activity_commen;
    }

    public static void start(Activity activity, String content, EditGroupOrClassTypeEnum typeEnum) {
        Intent intent = new Intent(activity, CommenActivity.class);
        intent.putExtra("Content", content);
        intent.putExtra("TypeEnum", typeEnum);
        activity.startActivityForResult(intent, 400);
    }

    @Override
    public void initView() {
        intent = getIntent();
        typeEnum = (EditGroupOrClassTypeEnum) intent.getSerializableExtra("TypeEnum");
        content = intent.getStringExtra("Content");
        tv_limit = ((TextView) findViewById(R.id.tv_limit));
        ((TextView) findViewById(R.id.title_right_text)).setText(R.string.completed);
        initCommentViews();
    }

    //根据枚举类型来初始化不同的界面
    private void initCommentViews() {
        instr = typeEnum == EditGroupOrClassTypeEnum.EditClassInstr ? "班级介绍" :
                (typeEnum == EditGroupOrClassTypeEnum.EditClassAlice ? "班级别名" :
                        (typeEnum == EditGroupOrClassTypeEnum.EditSchoolName ? "学校名称" :
                                (typeEnum == EditGroupOrClassTypeEnum.EditGroupInstr ? "社群介绍" : "社群名称")));


        limitCount = typeEnum == EditGroupOrClassTypeEnum.EditClassInstr ? 500 :
                (typeEnum == EditGroupOrClassTypeEnum.EditClassAlice ? 10 :
                        (typeEnum == EditGroupOrClassTypeEnum.EditSchoolName ? 25 :
                                (typeEnum == EditGroupOrClassTypeEnum.EditGroupInstr ? 500 : 20)));

        et_content = ((EditText) findViewById(R.id.et_content));
        ((TextView) findViewById(R.id.title_middle_text)).setText(instr);
        et_content.setHint("请输入" + instr);
        et_content.setText(content);
        et_content.setMaxEms(limitCount);
        tv_limit.setText("最多输入：" + limitCount + "个字");

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_content.getText().length() <= limitCount) {
                    tv_limit.setText("剩余：" + (limitCount - et_content.getText().length()) + " 个字");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                Editable editable = et_content.getText();
                int len = editable.length();
                if (len > limitCount) {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, limitCount);
                    et_content.setText(newStr);
                    editable = et_content.getText();

                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);

                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:

                doBackAction();

                break;

            //点击完成，返回否界面需要刷新
            case R.id.title_right:

                setResult();

                break;
        }
    }

    //页面返回时，根据内容是否别编辑过做出相应处理
    private void doBackAction() {
        content_after = et_content.getText().toString().trim();
        if (StringUtil.isEmpty(content_after) || StringUtil.isEmpty(content)) {

            if (!StringUtil.isEmpty(content) || !StringUtil.isEmpty(content_after)) {

                showGivingUpNotice();
            } else {
                setResult(-1);
                this.finish();
            }

        } else {

            if (!content.equals(content_after)) {
                showGivingUpNotice();
            } else {
                setResult(-1);
                this.finish();
            }
        }
    }

    private void showGivingUpNotice() {

        ReminderHelper.getIntentce().showDialog(CommenActivity.this, "提示", "是否放弃修改", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                //放弃修改，返回后页面不用刷新
                setResult(-1);
                CommenActivity.this.finish();
            }


        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });

    }

    private void setResult() {
        content_after = et_content.getText().toString().trim();
        intent.putExtra("typeEnum", typeEnum);
        intent.putExtra("content", content_after);
        this.setResult(200, intent);
        this.finish();
    }

}
