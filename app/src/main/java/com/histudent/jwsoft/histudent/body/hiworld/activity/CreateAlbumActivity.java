
package com.histudent.jwsoft.histudent.body.hiworld.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommenPrivacySetting;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.ListenerUtils;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.AuthorityEnum;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * 创建相册
 */

public class CreateAlbumActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_file_name, et_file_des;
    private TextView tv_save, tv_title;
    private AuthorityEnum authority;
    private String albumName, albumDescription;
    private String content;
    private boolean isEditeAlbum;
    private static AlbumInfoModel model;
    private View line;
    private String id;
    private ActionTypeEnum typeEnum;
    private TextView tv_quality;
    private TextView tv_name_count, tv_instr_count;
    private int ownerType;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //处理请求创建相册结果
                case 0:

                    content = (String) msg.obj;
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        JSONObject object = JSON.parseObject(content);
                        if (object != null) {
                            ReminderHelper.getIntentce().ToastShow_with_image(CreateAlbumActivity.this,
                                    "创建相册成功", R.string.icon_check);
                            setResult(200);
                            CreateAlbumActivity.this.finish();


                        }
                    }
                    Log.e("CreatePhotoFileActivity", content);
                    break;


                //编辑相册时，提交修改后的相册信息
                case 1:

                    content = (String) msg.obj;
                    if (msg.arg1 == HiWorldCache.SUCCESS) {
                        JSONObject object = JSON.parseObject(content);
                        if (object != null) {
                            isEditeAlbum = false;
                            model = null;
                            ReminderHelper.getIntentce().ToastShow_with_image(CreateAlbumActivity.this,
                                    "修改相册成功", R.string.icon_check);
                            setResult(200);
                            CreateAlbumActivity.this.finish();

                        }
                    }
                    Log.e("AlbumInfor", content);

                    break;
            }
        }
    };

    @Override
    public int setViewLayout() {
        return R.layout.activity_create_photo_file;
    }

    /**
     * 创建新相册的入口
     *
     * @param activty
     * @param typeEnum    相册的类型
     * @param id          相册拥有者的id
     * @param requestCode
     */

    public static void start(Activity activty, ActionTypeEnum typeEnum, String id, int requestCode) {

        Intent intent = new Intent(activty, CreateAlbumActivity.class);
        intent.putExtra("typeEnum", typeEnum);
        intent.putExtra("id", id);
        activty.startActivityForResult(intent, requestCode);

    }

    /**
     * 创建新相册的入口(返回码固定)
     *
     * @param activty
     * @param typeEnum 相册的类型
     * @param id       相册拥有者的id
     */
    public static void start(Activity activty, ActionTypeEnum typeEnum, String id) {

        Intent intent = new Intent(activty, CreateAlbumActivity.class);
        intent.putExtra("typeEnum", typeEnum);
        intent.putExtra("id", id);
        activty.startActivityForResult(intent, 500);

    }

    /**
     * 编辑相册入口
     *
     * @param activty
     * @param model    相册信息类
     * @param typeEnum 相册的类型（类型不同，相册的权限控制不同）
     * @param id       相册拥有者的id
     */


    public static void startEditeAlbum(Activity activty, AlbumInfoModel model, ActionTypeEnum typeEnum, String id) {

        CreateAlbumActivity.model = model;
        Intent intent = new Intent(activty, CreateAlbumActivity.class);
        intent.putExtra("typeEnum", typeEnum);
        intent.putExtra("Edite", true);
        intent.putExtra("id", id);
        activty.startActivityForResult(intent, 600);
    }

    @Override
    public void initView() {

        typeEnum = ((ActionTypeEnum) getIntent().getSerializableExtra("typeEnum"));
        id = getIntent().getStringExtra("id");

        initKey();

        line = findViewById(R.id.line);

        et_file_name = ((EditText) findViewById(R.id.et_file_name));
        et_file_des = ((EditText) findViewById(R.id.et_file_des));
        tv_save = ((TextView) findViewById(R.id.title_right_text));
        tv_title = ((TextView) findViewById(R.id.title_middle_text));
        tv_quality = ((TextView) findViewById(R.id.tv_authority));
        tv_instr_count = ((TextView) findViewById(R.id.tv_num));
        tv_name_count = ((TextView) findViewById(R.id.file_name_num));
        ((TextView) findViewById(R.id.title_left_text)).setText("取消");
        findViewById(R.id.title_left_image).setVisibility(View.GONE);

        tv_save.setText("保存");
        tv_title.setText("新建相册");

        initUserType();
        setTextCountListener();


        isEditeAlbum = getIntent().getBooleanExtra("Edite", false);
        if (isEditeAlbum) {
            updateUI();
        }

    }

    private void initUserType() {

        ownerType = typeEnum == ActionTypeEnum.OWNER ? 1 : typeEnum == ActionTypeEnum.CLASS ? 2 : 3;
    }

    private void setTextCountListener() {

        changTextColor();

        ListenerUtils.setTextChangeListener(et_file_name, tv_name_count);
        ListenerUtils.setTextChangeListener(et_file_des, tv_instr_count);

        et_file_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                changTextColor();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initKey() {

        ownerType = typeEnum == ActionTypeEnum.CLASS ? 2 : typeEnum == ActionTypeEnum.GROUP ? 3 : 1;
    }

    //编辑相册信息时，UI界面的设置
    private void updateUI() {
        tv_title.setText("修改相册信息");
        et_file_des.setText(model.getAlbumDescription());
        et_file_name.setText(model.getAlbumName());
        if (!StringUtil.isEmpty(model.getAlbumName()))
            tv_name_count.setText(model.getAlbumName().length() + "/" + 20);
        if (!StringUtil.isEmpty(model.getAlbumDescription()))
            tv_instr_count.setText(model.getAlbumDescription().length() + "/" + 50);
        int status = model.getPrivacyStatus();
        tv_quality.setText(DataUtils.changeNumber2QualityString(status));

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //权限选择
            case R.id.relative_quality:

//                CommenPrivacySetting.start(this, tv_quality.getText().toString(), typeEnum == ActionTypeEnum.CLASS ? ActionTypeEnum.OWNER : typeEnum, 101);
                CommenPrivacySetting.start(this, tv_quality.getText().toString(), typeEnum, 101);

                break;

            //取消
            case R.id.title_left:
                if (isEditeAlbum) {
                    isEditeAlbum = false;
                    model = null;
                }
                this.finish();
                break;

            //保存
            case R.id.title_right:

                albumName = et_file_name.getText().toString().trim();
                albumDescription = et_file_des.getText().toString().trim();
                //判断输入的相册名是否为空 ，如果为空，不能跳转
                if (StringUtil.isEmpty(albumName)) {
                    ReminderHelper.getIntentce().ToastShow_with_image(this,
                            "相册名不能为空", R.string.icon_remind);
//                    Toast.makeText(this, "相册名不能为空！", Toast.LENGTH_LONG).show();
                } else {

                    //检查相册描述是否超过长度
                    if (albumName.length() > 20) {
                        ReminderHelper.getIntentce().ToastShow_with_image(this,
                                "相册名称超过长度", R.string.icon_remind);
//                        Toast.makeText(this, "相册名称超过长度！", Toast.LENGTH_LONG).show();
                    } else {

                        if (!StringUtil.isEmpty(albumDescription) && albumDescription.length() > 50) {
                            ReminderHelper.getIntentce().ToastShow_with_image(this,
                                    "相册描述超过长度", R.string.icon_remind);
//                            Toast.makeText(this, "相册描述超过长度！", Toast.LENGTH_LONG).show();
                        } else {
                            //不为空时向后台提交数据
                            if (isEditeAlbum) {
                                postEditeAlbumInfor();
                            } else {
                                postInfor();
                            }
                        }
                    }
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == 200) {
                if (data != null && !StringUtil.isEmpty(data.getStringExtra("authority"))) {
                    tv_quality.setText(data.getStringExtra("authority"));
                }
            }
        }
    }

    //提交用户选择的信息
    private void postInfor() {


        Map<String, Object> map = new HashMap<>();
        map.put("albumName", albumName);
        map.put("albumDescription", albumDescription);
        map.put("privacyStatus", DataUtils.changeQualityString2Number(tv_quality.getText().toString()) + "");
        map.put("ownerId", id);
        map.put("ownerType", ownerType);

        Log.e("URL", HistudentUrl.createphotofilelist_url);
        HiWorldCache.postHttpData(this,
                handler, 0, HistudentUrl.createphotofilelist_url, map, HiWorldCache.Body, false,true);

    }


    //编辑相册时，提交修改后的相册信息
    private void postEditeAlbumInfor() {

        Map<String, Object> map = new HashMap<>();
        map.put("albumId", model.getAlbumId());
        map.put("albumName", et_file_name.getText().toString().trim());
        map.put("albumDescription", et_file_des.getText().toString().trim());
        map.put("privacyStatus", DataUtils.changeQualityString2Number(tv_quality.getText().toString()) + "");
        map.put("ownerId", id);
        map.put("ownerType", ownerType);
        HiWorldCache.postHttpData(this, handler, 1, HistudentUrl.mine_edit_image_file_url, map, HiWorldCache.Body, false,true);

    }

    //改变发布的点击状态
    private void changTextColor() {
        ViewUtils.changeTitleRightClickable(this, !StringUtil.isEmpty(et_file_name.getText().toString().trim()));
    }

}
