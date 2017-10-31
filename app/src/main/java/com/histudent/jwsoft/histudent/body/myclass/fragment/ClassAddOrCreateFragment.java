package com.histudent.jwsoft.histudent.body.myclass.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCreateActivity;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassJoinActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.constant.TransferKeys;

/**
 * 没有班级时，加入班级或者创建班级的页面
 */

public class ClassAddOrCreateFragment extends BaseFragment {
    private View view;
    private LinearLayout student_layout, teacher_layout;
    private View teacher_create_class, teacher_add_class, student_add_class;
    private int CREATE = 100;
    private int JOIN = 200;
    private boolean isJoinClassGone;
    private TextView mStudentAddNoneClass;
    private TextView mTeacherAddNoneClass;
    private TextView mTeacherNormalCreateClass;
    private TextView mTeacherNormalAddClass;
    private LinearLayout mLLTeacherLayoutNormal;
    private TextView mClassTitle;
    private View mClassLine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_add_create_class, container, false);
        return view;
    }

    /**
     * @param isGone 显示加入班级时 是否显示没有加入任何班级
     * @return
     */
    public static ClassAddOrCreateFragment getInstance(boolean isGone) {
        ClassAddOrCreateFragment fragment = new ClassAddOrCreateFragment();
        Bundle arg = new Bundle();
        arg.putBoolean(TransferKeys.IS_GONE, isGone);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        if (getArguments() != null)
            isJoinClassGone = getArguments().getBoolean(TransferKeys.IS_GONE);
        student_layout = view.findViewById(R.id.student_layout);
        teacher_layout = view.findViewById(R.id.teacher_layout);
        teacher_create_class = view.findViewById(R.id.teacher_create_class);
        teacher_add_class = view.findViewById(R.id.teacher_add_class);
        student_add_class = view.findViewById(R.id.student_add_class);
        mStudentAddNoneClass = view.findViewById(R.id.tv_student_add_any_none_class);
        mTeacherAddNoneClass = view.findViewById(R.id.tv_teacher_add_any_none_class);

        mTeacherNormalCreateClass = view.findViewById(R.id.tv_teacher_create_class);
        mTeacherNormalAddClass = view.findViewById(R.id.tv_teacher_add_class);
        mLLTeacherLayoutNormal = view.findViewById(R.id.ll_teacher_layout_normal);
        mClassTitle = view.findViewById(R.id.tv_class_title);
        mClassLine = view.findViewById(R.id.view_class_line);


//        if (isJoinClassGone) {
//            mStudentAddNoneClass.setVisibility(View.GONE);
//            mTeacherAddNoneClass.setVisibility(View.GONE);
//        } else {
//            mStudentAddNoneClass.setVisibility(View.VISIBLE);
//            mTeacherAddNoneClass.setVisibility(View.VISIBLE);
//        }

        if (HiCache.getInstance().getLoginUserInfo().getUserType() == 3) {
            //老师
            if (isJoinClassGone) {
                mLLTeacherLayoutNormal.setVisibility(View.GONE);
                mClassTitle.setVisibility(View.VISIBLE);
                mClassLine.setVisibility(View.VISIBLE);
                teacher_layout.setVisibility(View.VISIBLE);
            } else {
                mLLTeacherLayoutNormal.setVisibility(View.VISIBLE);
                mClassTitle.setVisibility(View.GONE);
                mClassLine.setVisibility(View.GONE);
                teacher_layout.setVisibility(View.GONE);
            }
            student_layout.setVisibility(View.GONE);
        } else {
            //学生
            teacher_layout.setVisibility(View.GONE);
            mLLTeacherLayoutNormal.setVisibility(View.GONE);
            mClassTitle.setVisibility(View.VISIBLE);
            mClassLine.setVisibility(View.VISIBLE);
            student_layout.setVisibility(View.VISIBLE);
        }

        teacher_create_class.setOnClickListener((View view) -> ClassCreateActivity.start(getActivity(), CREATE));
        teacher_add_class.setOnClickListener((View view) -> ClassJoinActivity.start(getActivity(), JOIN));
        student_add_class.setOnClickListener((View view) -> ClassJoinActivity.start(getActivity(), JOIN));

        mTeacherNormalCreateClass.setOnClickListener((View view) -> ClassCreateActivity.start(getActivity(), CREATE));
        mTeacherNormalAddClass.setOnClickListener((View view) -> ClassJoinActivity.start(getActivity(), JOIN));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 200) {
            getActivity().setResult(200);
        }
    }
}
