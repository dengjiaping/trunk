package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gzsll.jsbridge.WVJBWebView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.ClassInfoBean;
import com.histudent.jwsoft.histudent.body.myclass.dialog.CommonDialog;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.comment2.activity.CommenSelectActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.EditGroupOrClassTypeEnum;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lichaojie on 2017/6/19.
 * 加入自定义的班级 page
 */

public class JoinSpecClassActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout createclass_schoool_layout, createclass_level_layout, createclass_grade_layout, createclass_class_layout;
    private TextView createclass_schoool, createclass_level, createclass_grade, createclass_class, compelet;
    public final int SCHOOL = 10;
    private ClassInfoBean classInfoBean;
    public static WVJBWebView.WVJBResponseCallback mCallBack;
    public static void start(Activity activity, WVJBWebView.WVJBResponseCallback callback){
        Intent intent = new Intent(activity, JoinSpecClassActivity.class);
        mCallBack = callback;
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_join_class;
    }


    @Override
    public void initView() {
        createclass_schoool_layout = (RelativeLayout) findViewById(R.id.createclass_schoool_layout);
        createclass_level_layout = (RelativeLayout) findViewById(R.id.createclass_level_layout);
        createclass_grade_layout = (RelativeLayout) findViewById(R.id.createclass_grade_layout);
        createclass_class_layout = (RelativeLayout) findViewById(R.id.createclass_class_layout);
        createclass_schoool = (TextView) findViewById(R.id.createclass_schoool);
        createclass_level = (TextView) findViewById(R.id.createclass_level);
        createclass_grade = (TextView) findViewById(R.id.createclass_grade);
        createclass_class = (TextView) findViewById(R.id.createclass_class);
        ((TextView) findViewById(R.id.title_middle_text)).setText(R.string.join_class);
        compelet = (TextView) findViewById(R.id.compelet);
        createclass_schoool_layout.setOnClickListener(this);
        createclass_level_layout.setOnClickListener(this);
        createclass_grade_layout.setOnClickListener(this);
        createclass_class_layout.setOnClickListener(this);
        findViewById(R.id.title_left).setOnClickListener(this);
    }

    @Override
    public void doAction() {
        super.doAction();
        classInfoBean = new ClassInfoBean();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.createclass_schoool_layout://学校
                AddressInforBean inforBean = HiWorldCache.getUserLocationInfor();
                CommenSelectActivity.start(this, EditGroupOrClassTypeEnum.EditSchoolName, null, null, null, null, null, inforBean.getCity(), inforBean.getAreaStr(), SCHOOL);

                break;
            case R.id.createclass_level_layout://学段

                if (TextUtils.isEmpty(classInfoBean.getSchoolNmae())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择学校");
                    return;
                }

                ClassEditActivity.start(this, ClassInfoBean.GETLEVE, classInfoBean);

                break;
            case R.id.createclass_grade_layout://年级

                if (TextUtils.isEmpty(classInfoBean.getSchoolNmae())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择学校");
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getEduSystemName())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择学校");
                    return;
                }

                ClassEditActivity.start(this, ClassInfoBean.GETGRADE, classInfoBean,true);

                break;
            case R.id.createclass_class_layout://班级

                if (TextUtils.isEmpty(classInfoBean.getSchoolNmae())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择学校");
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getEduSystemName())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择学校");
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getGradeNmae())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择年级");
                    return;
                }

                ClassEditActivity.start(this, ClassInfoBean.GETCLASS, classInfoBean,true);

                break;

            case R.id.compelet://完成

                if (TextUtils.isEmpty(classInfoBean.getSchoolNmae())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择学校");
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getEduSystemName())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择学段");
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getGradeNmae())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择年级");
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getClassNmae())) {
                    ToastTool.showCommonToast(getApplicationContext(), "请先选择班次");
                    return;
                } else {
                    judgeClassExist();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCHOOL && resultCode == 200) {
            classInfoBean = new ClassInfoBean();
            classInfoBean.setSchoolId(data.getStringExtra("schoolValue"));
            classInfoBean.setSchoolNmae(data.getStringExtra("schoolName"));
            updateUI();
        } else if (requestCode == 200 && resultCode == 200) {
            classInfoBean = (ClassInfoBean) data.getSerializableExtra("bean");
            updateUI();
        }

    }

    private void updateUI() {

        createclass_schoool.setText(TextUtils.isEmpty(classInfoBean.getSchoolNmae()) ? "" : classInfoBean.getSchoolNmae());

        createclass_level.setText(TextUtils.isEmpty(classInfoBean.getEduSystemName()) ? "" : classInfoBean.getEduSystemName());

        createclass_grade.setText(TextUtils.isEmpty(classInfoBean.getGradeNmae()) ? "" : classInfoBean.getGradeNmae());

        if (TextUtils.isEmpty(classInfoBean.getClassNmae())) {
            createclass_class.setText("");
            compelet.setOnClickListener(null);
            compelet.setBackgroundResource(R.drawable.gray_button_bg);
        } else {
            createclass_class.setText(classInfoBean.getClassNmae());
            compelet.setOnClickListener(this);
            compelet.setBackgroundResource(R.drawable.green_button_bg);
        }
    }

    /**
     * 判断班级是否存在
     */
    private void judgeClassExist() {
        if (classInfoBean == null) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("orgId", classInfoBean.getSchoolId());
        map.put("eduId", classInfoBean.getEduSystemId());
        map.put("gradeName", classInfoBean.getGradeNmae());
        map.put("className", classInfoBean.getClassNmae());
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.classIsExsit, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                //判断班级是否存在(返回-13时代表该班级没有被创建，返回1以及班级Id时代表班级已创建)
                try {
                    org.json.JSONObject object = new org.json.JSONObject(result);
                    int status = object.optInt("status");
                    String classId = object.getString("cId");
                    if (status == 1) {
                        showHintMsgDialog(classId);
                    } else {
                        //班级不存在 不可以加入班级
                        ToastTool.showCommonToast(getApplicationContext(), "该班级不存在,请重新选择班级！");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastTool.showCommonToast(getApplicationContext(), "数据异常 请稍后再试");
            }
        });
    }


    private void showHintMsgDialog(String classId){
        runOnUiThread(()->{
            CommonDialog commonDialog = new CommonDialog(JoinSpecClassActivity.this);
            commonDialog.setTitle("提示");
            commonDialog.setDesMsg("班级已存在,是否加入班级");
            commonDialog.setOnNegativeClickListener("取消",()->{
                commonDialog.dismiss();
                return;});
            commonDialog.setOnPositiveClickListener("加入班级",()-> {
                joinSpecClass(classId);
                commonDialog.dismiss();
            });
            commonDialog.show();
        });
    }

    /**
     * 根据用户所搜索的指定班级 加入
     * @param classId
     */
    private void joinSpecClass(String classId) {
        Map<String, Object> map = new HashMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.joinSpecCreateClass, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                //班级存在 加入班级 -->通知H5-page
                ToastTool.showCommonToast(getApplicationContext(),"申请加入班级成功！");
                mCallBack.callback("1");
                finish();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

}
