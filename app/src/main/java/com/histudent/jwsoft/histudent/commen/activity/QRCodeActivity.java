
package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupDetailsBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.YdType;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.bean.ClassBean;
import com.histudent.jwsoft.histudent.comment2.utils.FileUtils;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看二维码界面
 */

public class QRCodeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_qrCode;
    private String objectId;
    private int type, action;
    private TextView tv_title, tv_activity_name, tv_name, tv_instr;
    private CircleImageView iv_pic;
    private GroupDetailsBean groupDetailsBean;
    private ClassBean classBean;
    private TopMenuPopupWindow menuWindow;
    private View.OnClickListener itemsOnClick;
    private ImageView iv_more;
    private String qrCodeUrl;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //二维码
                case 0:
                    String content = ((String) msg.obj);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        JSONObject object = JSON.parseObject(content);
                        if (object != null) {

                            qrCodeUrl = object.getString("qrCodeUrl");
                            if (!StringUtil.isEmpty(qrCodeUrl)) {

                                Log.e("qrCodeUrl", qrCodeUrl);

                                CommonGlideImageLoader.getInstance().displayNetImage(QRCodeActivity.this, qrCodeUrl,
                                        iv_qrCode, PhotoManager.getInstance().getDefaultPlaceholderResource());
                                sendEmptyMessage(4);
                            }
                        }
                    }
                    Log.e("QRCOdeResult", content);
                    break;

                //班级
                case 1:
                    content = ((String) msg.obj);
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        classBean = JSONObject.parseObject(content, ClassBean.class);
                        if (classBean != null) {
                            upadteUI();
                        }
                    }
                    Log.e("ClassInfor", content);
                    break;

                //社群
                case 2:
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        content = ((String) msg.obj);

                        groupDetailsBean = JSONObject.parseObject(content, GroupDetailsBean.class);
                        if (groupDetailsBean != null) {

                            upadteUI();

                        }
                    }
                    break;

                //保存二维码图片
                case 3:
                    content = ((String) msg.obj);
                    if (content.equals("success")) {
                        Toast.makeText(QRCodeActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 4:

                    showUserShadeWindow(QRCodeActivity.this, tv_title, YdType.PERSON_TWO, null);

                    break;
            }
        }
    };

    @Override
    public int setViewLayout() {
        return R.layout.activity_qrcode;
    }

    public static void start(Activity context, String objectId, int type, int action) {

        Intent intent = new Intent(context, QRCodeActivity.class);

        intent.putExtra("objectId", objectId);
        intent.putExtra("Type", type);
        intent.putExtra("Action", action);
        context.startActivityForResult(intent, 600);
    }

    @Override
    public void initView() {

        objectId = getIntent().getStringExtra("objectId");
        type = getIntent().getIntExtra("Type", -1);
        action = getIntent().getIntExtra("Action", -1);

        iv_qrCode = ((ImageView) findViewById(R.id.iv_qrcode));
        tv_title = ((TextView) findViewById(R.id.title_middle_text));
        tv_activity_name = ((TextView) findViewById(R.id.tv_activity_name));
        tv_name = ((TextView) findViewById(R.id.tv_name));
        tv_instr = ((TextView) findViewById(R.id.tv_instr));
        iv_more = ((ImageView) findViewById(R.id.title_right_image));
        iv_pic = ((CircleImageView) findViewById(R.id.iv_pic));

        iv_more.setVisibility(View.VISIBLE);
        iv_more.setImageResource(R.mipmap.mune);

        iv_more.setOnClickListener(this);
        getQRCodeImage();
        getBaseData();

        itemsOnClick = new View.OnClickListener() {

            public void onClick(View v) {
                menuWindow.dismiss();
                switch (v.getId()) {

                    //分享二维码
                    case R.id.btn_01:
//                        new SharePopWindow((BaseActivity) activity, null, 0).showAtLocation(v, Gravity.CENTER, 0, 0);

                        break;

//                    //换个样式
//                    case R.id.btn_02:
//                        break;

                    //保存到相册
                    case R.id.btn_02:

                        if (FileUtils.imageFileIsExist(qrCodeUrl)) {
                            Toast.makeText(QRCodeActivity.this, "图片已经存在！", Toast.LENGTH_LONG).show();
                        } else {
                            FileUtils.saveImageToAlbum(QRCodeActivity.this, handler, 3, qrCodeUrl, true);
                        }

                        break;

                    //取消
                    default:
                        break;
                }
            }

        };

    }

    private void upadteUI() {

        String imageUrl, activity_name, name, instr;
        activity_name = name = imageUrl = instr = "";
        switch (type) {

            //个人
            case 1:

                imageUrl = HiCache.getInstance().getLoginUserInfo().getAvatar();
                name = null;
                activity_name = HiCache.getInstance().getLoginUserInfo().getRealName();
                instr = "扫描二维码，快速加他为好友";
                if (!StringUtil.isEmpty(imageUrl)) {
                    CommonGlideImageLoader.getInstance().displayNetImage(QRCodeActivity.this, imageUrl,
                            iv_pic, ContextCompat.getDrawable(this, R.mipmap.avatar_def));
                }
                break;

            //班级
            case 2:
                imageUrl = classBean.getClassLogo();
                activity_name = classBean.getSchoolName();
                name = "班主任：" + classBean.getClassUserRealName();
                instr = "扫描班级二维码，快速加入班级";
                if (!StringUtil.isEmpty(imageUrl)) {

//                    Picasso.with(this).load(imageUrl).error(R.mipmap.default_class).into(iv_pic);
                    CommonGlideImageLoader.getInstance().displayNetImage(QRCodeActivity.this, imageUrl,
                            iv_pic, ContextCompat.getDrawable(this, R.mipmap.default_class));
                }
                break;


            //社群
            case 3:
                imageUrl = groupDetailsBean.getGroupLogo();
                activity_name = groupDetailsBean.getGroupName();
                name = "群主：" + groupDetailsBean.getGroupUserRealaName();
                instr = "扫描社群二维码，快速加入社群";
                if (!StringUtil.isEmpty(imageUrl)) {
                    CommonGlideImageLoader.getInstance().displayNetImage(QRCodeActivity.this, imageUrl,
                            iv_pic, ContextCompat.getDrawable(this, R.mipmap.default_group));
                }
                break;
        }


        tv_instr.setText(instr);
        if (name == null) {
            tv_name.setVisibility(View.GONE);
        } else {
            tv_name.setText(name);
        }
        tv_activity_name.setText(activity_name);

    }

    private void getQRCodeImage() {
        Map<String, Object> map = new HashMap<>();
        map.put("objectId", objectId);
        map.put("action", action + "");
        map.put("type", type + "");

        HiWorldCache.postHttpData(this, handler, 0, HistudentUrl.getQRCOde_url, map, HiWorldCache.Quarry, true, true);
    }

    private void getBaseData() {

        String url = "";
        Map<String, Object> map;
        switch (type) {

            case 1:

                tv_title.setText("个人二维码");
                upadteUI();

                break;

            case 2:

                tv_title.setText("班级二维码");
                url = HistudentUrl.getClassModel_url;
                map = new HashMap<>();
                map.put("classId", objectId);
                HiWorldCache.postHttpData(this, handler, 1, url, map, HiWorldCache.Quarry, false, true);

                break;

            case 3:

                tv_title.setText("社群二维码");
                url = HistudentUrl.single_group_list_url;
                map = new HashMap<>();
                map.put("groupId", objectId);
                HiWorldCache.postHttpData(this, handler, 2, url, map, HiWorldCache.Quarry, false, true);

                break;

        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:

                setResult(-5);
                this.finish();
                break;

            //更多
            case R.id.title_right_image:
            case R.id.iv_qrcode:

                showButtonWindows();
                break;
        }
    }

    private void showButtonWindows() {
        List<String> list_name = new ArrayList<>();

        list_name.add("分享二维码");
//                list_name.add("换个样式");
        list_name.add("保存到相册");
        List<Integer> list_color = new ArrayList<>();
        list_color.add(Color.rgb(51, 51, 51));
//                list_color.add(Color.rgb(51, 51, 51));
        list_color.add(Color.rgb(51, 51, 51));
        menuWindow = new TopMenuPopupWindow(this, itemsOnClick, list_name, list_color, false);
        menuWindow.showAtLocation(findViewById(R.id.iv_pic), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

}
