package com.histudent.jwsoft.histudent.account.regist.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.account.regist.activity.NewPersonActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.InputActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.comment2.activity.CommenSelectActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.EditGroupOrClassTypeEnum;

import static com.histudent.jwsoft.histudent.account.regist.activity.NewPersonActivity.selectModel;

/**
 * Created by liuguiyu-pc on 2016/12/5.
 */

public class NewPersonFragmentTheSecond extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView add_class;
    private CurrentUserInfoModel model;
    private RelativeLayout camera_layout, alias_layout, introduce_layout, class_layout, studyLevel_layout, grade_layout, classes_layout;
    private TextView class_name, studyLevel_name, grade_name, classes_name, alias_name;
    private String schoolId, schoolName, eduSystemId, eduSystemName, gradeName, gradeId, className, classId;
    private HiStudentHeadImageView headImageView;
    private int userType;//账号类型
    public final int SCHOOL = 10;
    private final int LEVEL = 20;
    private final int GRADE = 30;
    private final int CLASS = 40;
    private final int CLASS_ALIA = 50;
    private final int CLASS_INFO = 60;
    private String classInfo = "";
    private PictureTailorHelper clippHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thesecondenewperson, container, false);
        return view;
    }

    @Override
    public void initView() {

        clippHelper = PictureTailorHelper.getInstance();
        model = HiCache.getInstance().getLoginUserInfo();

        userType = model.getUserType();

        if (selectModel != null)
            NewPersonActivity.selectModel.setUserType(userType);

        headImageView = (HiStudentHeadImageView) view.findViewById(R.id.camera);
        camera_layout = (RelativeLayout) view.findViewById(R.id.camera_layout);
        alias_layout = (RelativeLayout) view.findViewById(R.id.alias_layout);
        introduce_layout = (RelativeLayout) view.findViewById(R.id.introduce_layout);
        class_layout = (RelativeLayout) view.findViewById(R.id.class_layout);
        studyLevel_layout = (RelativeLayout) view.findViewById(R.id.studyLevel_layout);
        grade_layout = (RelativeLayout) view.findViewById(R.id.grade_layout);
        classes_layout = (RelativeLayout) view.findViewById(R.id.classes_layout);

        class_name = (TextView) view.findViewById(R.id.class_name);
        alias_name = (TextView) view.findViewById(R.id.alias_name);
        studyLevel_name = (TextView) view.findViewById(R.id.studyLevel_name);
        grade_name = (TextView) view.findViewById(R.id.grade_name);
        classes_name = (TextView) view.findViewById(R.id.classes_name);

        add_class = (TextView) view.findViewById(R.id.add_class);
        add_class.setText(HiCache.getInstance().getLoginUserInfo().getUserType() == 3 ? "创建班级" : "加入班级");

        if (userType == 1) {
            camera_layout.setVisibility(View.GONE);
            alias_layout.setVisibility(View.GONE);
            introduce_layout.setVisibility(View.GONE);
        }

        camera_layout.setOnClickListener(this);
        alias_layout.setOnClickListener(this);
        introduce_layout.setOnClickListener(this);
        class_layout.setOnClickListener(this);
        studyLevel_layout.setOnClickListener(this);
        grade_layout.setOnClickListener(this);
        classes_layout.setOnClickListener(this);

        AddressInforBean inforBean = HiWorldCache.getUserLocationInfor();
        if (inforBean != null && selectModel != null) {
            selectModel.setCityName(inforBean.getCity());
            selectModel.setAreaName(inforBean.getAreaStr());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_layout://头像
                getActivity().sendBroadcast(new Intent(TheReceiverAction.GETHEADIMAGE));
                break;
            case R.id.class_layout://学校
                CommenSelectActivity.start(getActivity(), EditGroupOrClassTypeEnum.AddSchool, selectModel, SCHOOL);
                break;
            case R.id.studyLevel_layout://学段
                if (TextUtils.isEmpty(selectModel.getSchoolName())) {
                    Toast.makeText(getActivity(), "请先选择学校", Toast.LENGTH_SHORT).show();
                } else {
                    CommenSelectActivity.start(getActivity(), EditGroupOrClassTypeEnum.AddPriod, selectModel, LEVEL);
                }
                break;
            case R.id.grade_layout://年级
                if (TextUtils.isEmpty(selectModel.getSchoolName())) {
                    Toast.makeText(getActivity(), "请先选择学校", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(selectModel.getEduSystemId())) {
                    Toast.makeText(getActivity(), "请先选择学段", Toast.LENGTH_SHORT).show();
                } else {
                    CommenSelectActivity.start(getActivity(), userType != 3 ? EditGroupOrClassTypeEnum.AddGrade : EditGroupOrClassTypeEnum.EditGrade, selectModel, GRADE);
                }
                break;
            case R.id.classes_layout://班次

                if (TextUtils.isEmpty(selectModel.getSchoolName())) {
                    Toast.makeText(getActivity(), "请先选择学校", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(selectModel.getEduSystemId())) {
                    Toast.makeText(getActivity(), "请先选择学段", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(selectModel.getGradeId())) {
                    Toast.makeText(getActivity(), "请先选择年级", Toast.LENGTH_SHORT).show();
                } else {
                    CommenSelectActivity.start(getActivity(), userType != 3 ? EditGroupOrClassTypeEnum.AddClass : EditGroupOrClassTypeEnum.EditClasses, selectModel, CLASS, userType == 1);
                }
                break;
            case R.id.alias_layout://班级别名

                String info = alias_name.getText().toString();
                InputActivity.startOnResult(getActivity(), "班级别名", "classAlias", (info.contains("10字以内")) ? "" : info, CLASS_ALIA);

                break;
            case R.id.introduce_layout://班级介绍

                InputActivity.startOnResult(getActivity(), "班级介绍", "classInfo", classInfo, CLASS_INFO);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCHOOL && resultCode == 200) {

            schoolId = data.getStringExtra("schoolValue");
            schoolName = data.getStringExtra("schoolName");

            class_name.setText(schoolName);

            selectModel.setSchoolId(schoolId);
            selectModel.setSchoolName(schoolName);

        } else if (requestCode == LEVEL && resultCode == 200) {

            eduSystemId = data.getStringExtra("eduId");
            eduSystemName = data.getStringExtra("eduName");

            studyLevel_name.setText(eduSystemName);

            selectModel.setEduSystemId(eduSystemId);
            selectModel.setEduSystemName(eduSystemName);

        } else if (requestCode == GRADE && resultCode == 200) {

            gradeId = data.getStringExtra("gradeId");
            gradeName = data.getStringExtra("grade");

            grade_name.setText(gradeName);

            selectModel.setGradeId(gradeId);
            selectModel.setGradeName(gradeName);

        } else if (requestCode == CLASS && resultCode == 200) {

            classId = data.getStringExtra("classId");
            className = data.getStringExtra("myclass");

            classes_name.setText(className);

            selectModel.setClassId(classId);
            selectModel.setClassName(className);

        } else if (requestCode == PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO && resultCode == -1) {

            clippHelper.startPhotoZoom((BaseActivity) getActivity(), Uri.fromFile(clippHelper.photo_path), 150);

        } else if (requestCode == PictureTailorHelper.PHOTO_REQUEST_GALLERY && resultCode == -1) {

            if (data != null)
                clippHelper.startPhotoZoom((BaseActivity) getActivity(), data.getData(), 150);

        } else if (requestCode == PictureTailorHelper.PHOTO_REQUEST_CUT && resultCode == -1) {

            if (data != null)
                setPicToView(data);

        } else if (requestCode == CLASS_ALIA && resultCode == 20) {

            selectModel.setNoteName(data.getStringExtra("info"));
            alias_name.setText(data.getStringExtra("info"));

        } else if (requestCode == CLASS_INFO && resultCode == 20) {

            classInfo = data.getStringExtra("info");
            selectModel.setClassDescription(classInfo);

        }
    }

    /**
     * 上传头像并展示于控件
     */
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            String url = clippHelper.setPicToView(picdata);
            MyImageLoader.getIntent().displayNetImage(getContext(), url, headImageView);
            selectModel.setLogo(url);
        }
    }

}
