package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gzsll.jsbridge.WVJBWebView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SyncClassAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ComShareAcitivity;
import com.histudent.jwsoft.histudent.commen.activity.CommenPrivacySetting;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.bean.WebBlogBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.ComViewUitls;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.LogParameterModel;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 写日志下一步
 */
public class LogNextActivity extends BaseActivity {
    private SyncClassAdapter syncClassAdapter;//同步班级适配
    private MyGridView gridView;
    private ArrayList<UserClassListModel> classListModels;//同步班级集合
    private LogParameterModel paramterModel;
    private String authority = "公开";
    private TextView tv_authority;
    private List<String> file_url_compress, filePath;
    private boolean isCompress;
    private WebBlogBean blogBean;
    private RelativeLayout relative_authority;
    private LinearLayout classes_layout;
    private static WVJBWebView.WVJBResponseCallback callback;
    private IconView mAthorityImg;
    private IconView mClassImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_log_next;
    }

    public static void start(Activity activity, LogParameterModel parameterModel, int requstCode) {
        Intent intent = new Intent(activity, LogNextActivity.class);
        intent.putExtra("parameterModel", parameterModel);
        activity.startActivityForResult(intent, requstCode);
    }

    public static void start(Activity activity, LogParameterModel parameterModel, WebBlogBean blogBean, WVJBWebView.WVJBResponseCallback callback, int requstCode) {
        Intent intent = new Intent(activity, LogNextActivity.class);
        intent.putExtra("parameterModel", parameterModel);
        intent.putExtra("blogBean", blogBean);
        LogNextActivity.callback = callback;
        activity.startActivityForResult(intent, requstCode);
    }

    @Override
    public void initView() {

        paramterModel = (LogParameterModel) getIntent().getSerializableExtra("parameterModel");
        blogBean = (WebBlogBean) getIntent().getSerializableExtra("blogBean");
        gridView = (MyGridView) findViewById(R.id.gridview);
        tv_authority = ((TextView) findViewById(R.id.tv_authority));
        ((TextView) findViewById(R.id.title_middle_text)).setText("写日志");
        ((TextView) findViewById(R.id.title_right_text)).setText("发布");
        ((TextView) findViewById(R.id.title_right_text)).setTextColor(getResources().getColor(R.color.green_color));

        relative_authority = (RelativeLayout) findViewById(R.id.relative_authority);
        classes_layout = (LinearLayout) findViewById(R.id.user_sycn_class_layout);
        mAthorityImg = (IconView) findViewById(R.id.rb_authority);
        mClassImg = (IconView) findViewById(R.id.sycn_image);
        classListModels = new ArrayList<>();
        filePath = new ArrayList<>();
        file_url_compress = new ArrayList<>();
        syncClassAdapter = new SyncClassAdapter(classListModels, this);
        gridView.setAdapter(syncClassAdapter);


        if (paramterModel != null) {
            if (StringUtil.isEmpty(paramterModel.getBlogId())) {//没有日志id时，表示日志是新创建的
                //非编辑状态则获取默认班级
                getSyncClassDate();
            } else {
                //编辑草稿状态，直接设置
                updateUI();
            }

            if (paramterModel.getFileList() != null) {
                filePath.addAll(paramterModel.getFileList());
            }
        }

        if (blogBean != null) {
            relative_authority.setVisibility(blogBean.isHideUIPrivacy() ? View.GONE : View.VISIBLE);
            classes_layout.setVisibility(blogBean.isHideUISyncClass() ? View.GONE : View.VISIBLE);
        }

    }

    private void updateUI() {

        authority = DataUtils.changeNumber2QualityString(paramterModel.getPrivacyStatus());
        tv_authority.setText(authority);
        ComViewUitls.setSyncViewVisiablity(this, authority);//控制同步班级是否显示
        if (paramterModel.getClassIds() != null) {
            classListModels.addAll(paramterModel.getClassIds());
            syncClassAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //返回
            case R.id.title_left:
                setResult();
                break;


            //发布
            case R.id.title_right:
                createBlog();
                break;

            //选择同步班级
            case R.id.sycn:

                SelecteClassActivity.start(LogNextActivity.this, classListModels, "", 100);
                break;


            //选择权限
            case R.id.relative_authority:

                CommenPrivacySetting.start(this, authority, ActionTypeEnum.OWNER, 444);
                break;
        }
    }


    //返回参数用户保存到草稿
    private void setResult() {

        Intent intent = new Intent();
        //返回选择的权限
        if (!StringUtil.isEmpty(authority)) {
            intent.putExtra("privacyStatus", DataUtils.changeQualityString2Number(authority));
        }

        //范湖选择的同步班级

        if (classListModels != null) {
            intent.putExtra("classIds", classListModels);
        }

        setResult(200, intent);
        finish();
    }

    /**
     * 获取默认同步的班级信息
     */
    public void getSyncClassDate() {
        DataUtils.GetUserSyncClassList(this, classListModels, syncClassAdapter, true);
        if (classListModels != null && classListModels.size() > 0) {
            mClassImg.setTextColor(getResources().getColor(R.color.green_color));
        } else {
            mClassImg.setTextColor(getResources().getColor(R.color.icon_color));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            //同步班级数据返回
            case 100:
                if (resultCode == 200)
                    if (data != null && data.getSerializableExtra("classList") != null) {

                        List<UserClassListModel> userClassListModels = (List<UserClassListModel>) data.getSerializableExtra("classList");
                        if (userClassListModels != null && userClassListModels.size() > 0) {
                            mClassImg.setTextColor(getResources().getColor(R.color.green_color));
                        } else {
                            mClassImg.setTextColor(getResources().getColor(R.color.icon_color));
                        }
                        classListModels.clear();
                        classListModels.addAll(userClassListModels);

                        syncClassAdapter.notifyDataSetChanged();
                        Log.e("Size", classListModels.size() + "");
                    }
                break;

            //权限返回
            case 444:
                if (resultCode == 200)
                    if (data != null && !StringUtil.isEmpty(data.getStringExtra("authority"))) {
                        authority = data.getStringExtra("authority");
                        mAthorityImg.setTextColor(getResources().getColor("公开".equals(authority) ? R.color.icon_color : R.color.green_color));
                        tv_authority.setText(authority);
                        ComViewUitls.setSyncViewVisiablity(this, authority);
                    }
                break;

        }
    }

    private void createBlog() {
        showLoadingImage(this, LoadingType.FLOWER);
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (filePath != null && filePath.size() > 0) {
                    if (!isCompress) {//如果已经压缩过，就不在压缩
                        file_url_compress.addAll(ImageUtils.comPressBitmaps(LogNextActivity.this, filePath, 80));
                    } else {
                        file_url_compress.addAll(filePath);
                    }
                }

                AddressInforBean inforBean = HiWorldCache.getUserLocationInfor();
                Map<String, Object> map = new TreeMap<>();
                map.put("title", paramterModel.getTitle());

                if (DataUtils.changeQualityString2Number(authority) != 2) {
                    map.put("classIds", DataUtils.getSyncClassIds(classListModels));
                }


                map.put("privacyStatus", DataUtils.changeQualityString2Number(authority));
                map.put("ownerId", HiCache.getInstance().getLoginUserInfo().getUserId());
                map.put("ownerType", "1");
                if (paramterModel.getAtUserList() != null && paramterModel.getAtUserList().size() > 0) {
                    map.put("atUser", DataUtils.getAtUserString(paramterModel.getAtUserList()));
                } else {
                    map.put("atUser", "");
                }


                if (inforBean == null) {//atUser
                    map.put("longitude", 0);
                    map.put("latitude", 0);
                    map.put("location", "");
                } else {
                    map.put("longitude", inforBean.getLongitude());
                    map.put("latitude", inforBean.getLatitude());
                    map.put("location", inforBean.getCity() + inforBean.getAreaStr());
                }
                map.put("content", paramterModel.getContent());//isDraft

                map.put("isDraft", false + "");//isDraft

                if (LogNextActivity.callback == null) {
                    HiStudentHttpUtils.postImageByOKHttp(LogNextActivity.this, file_url_compress, map, HistudentUrl.createBlog, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {

                            ImageUtils.deleteCompressFile();

                            MyShareBean bean = JSON.parseObject(result, MyShareBean.class);
                            if (bean != null) {
                                HTWebActivity.start(LogNextActivity.this, bean.getShareUrl());
                            }
                            setResult(400);
                            finish();

                        }

                        @Override
                        public void onFailure(String errorMsg) {
                            isCompress = true;
                        }
                    });
                } else {
                    map.put("appExtType", blogBean.getAppType());
                    HiStudentHttpUtils.postImageByOKHttp(LogNextActivity.this, file_url_compress, map, HistudentUrl.createCommonBlog, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {

                            MyShareBean shareBean = JSON.parseObject(result, MyShareBean.class);
                            ComShareAcitivity.start(LogNextActivity.this, shareBean, callback);
                            setResult(101);
                            finish();

                        }

                        @Override
                        public void onFailure(String errorMsg) {
                            setResult(101);
                            finish();
                            isCompress = true;
                        }
                    });
                }

            }
        }).start();
    }

    //编辑草稿完成，重新发送后，草稿删除
    private void deleteDraft(String logId) {


        if (!StringUtil.isEmpty(logId)) {
            DataUtils.deleteDraft(this, logId, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    setResult(200);
                    finish();

                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        }
    }
}
