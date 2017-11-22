package com.histudent.jwsoft.histudent.comment2.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.GroupAuthoritySelectActivity;
import com.histudent.jwsoft.histudent.body.find.bean.GroupDetailsBean;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;
import com.histudent.jwsoft.histudent.comment2.utils.EditGrouoOrClassViewUitils;
import com.histudent.jwsoft.histudent.comment2.utils.EditGroupOrClassTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.GroupOrClassActionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TakePicUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 编辑我的班级或者社群
 */
public class EditClassOrGroupActivity extends BaseActivity {

    private String id;
    private int type;
    private GroupDetailsBean groupModel;
    private ClassModel classModel;
    private View parentView;
    private TopMenuPopupWindow takePicPopupWindow;
    private View.OnClickListener takePicOnclick;
    List<String> list_name;
    List<Integer> list_color;
    private String filePath, tagId;
    private String city, area;
    private EditGroupOrClassTypeEnum typeEnum;
    private GroupOrClassActionUtils actionUtils;
    private GroupOrClassActionUtils.isSuccessCallBack isSuccessCallBack;
    private Map<String, Object> map_edit;
    private boolean isChanged;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //班级或者社群信息返回
                case 0:
                    String content = ((String) msg.obj);

                    if (msg.arg1 == HiWorldCache.SUCCESS) {

                        switch (type) {
                            //班级
                            case 1:
                                classModel = JSON.parseObject(content, ClassModel.class);
                                if (classModel != null) {
                                    UpDateUi();
                                }

                                break;
                            //社群
                            case 2:

                                groupModel = JSON.parseObject(content, GroupDetailsBean.class);
                                if (groupModel != null) {
                                    UpDateUi();
                                }

                                break;
                        }


                    }
                    break;
            }
        }


    };


    @Override
    public int setViewLayout() {
        return R.layout.activity_edit_class_or_group;
    }

    public static void start(Activity activity, String id, int type) {
        Intent intent = new Intent(activity, EditClassOrGroupActivity.class);
        intent.putExtra("Id", id);
        intent.putExtra("Type", type);
        activity.startActivityForResult(intent, 200);
    }


    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        id = getIntent().getStringExtra("Id");
        type = getIntent().getIntExtra("Type", -1);
        parentView = ((View) findViewById(R.id.parent_view));
        ((TextView) findViewById(R.id.title_right_text)).setText(R.string.completed);


        isSuccessCallBack = new GroupOrClassActionUtils.isSuccessCallBack() {
            @Override
            public void isSuccess(int type, boolean isSuccess) {

                if (isSuccess) {
                    isChanged = true;
                    if (type == 4) {

                        setResult();
                    }
                }
            }
        };

        actionUtils = new GroupOrClassActionUtils(this, isSuccessCallBack);


        EditGrouoOrClassViewUitils.initGroupOrClassEditViews(parentView, type);
        getClassOrGroupInfor();


        takePicOnclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePicPopupWindow.dismiss();
                switch (v.getId()) {

                    //本地获取图片
                    case R.id.btn_01:

                        TakePicUtils.StartTakePicFromLocalIntent(EditClassOrGroupActivity.this, 1);
                        break;

                    //拍照
                    case R.id.btn_02:
                        checkTakePhotoPermission(new IPermissionCallBackListener() {
                            @Override
                            public void doAction() {
                                TakePicUtils.StartCameraIntent(EditClassOrGroupActivity.this, 2);
                            }
                        });

                        break;

                }
            }
        };

    }

    //更新界面
    private void UpDateUi() {

        EditGrouoOrClassViewUitils.upDateClassOrGroupUi(parentView,
                type == 1 ? classModel : groupModel);

        if (type == 2) {
            getCityAndArea();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            // 如果是直接从相册获取
            case 1:
                if (data != null && data.getData() != null) {

                    TakePicUtils.startPhotoZoom(EditClassOrGroupActivity.this, data.getData(), 3, true);
                }

                break;
            // 如果是调用相机拍照时
            case 2:
                File temp = TakePicUtils.getTepFile();

                if (temp.exists()) {
                    TakePicUtils.startPhotoZoom(EditClassOrGroupActivity.this, Uri.fromFile(temp), 3, true);
                }


                break;
            // 取得裁剪后的图片
            case 3:
                filePath = TakePicUtils.setPicToViewAndGetPicPath(data, ((CircleImageView) findViewById(R.id.iv_log)),
                        EditClassOrGroupActivity.this);
                Log.e("FilePath", filePath);

                break;

            //修改社群类型返回
            case 100:
                if (resultCode == 100) {
                    groupModel.setTagId(data.getStringExtra("tagId"));
                    ((TextView) findViewById(R.id.tv_02_content)).setText(data.getStringExtra("type"));
                }


                break;

            //修改可编辑数据的返回
            case 400:

                //返回数据需要界面刷新
                if (resultCode == 200) {

                    if (data != null) {
                        EditGroupOrClassTypeEnum enum1 = ((EditGroupOrClassTypeEnum) data.getSerializableExtra("typeEnum"));
                        if (type == 1) {
                            classModel = ((ClassModel) EditGrouoOrClassViewUitils.OnResultUpDateUi(enum1, data, classModel, parentView));
                        } else {
                            groupModel = ((GroupDetailsBean) EditGrouoOrClassViewUitils.OnResultUpDateUi(enum1, data, groupModel, parentView));
                        }
                    }
                }

                break;

            //地址返回

            case 300:
                if (resultCode == 200) {

                    city = data.getStringExtra("city");
                    area = data.getStringExtra("area");
                    groupModel.setAearStr(data.getStringExtra("city") + data.getStringExtra("area"));
                    ((TextView) findViewById(R.id.tv_04_content)).setText(data.getStringExtra("city") + data.getStringExtra("area"));
                }
                break;

            //社群隐私权限返回
            case 1000:
                if (resultCode == 600) {
                    groupModel.setIsPublic(data.getStringExtra("authority").equals("公开") == true ? true : false);
                    ((TextView) findViewById(R.id.tv_03_content)).setText(data.getStringExtra("authority"));
                }

                break;
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:
                setResult();
                break;

            //修改学校或者社群名称
            case R.id.relative_01:
                if (type == 1) {
                    //
//                    CommenSelectActivity.start(this, EditGroupOrClassTypeEnum.EditSchoolName,
//                            "", "", "", classModel.getClassId(), classModel.getSchoolName(),
//                            classModel.getAreaShiName(), classModel.getAreaName());
                } else {
                    CommenActivity.start(this, groupModel.getGroupName(),
                            EditGroupOrClassTypeEnum.EditGroupName);
                }


                break;

            //修改班级学段或者社群分类
            case R.id.relative_02:
                if (type == 2) {
//                    ActivityType.start(this, 1, 100, groupModel.getTagId());
                } else {
                    CommenSelectActivity.start(this, EditGroupOrClassTypeEnum.EditPriod, classModel.getOrgId(), "", "", "", "", "", "");
                }

                break;

            //修改年级或者权利类型
            case R.id.relative_03:

                if (type == 1) {
                    CommenSelectActivity.start(this, EditGroupOrClassTypeEnum.EditGrade, "", "", "", "", "", "", "");
                } else {
                    GroupAuthoritySelectActivity.start(this,
                            groupModel.isIsPublic() == true ? "公开" : "不公开", 1000);
                }
                break;

            //修改班次或者社群位置
            case R.id.relative_04:
                if (type == 2) {

                    getCityAndArea();
                    SelectAddressActivity.start(this, city, area, 2);
                } else {
                    CommenSelectActivity.start(this, EditGroupOrClassTypeEnum.EditClasses, classModel.getOrgId()
                            , classModel.getGradeName(), classModel.getClassId(), classModel.getEductionSystemId() + "", "", "", "");
                }

                break;

            //修改别名
            case R.id.relative_05:

                CommenActivity.start(this, classModel.getAlias(), EditGroupOrClassTypeEnum.EditClassAlice);
                break;

            //修改班级或者社群介绍
            case R.id.relative_06:
//
//                CommenActivity.start(this, type == 1 ? classModel.getClassDescribe() : groupModel.getGroupDescription(),
//                        type == 1 ? EditGroupOrClassTypeEnum.EditClassInstr : EditGroupOrClassTypeEnum.EditGroupInstr);

                break;

            //加入开关
            case R.id.relative_07:
                break;

            case R.id.iv_add:

                list_name = new ArrayList<>();
                list_name.add("本地图片");
                list_name.add("拍照");

                list_color = new ArrayList<>();
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                takePicPopupWindow = new TopMenuPopupWindow(EditClassOrGroupActivity.this, takePicOnclick, list_name, list_color,false);
                takePicPopupWindow.showAtLocation(findViewById(R.id.title_left), Gravity.CENTER, 0, 0);

                break;


            //完成
            case R.id.title_right:

                if (type == 1) {
                    classModel.setAllowJoin(((CheckBox) findViewById(R.id.cb_07)).isChecked());
                }

                if (type == 2) {

                    if (StringUtil.isEmpty(city)) {

                        Toast.makeText(this, "请选择城市！", Toast.LENGTH_LONG).show();
                        return;
                    } else if (StringUtil.isEmpty(area)) {
                        Toast.makeText(this, "请选择地区！", Toast.LENGTH_LONG).show();
                        return;
                    }

                }
                actionUtils.EditGroupOrClass(type == 1 ? true : false, type == 1 ? classModel : groupModel, filePath);
                break;
        }
    }

    //获取班级或者社群的信息
    private void getClassOrGroupInfor() {

        String key = "", url = "";
        if (type == 1) {

            //班级
            key = "classId";
            url = HistudentUrl.getClassModel_url;

        } else {

            //社群
            key = "groupId";
            url = HistudentUrl.single_group_list_url;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(key, id);
        HiWorldCache.postHttpData(this, mHandler, 0, url, map, HiWorldCache.Quarry, true,true);
    }

    public void setResult() {
        if (isChanged) {
            setResult(200);
        } else {
            setResult(-1);
        }
        this.finish();
    }


    private void getCityAndArea() {


        String address = groupModel.getAearStr();

        Log.e("city=======>", address);
        if (address.contains("省")) {
            address = address.substring(address.indexOf("省") + 1, address.length());
        }

        city = address.substring(0, address.indexOf("市") + 1);
        area = address.substring(address.indexOf("市") + 1, address.length());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageUtils.deleteCompressFile();
        ActivityCollector.finishAll();
    }

}
