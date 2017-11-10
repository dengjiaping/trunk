package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupDetailsBean;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.comment2.bean.ClassSelectModel;
import com.histudent.jwsoft.histudent.comment2.bean.GradeBean;
import com.histudent.jwsoft.histudent.comment2.bean.SchoolBean;
import com.histudent.jwsoft.histudent.comment2.bean.SystemProid;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑班级或者社群的界面的帮助类
 * Created by ZhangYT on 2016/11/30.
 */


//根据类型初始化界面
public class EditGrouoOrClassViewUitils {

    public static void initGroupOrClassEditViews(View parenView, int type) {

        String groupInstr[] = {"社群名称", "社群分类", "权限类型", "社群位置", "社群介绍"};
        String classInstr[] = {"学校", "学段", "年级", "班级", "班级介绍", "别名", "加入开关"};
        List<TextView> instrViews = new ArrayList<>();
        instrViews.add(((TextView) parenView.findViewById(R.id.tv_01_instr)));
        instrViews.add(((TextView) parenView.findViewById(R.id.tv_02_instr)));
        instrViews.add(((TextView) parenView.findViewById(R.id.tv_03_instr)));
        instrViews.add(((TextView) parenView.findViewById(R.id.tv_04_instr)));
        instrViews.add(((TextView) parenView.findViewById(R.id.tv_06_instr)));


        String str[];
        if (type == 1) {
            str = classInstr;
            instrViews.add(((TextView) parenView.findViewById(R.id.tv_05_instr)));
            instrViews.add(((TextView) parenView.findViewById(R.id.tv_07_instr)));
        } else {
            parenView.findViewById(R.id.relative_07).setVisibility(View.GONE);
            parenView.findViewById(R.id.relative_05).setVisibility(View.GONE);
            str = groupInstr;
        }

        for (int i = 0; i < str.length; i++) {
            instrViews.get(i).setText(str[i]);
        }
    }

    public static void upDateClassOrGroupUi(View parentView, Object model) {

        String logUrl = "";
        List<TextView> contentViews = new ArrayList<>();
        contentViews.add(((TextView) parentView.findViewById(R.id.tv_01_content)));
        contentViews.add(((TextView) parentView.findViewById(R.id.tv_02_content)));
        contentViews.add(((TextView) parentView.findViewById(R.id.tv_03_content)));
        contentViews.add(((TextView) parentView.findViewById(R.id.tv_04_content)));
        contentViews.add(((TextView) parentView.findViewById(R.id.tv_05_content)));

        String content[];
        if (model instanceof GroupDetailsBean) {
            GroupDetailsBean b = (GroupDetailsBean) model;
            content = new String[]{b.getGroupName(), b.getTagName(), b.isIsPublic() == true ? "公开" : "不公开", b.getAearStr()};
            logUrl = b.getGroupLogo();
            parentView.findViewById(R.id.relative_05).setVisibility(View.GONE);
        } else {

            ClassModel cModel = ((ClassModel) model);

            content = new String[]{cModel.getSchoolName(), cModel.getEductionSystemName(),
                    cModel.getGradeName(), cModel.getCName(), cModel.getAlias()};
            logUrl = cModel.getClassLogo();

            ((CheckBox) parentView.findViewById(R.id.cb_07)).setChecked(cModel.isAllowJoin());
        }
        for (int i = 0; i < content.length; i++) {
            contentViews.get(i).setText(content[i]);
        }


        if (!StringUtil.isEmpty(logUrl)) {
            final CircleImageView circleImageView = parentView.findViewById(R.id.iv_log);
            if (model instanceof GroupDetailsBean) {
                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(HTApplication.getInstance(), logUrl,
                        circleImageView, ContextCompat.getDrawable(HTApplication.getInstance(), R.mipmap.default_group));
            } else {

                CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(HTApplication.getInstance(), logUrl,
                        circleImageView, ContextCompat.getDrawable(HTApplication.getInstance(), R.mipmap.default_class1));
            }
        }


    }


    //根据枚举类型，初始化编辑我的班级选择界面
    public static void initEditClassActionView(final EditGroupOrClassTypeEnum actionEnum, View parent,
                                               final GroupOrClassActionUtils actionUtils, final ClassSelectModel model, final List<Object> mList) {

        TextView tv_instr = parent.findViewById(R.id.tv_instr);
        TextView tiitle_middle = parent.findViewById(R.id.title_middle_text);

        EditText_clear clearEditText = parent.findViewById(R.id.filter_edit);
        switch (actionEnum) {

            case EditSchoolName:
            case AddSchool:
                tiitle_middle.setText("选择学校");
                parent.findViewById(R.id.tv_add).setVisibility(View.VISIBLE);
                parent.findViewById(R.id.linear_search).setVisibility(View.VISIBLE);
                parent.findViewById(R.id.linear_address).setVisibility(View.VISIBLE);

                actionUtils.getSchool(model.getSchoolName(), model.getAreaName(), model.getCityName(), 0, mList, false);

                ((TextView) parent.findViewById(R.id.tv_)).setText(StringUtil.isEmpty(model.getCityName()) == true ? "" : model.getCityName() +
                        (StringUtil.isEmpty(model.getAreaName()) == true ? "" : "—" + model.getAreaName()));
                clearEditText.setText(model.getSchoolName());

                break;

            case EditPriod:
            case AddPriod:
                tiitle_middle.setText("选择学段");
                tv_instr.setText("学段");

                actionUtils.getSystenList(model.getSchoolId(), mList);

                break;

            case EditGrade:
                tiitle_middle.setText("选择年级");
                tv_instr.setText("年级");
                actionUtils.getGrades(mList);
                break;

            case AddGrade:
                tiitle_middle.setText("选择年级");
                tv_instr.setText("年级");
                actionUtils.getGrades_addClass(mList, model);
                break;

            case EditClasses:
                tiitle_middle.setText("选择班级");
                tv_instr.setText("班级");
                actionUtils.getClasses(model.getGradeName(), model.getClassId(), model.getSchoolId(), model.getEduSystemId(), mList);
                break;

            case AddClass:
                tiitle_middle.setText("选择班级");
                tv_instr.setText("班级");
                actionUtils.getClasses_addClass(mList, model);
                break;

        }

    }

    public static void setResult(Activity activity, Intent intent, EditGroupOrClassTypeEnum typeEnum,
                                 Map<String, Object> map, boolean ischanged) {

        if (!ischanged || map == null || map.size() == 0) {
            activity.setResult(-1);
        } else {
            for (String key : map.keySet()) {
                intent.putExtra(key, map.get(key).toString());
            }

            intent.putExtra("typeEnum", typeEnum);
            activity.setResult(200, intent);
        }

        activity.finish();
    }


    public static void showNoticeDialog(final Activity activity) {
        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否放弃本次操作？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                setResult(activity, null, null, null, false);
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });
    }

    public static Map<String, Object> onItemSelected(EditGroupOrClassTypeEnum typeEnum, Object o) {

        Map<String, Object> map = new HashMap<>();
        switch (typeEnum) {

            case EditClasses:
            case AddClass:
                GradeBean classBean = ((GradeBean) o);
                map.put("myclass", classBean.getName());
                map.put("classId", classBean.getValue());
                break;

            case EditGrade:
            case AddGrade:
                classBean = ((GradeBean) o);
                map.put("grade", classBean.getName());
                map.put("gradeId", classBean.getValue());
                break;

            case EditPriod:
            case AddPriod:
                SystemProid proid = ((SystemProid) o);
                map.put("eduId", proid.getEduId());
                map.put("eduName", proid.getEduName());
                break;

            case EditSchoolName:
            case AddSchool:
                SchoolBean.ItemsBean schoolBean = ((SchoolBean.ItemsBean) o);
                map.put("schoolName", schoolBean.getName());
                map.put("schoolValue", schoolBean.getValue());
                break;
        }

        return map;
    }

    public static Object OnResultUpDateUi(EditGroupOrClassTypeEnum typeEnum, Intent data, Object o, View parent) {


        ClassModel classModel = null;
        GroupDetailsBean groupModel = null;
        switch (typeEnum) {
            case EditClassInstr:
                classModel = ((ClassModel) o);
                String content = data.getStringExtra("content");
//                ((TextView) parent.findViewById(R.id.tv_06_content)).setText(content);
//                classModel.setClassDescribe(content);
                Log.e("setClassDescribe", content);
                break;

            case EditClassAlice:
                classModel = ((ClassModel) o);
                content = data.getStringExtra("content");
                Log.e("setAlias", content);
                classModel.setAlias(content);
                ((TextView) parent.findViewById(R.id.tv_05_content)).setText(content);
                break;

            case EditPriod:
                classModel = ((ClassModel) o);

                String name;
                int eduId;

                name = data.getStringExtra("eduName");
                eduId = Integer.valueOf(data.getStringExtra("eduId"));

                if (!StringUtil.isEmpty(name)) {
                    if (!name.equals(classModel.getEductionSystemName()) && classModel.getEductionSystemId() != eduId) {
                        classModel.setEductionSystemName(name);
                        classModel.setEductionSystemId(eduId);
                        classModel.setCName("");
                        classModel.setGradeName("");
                        refreshViews(parent, classModel);
                    }
                }
                Log.e("setEductionSystemName", name);
                Log.e("setEductionSystemId", eduId + "");
                ((TextView) parent.findViewById(R.id.tv_02_content)).setText(classModel.getEductionSystemName());
                break;

            case EditClasses:
                classModel = ((ClassModel) o);

                classModel.setCName(data.getStringExtra("myclass"));
                Log.e("setCName", data.getStringExtra("myclass"));
                ((TextView) parent.findViewById(R.id.tv_04_content)).setText(classModel.getCName());
                break;
            case EditGrade:

                String grade;
                classModel = ((ClassModel) o);

                grade = data.getStringExtra("grade");

                if (!StringUtil.isEmpty(grade) && !grade.equals(classModel.getGradeName())) {
                    classModel.setGradeName(grade);
                    classModel.setCName("");
                    refreshViews(parent, classModel);
                }

                Log.e("setGradeName", grade);

                Log.e("setCName", classModel.getCName() + "");

                ((TextView) parent.findViewById(R.id.tv_03_content)).setText(classModel.getGradeName());
                break;
            case EditSchoolName:

                String schoolName, schoolValue;
                classModel = ((ClassModel) o);
                if (data != null) {
                    schoolName = data.getStringExtra("schoolName");
                    schoolValue = data.getStringExtra("schoolValue");
                    if (!StringUtil.isEmpty(schoolName) && !StringUtil.isEmpty(schoolValue)) {

                        if (!schoolName.equals(classModel.getSchoolName()) || !schoolValue.equals(classModel.getOrgId())) {
                            classModel.setSchoolName(schoolName);
                            classModel.setOrgId(schoolValue);
                            classModel.setCName("");
                            classModel.setGradeName("");
                            classModel.setEductionSystemName("");
                            classModel.setEductionSystemId(-1);
                            refreshViews(parent, classModel);
                            Log.e("setOrgId", classModel.getSchoolName());
                            Log.e("setSchoolName", classModel.getOrgId());
                            ((TextView) parent.findViewById(R.id.tv_01_content)).setText(classModel.getSchoolName());
                        }
                    }
                }

                break;

            case EditGroupName:
                groupModel = ((GroupDetailsBean) o);
                content = data.getStringExtra("content");
                ((TextView) parent.findViewById(R.id.tv_01_content)).setText(content);
                groupModel.setGroupName(content);
                Log.e("setGroupName", content);
                break;

            case EditGroupInstr:
                groupModel = ((GroupDetailsBean) o);
                content = data.getStringExtra("content");
                groupModel.setGroupDescription(content);
//                ((TextView) parent.findViewById(R.id.tv_06_content)).setText(content);
                Log.e("setGroupDescription", content);
                break;
        }

        if (o instanceof ClassModel) {
            return classModel;
        } else {
            return groupModel;
        }
    }


    //当联动改变数据时，重置原来班级相关信息

    private static void refreshViews(View parent, ClassModel classModel) {


        ((TextView) parent.findViewById(R.id.tv_02_content)).setText(classModel.getEductionSystemName());
        ((TextView) parent.findViewById(R.id.tv_03_content)).setText(classModel.getGradeName());
        ((TextView) parent.findViewById(R.id.tv_04_content)).setText(classModel.getCName());
        ((TextView) parent.findViewById(R.id.tv_05_content)).setText(classModel.getAlias());
    }
}
