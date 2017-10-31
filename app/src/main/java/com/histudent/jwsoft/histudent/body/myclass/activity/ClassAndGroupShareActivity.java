package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupCreateSuccedBean;
import com.histudent.jwsoft.histudent.body.myclass.bean.CreatClassSuccedBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ShareUtils;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/5/19.
 * 创建社群和班级成功后的分享页面
 */

public class ClassAndGroupShareActivity extends BaseActivity {

    private TextView class_name, class_code, share_tip, share_type, title, share_code_name, share_group_marker;
    private IconView share_weixin, share_qq;
    private HiStudentHeadImageView class_logo;
    private CreatClassSuccedBean classBean;
    private GroupCreateSuccedBean groupBean;
    private int type;
    private LinearLayout class_layout, group_share_layout;
    private IconView mLeftImg;

    public static void start(Activity activity, CreatClassSuccedBean bean, int requstCode) {
        Intent intent = new Intent(activity, ClassAndGroupShareActivity.class);
        intent.putExtra("CreatClassSuccedBean", bean);
        intent.putExtra("type", 1);
        activity.startActivityForResult(intent, requstCode);
    }

    public static void start(Activity activity, GroupCreateSuccedBean bean, int requstCode) {
        Intent intent = new Intent(activity, ClassAndGroupShareActivity.class);
        intent.putExtra("GroupCreateSuccedBean", bean);
        intent.putExtra("type", 2);
        activity.startActivityForResult(intent, requstCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.fragment_thethirdcreateclass;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                setResult(200);
                finish();
                break;
        }
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            classBean = (CreatClassSuccedBean) getIntent().getSerializableExtra("CreatClassSuccedBean");
        } else {
            groupBean = (GroupCreateSuccedBean) getIntent().getSerializableExtra("GroupCreateSuccedBean");
        }
        mLeftImg = (IconView) findViewById(R.id.title_left_image);
        class_name = (TextView) findViewById(R.id.class_name);
        class_code = (TextView) findViewById(R.id.class_code);
        title = (TextView) findViewById(R.id.title_middle_text);
        share_weixin = (IconView) findViewById(R.id.share_weixin);
        share_tip = (TextView) findViewById(R.id.share_tip);
        share_type = (TextView) findViewById(R.id.share_type);
        share_code_name = (TextView) findViewById(R.id.share_code_name);
        share_qq = (IconView) findViewById(R.id.share_qq);
        class_logo = (HiStudentHeadImageView) findViewById(R.id.class_logo);
        share_group_marker = (TextView) findViewById(R.id.share_group_marker);
        class_layout = (LinearLayout) findViewById(R.id.class_layout);
        group_share_layout = (LinearLayout) findViewById(R.id.group_share_layout);

        title.setText("分享邀请");
        mLeftImg.setText(R.string.icon_close);
        if (type == 1) {
            showClassInfo(classBean);
        } else {
            showGroupInfo(groupBean);
        }
    }

    @Override
    public void doAction() {
        super.doAction();

        share_weixin.setOnClickListener((View v)->{
            if (type == 1) {
                getShareData(ClassAndGroupShareActivity.this, classBean.getCId(), ShareUtils.WEIXIN);
            } else {
                getShareData(ClassAndGroupShareActivity.this, groupBean.getGroupId(), ShareUtils.WEIXIN);
            }
        });

        share_qq.setOnClickListener((View v)->{
            if (type == 1) {
                getShareData(ClassAndGroupShareActivity.this, classBean.getCId(), ShareUtils.QQ);
            } else {
                getShareData(ClassAndGroupShareActivity.this, groupBean.getGroupId(), ShareUtils.QQ);
            }
        });
    }

    /**
     * 获取班级分享数据并予以分享
     *
     * @param activityId
     */
    private void getShareData(final BaseActivity activity, final String activityId, final int flag) {
        if (TextUtils.isEmpty(activityId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("objectId", activityId);
        map.put("shareObjectType", type == 1 ? 7 : 13);//创建社群为13

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.share_url_, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                MyShareBean shareBean = JSON.parseObject(result, MyShareBean.class);
                ShareUtils.share(activity, shareBean, flag);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    private void showClassInfo(CreatClassSuccedBean bean) {
        if (bean == null) return;
        group_share_layout.setVisibility(View.GONE);
        class_layout.setVisibility(View.VISIBLE);
        MyImageLoader.getIntent().displayNetImage(this, bean.getClassLogo(), class_logo);
        class_name.setText(bean.getClassFuName());
        class_code.setText(bean.getClassNum());
        share_tip.setText(R.string.share_class_tip);
        share_type.setText(R.string.share_class_type);
        share_code_name.setText(R.string.share_class_code);
    }

    private void showGroupInfo(GroupCreateSuccedBean bean) {
        if (bean == null) return;
        group_share_layout.setVisibility(View.VISIBLE);
        class_layout.setVisibility(View.GONE);
        share_group_marker.setText(bean.getGroupMasterName());
        MyImageLoader.getIntent().displayNetImage(this, bean.getGroupLogo(), class_logo);
        class_name.setText(bean.getGroupName());
        class_code.setText(bean.getGroupNumber());
        share_tip.setText(R.string.share_group_tip);
        share_type.setText(R.string.share_group_type);
        share_code_name.setText(R.string.share_group_code);
    }

}
