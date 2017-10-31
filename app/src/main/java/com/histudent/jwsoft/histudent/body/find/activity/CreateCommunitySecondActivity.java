package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.CreateHuoDongBean;
import com.histudent.jwsoft.histudent.body.find.bean.GroupCreateSuccedBean;
import com.histudent.jwsoft.histudent.body.find.event.ActivityFinishEvent;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassAndGroupShareActivity;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommenPrivacySetting;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.FileUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TakePicUtils;
import com.histudent.jwsoft.histudent.constant.ConstantUtil;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 创建社群，填写内容
 */
public class CreateCommunitySecondActivity extends BaseActivity {
    private static final String TAG = CreateCommunitySecondActivity.class.getSimpleName();
    @BindView(R.id.title_middle_text)
    TextView titleMiddleText;
    @BindView(R.id.title_left)
    LinearLayout mBack;
    @BindView(R.id.community_camera)
    HiStudentHeadImageView communityCamera;
    @BindView(R.id.community_name)
    EditText communityName;
    @BindView(R.id.community_tag)
    TextView communityTag;
    @BindView(R.id.community_permission)
    TextView communityPermission;
    @BindView(R.id.community_tv_description)
    TextView communityTvDescription;
    @BindView(R.id.community_finish_btn)
    TextView communityFinishBtn;
    @BindView(R.id.community_description)
    LinearLayout mLinearLayout;
    @BindView(R.id.community_select_tag)
    LinearLayout communitySelectTag;
    @BindView(R.id.community_select_permission)
    LinearLayout communitySelectPermission;

    /*********上个页面传递过来的数据**************/
    private String resetToken;//票据
    private String objectName;//主体名称
    private String objectCode;//营业执照注册号
    private String mgrMobile;//电话号码
    private String mgrName;//负责人名字
    /*********本页面的成员变量**************/
    private int objectType;//主体类型 (1 : 个人 , 2 : 学校 , 3 : 培训机构 )
    private boolean isLogo;//是否包含Logo（如果包含，图片名称约定为logo）
    private String groupName;//社群名称
    private String tagId;//一级分类ID
    private String childTag;//二级分类ID
    private boolean isPublic = false;//是否公开
    private String groupIntroduce = null;//社群介绍
    private boolean isIntroImg;//社群介绍是否包含照片
    private String logo;
    //社群标签请求码
    private static final int COMMUNITY_TAG = 100;
    //相册请求码
    private static final int LOCAL_PHOTO = 1;
    private static final int CAMBER = 2;
    private static final int LOCAL_CAMBER_CUT = 3;

    //社群权限请求码
    private static final int COMMUNITY_PERMISSION = 110;
    //社群介绍请求码
    private static final int COMMUNITY_DESCRIPTION = 115;
    //社群编辑
    private TopMenuPopupWindow takePicPopupWindow;
    private View.OnClickListener itemsSetPicOnClick;
    private CreateHuoDongBean activityBean;
    private Map<String, String> fileMap;

    //培训机构
    public static void start(Activity activity, String token, String school, String business, String phone, String person, int type) {
        Intent intent = new Intent(activity, CreateCommunitySecondActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("school", school);
        intent.putExtra("business", business);
        intent.putExtra("phone", phone);
        intent.putExtra("person", person);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    //学校
    public static void start(Activity activity, String token, String school, String phone, String person, int type) {
        Intent intent = new Intent(activity, CreateCommunitySecondActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("school", school);
        intent.putExtra("phone", phone);
        intent.putExtra("person", person);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    //个人
    public static void start(Activity activity, String token, String phone, String person, int type) {
        Intent intent = new Intent(activity, CreateCommunitySecondActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("phone", phone);
        intent.putExtra("person", person);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_create_community_second;
    }

    @Override
    public void initView() {
        titleMiddleText.setText(R.string.community_create);
        activityBean = new CreateHuoDongBean();
        initItemListener();
        fileMap = new HashMap<>();
        //初始化必要数据
        initData();
    }

//    private void checkButton() {
//        if (StringUtil.isEmpty(groupName)
//                || StringUtil.isEmpty(communityTag.getText().toString())
//                || StringUtil.isEmpty(communityPermission.getText().toString().trim())) {
//            communityFinishBtn.setEnabled(false);
//            communityFinishBtn.setBackground(getResources().getDrawable(R.drawable.btn_ground_corner_two));
//        } else {
//            communityFinishBtn.setEnabled(true);
//            communityFinishBtn.setBackgroundColor(getResources().getColor(R.color.green_color));
//        }
//    }

    private void initItemListener() {
        itemsSetPicOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicPopupWindow.dismiss();
                switch (view.getId()) {
                    //本地照片上传
                    case R.id.btn_01:
                        TakePicUtils.StartTakePicFromLocalIntent(CreateCommunitySecondActivity.this, LOCAL_PHOTO);
                        break;
                    //拍照
                    case R.id.btn_02:
                        checkTakePhotoPermission(new IPermissionCallBackListener() {
                            @Override
                            public void doAction() {
                                TakePicUtils.StartCameraIntent(CreateCommunitySecondActivity.this, CAMBER);
                            }
                        });
                        break;
                    //取消
                    default:
                        break;
                }
            }
        };
    }

    private void initData() {
        try {
            if (getIntent().getIntExtra("type", -1) == 2) {
                resetToken = getIntent().getStringExtra("token");
                objectName = getIntent().getStringExtra("school");
                mgrMobile = getIntent().getStringExtra("phone");
                mgrName = getIntent().getStringExtra("person");
                objectType = getIntent().getIntExtra("type", 2);
                Log.e(TAG, "学校数据为--" + "-" + objectName + "-" + mgrMobile + "-" + mgrName);
            } else if (getIntent().getIntExtra("type", -1) == 3) {
                resetToken = getIntent().getStringExtra("token");
                objectName = getIntent().getStringExtra("school");
                mgrMobile = getIntent().getStringExtra("phone");
                mgrName = getIntent().getStringExtra("person");
                objectCode = getIntent().getStringExtra("business");
                objectType = getIntent().getIntExtra("type", -1);
                Log.e(TAG, "培训机构数据为--" + "-" + objectName + "-" + mgrMobile + "-" + mgrName + "-" + objectCode);
            } else {
                resetToken = getIntent().getStringExtra("token");
                mgrMobile = getIntent().getStringExtra("phone");
                mgrName = getIntent().getStringExtra("person");
                objectType = getIntent().getIntExtra("type", -1);
                Log.e(TAG, "个人数据为------>>>>>>>" + "--" + mgrMobile + "--" + mgrName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initListeners();
    }

    /**
     * 监听事件
     */
    private void initListeners() {
        //返回
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCommunitySecondActivity.this.finish();
            }
        });
        //选择照片
        communityCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
        //社群名字
        communityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                groupName = communityName.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //社群标签
        communitySelectTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupFristCategoryActivity.start(CreateCommunitySecondActivity.this, COMMUNITY_TAG);
            }
        });
        //社群权限
        communitySelectPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommenPrivacySetting.start(CreateCommunitySecondActivity.this,communityPermission.getText().toString(), ActionTypeEnum.GROUP_, COMMUNITY_PERMISSION);
            }
        });
        //社群介绍跳转
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateHuoDongSeond.start(CreateCommunitySecondActivity.this, activityBean, 3, COMMUNITY_DESCRIPTION);
            }
        });
        //创建社群点击完成
        communityFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCommunity(objectType);
            }
        });
    }

    /**
     * 照片的选择方式
     */
    private void selectPhoto() {
        List<String> list_name = new ArrayList<>();
        list_name.add("本地图片");
        list_name.add("拍照");
        List<Integer> list_color = new ArrayList<>();
        list_color.add(Color.rgb(51, 51, 51));
        list_color.add(Color.rgb(51, 51, 51));
        takePicPopupWindow = new TopMenuPopupWindow(CreateCommunitySecondActivity.this, itemsSetPicOnClick, list_name, list_color, false);
        takePicPopupWindow.showAtLocation(findViewById(R.id.community_finish_btn), Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case COMMUNITY_TAG:
                if (data != null && resultCode == 200) {
                    tagId = data.getStringExtra("parentId");
                    childTag = data.getStringExtra("childId");
                    communityTag.setText(data.getStringExtra("parentType") + "-" + data.getStringExtra("childType"));
                    communityTag.setTextColor(getResources().getColor(R.color.text_black_h2));
                }
                break;
            case COMMUNITY_PERMISSION:
                if (data != null && resultCode == 200) {
                    communityPermission.setText(data.getStringExtra("authority"));
                    if ("公开".equals(data.getStringExtra("authority"))) {
                        isPublic = true;
                    } else {
                        isPublic = false;
                    }
                }
                break;
            case COMMUNITY_DESCRIPTION:
                if (data != null && resultCode == 200) {
                    activityBean = (CreateHuoDongBean) data.getSerializableExtra("infor");
                    communityTvDescription.setText(activityBean.getInstruction());
                    groupIntroduce = activityBean.getInstruction();
                    if (activityBean.getImages() != null && activityBean.getImages().size() > 0) {
                        for (int i = 0; i < activityBean.getImages().size(); i++) {
                            fileMap.put("instr" + i, activityBean.getImages().get(i));
                        }
                        isIntroImg = true;
                    } else {
                        isIntroImg = false;
                    }
                }
                break;
            // 如果是直接从相册获取
            case LOCAL_PHOTO:
                if (data != null && data.getData() != null) {
                    TakePicUtils.startPhotoZoom(CreateCommunitySecondActivity.this, data.getData(), LOCAL_CAMBER_CUT, true);
                }
                break;
            // 如果是调用相机拍照时
            case CAMBER:
                File temp = TakePicUtils.getTepFile();
                if (temp.exists()) {
                    TakePicUtils.startPhotoZoom(CreateCommunitySecondActivity.this, Uri.fromFile(temp), LOCAL_CAMBER_CUT, true);
                }
                break;
            // 取得裁剪后的图片
            case LOCAL_CAMBER_CUT:
                logo = TakePicUtils.setPicToViewAndGetPicPath(data, communityCamera, CreateCommunitySecondActivity.this);
                if (StringUtil.isEmpty(logo)) {
                    isLogo = false;
                } else {
                    isLogo = true;
                    fileMap.put("logo", logo);
                }
                break;
        }
    }

    /**
     * 创建社群操作。与后台交互
     *
     * @param objectType
     */
    private void createCommunity(int objectType) {
        if (tagId == null) {
            Toast.makeText(this, "请选择一级分类", Toast.LENGTH_SHORT).show();
            return;
        }

        if (childTag == null) {
            Toast.makeText(this, "请选择二级分类", Toast.LENGTH_SHORT).show();
            return;
        }

        if (groupName == null) {
            Toast.makeText(this, "社群名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (objectType == 3) {
            GroupHelper.createComunity(this, resetToken, objectType, objectName, objectCode,
                    mgrName, mgrMobile, isLogo, groupName, tagId, childTag, isPublic, groupIntroduce, isIntroImg, fileMap, callCommunityBack);
        } else if (objectType == 2) {
            GroupHelper.createComunity(this, resetToken, objectType, objectName, "",
                    mgrName, mgrMobile, isLogo, groupName, tagId, childTag, isPublic, groupIntroduce, isIntroImg, fileMap, callCommunityBack);
        } else {
            GroupHelper.createComunity(this, resetToken, objectType, mgrName, "",
                    mgrName, mgrMobile, isLogo, groupName, tagId, childTag, isPublic, groupIntroduce, isIntroImg, fileMap, callCommunityBack);
        }
        Log.e(TAG, "----------->>>>>>>>" + isLogo + "-----" + isPublic + "----" + isIntroImg);
    }

    private final HttpRequestCallBack callCommunityBack = new HttpRequestCallBack() {
        @Override
        public void onSuccess(String result) {
            FileUtils.DeleteTepFile();
            EventBus.getDefault().post(new ActivityFinishEvent(ConstantUtil.FINISH));//当创建社群成功，结束上个页面
            final GroupCreateSuccedBean bean = JSON.parseObject(result, GroupCreateSuccedBean.class);

            PersionHelper.getInstance().getUserInfo(CreateCommunitySecondActivity.this, HiCache.getInstance().getLoginUserInfo().getUserId(), new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    ClassAndGroupShareActivity.start(CreateCommunitySecondActivity.this, bean, 100);
                    finish();
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        }

        @Override
        public void onFailure(String errorMsg) {
            Log.e(TAG, "创建社群失败的原因------->>>>>" + errorMsg);
        }
    };

}
