package com.histudent.jwsoft.histudent.body.myclass.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCreateActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassEditActivity;
import com.histudent.jwsoft.histudent.body.myclass.bean.ClassInfoBean;
import com.histudent.jwsoft.histudent.body.myclass.bean.CreatClassSuccedBean;
import com.histudent.jwsoft.histudent.body.myclass.dialog.CommonDialog;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.comment2.activity.CommenSelectActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.EditGroupOrClassTypeEnum;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/12/5.
 */

public class CreateClassFragmentTheSecond extends BaseFragment implements View.OnClickListener {

    private View view;
    private RelativeLayout createclass_schoool_layout, createclass_level_layout, createclass_grade_layout, createclass_class_layout;
    private TextView createclass_schoool, createclass_level, createclass_grade, createclass_class, compelet;
    public final int SCHOOL = 10;
    private ClassInfoBean classInfoBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thesecondecreateclass, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();

        classInfoBean = new ClassInfoBean();

        createclass_schoool_layout = (RelativeLayout) view.findViewById(R.id.createclass_schoool_layout);
        createclass_level_layout = (RelativeLayout) view.findViewById(R.id.createclass_level_layout);
        createclass_grade_layout = (RelativeLayout) view.findViewById(R.id.createclass_grade_layout);
        createclass_class_layout = (RelativeLayout) view.findViewById(R.id.createclass_class_layout);

        createclass_schoool = (TextView) view.findViewById(R.id.createclass_schoool);
        createclass_level = (TextView) view.findViewById(R.id.createclass_level);
        createclass_grade = (TextView) view.findViewById(R.id.createclass_grade);
        createclass_class = (TextView) view.findViewById(R.id.createclass_class);
        compelet = (TextView) view.findViewById(R.id.compelet);

        createclass_schoool_layout.setOnClickListener(this);
        createclass_level_layout.setOnClickListener(this);
        createclass_grade_layout.setOnClickListener(this);
        createclass_class_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AddressInforBean inforBean = HiWorldCache.getUserLocationInfor();
        switch (view.getId()) {
            case R.id.createclass_schoool_layout://学校

                CommenSelectActivity.start(getActivity(), EditGroupOrClassTypeEnum.EditSchoolName, null, null, null, null, null, inforBean.getCity(), inforBean.getAreaStr(), SCHOOL);

                break;
            case R.id.createclass_level_layout://学段

                if (TextUtils.isEmpty(classInfoBean.getSchoolNmae())) {
                    Toast.makeText(getActivity(), "请先选择学校", Toast.LENGTH_SHORT).show();
                    return;
                }

                ClassEditActivity.start(getActivity(), ClassInfoBean.GETLEVE, classInfoBean);

                break;
            case R.id.createclass_grade_layout://年级

                if (TextUtils.isEmpty(classInfoBean.getSchoolNmae())) {
                    Toast.makeText(getActivity(), "请先选择学校", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getEduSystemName())) {
                    Toast.makeText(getActivity(), "请先选择学段", Toast.LENGTH_SHORT).show();
                    return;
                }

                ClassEditActivity.start(getActivity(), ClassInfoBean.GETGRADE, classInfoBean);

                break;
            case R.id.createclass_class_layout://班级

                if (TextUtils.isEmpty(classInfoBean.getSchoolNmae())) {
                    Toast.makeText(getActivity(), "请先选择学校", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getEduSystemName())) {
                    Toast.makeText(getActivity(), "请先选择学段", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getGradeNmae())) {
                    Toast.makeText(getActivity(), "请先选择年级", Toast.LENGTH_SHORT).show();
                    return;
                }

                ClassEditActivity.start(getActivity(), ClassInfoBean.GETCLASS, classInfoBean);

                break;

            case R.id.compelet://完成

                if (TextUtils.isEmpty(classInfoBean.getSchoolNmae())) {
                    Toast.makeText(getActivity(), "请先选择学校", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getEduSystemName())) {
                    Toast.makeText(getActivity(), "请先选择学段", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getGradeNmae())) {
                    Toast.makeText(getActivity(), "请先选择年级", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(classInfoBean.getClassNmae())) {
                    Toast.makeText(getActivity(), "请先选择班次", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    judgeClassExist(classInfoBean);
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
            updataUI(classInfoBean);
        } else if (requestCode == 200 && resultCode == 200) {
            classInfoBean = (ClassInfoBean) data.getSerializableExtra("bean");
            updataUI(classInfoBean);
        }

    }

    private void updataUI(ClassInfoBean classInfoBean) {

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
    private void judgeClassExist(final ClassInfoBean classInfoBean) {
        if (classInfoBean == null) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("orgId", classInfoBean.getSchoolId());
        map.put("eduId", classInfoBean.getEduSystemId());
        map.put("gradeName", classInfoBean.getGradeNmae());
        map.put("className", classInfoBean.getClassNmae());
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.classIsExsit, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    org.json.JSONObject object = new org.json.JSONObject(result);
                    int status = object.optInt("status");
                    String classId = object.getString("cId");
                    if (status == 1) {
                        showHintMsgDialog(classId);
//                        ReminderHelper.getIntentce().ToastShow_with_image(getContext(), "该班级已被创建，请确认", R.string.icon_tip);
                    } else if (status == -13) {

                        createClass(classInfoBean);
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

    private void showHintMsgDialog(String classId) {
        getActivity().runOnUiThread(() -> {
            CommonDialog commonDialog = new CommonDialog(getActivity());
            commonDialog.setTitle("提示");
            commonDialog.setDesMsg("班级已存在,是否加入班级");
            commonDialog.setOnNegativeClickListener("取消", () -> {
                commonDialog.dismiss();
                return;
            });
            commonDialog.setOnPositiveClickListener("加入班级", () -> {
                joinSpecClass(classId);
                commonDialog.dismiss();
            });
            commonDialog.show();
        });
    }

    /**
     * 创建班级
     */
    private void createClass(ClassInfoBean classInfoBean) {
        Map<String, Object> map = new TreeMap<>();

        boolean flag = !TextUtils.isEmpty(ClassCreateActivity.logoPath);

        map.put("orgId", classInfoBean.getSchoolId());
        map.put("orgName", classInfoBean.getSchoolNmae());
        map.put("gradeName", classInfoBean.getGradeNmae());
        map.put("className", classInfoBean.getClassNmae());
        map.put("isLogo", flag);
        map.put("eduSystem", classInfoBean.getEduSystemId());

        List<String> list_path = new ArrayList<>();
        if (flag)
            list_path.add(ClassCreateActivity.logoPath);

        HiStudentHttpUtils.postImageByOKHttp((BaseActivity) getActivity(), list_path, map, HistudentUrl.classCreate_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                CreatClassSuccedBean bean = JSON.parseObject(result, CreatClassSuccedBean.class);
                EventBus.getDefault().post(bean);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }


    /**
     * 根据用户所搜索的指定班级 加入
     *
     * @param classId
     */
    private void joinSpecClass(String classId) {
        Map<String, Object> map = new HashMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.joinSpecClass, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                ToastTool.showCommonToast(getActivity(), "加入班级成功！");
                //跳转班级页面 刷新数据
                CreateClassFragmentTheSecond.this.getActivity().setResult(200);
                CreateClassFragmentTheSecond.this.getActivity().finish();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

}
