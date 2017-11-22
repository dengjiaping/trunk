package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.ClassInfoBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.comment2.adapter.CommentSelectAdapter;
import com.histudent.jwsoft.histudent.comment2.bean.GradeBean;
import com.histudent.jwsoft.histudent.comment2.bean.SystemProid;
import com.histudent.jwsoft.histudent.model.constant.ParamKeys;
import com.histudent.jwsoft.histudent.model.constant.TransferKeys;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 班级编辑
 */

public class ClassEditActivity extends BaseActivity {
    private TextView title, tv_instr;
    private ListView mListView;
    private CommentSelectAdapter mAdapter;
    private List<Object> mList;
    private int actionType;
    private ClassInfoBean bean;
    private boolean mIsJoinClass;

    public static void start(Activity activity, int actionType, ClassInfoBean bean) {
        Intent intent = new Intent(activity, ClassEditActivity.class);
        intent.putExtra("actionType", actionType);
        intent.putExtra("bean", bean);
        activity.startActivityForResult(intent, 200);
    }

    public static void start(Activity activity, int actionType, ClassInfoBean bean,boolean isJoinClass) {
        Intent intent = new Intent(activity, ClassEditActivity.class);
        intent.putExtra("actionType", actionType);
        intent.putExtra("bean", bean);
        intent.putExtra(TransferKeys.JOIN_CLASS,isJoinClass);
        activity.startActivityForResult(intent, 200);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_edit;
    }

    @Override
    public void initView() {

        actionType = getIntent().getIntExtra("actionType", 0);
        bean = (ClassInfoBean) getIntent().getSerializableExtra("bean");
        mIsJoinClass = getIntent().getBooleanExtra(TransferKeys.JOIN_CLASS,false);
        mList = new ArrayList<>();
        mAdapter = new CommentSelectAdapter(mList, this);
        title = (TextView) findViewById(R.id.title_middle_text);
        tv_instr = (TextView) findViewById(R.id.tv_instr);
        mListView = (ListView) findViewById(R.id.list_view);

        switch (actionType) {
            case ClassInfoBean.GETLEVE:
                title.setText("选择学段");
                break;
            case ClassInfoBean.GETGRADE:
                title.setText("选择年级");
                break;
            case ClassInfoBean.GETCLASS:
                title.setText("选择班级");
                break;
        }

    }

    @Override
    public void doAction() {
        super.doAction();
        mListView.setAdapter(mAdapter);

        switch (actionType) {
            case ClassInfoBean.GETLEVE:
                tv_instr.setText("学段");
                getSystenList(bean.getSchoolId());
                break;

            case ClassInfoBean.GETGRADE:
                tv_instr.setText("年级");
                if(mIsJoinClass){
                    requestJoinSpecGradeList();
                }else{
                    getGrades();
                }
                break;

            case ClassInfoBean.GETCLASS:
                tv_instr.setText("班级");
                if(mIsJoinClass){
                    requestJoinSpecClassList();
                }else{
                    getClasses();
                }
                break;
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Object object = mList.get(i);
                Intent intent = new Intent();
                switch (actionType) {
                    case ClassInfoBean.GETLEVE:
                        if (!TextUtils.isEmpty(bean.getEduSystemName()) && !bean.getEduSystemName().equals(((SystemProid) object).getEduName())) {
                            bean.setGradeNmae("");
                            bean.setClassNmae("");
                            bean.setGradeId("");
                        }
                        bean.setEduSystemId(((SystemProid) object).getEduId() + "");
                        bean.setEduSystemName(((SystemProid) object).getEduName());
                        intent.putExtra("bean", bean);
                        setResult(200, intent);
                        finish();
                        break;

                    case ClassInfoBean.GETGRADE:
                        if (!TextUtils.isEmpty(bean.getGradeNmae()) && !bean.getGradeNmae().equals(((GradeBean) object).getName())) {
                            bean.setClassNmae("");
                        }
                        bean.setGradeNmae(((GradeBean) object).getName());
                        bean.setGradeId(((GradeBean) object).getValue());
                        intent.putExtra("bean", bean);
                        setResult(200, intent);
                        finish();
                        break;

                    case ClassInfoBean.GETCLASS:
                        bean.setClassNmae(((GradeBean) object).getName());
                        intent.putExtra("bean", bean);
                        setResult(200, intent);
                        finish();
                        break;
                }
            }
        });

    }



    /**
     * 加入班级page
     * 获取指定年级列表
     */
    private void requestJoinSpecGradeList() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ParamKeys.ORG_ID,bean.getSchoolId());
        hashMap.put(ParamKeys.EDU_SYSTEM_ID,bean.getEduSystemId());
        HiStudentHttpUtils.postDataByOKHttp(ClassEditActivity.this,
                hashMap, HistudentUrl.getSpecGradeList,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        mList.clear();
                        List<GradeBean> gradeBean = JSON.parseArray(result, GradeBean.class);
                        mList.addAll(gradeBean);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
    }

    /**
     * 加入班级page
     * 获取指定年级的班级列表
     */
    private void requestJoinSpecClassList() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ParamKeys.SCHOOL_ID,bean.getSchoolId());
        hashMap.put(ParamKeys.GRADE_ID,bean.getGradeId());
        HiStudentHttpUtils.postDataByOKHttp(this,
                hashMap, HistudentUrl.getSpecClassList,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        mList.clear();
                        List<GradeBean> gradeBean = JSON.parseArray(result, GradeBean.class);
                        mList.addAll(gradeBean);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left://取消
                finish();
                break;
        }
    }

    //获取学段
    private void getSystenList(String orgId) {
        Map<String, Object> map_class = new TreeMap<>();
        map_class.put("orgId", orgId);
        HiStudentHttpUtils.postDataByOKHttp(ClassEditActivity.this,
                map_class, HistudentUrl.getSchoolSystemList,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        mList.clear();
                        List<SystemProid> proid = JSON.parseArray(result, SystemProid.class);
                        mList.addAll(proid);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
    }

    //获取年级
    private void getGrades() {
        HiStudentHttpUtils.postDataByOKHttp(ClassEditActivity.this,
                null, HistudentUrl.gradeList_url,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        mList.clear();
                        List<GradeBean> gradeBean = JSON.parseArray(result, GradeBean.class);
                        mList.addAll(gradeBean);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });

    }

    //获取年级下的班级
    public void getClasses() {
        HiStudentHttpUtils.postDataByOKHttp(this,
                null, HistudentUrl.classList_url,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        mList.clear();
                        List<GradeBean> gradeBean = JSON.parseArray(result, GradeBean.class);
                        mList.addAll(gradeBean);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });


    }

    // 判断班级是否存在
    private void judgeClassExist(final ClassInfoBean classInfoBean) {
        if (classInfoBean == null) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("orgId", classInfoBean.getSchoolId());
        map.put("eduId", classInfoBean.getEduSystemId());
        map.put("gradeName", classInfoBean.getGradeNmae());
        map.put("className", classInfoBean.getClassNmae());
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.classIsExsit, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    org.json.JSONObject object = new org.json.JSONObject(result);
                    int status = object.optInt("status");

                    if (status == 1) {

                        ReminderHelper.getIntentce().ToastShow_with_image(ClassEditActivity.this, "该班级已被创建，请确认", R.string.icon_tip);

                    } else if (status == -13) {
                        Intent intent = new Intent();
                        intent.putExtra("bean", classInfoBean);
                        setResult(200, intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    //班级存在时，直接加入班级
    public void joinClasses(String classId) {
        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp(this,
                map, HistudentUrl.classTransferByCreateing_url,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        setResult(300);
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });


    }


}
