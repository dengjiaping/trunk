package com.histudent.jwsoft.histudent.body.mine.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.netease.nim.uikit.common.util.string.StringUtil;


/*

   自定义隐私权限页面
 */
public class PrivacySettingDefinedActivity extends BaseActivity implements View.OnClickListener{

//    private ImageView iv_back;
    private TextView tv_done;
    private EditText et_question,et_answer;
    private String question,answer;

    @Override
    public int setViewLayout() {
        return R.layout.activity_privacy_setting_defined;
    }

    @Override
    public void initView() {

//        iv_back= ((ImageView) findViewById(R.id.title_left_image));
        tv_done= ((TextView) findViewById(R.id.title_right_text));
        et_answer= ((EditText) findViewById(R.id.et_answer));
        et_question= ((EditText) findViewById(R.id.et_question));


        tv_done.setText("完成");

        ((TextView) findViewById(R.id.title_middle_text)).setText("隐私设置");
//        iv_back.setOnClickListener(this);
//        tv_done.setOnClickListener(this);

        question=getIntent().getStringExtra("question");
        answer=getIntent().getStringExtra("answer");

        if(!StringUtil.isEmpty(question)&&!StringUtil.isEmpty(answer)){

            et_answer.setText(answer);
            et_question.setText(question);
        }


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()){

            //返回
            case R.id.title_left:
                this.finish();
                break;


            //完成
            case R.id.title_right:

                question=et_question.getText().toString().trim();
                answer=et_answer.getText().toString().trim();

                if(StringUtil.isEmpty(question)||StringUtil.isEmpty(answer)){
                    Toast.makeText(this,"请填写问题和答案",Toast.LENGTH_LONG).show();
                }else {

                    //返回填写的问题和答案
                    getIntent().putExtra("question",question);
                    getIntent().putExtra("answer",answer);
                    setResult(100,getIntent());
                    this.finish();

                }

                break;
        }
    }

}
