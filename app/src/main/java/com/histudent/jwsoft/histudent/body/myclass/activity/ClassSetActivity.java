package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.GroupAssignmentActivity;
import com.histudent.jwsoft.histudent.body.find.activity.GroupInfoActivity;
import com.histudent.jwsoft.histudent.body.find.activity.GroupMemberActivity;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/22.
 * 班级(社群)设置
 */

public class ClassSetActivity extends BaseActivity {

    private ClassModel classModel;
    private ImageView class_join;
    private TextView title, class_name, class_code, class_member_num, group_stype, class_member_text_00, class_member_text_01;
    private HiStudentHeadImageView class_logo;
    private LinearLayout teacher_layout;
    private RelativeLayout join_layout;
    private TextView class_out;
    private TopMenuPopupWindow menuWindow;
    private GroupBean groupBean;
    private boolean isClassSet;

    /**
     * 班级设置
     *
     * @param activity
     * @param code
     */
    public static void start(Activity activity, int code) {
        Intent intent = new Intent(activity, ClassSetActivity.class);
        intent.putExtra("isSeleted", false);
        activity.startActivityForResult(intent, code);
    }

    /**
     * 社群设置
     *
     * @param activity
     * @param code
     */
    public static void start(Activity activity, GroupBean bean, int code) {
        Intent intent = new Intent(activity, ClassSetActivity.class);
        intent.putExtra("groupBean", bean);
        intent.putExtra("isSeleted", false);
        activity.startActivityForResult(intent, code);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_set;
    }

    @Override
    public void initView() {
        groupBean = (GroupBean) getIntent().getSerializableExtra("groupBean");
        isClassSet = (groupBean == null);
        class_join = (ImageView) findViewById(R.id.class_join);
        title = (TextView) findViewById(R.id.title_middle_text);
        class_name = (TextView) findViewById(R.id.class_name);
        class_code = (TextView) findViewById(R.id.class_code);
        class_member_text_00 = (TextView) findViewById(R.id.class_member_text_00);
        class_member_text_01 = (TextView) findViewById(R.id.class_member_text_01);
        class_member_num = (TextView) findViewById(R.id.class_member_num);
        class_logo = (HiStudentHeadImageView) findViewById(R.id.class_logo);
        teacher_layout = (LinearLayout) findViewById(R.id.teacher_layout);
        class_out = (TextView) findViewById(R.id.class_out);
        group_stype = (TextView) findViewById(R.id.group_stype);
        join_layout = (RelativeLayout) findViewById(R.id.join_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUI();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left://返回
                finish();
                break;

            case R.id.class_my://班级/社群logo
                if (isClassSet) {
                    ClassInfoActivity.start(this, CodeNum.CLASS_INFO_REQUEST);
                } else {
                    GroupInfoActivity.start(this, groupBean, CodeNum.GROUP_INFO_REQUEST);
                }
                break;

            case R.id.class_member://班级/社群成员
                if (isClassSet) {
                    ClassMemberActivity.start(this, CodeNum.CLASS_MEMBER_REQUEST);
                } else {
                    GroupMemberActivity.start(this, groupBean, CodeNum.GROUP_MEMBER_REQUEST);
                }
                break;

            case R.id.class_exchange://转让班级/社群
                if (isClassSet) {
                    ClassAssignmentActivity.start(this, CodeNum.CLASS_TRANSFER_REQUEST);
                } else {
                    GroupAssignmentActivity.start(this, groupBean, CodeNum.GROUP_TRANSFER_REQUEST);
                }
                break;

            case R.id.class_join://加入开关
                setClassJoin();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeNum.CLASS_DISSOLVE_REQUEST && resultCode == CodeNum.CLASS_DISSOLVE_RESULT) {//解散班级
            setResult(CodeNum.CLASS_DISSOLVE_RESULT);
            finish();

        } else if (requestCode == CodeNum.GROUP_DISSOLVE_REQUEST && resultCode == CodeNum.GROUP_DISSOLVE_RESULT) {//解散社群
            setResult(CodeNum.GROUP_DISSOLVE_RESULT);
            finish();

        } else if (requestCode == CodeNum.CLASS_TRANSFER_REQUEST && resultCode == CodeNum.CLASS_TRANSFER_RESULT) {//转让班级
            reFreshClassInfo();

        } else if (requestCode == CodeNum.GROUP_TRANSFER_REQUEST && resultCode == CodeNum.GROUP_TRANSFER_RESULT) {//转让社群
            setResult(CodeNum.GROUP_TRANSFER_RESULT);
            reFreshGroupInfo();

        } else if (requestCode == CodeNum.CLASS_MEMBER_REQUEST && resultCode == CodeNum.CLASS_MEMBER_RESULT) {//班级成员信息改变
            reFreshClassInfo();

        } else if (requestCode == CodeNum.GROUP_MEMBER_REQUEST && resultCode == CodeNum.GROUP_MEMBER_RESULT) {//社群成员信息改变
            setResult(CodeNum.GROUP_MEMBER_RESULT);
            reFreshGroupInfo();

        } else if (requestCode == CodeNum.CLASS_INFO_REQUEST && resultCode == CodeNum.CLASS_INFO_RESULT) {//班级信息改变
            reFreshClassInfo();

        } else if (requestCode == CodeNum.GROUP_INFO_REQUEST && resultCode == CodeNum.GROUP_INFO_RESULT) {//社群信息改变
            setResult(CodeNum.GROUP_INFO_RESULT);
            reFreshGroupInfo();

        }
    }

    private void showUI() {
        if (!isClassSet) {//社群

            title.setText("社群设置");
            join_layout.setVisibility(View.GONE);

            class_member_text_00.setText("社群成员");
            class_member_text_01.setText("转让社群");
            class_out.setText("解散社群");
            if (groupBean.isIsGroupMaker()) {
                teacher_layout.setVisibility(View.VISIBLE);
                class_out.setBackgroundResource(R.drawable.red_button_bg);
                class_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClassDissolveActivity.start(ClassSetActivity.this, CodeNum.GROUP_DISSOLVE_REQUEST, isClassSet, groupBean.getGroupId());
                    }
                });
            } else {
                teacher_layout.setVisibility(View.GONE);
                class_out.setBackgroundResource(R.drawable.red_button_bg);
                class_out.setText("退出社群");
                class_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exitGroup();
                    }
                });
            }

            MyImageLoader.getIntent().displayNetImage(this, groupBean.getGroupLogo(), class_logo);
            class_name.setText(groupBean.getGroupName());
            class_code.setVisibility(View.GONE);
            group_stype.setVisibility(View.VISIBLE);
            group_stype.setText(groupBean.getChildTagName());
            class_member_num.setText(groupBean.getGroupMemberNum() + "人");

        } else {//班级

            title.setText("班级设置");
            classModel = HiCache.getInstance().getClassDetailInfo();
            if (classModel == null) return;
            class_join.setImageResource(classModel.isAllowJoin() ? R.mipmap.checkbox_true : R.mipmap.checkbox_false);

            if (classModel.isIsClassMaker()) {
                teacher_layout.setVisibility(View.VISIBLE);
                class_out.setText("解散班级");
                class_out.setBackgroundResource(R.drawable.red_button_bg);
                class_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClassDissolveActivity.start(ClassSetActivity.this, CodeNum.CLASS_DISSOLVE_REQUEST, isClassSet);
                    }
                });
            } else {
                teacher_layout.setVisibility(View.GONE);
                class_out.setBackgroundResource(R.drawable.red_button_bg);
                class_out.setText("退出班级");
                class_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exitClass();
                    }
                });
            }

            MyImageLoader.getIntent().displayNetImage(this, classModel.getClassLogo(), class_logo);
            class_name.setText(classModel.getSchoolName() + classModel.getGradeName() + classModel.getCName());
            class_code.setText("代码：" + classModel.getClassNum());
            class_member_num.setText(classModel.getClassMemberNum() + "");

        }
    }

    /**
     * 退出班级
     */
    private void exitClass() {
        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();
        list_name.add("确定要退出该班级吗？");
        list_name.add("退出");
        list_color.add(Color.rgb(51, 51, 51));
        list_color.add(Color.rgb(255, 0, 0));

        menuWindow = new TopMenuPopupWindow(ClassSetActivity.this, new View.OnClickListener() {

            public void onClick(View v) {
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_01:
                        break;

                    case R.id.btn_02:

                        ClassHelper.exitClass(ClassSetActivity.this, classModel.getClassId(), HiCache.getInstance().getLoginUserInfo().getUserId(), new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                setResult(CodeNum.CLASS_EXIT_RESULT);
                                finish();
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        }, list_name, list_color, false);

        menuWindow.showAtLocation(findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }


    /**
     * 退出社群
     */
    private void exitGroup() {
        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();
        list_name.add("确定要退出该社群吗？");
        list_name.add("退出");
        list_color.add(Color.rgb(51, 51, 51));
        list_color.add(Color.rgb(255, 0, 0));

        menuWindow = new TopMenuPopupWindow(ClassSetActivity.this, new View.OnClickListener() {

            public void onClick(View v) {
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_01:
                        break;

                    case R.id.btn_02:

                        Map<String, Object> map = new TreeMap<>();
                        map.put("addOrRemove", false);
                        map.put("groupId", groupBean.getGroupId());
                        map.put("memberUserId", HiCache.getInstance().getLoginUserInfo().getUserId());
                        HiStudentHttpUtils.postDataByOKHttp(ClassSetActivity.this, map, HistudentUrl.setMembers_group, new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                setResult(CodeNum.GROUP_EXIT_RESULT);
                                finish();
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        });

                        break;

                    default:
                        break;
                }
            }
        }, list_name, list_color, false);

        menuWindow.showAtLocation(findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 刷新社群信息
     */
    private void reFreshGroupInfo() {
        if (TextUtils.isEmpty(groupBean.getGroupId())) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("groupId", groupBean.getGroupId());
        map.put("pageSize", 5);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.singleGroup, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                groupBean = JSON.parseObject(result, GroupBean.class);
                showUI();
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 刷新班级信息
     */
    private void reFreshClassInfo() {
        ClassHelper.getClassInfo(ClassSetActivity.this, classModel.getClassId(), new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                showUI();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.FLOWER);
    }

    /**
     * 设置允许加入班级开关
     */
    private void setClassJoin() {
        final boolean flag = !classModel.isAllowJoin();
        ClassHelper.setClassApplyAudit(ClassSetActivity.this, flag, classModel.getClassId(), new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                classModel.setAllowJoin(flag);
                showUI();
            }

            @Override
            public void onFailure(String errorMsg) {
                classModel.setAllowJoin(!flag);
                showUI();
            }
        });
    }

}
