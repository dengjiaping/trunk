package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.myclass.bean.ClassInfoBean;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.activity.CommenSelectActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.EditGroupOrClassTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.TakePicUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/22.
 * 班级信息
 */

public class ClassInfoActivity extends BaseActivity {

    private TextView title, school_name, phase_name, grade_name, class_name, title_left_text, title_right_text;
    private HiStudentHeadImageView class_logo;
    private IconView title_left_image;
    private ClassInfoBean bean;
    private TopMenuPopupWindow takePicPopupWindow;
    private View.OnClickListener takePicOnclick;
    private ClassModel model;
    private boolean isAdmin, isClickAble;

    public static void start(Activity activity, int code) {
        activity.startActivityForResult(new Intent(activity, ClassInfoActivity.class), code);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_info;
    }

    @Override
    public void initView() {

        title = (TextView) findViewById(R.id.title_middle_text);
        class_name = (TextView) findViewById(R.id.class_name);
        school_name = (TextView) findViewById(R.id.school_name);
        phase_name = (TextView) findViewById(R.id.phase_name);
        grade_name = (TextView) findViewById(R.id.grade_name);
        title_left_text = (TextView) findViewById(R.id.title_left_text);
        title_right_text = (TextView) findViewById(R.id.title_right_text);
        title_left_image = (IconView) findViewById(R.id.title_left_image);
        class_logo = (HiStudentHeadImageView) findViewById(R.id.class_logo);

        model = HiCache.getInstance().getClassDetailInfo();

        if (model != null) {
            isAdmin = model.isIsClassMaker();
            bean = new ClassInfoBean(model.getClassLogo(), model.getOrgId(), model.getSchoolName(), model.getEductionSystemName(), model.getEductionSystemId() + "", model.getGradeName(), model.getCName(),"");
        }
    }

    @Override
    public void doAction() {
        super.doAction();


        title.setText("班级信息");
        if (isAdmin) {
            title_left_image.setVisibility(View.GONE);
            title_left_text.setText("取消");
            title_right_text.setText("保存");
            title_right_text.setTextColor(getResources().getColor(R.color.click_true));
        }

        showClassInfo(bean);

        takePicOnclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePicPopupWindow.dismiss();
                switch (v.getId()) {

                    //本地获取图片
                    case R.id.btn_01:

                        TakePicUtils.StartTakePicFromLocalIntent(ClassInfoActivity.this, 1);
                        break;

                    //拍照
                    case R.id.btn_02:
                        checkTakePhotoPermission(new IPermissionCallBackListener() {
                            @Override
                            public void doAction() {
                                TakePicUtils.StartCameraIntent(ClassInfoActivity.this, 2);
                            }
                        });

                        break;

                }
            }
        };

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left://取消
                finish();
                break;

            case R.id.title_right://保存
                if (!isAdmin) return;
                updataClasses();
                break;

            case R.id.class_logo_layout://班级logo
                if (!isAdmin) return;
                List<String> list_name = new ArrayList<>();
                list_name.add("本地图片");
                list_name.add("拍照");

                List<Integer> list_color = new ArrayList<>();
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                takePicPopupWindow = new TopMenuPopupWindow(ClassInfoActivity.this, takePicOnclick, list_name, list_color, false);
                takePicPopupWindow.showAtLocation(findViewById(R.id.title_left), Gravity.CENTER, 0, 0);
                break;

            case R.id.school_name_layout://学校
                if (!isAdmin) return;
                AddressInforBean inforBean = HiWorldCache.getUserLocationInfor();
                CommenSelectActivity.start(this, EditGroupOrClassTypeEnum.EditSchoolName, null, null, null, null, null, inforBean.getCity(), inforBean.getAreaStr(), 100);
                break;

            case R.id.phase_name_layout://学段
                if (!isAdmin) return;
                ClassEditActivity.start(this, ClassInfoBean.GETLEVE, bean);
                break;

            case R.id.grade_name_layout://年级
                if (!isAdmin) return;
                ClassEditActivity.start(this, ClassInfoBean.GETGRADE, bean);
                break;

            case R.id.class_name_layout://班次
                if (!isAdmin) return;
                ClassEditActivity.start(this, ClassInfoBean.GETCLASS, bean);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            // 如果是直接从相册获取
            case 1:
                if (data != null && data.getData() != null) {
                    TakePicUtils.startPhotoZoom(ClassInfoActivity.this, data.getData(), 3, true);
                }

                break;
            // 如果是调用相机拍照时
            case 2:
                File temp = TakePicUtils.getTepFile();
                if (temp.exists()) {
                    TakePicUtils.startPhotoZoom(ClassInfoActivity.this, Uri.fromFile(temp), 3, true);
                }


                break;
            // 取得裁剪后的图片
            case 3:
                bean.setLogoPath(TakePicUtils.setPicToViewAndGetPicPath(data, class_logo, ClassInfoActivity.this));
                title_right_text.setTextColor(getResources().getColor(R.color.click_true));
                isClickAble = true;
                break;

            //修改可编辑数据的返回
            case 100:
                if (resultCode == 200) {

                    if (!bean.getSchoolNmae().equals(data.getStringExtra("schoolName"))) {
                        bean.setEduSystemName("");
                        bean.setGradeNmae("");
                        bean.setClassNmae("");
                        bean.setGradeId("");
                        bean.setSchoolId(data.getStringExtra("schoolValue"));
                        bean.setSchoolNmae(data.getStringExtra("schoolName"));
                        updataUI(bean);
                        title_right_text.setTextColor(getResources().getColor(R.color.click_true));
                        isClickAble = true;
                    }
                }
                break;

            case 200:
                if (resultCode == 200) {
                    bean = (ClassInfoBean) data.getSerializableExtra("bean");
                    updataUI(bean);
                    title_right_text.setTextColor(getResources().getColor(R.color.click_true));
                    isClickAble = true;
                }

                break;

        }
    }

    private void showClassInfo(ClassInfoBean bean) {
        if (bean == null) return;

        MyImageLoader.getIntent().displayNetImage(this, bean.getLogoPath(), class_logo);
        school_name.setText(bean.getSchoolNmae());
        phase_name.setText(bean.getEduSystemName());
        grade_name.setText(bean.getGradeNmae());
        class_name.setText(bean.getClassNmae());

    }

    private void updataUI(ClassInfoBean classInfoBean) {

        school_name.setText(TextUtils.isEmpty(classInfoBean.getSchoolNmae()) ? "" : classInfoBean.getSchoolNmae());

        phase_name.setText(TextUtils.isEmpty(classInfoBean.getEduSystemName()) ? "" : classInfoBean.getEduSystemName());

        grade_name.setText(TextUtils.isEmpty(classInfoBean.getGradeNmae()) ? "" : classInfoBean.getGradeNmae());

        class_name.setText(TextUtils.isEmpty(classInfoBean.getClassNmae()) ? "" : classInfoBean.getClassNmae());

    }

    /**
     * 更新班级
     */
    public void updataClasses() {
        if (!isClickAble) {
            finish();
        } else {
            Map<String, Object> map = new TreeMap<>();
            boolean flag = !bean.getLogoPath().equals(model.getClassLogo());
            map.put("cId", model.getClassId());
            map.put("orgId", bean.getSchoolId());
            map.put("orgName", bean.getSchoolNmae());
            map.put("gradeName", bean.getGradeNmae());
            map.put("className", bean.getClassNmae());
            map.put("isLogo", flag);
            map.put("eduSystem", bean.getEduSystemId());
            map.put("isAllowJoin", model.isAllowJoin());

            List<String> list_path = new ArrayList<>();
            if (flag)
                list_path.add(bean.getLogoPath());

            HiStudentHttpUtils.postImageByOKHttp(this, list_path,
                    map, HistudentUrl.classUpdate_url,
                    new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {

                            ClassHelper.getClassInfo(ClassInfoActivity.this, model.getClassId(), new HttpRequestCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    setResult(CodeNum.CLASS_INFO_RESULT);
                                    finish();
                                }

                                @Override
                                public void onFailure(String errorMsg) {

                                }
                            }, LoadingType.FLOWER);

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
        }
    }

}
