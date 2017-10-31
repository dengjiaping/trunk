package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.CreateHuoDongBean;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommenPrivacySetting;
import com.histudent.jwsoft.histudent.commen.activity.InputActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.bean.NetImageModel;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.TakePicUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/22.
 * 社群信息
 */

public class GroupInfoActivity extends BaseActivity {

    private TextView title, school_name, phase_name, grade_name, class_name, title_left_text, title_right_text;
    private HiStudentHeadImageView class_logo;
    private IconView title_left_image;
    private GroupEditInfo groupEditInfo;
    private TopMenuPopupWindow takePicPopupWindow;
    private View.OnClickListener takePicOnclick;
    private GroupBean groupBean;
    private boolean isAdmin;

    public static void start(Activity activity, GroupBean groupBean, int code) {
        Intent intent = new Intent(activity, GroupInfoActivity.class);
        intent.putExtra("groupBean", groupBean);
        activity.startActivityForResult(intent, code);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_group_info;
    }

    @Override
    public void initView() {
        groupBean = (GroupBean) getIntent().getSerializableExtra("groupBean");
        title = (TextView) findViewById(R.id.title_middle_text);
        class_name = (TextView) findViewById(R.id.class_name);
        school_name = (TextView) findViewById(R.id.school_name);
        phase_name = (TextView) findViewById(R.id.phase_name);
        grade_name = (TextView) findViewById(R.id.grade_name);
        title_left_text = (TextView) findViewById(R.id.title_left_text);
        title_right_text = (TextView) findViewById(R.id.title_right_text);
        title_left_image = (IconView) findViewById(R.id.title_left_image);
        class_logo = (HiStudentHeadImageView) findViewById(R.id.class_logo);
        isAdmin = groupBean.isIsManager();

        if (groupBean != null) {
            groupEditInfo = new GroupEditInfo();
            groupEditInfo.setGroupLogo(groupBean.getGroupLogo());
            groupEditInfo.setGroupName(groupBean.getGroupName());
            groupEditInfo.setTagName(groupBean.getTagName());
            groupEditInfo.setChildTagName(groupBean.getChildTagName());
            groupEditInfo.setPublic(groupBean.isIsPublic());
            groupEditInfo.setTagId(groupBean.getTagId());
            groupEditInfo.setTagChildId(groupBean.getChildTagId());
            groupEditInfo.setIntroduce(groupBean.getGroupDescription());
        }

    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("社群信息");
        if (isAdmin) {
            title_left_image.setVisibility(View.GONE);
            title_left_text.setText("取消");
            title_right_text.setText("保存");
        }

        showGroupInfo(groupEditInfo);

        takePicOnclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePicPopupWindow.dismiss();
                switch (v.getId()) {

                    //本地获取图片
                    case R.id.btn_01:

                        TakePicUtils.StartTakePicFromLocalIntent(GroupInfoActivity.this, 1);
                        break;

                    //拍照
                    case R.id.btn_02:
                        checkTakePhotoPermission(new IPermissionCallBackListener() {
                            @Override
                            public void doAction() {
                                TakePicUtils.StartCameraIntent(GroupInfoActivity.this, 2);
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
                takePicPopupWindow = new TopMenuPopupWindow(GroupInfoActivity.this, takePicOnclick, list_name, list_color, false);
                takePicPopupWindow.showAtLocation(findViewById(R.id.title_left), Gravity.CENTER, 0, 0);
                break;

            case R.id.school_name_layout://社群名称
                if (!isAdmin) return;
                InputActivity.startOnResult(this, "社群名称", "groupName", groupEditInfo.getGroupName(), 10);
                break;

            case R.id.phase_name_layout://社群标签
                if (!isAdmin) return;
                GroupFristCategoryActivity.start(this, 11);
                break;

            case R.id.grade_name_layout://访问权限
                if (!isAdmin) return;
                CommenPrivacySetting.start(this, groupEditInfo.isPublic() ? "公开" : "成员可见", ActionTypeEnum.GROUP, 12);
                break;

            case R.id.class_name_layout://社群介绍
                if (!isAdmin) {
                    return;
//                    GroupIntroduceActivity.start(this, groupBean);
                } else {
                    CreateHuoDongBean bean = new CreateHuoDongBean();
                    bean.setInstruction(groupBean.getGroupDescription());
                    List<GroupBean.GroupDescriptionImgsListBean> imgs = groupBean.getGroupDescriptionImgsList();
                    List<NetImageModel> beans = new ArrayList<>();
                    if (imgs != null)
                        for (int i = 0; i < imgs.size(); i++) {
                            NetImageModel model = new NetImageModel();
                            model.setId(imgs.get(i).getImgId());
                            model.setUrl(imgs.get(i).getSavePath());
                            beans.add(model);
                        }
                    bean.setInstrFileList(beans);
                    CreateHuoDongSeond.start(this, bean, 3, 13);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == -1) {//获取本地图片返回

            if (data != null && data.getData() != null)
                TakePicUtils.startPhotoZoom(GroupInfoActivity.this, data.getData(), 3, true);

        } else if (requestCode == 2 && resultCode == -1) {//手机系统拍照返回

            File temp = TakePicUtils.getTepFile();
            if (temp.exists())
                TakePicUtils.startPhotoZoom(GroupInfoActivity.this, Uri.fromFile(temp), 3, true);

        } else if (requestCode == 3 && resultCode == -1) {//照片剪裁返回

            groupEditInfo.setGroupLogo(TakePicUtils.setPicToViewAndGetPicPath(data, class_logo, GroupInfoActivity.this));

        } else if (requestCode == 10 && resultCode == 20) {//编辑社群名称返回

            groupEditInfo.setGroupName(data.getStringExtra("info"));
            school_name.setText(groupEditInfo.getGroupName());

        } else if (requestCode == 11 && resultCode == 200) {//编辑社群标签返回

            groupEditInfo.setTagName(data.getStringExtra("parentType"));
            groupEditInfo.setChildTagName(data.getStringExtra("childType"));
            groupEditInfo.setTagId(data.getStringExtra("parentId"));
            groupEditInfo.setTagChildId(data.getStringExtra("childId"));
            phase_name.setText(groupEditInfo.getTagName() + "-" + groupEditInfo.getChildTagName());

        } else if (requestCode == 12 && resultCode == 200) {//编辑社群权限返回

            groupEditInfo.setPublic("公开".equals(data.getStringExtra("authority")));
            grade_name.setText(groupEditInfo.isPublic() ? "公开" : "成员可见");

        } else if (requestCode == 13 && resultCode == 200) {//编辑社群简介返回

            CreateHuoDongBean bean = (CreateHuoDongBean) data.getSerializableExtra("infor");
            if (bean != null) {
                groupBean.setGroupDescription(bean.getInstruction());
                groupEditInfo.setIntroduce(bean.getInstruction());
                groupEditInfo.setAddImage(bean.getImages());
                groupEditInfo.setDeletImage(bean.getDeleteImageId());
                class_name.setText(groupEditInfo.getIntroduce());
            }

        }

    }

    private void showGroupInfo(GroupEditInfo editInfo) {
        if (editInfo == null) return;
        MyImageLoader.getIntent().displayNetImage(this, editInfo.getGroupLogo(), class_logo);
        school_name.setText(editInfo.getGroupName());
        phase_name.setText(editInfo.getTagName() + "-" + editInfo.getChildTagName());
        grade_name.setText(editInfo.isPublic() ? "公开" : "成员可见");
        SystemUtil.showHtml(class_name, this, editInfo.getIntroduce());
    }

    //更新班级
    public void updataClasses() {

//        if (bean.getLogoPath().equals(model.getClassLogo())
//                && bean.getSchoolId().equals(model.getOrgId())
//                && bean.getEduSystemId().equals(model.getEductionSystemId() + "")
//                && bean.getGradeNmae().equals(model.getGradeName())
//                && bean.getClassNmae().equals(model.getCName())) return;

        Map<String, Object> map = new TreeMap<>();
        boolean flag_logo = !groupBean.getGroupLogo().equals(groupEditInfo.getGroupLogo());
        boolean flag_addImge = (groupEditInfo.getAddImage() != null && groupEditInfo.getAddImage().size() > 0);
        map.put("groupId", groupBean.getGroupId());
        map.put("isLogo", flag_logo);
        map.put("groupName", groupEditInfo.getGroupName());
        map.put("tagId", groupEditInfo.getTagId());
        map.put("childTag", groupEditInfo.getTagChildId());
        map.put("isPublic", groupEditInfo.isPublic());
        map.put("groupIntroduce", groupEditInfo.getIntroduce());
        map.put("isIntroImg", flag_addImge);
        List<String> deletImages = groupEditInfo.getDeletImage();

        StringBuilder builder = new StringBuilder();
        if (deletImages != null && deletImages.size() > 0) {

            for (String imageId : deletImages) {
                builder.append(imageId).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }


        map.put("excludeImgs", deletImages == null ? "" : builder.toString());
        if (flag_logo) map.put("logo", groupEditInfo.getGroupLogo());
        if (flag_addImge) map.put("imageFile", groupEditInfo.getAddImage());

        HiStudentHttpUtils.postVoidAndImageByOKHttp(this, map, HistudentUrl.groupUpdate,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        setResult(CodeNum.GROUP_INFO_RESULT);
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });

    }

    public class GroupEditInfo {
        private String groupLogo;
        private String groupName;
        private String tagName;
        private String tagId;
        private String tagChildId;
        private boolean isPublic;
        public String introduce;
        private String childTagName;

        public String getChildTagName() {
            return childTagName;
        }

        public void setChildTagName(String childTagName) {
            this.childTagName = childTagName;
        }

        public List<String> deletImage;
        public List<String> addImage;

        public List<String> getDeletImage() {
            return deletImage;
        }

        public void setDeletImage(List<String> deletImage) {
            this.deletImage = deletImage;
        }

        public List<String> getAddImage() {
            return addImage;
        }

        public void setAddImage(List<String> addImage) {
            this.addImage = addImage;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getTagChildId() {
            return tagChildId;
        }

        public void setTagChildId(String tagChildId) {
            this.tagChildId = tagChildId;
        }

        public String getGroupLogo() {
            return groupLogo;
        }

        public void setGroupLogo(String groupLogo) {
            this.groupLogo = groupLogo;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public boolean isPublic() {
            return isPublic;
        }

        public void setPublic(boolean aPublic) {
            isPublic = aPublic;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }
    }

}
